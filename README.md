# Programming Game in Java
Framework for a programming game in Java

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)

## General info
The purpose was to design and create a programming computer game, based on an object-oriented programming language. This game is meant to be the basis for further development and serve educational purposes. With this in mind, a program was created based on the Java programming language , taking into account issues related to artificial intelligence.
	
## Technologies
Project is created with:
* Apache NetBeans IDE: 11.3
* JDK 13
	
## Setup and Installation
To run this project, either download the source code and compile it, or use the following link to download already compiled file.
[Release](https://github.com/SoldiersPL/Programming-Game/releases/latest) <br/>
Once you obtain compiled file, avoid putting it in protected location (like desktop), since anti-virus programs have tendencies to interrupt its work in there.
Aside from that, all you have to do is to run the downloaded executable to start the program.

## Features
As of now, program operates on Java language and Hex-based map.
It contains:
 - Map editor, for creation and edition of maps.
 - Methods for saving and loading, of both maps, and after game reports as a serialized objects.
 - Methods for saving and loading, of both maps, and after game reports as a serialized objects.
 - Setup for initialization and running of player-written classes during its runtime

### Constraints for current Player-written code
- Classes for each specific player are set as static names, so don't change given class name
- Class has to extend packGame.BaseObject class, or have 4 methods, with exact same names and arguments as that class
- Class can't belong to any pack
- Only single file can be compiled for now

Example of code i ran for this, are in [./maps folder](https://github.com/SoldiersPL/Programming-Game/tree/master/maps)
