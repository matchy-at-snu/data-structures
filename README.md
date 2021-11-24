# Data Structures

![GitHub top language](https://img.shields.io/github/languages/top/matchy233/data-structures-snu?color=f89917)
[![wakatime](https://wakatime.com/badge/github/matchy233/data-structures-snu.svg)](https://wakatime.com/badge/github/matchy233/data-structures-snu)
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/matchy233/data-structures-snu/Code%20Coverage)
[![codecov](https://codecov.io/gh/matchy233/data-structures-snu/branch/main/graph/badge.svg?token=N6SG53B488)](https://codecov.io/gh/matchy233/data-structures-snu)
![GitHub last commit](https://img.shields.io/github/last-commit/matchy233/data-structures-snu)

I'm taking Data Structures this (2021 fall) semester and this is just a repository for taking notes and doing homework. The textbook used in my class is [*Data Structures & Algorithm Analysis*](https://people.cs.vt.edu/shaffer/Book/) (Java version) so the notes and practices will in general follow the book outline.

As is required by the professor all source code in `assignments` directory is written in `Java 11`. 

I made this repo public to push myself to learn new things and update it regularly :/ Suggestions are welcome but please don't mock my ignorance.

## File structure and build system

Apart from all the files necessary for `Gradle` to function, this repository has three main directories: `assignments`, `notes` and `practices`, whose names are self-explanatory: `assignments` contains Programming Assignments, `practices` consists of coding practices for myself, and `notes` is for note-taking. 

Programming Assignments and coding practices are mini `Java` projects. They are organized according to [the standard directory layout recommended by Apache Maven](http://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html). Navigate to `src/main/java` in those directories to read the implementation (if there is anyone who wants to).

I adopted `Gradle` as the build system -- this is probably using a sledgehammer to crack a nut. Navigate to the root dir of this repo, and then you can use `gradlew` to:

```shell
# Build, run or test all the mini projects
./gradlew build
./gradlew run
./gradlew test

# Or perform those actions on a specific assignment / practice
./gradlew :assignments:1:run --args="arg1 --foo bar"
./gradlew :assignments:1:test
```
