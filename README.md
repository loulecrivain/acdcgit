# acdcgit
Java utility for managing git repositories in your system.

## features
- is capable of finding git repositories from an entry point
  in your filesystem
- can read a list of repositories from a file
- can show you the status of the found repositories relative
  to their remote
- can show you the differences between the found repositories
  and their remote counterpart
- can synchronize found repositories. There is two ways you
  can do this:
  - manually: when needed, you'll be prompted for a password.
    currently, we don't have support for challenge auth with
    ssh public keys.
  - automatically: you won't be asked for a password. This
    enables you to make non-interactive scripts. Downside
    is that some operations will be impossible (typically
    the "push" command will not be available)

## how to build
You need to have maven installed in order to build this project.
Change your work dir to the acdcgit directory of this repository
and issue the following command:
```
mvn clean dependency:copy-dependencies package
```
Note that some tests require being able to reach the project's remote,
 so building will fail if you have no active Internet connection.
The tests assumes an known "clean state", so be aware
that they may fail if you commit local changes.

## how to run
After building, change your work dir to the acdcgit/target directory
and use the following command:
```
java -cp dependency/bcpg-jdk15on-1.61.jar:dependency/bcpkix-jdk15on-1.61.jar:dependency/bcprov-jdk15on-1.61.jar:dependency/JavaEWAH-1.1.6.jar:dependency/jsch-0.1.55.jar:dependency/junit-3.8.1.jar:dependency/jzlib-1.1.1.jar:dependency/org.eclipse.jgit-5.5.1.201910021850-r.jar:dependency/org.eclipse.jgit.ui-5.5.1.201910021850-r.jar:dependency/slf4j-api-1.7.2.jar:acdcgit-0.0.1-SNAPSHOT.jar fr.imt.acdcgit.App
```
Alternatively, you can use absolute paths for the jar files.
Arguments are specified after the main class part. Example:
```
java -cp dependency/bcpg-jdk15on-1.61.jar:dependency/bcpkix-jdk15on-1.61.jar:dependency/bcprov-jdk15on-1.61.jar:dependency/JavaEWAH-1.1.6.jar:dependency/jsch-0.1.55.jar:dependency/junit-3.8.1.jar:dependency/jzlib-1.1.1.jar:dependency/org.eclipse.jgit-5.5.1.201910021850-r.jar:dependency/org.eclipse.jgit.ui-5.5.1.201910021850-r.jar:dependency/slf4j-api-1.7.2.jar:acdcgit-0.0.1-SNAPSHOT.jar fr.imt.acdcgit.App list /home/user/git
```
This program is meant to work with online remotes. In the event
of a limited or inexistent connectivity, the program will fail,
as its usage offline makes little sense.
