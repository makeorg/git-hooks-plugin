/*
 * Copyright (C) 2017 Make.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.make

import java.io.{FileInputStream, InputStream}
import java.nio.file.Files
import java.nio.file.attribute.PosixFilePermissions
import java.security.MessageDigest
import javax.xml.bind.annotation.adapters.HexBinaryAdapter

import sbt.complete.DefaultParsers
import sbt.{Def, _}

object GitHooks extends AutoPlugin {

  object autoImport {
    val gitHooksDirectory: SettingKey[File] = sbt.settingKey("directory with the hooks")
    val gitApplyPatchHook: SettingKey[Option[File]] = sbt.settingKey("file to use for applypatch-msg hook")
    val gitCommitMessageHook: SettingKey[Option[File]] = sbt.settingKey("file to use for commit-msg hook")
    val gitPostUpdateHook: SettingKey[Option[File]] = sbt.settingKey("file to use for post-update hook")
    val gitPreApplyPatchHook: SettingKey[Option[File]] = sbt.settingKey("file to use for pre-applypatch hook")
    val gitPreCommitHook: SettingKey[Option[File]] = sbt.settingKey("file to use for pre-commit hook")
    val gitPrepareCommitMessageHook: SettingKey[Option[File]] =
      sbt.settingKey("file to use for prepare-commit-msg hook")
    val gitPrePushHook: SettingKey[Option[File]] = sbt.settingKey("file to use for pre-push hook")
    val gitPreRebaseHook: SettingKey[Option[File]] = sbt.settingKey("file to use for pre-rebase hook")
    val gitPreReceiveHook: SettingKey[Option[File]] = sbt.settingKey("file to use for pre-receive hook")
    val gitUpdateHook: SettingKey[Option[File]] = sbt.settingKey("file to use for update hook")
  }

  import autoImport._

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    gitHooksDirectory := Keys.baseDirectory.value / ".git" / "hooks",
    gitApplyPatchHook := None,
    gitCommitMessageHook := None,
    gitPostUpdateHook := None,
    gitPreApplyPatchHook := None,
    gitPreCommitHook := None,
    gitPrepareCommitMessageHook := None,
    gitPrePushHook := None,
    gitPreRebaseHook := None,
    gitPreReceiveHook := None,
    gitUpdateHook := None
  )

  override def globalSettings: Seq[Def.Setting[_]] = {
    Keys.commands += attachHooks
  }

  private lazy val attachHooks = Command("attach-hooks")(_ => DefaultParsers.EOF) { (state: State, _) =>
    overrideHooks(state)
    state
  }

  private def overrideHooks(state: State): Unit = {
    val extracted = Project.extract(state)
    import extracted._

    val baseHookDirectory = gitHooksDirectory.in(currentRef).get(structure.data).get

    overrideFileIfNeeded(baseHookDirectory, "applypatch-msg", gitApplyPatchHook.in(currentRef).get(structure.data).get)
    overrideFileIfNeeded(baseHookDirectory, "commit-msg", gitCommitMessageHook.in(currentRef).get(structure.data).get)
    overrideFileIfNeeded(baseHookDirectory, "post-update", gitPostUpdateHook.in(currentRef).get(structure.data).get)
    overrideFileIfNeeded(
      baseHookDirectory,
      "pre-applypatch",
      gitPreApplyPatchHook.in(currentRef).get(structure.data).get
    )
    overrideFileIfNeeded(baseHookDirectory, "pre-commit", gitPreCommitHook.in(currentRef).get(structure.data).get)
    overrideFileIfNeeded(
      baseHookDirectory,
      "prepare-commit-msg",
      gitPrepareCommitMessageHook.in(currentRef).get(structure.data).get
    )
    overrideFileIfNeeded(baseHookDirectory, "pre-push", gitPrePushHook.in(currentRef).get(structure.data).get)
    overrideFileIfNeeded(baseHookDirectory, "pre-rebase", gitPreRebaseHook.in(currentRef).get(structure.data).get)
    overrideFileIfNeeded(baseHookDirectory, "pre-receive", gitPreReceiveHook.in(currentRef).get(structure.data).get)
    overrideFileIfNeeded(baseHookDirectory, "update", gitUpdateHook.in(currentRef).get(structure.data).get)
  }

  private def overrideFileIfNeeded(baseHookDirectory: File, fileName: String, hook: Option[File]): Unit = {
    val originalHook = baseHookDirectory / fileName
    if (shouldOverrideFile(originalHook, hook)) {
      hook.foreach { hookFile =>
        IO.copyFile(hookFile, originalHook)
        Files.setPosixFilePermissions(originalHook.toPath, PosixFilePermissions.fromString("rwxr-xr-x"))
      }
    }
  }

  private def shouldOverrideFile(actualHook: File, referenceHook: Option[File]): Boolean = {
    referenceHook
      .filter(_.exists())
      .exists { hookFile =>
        !actualHook.exists() || hashFile(actualHook) != hashFile(hookFile)
      }
  }

  private def hashFile(file: File): String = {
    val digest: MessageDigest = MessageDigest.getInstance("SHA-1")
    val fis: InputStream = new FileInputStream(file)
    var n: Int = 0
    val bufferSize = 8192
    val buffer: Array[Byte] = Array.ofDim[Byte](bufferSize)
    while (n != -1) {
      n = fis.read(buffer)
      if (n > 0) {
        digest.update(buffer, 0, n)
      }
    }
    new HexBinaryAdapter().marshal(digest.digest())
  }

}
