# Attach your git hooks with sbt

## Add the plugin to your build

In your `project/plugins.sbt` file:

```sbtshell
addSbtPlugin("org.make" % "git-hooks-plugin" % "1.0.0")

resolvers += Resolver.url(
  "bintray-flaroche-sbt-plugins",
  url("http://dl.bintray.com/flaroche/make-sbt-plugins/")
)(Resolver.ivyStylePatterns)
```

## Configure hooks

```sbtshell
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

```bash
sbt attach-hooks
```
