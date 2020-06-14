# Programming-Game
Framework for a programming game

As of now, program operates on Java language and Hex-based map.
It contains:
-Map editor, for creation and edition of maps.
-Methods for saving and loading, of both maps, and after game reports as a serialized objects.
-Setup for initialization and running of player-written classes during its runtime

Constraints for current Player-written code:
-Classes for each specific player are set as static names, so don't change given class name
-Class has to extend packGame.BaseObject class, or have 4 methods, with exact same names and arguments as that class
-Class can't belong to any pack
-Only single file can be compiled for now

Example of code i ran for this, are in ./maps folder
