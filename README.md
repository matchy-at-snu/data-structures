# Data Structures

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
./gadlew test

# Or perform those actions on a specific assignment / practice
./gradlew :assignments:1:test --args="arg1 --foo bar"
./gradlew :assignments:1:test
```
