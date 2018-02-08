# Attach your git hooks with sbt

## Add the plugin to your build

In your `project/plugins.sbt` file:

```sbtshell
addSbtPlugin("org.make" % "git-hooks-plugin" % "1.0.4")
```

## Configure hooks

```sbtshell
import org.make.GitHooks

enablePlugins(GitHooks)


gitApplyPatchHook := Some(baseDirectory.value / "hooks" / "apply-patch.sh")
gitCommitMessageHook := Some(baseDirectory.value / "hooks" / "commit-msg.sh")
gitPostUpdateHook := Some(baseDirectory.value / "hooks" / "post-update.sh")
gitPreApplyPatchHook := Some(baseDirectory.value / "hooks" / "pre-apply-patch.sh")
gitPreCommitHook := Some(baseDirectory.value / "hooks" / "pre-commit-hooks.sh")
gitPrepareCommitMessageHook := Some(baseDirectory.value / "hooks" / "prepare-commit-msg.sh")
gitPrePushHook := Some(baseDirectory.value / "hooks" / "pre-push.sh")
gitPreRebaseHook := Some(baseDirectory.value / "hooks" / "pre-rebase.sh")
gitPreReceiveHook := Some(baseDirectory.value / "hooks" / "pre-receive.sh")
gitUpdateHook := Some(baseDirectory.value / "hooks" / "update.sh")
```

## attach hooks 

Hooks are automatically attached when sbt is started. 
If a hook file changes, it will be automatically updated.
