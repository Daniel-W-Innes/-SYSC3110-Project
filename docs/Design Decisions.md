Design Decisions with Rationale
===
**Milestone 1**

---

## Authors:
 1. Frank Liu
 2. Binyamin Brion

## Introduction
The design of the program revolves around the MVC pattern. The MVC design used resembles an archery target, in the following sense.
 
![alt text](https://github.com/Daniel-W-Innes/SYSC3110-Project/blob/Milestone1/docs/MVC.PNG)


With the design above, the link between the View and the Model is through the controller; the two never directly modify one another. This allows clear separation of the parts of the program that:
1. display the game and handle user input
2. handle the logic of the game
3. store the internal representation of the game

There are additionally helper classes that help to implement the functionality of the controller and the model classes. 

## MVC Classes and Descriptions

The classes that make up each part of the MVC pattern are described here, along with the reason for their existence.

**_Model(s)_**

---

Board:

The main model of the program is the Board, which is responsible for the state of the game. By having a central place where all of the game pieces are stored, it is easy to query the state of the game as only one place of the program has to be looked at. 
The static Builder class is used in order to adhere to the Builder Design Pattern. This reduces the code in the constructor of Board as the logic creating a board cannot be reduces into a few lines. 

All important squares are stored in a Hashmap of type <Point, Square>. A location on the board is represented as a Point instead of a separate x and y variable to increase cohesion and make it easier to pass board locations to different parts of the program. An important square is a square that either has a piece on it or is either a square with a hole or a raised square. 

Note that this model is directly observed by a TextView. This is done to reduce the amount of code in order for the TextView to have the necessary information to display to the user. The Board is never modified by the TextView however. Unlike a normal observer pattern, there is only one observer per observable object. The reason is simple- only the TextView class needs to be able to view the board. 

**_Controller(s)_**

---

Game:

The Game class is the interface connecting the TextView to the Board class. It takes in the result of user input and calls the correct game logic to advance the game. For example, determining if the user is moving a Rabbit to a different location. It also services the entire game by providing critical functionality such as creating the initial board.
There is additional functionality not yet implemented, but will be in future milestones: move, undo, serialize, and possibly others. 

**_View_**

---

TextView:

The TextView is the class that the user interacts with. When the program is launched, it is done so through TextView- this is where the main method is located, and the creation of this class starts a sequence of function calls that initialize the entire game.
Within this class is an infinite game loop that constantly takes in user input, as game is only quit after the user has decided so. Whenever user input is given, it was decided to parse the input into tokens representing what the user wanted. The first token was matched against the possible commands with the rest of the tokens being the information to execute the user command.


Each possible game command is a function in the Game class. By marking each function there with the UserCommand annotation, that command is automatically added to the list of available commands. This allows the “Help” command to have loose coupling with the list of available command as that list is not hard coded.

**_Helper_**

---

Move:

The Move class is an immutable class with two state variables: startPoint and endPoint, signaling the start point and end point of a move. While it is simple to represent a move without a class, it is believed it is better to use a class because: 
*	It is easier to store in collections (which is planned to be done in milestone 3)
*	A move object reduces the chances for errors. Consider the following use cases for an undo function with and without a Move class: 
1. Move undo() vs. 
2. Point[] undo()
In the first example, the specific points can only be accessed through well-defined functions. Returning an array, on the other hand, could lead to out of bounds errors if an incorrect index was (accidentally) specified.
* It gives cohesion to a logical unit of a move through the use of startPoint and an endPoint, thus making it logically easier to work with move related data as related information is bundled together into one single unit.

The Move class is immutable because it is believed that there is no reason more for a move to change once created. Once a moved is specified, it remains the same until it is executed, at which point it is discarded for a completely new move. The benefit of doing so is providing the program with a consistent state among function calls.

The Piece Enum:

Pieces are objects with no states. Thus enums were used decided to be used. Every possible piece that can be on the board is represented by a respective enum. 
Each piece takes up one square on the board, except for the foxes. The foxes have special enums representing the direction they face, such as FOX_PLUS_X, which means it is facing in the positive x direction. Currently there is no way to tell between a fox head and fox tail, which will be fixed in the GUI milestone.
Note that holes are not considered to be a piece, but rather a part of a state of a board 

Square:

The Square class contains variables regarding the state of a single board square. These variables include whether it has a Piece on it and if so what Piece as well as the properties of the square itself- whether it is a hole or is raised, or just a flat square.
Since pieces can move, there is a setPiece method that will update the piece on the Square. This is the only mutable method that changes the only mutable member variable representing the current Piece; however the terrain variables and other member functions are immutable. Having immutability, as mentioned earlier, eases the complexity of reasoning about the state of the program.

Rabbit and Fox:

These two classes calculate the possible moves for Rabbits and Foxes respectively. 
They isolate long and logic heavy code from the rest of the program. When it is determined that a particular piece is being moved (from the user input), such as a Rabbit is being moved, the appropriate class is used to determine if the user command can be executed according to game logic. If it can, the board is updated to reflect the changes made to the location of the Piece.   


