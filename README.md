# Choreographer

**A Manufacturing Tracker for Foxhole.**

No more fiddling with truck space. No need to do math in your head while other driver are honking at you. Focus on the factory dance, while the Choreographer helps with your moves.

## Status

Choreographer is a **Work in Progress**. Expect bugs, drastic change, half-baked UI, etc.

## Feature

- **Create Manufacturing Goal**
    - Enter your own target and see how much resource is needed.
    - Integration with [LogiHub](https://logihub.app), reading manufacturing goal for a certain stockpile.
- **Choreograph your Factory Dance**
    - Choose what queue type to add to your truck at a push of a button.
        - Queue which do not fit in the truck are remembered to be added later automatically.
    - View exactly how much resource is needed to set queues.
    - Confirm that queue has been set also at a push of a button.
    - Track what have been set (and assumed collected) in a dance.
    - Information provided at a glance via an overlay.
        - Note that this overlay only work in Windowed Mode of Foxhole, by simply put the app in overlay mode on top of the game. True overlaying is not possible as it require code injection into the game, which is not allowed.
- **Manufacturing Report**
    - Get a report of what you have produced after finishing a session.

## Running

You can find the latest stable version of the app in the [Release](https://github.com/LunaciaDev/Choreographer/releases) section.

## Building from Source

### Prerequisite

Please make sure that you have the following installed:

- A desktop platform with [LibGDX](https://libgdx.com/) and its dependencies installed.

### Getting the Source Code

Clone the repository:

```bash
git clone https://github.com/LunaciaDev/Choreographer.git
cd Choreographer
```

To update the source code, run this command inside the `Choreographer` directory:

```bash
git pull
```

### Running

Please use `gradlew` which is provided to run the app:

```bash
./gradlew run lwjgl3:run
```

### Building

`gradlew` also provide build task for most platform using [Construo](https://github.com/fourlastor-alexandria/construo):

```bash
./gradlew run lwjgl3:<target>
```

where `<target>` is one of the following:

- `packageMacM1` for Mac M1
- `packageMacX64` for Mac OSX
- `packageLinuxX64` for Linux x64
- `packageWindowsX64` for Windows x64

This will create a zip file in `lwjgl3/build/construo/dist`, containing the built app.

See [LibGDX deployment](https://libgdx.com/wiki/deployment/bundling-a-jre) for more.

## Special Thanks

- **Siege Camp:** for creating Foxhole <3