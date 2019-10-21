# User Manual
### Prepared by: Binyamin Brion
The following document describes how to play the JumpIN’ game implemented in this project.
Please refer to the README file as well as some parts here are identical to the information presented there.
There are three steps to playing the game:

 1. 
	- Install an appropriate version of the JVM onto a machine, supporting Java 13.
		 - Suitable JVM implementations can be had from directly from Oracle. However, due to recent changes, an Oracle account is required to obtain a JVM from them. Thus, to obtain a JVM implementation without having to create an account, visit [openjdk](https://adoptopenjdk.net/)
 3.  
	- Download the JAR file found in this repository.
	-  Navigate to the downloaded the folder containing the JAR file in the operating system and invoke the following command: java -jar --enable-preview SYSC3110-Project.jar	
		- The suitable JVM implementation must be globally defined (i.e. in the PATH variable on Microsoft Windows) in order for the program to automatically launch from the file explorer.
 4. 
	*  Upon first launching the game, the initial board is presented.
	*  To move a piece, enter a command in the following form: 
		 - move {initialX, initialY} {targetX, targetY}.
	* To exit, the game, simply type in exit.
	* To reset the game, simply type in reset.
	* To redraw the game, simply type in draw.
	* These commands can be reviewed with by typing in help.
	* Keep entering moves until you win the game, or get bored.
	
	- The are letter abrievations for the state of each square. The first letter describes whether the terrain is raised or has a hole in it. The second letter shows the piece that is on the square. An underscore symbolizes that the square is either flat or has no pieice on it respectively. Some possible combinations are, as an example:
	
	1. _ _ : Terrain is flat with no piece on it
	2. R _ : Terrain is raised with no piece on it
	3. H R: Terrain has a hole with a rabbit on it
	
	
	
