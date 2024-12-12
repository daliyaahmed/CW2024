## Author:
Daliya Safdar Ahmed

## GitHub:
https://github.com/daliyaahmed/CW2024


## Compilation Instructions


1. *Install IntelliJ IDEA Community* if not already installed.
2. *Open Project* in IntelliJ via File > Open....
3. *Load Maven Dependencies*: IntelliJ auto-loads dependencies. If not, click the Maven tool window and refresh.
4. *Set Correct JDK*: Ensure the project uses a compatible JDK (Java 8+).
5. *Build Project*: Select Build > Build Project.
6. *Run Application*: Click the green Run button or press Shift + F10.
7. *Play the Game*: The game window will open. Enjoy!
*Dependencies*: Maven for dependencies, JavaFX for UI.


##  Implemented and Working Properly: 

1. Removed Observer class and added Property Change Listener to carry out all the tasks that Observer class used to do .
2. *Refactored the Win Image*, I changed the position of the Win Image on the screen.
3.  I attached the Shield Image to the Boss Plane.
4.  *Package Organization*: The classes were arranged into packages according to how they are related.
5. *Better Precision for the Level One*: The objects' heights were decreased to a certain height so the bullets would be hitting targets more precisely.
6.  *Implemented FullScreenHandler*: This class helps resize the stage the second the game opens, helped my game have a singular class dealing with the Screen Height and Width logic to lessen the issues that I was dealing with throughout the game.
7. *Added Main Menu *: This class helps introduce my game to what it stands for and has  cool interactive buttons that are dynamic, rather than implementing a picture as a button. There are three different buttons that lead to "Play" button:Level One, "Guide To Play": Show Guide window, "Quit": exit the game.
8.  *Refactored Gamev Over Image* : The game over image's configuration was changed to show up properly, since it was very zoomed in before. 
9.  *Added Collision class": The levelParent was carrying too many responsibilities so I removed the collision logic and added it into the new class which helped in making the code more organized.
10. *Added Pause Menu* and *Added a Pause Button*
• 
• 
• 
• 
• 



## Implemented but Not Working Properly: 
List any features that have been
implemented but are not working correctly. Explain the issues you encountered,
and if possible, the steps you took to address them.
## Features Not Implemented: 
• 


## New Java Classes:
 Enumerate any new Java classes that you introduced for the
assignment. Include a brief description of each class's purpose and its location in the
code.
## Modified Java Classes: 
List the Java classes you modified from the provided code
base. Describe the changes you made and explain why these modifications were
necessary.
##  Unexpected Problems: 
Shield Image issues causing error boxes to show on the screen, even after changing the jpg to png.
Solution: After a long time of debugging the issues, I fixed them .
Transitioning from level one to level two, once the level one was done , the errors would show up and my IDE would crash fully.
Solution: Added a flag to check if levelTransition happened to make sure that it doesn't run the same loop a thousand times.

