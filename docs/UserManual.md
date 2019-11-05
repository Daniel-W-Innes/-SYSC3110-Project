# User Manual
### Prepared by: Binyamin Brion
The following document describes how to play the JumpIN’ game implemented in this project.
Please refer to the README file as well as some parts here are identical to the information presented there.
There are three steps to playing the game:

 1. 
	- Install an appropriate version of the JVM onto a machine, supporting Java 13.
		 - Suitable JVM implementations can be had from directly from Oracle. However, due to recent changes, an Oracle account is required to obtain a JVM from them. Thus, to obtain a JVM implementation without having to create an account, visit [openjdk](https://adoptopenjdk.net/)
    - Add the ––enable–preview JVM Options, this project uses enhanced switch statements from [JEP 325](https://openjdk.java.net/jeps/325) and [JEP 354](https://openjdk.java.net/jeps/354). 
 2.  
	- Download the JAR file and the resource folder found in this repository. This is done already in the submission folder, in the sub-folder "bin".
	-  Navigate to the downloaded the folder containing the JAR file in the operating system terminal and invoke the following command: java -jar --enable-preview SYSC3110-Project.jar	
	
		**Note: The resource folder must be in the same folder as the jar file!**
	
	- The suitable JVM implementation must be globally defined (i.e. in the PATH variable on Microsoft Windows) in order for the program to automatically launch from the file explorer.
		
 4. 
	*  Upon first launching the game, the initial board is presented.
	*  To move a piece, click on it to view available moves. Valid end locations of the move are highlighted
	   in yellow
	   
	   **Note: For the fox, the head must be clicked to see the list of available moves. Valid end locations are where the fox head moves to.**
	* Click a valid location to move the piece to that location 
	- To exit, the game, click the x button in the top-right corner.
	- To reset the game, click Reset Game.
	- To change levels, click change levels then type of level number.
	    - The available levels are: 1, 2, 3, 20, 60 
	- Keep entering moves until you win the game, or get bored.
