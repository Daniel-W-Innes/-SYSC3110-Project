JumpIN' Java Implementation
===========================

**This repository cannot be made public due to the copyright given to the creators of JumpIN'.** 
   
**The game implementation found in this repository is created for educational purposes ONLY.** 

## Known Issues

* After exiting the game design mode, the game is not able to continue running due to
a CalssNotFoundException. This also corrupts the saved file leading to the saved level unable to be loaded. Running from the IDE works however.

## Authors

* Binyamin Brion
* Daniel Innes
* Frank Liu
* Frank Yu

## Overview

JumpIN' is a puzzle game in which there are rabbits, foxes and obstacles. 
The goal is to get all the rabbits to certain positions of the board.
However, there are rules that dictate possible movements of the rabbits and foxes,
which are further made complicated by the obstacles on the board.


For specific game rules, see:
https://www.youtube.com/watch?v=8sEoYzcmOfc&feature=youtu.be

## Implementation Phases

This repository contains an implementation of the jumpIn game using Java.
There are four phases of this program:

1. A text-based playable version of the game, with input recieved from the console.
2. A GUI version of the game, with input recieved with the mouse.
3. Features are added such as a game solver as well as the ability to undo user moves.
4. A game level builder, and save/load functionality.

More details can be found at appropriate document on cuLearn.

Currently, phase three has been implemented.

## Program Requirements

To run the game, a version of the JVM supporting Java version 13
must be installed on a machine. Suitable JVM implementations can be found at:

https://adoptopenjdk.net/

Having a compatible JVM installed, follow the below steps to start playing the game:

1. Download the submitted JAR file 
1. Navigate to the downloaded the folder containing the JAR file in the operating system terminal  
1. Enter the following command: java -jar --enable-preview SYSC3110-Project.jar

## Included Documentation

Other files that can be found in this repository:

1. UML Diagram, describing the relationship between the classes that make up the game
1. Sequence Diagram, showing how the order of events fit together to create a playable game
1. User Manual, describing how to play the game given the current implementation
1. Design Decisions, a document discussing the design decisions used in this implementation
1. JavaDocs, information on the functions implementation and usage
