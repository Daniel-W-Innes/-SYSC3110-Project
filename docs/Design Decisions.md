Design Decisions with Rationale
===
**Milestone 2**

---

## Authors:
 1. Frank Liu
 2. Binyamin Brion
 
## Primary Differences With Regards to Milestone 1

•	The pieces are now classes instead of enums. This allows them to implement interfaces. In doing so, piece specific logic can be placed in different classes that implement that interface. Polymorphism can now be used whenever pieces are involved in the program logic to reduce code. 


•	Squares and pieces are now separated. The board stores both of those items separately. Whenever a piece needs to be used, there is no risk of changing the square. Additionally, logically it separates class instances that can be moved and those that remain static for the remainder of the game.


•	The logic of creating the game is now outside of the game controller. This is done through a GameBuilder class. Separation is provided between initialization and main game logic.

•	Builders for various classes have been removed. For example, the Board Builder. It was felt that the advantages it brought were not sufficient given the scope of this project and the reduction in code readability. 

More information can be found in the following document.

## Primary Differences With Regards to Milestone 2

* Added a graph-based solution that gives hint to the user when requested, as well as undo/redo functionality.

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

All important squares are stored in a Hashmap of type <Point, Square>. A location on the board is represented as a Point instead of a separate x and y variable to increase cohesion and make it easier to pass board locations to different parts of the program. An important square is a square with a hole or a raised square.

The same is true of Pieces. They are stored in a Hashmap of type<Point, Square>. They are stored separately than the squares so that they can be moved independent without having to change the squares. Logically, this make sense, as only pieces can be moved; the terrain of the board stays constant throughout the game.

Board handles the logic of moving the pieces as there has to be synchronization between where the board stores the location of the piece, and where the piece internally stores its location. Delegating this to the piece would involve modifying the board (more specifically, where the location of the piece) inside the piece class. This is unintuitive, as logically the board owns the piece and therefore should not be modified by it.  

This model is directly observed by the GUI. This allows the model to tell the GUI to update whenever a piece is moved. This reduces the amount of code needed to update the view compared to marshalling the change through the controller. Since there is only one GUI displayed to the user, there is also only one view. The board is never modified by the GUI.


**_Controller(s)_**

---

Game:

The Game class is the interface connecting the GUI to the Board class. It takes in the result of user input and calls the correct game logic to advance the game. The primary operations are to query the possible moves for the piece clicked and telling the view of that, and forwarding a move to the board once a user has specifed a location to move a piece to. This stops the view from having to talk directly to the model. Another operation is to handle requests about hints, undo and redo operations.

There is additional functionality not yet implemented, but will be in future milestones: serialize, and possibly others. 

**_View(s)_**

---

GUI:

The view is split into three different classes, as there are logically three different parts to the GUI- the window itself, the panel that contains all of the buttons, and the buttons themselves. Dividing the functionality into these three pieces allow for delegation to happen. For example, having the logic of how a button is painted is handled by the Tile class. This allows the BoardPanel to not worry how to paint each button- it just needs to be able to organize them.

**_Helper(s)_**

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

Square:

The Square class contains variables regarding the state of a single board square. These variables include whether it is a hole.

The terrain variables and other member functions are immutable. Having immutability, it eases the complexity of reasoning about the state of the program, and logically terrain squares is constant for the duration of the game.

Rabbit, Mushroom and Fox:

These classes calculate the possible moves for the respective piece. They isolate long and logic heavy code from the rest of the program. For example, hiding the logic of determining possible moves for the pieces from the rest of the program. These classes store their textures to be used for drawing, so that the GUI does not need to know what piece it is working with. 

Additionally, each piece stores the location of themselves on the board. This is so that when adding a piece to the board, the board does not need to check if a piece is a Fox, which covers two points- it just needs to add a piece to the where that piece says it is stored. Note that the whenever a piece is moved, its internal location has to be updated so that it can start generating valid possible moves at its new location.

Foxes are a special case. Since they are made up of two pieces, all of its moves are done relative to its head. By doing so, it removes any ambiguity around where the fox is being moved- the user clicks a square where to move the head only.   

GameBuilder:

The GameBuilder separates the logic of creating a game with the game controller. It creates a distinction between initializing the game and processing it. The only functionality provided to it is to initialize a board- nothing else. By doing so, it is quick to find where to add additional code to in order to add more levels.

Graph:

The graph creates a solution to the current state of the game, which is dependent on the board passed into the constructor of the graph. The solution is generated as a graph as it allows for a path to a solution to be found. This solution is stored as a stack as the user only needs the next hint, not an arbitrary hint.

