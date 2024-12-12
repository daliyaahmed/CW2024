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

1. *Added Propertty Change Listener*:  Removed Observer class and added Property Change Listener to carry out all the tasks that Observer class used to do .
2. *Attached Shield to Boss*: I attached the Shield Image to the Boss Plane.
3.  *Added Main Menu *: This class helps introduce my game to what it stands for and has  cool interactive buttons that are dynamic, rather than implementing a picture as a button. There are three different buttons that lead to "Play" button:Level One, "Guide To Play": Show Guide window, "Quit": exit the game.

4. *Added Collision class": The levelParent was carrying too many responsibilities so I removed the collision logic and added it into the new class which helped in making the code more organized.
5. *Added Pause Menu* and *Added a Pause Button* : The pause menu shows two options : Resume and Quit , Resume will go back to the game and continue and Quit will lead you to the Main Menu. 
6. *Added Level Banners*: The level banners display a gif animation that appears when the level starts.
7. *ResumeCountDown *: Once you click Resume button, a 3,2,1 countdown displays in the center of the screen with the neon and arcadic font which I will mention later.
8. *Added Level Three*: In which the user will have to kill 15 pafplanes to win the level and move on to the next. 
9. *Added PowerUp button*: This powerUP button was added in both Level 3 and 4 to help the user with the immense difficulty of these levels. Once the button is clicked, the user plane will be able to MAX out on speed rather than usual speed for 20 seconds.
10. *Added PafPlane Object pool*: Instead of creating new instances, I decided to apply the object pooling method for the enemies (PafPlane) in level 3. This way the storage will not go out of memory and heap space will be secured 
11. *Added Enemy Missiles for PafPlane* :This feature is incredibly favorite ui feature.
12. *Added Kill Count* : The kill count feature counts all the enemies that have been shot down. In Level Four, it is not included.
13. *Added Level 4* : In level 4, There are three different enemies and they are extremenly fast when it comes to moving directions, so often then not they usually dodge user bullets a lot which makes my level very hard to play. The user will have kill atleast 4 Blue Jets, 4 White Jets, and 4 Green Jets in a matter of only 60 seconds. Since it is very intense, user helath is increased to 10. 
14. *Added CountDown Timer*: The countdown timer works and udpates and is very cool like font and neon gold color glow. The porblem with this is when pause is clicked the countdown timer will continue and not pause, but over than that the functionality is working perfectly.
15. *Added Level 4 Enemy Factory* : It handles all the enemy logic since a lot is happening, it needed to have factory method to take care of everything.
16. *Added Restart window*: Once user wins the game, a window will show up that will give you options to go either to level one, two , three, four, or quit. It also shows a cool blood splatter on the screen. 
17. *Blood Splatter*: I did that using the shapes to display that look and made sure that the circles were randomized, so every time the Restart window displays user gets a different blood splatter pattern.
18. *Arcade-like Font*: I added Astroz font for my whole game. It amplified my whole gaming experience.
19. *Added Sounds*: I added an upbeat background sound and when user fires a projectile, I added a cool shooting sound. 
20. *Added Confetti in Win Image*: Once the winImage is displayed, the circles meaning the confetti will drop from the top of the screen.



## Implemented but Not Working Properly: 
1. Banners overlap with the bullets and planes 
2. In level four, when the pause button is pressed, the time remaining doesn't stop other than that it works. 
## Features Not Implemented: 
1. *User's Shield*: User's shield will help protect the user form the enemies' projectiles.
2. *Explosion* : When user plane and enemy plane collide, an explosion will occur between them. 
3. *Add Sound for winGame and loseGame* : After losing the game, there would have been sounds that come to celebrate.
4. * 
 


## New Java Classes:
**Implemented FullScreenHandler**: This class helps resize the stage the second the game opens, helped my game have a singular class dealing with the Screen Height and Width logic to lessen the issues that I was dealing with throughout the game.

**PafPlanes class**:
The PafPlane class represents a specialized enemy plane in Level 3 of the game, serving as an Easter egg inspired by the Pakistani Air Force (PAF). This class extends the FighterPlane class and introduces unique features and behaviors tailored for the level's gameplay dynamics.
Location: com.example.demo.actors.l3enemies.PafPlane
**PafPlanePool class**:
 Implements an object pool for managing PafPlane instances, enabling efficient reuse of these objects to optimize performance and memory usage during gameplay.
 Location:com.example.demo.actors.l3enemies.PafPlanePool
**BlueJet class**:
 Represents a level four enemy plane that moves vertically between specified bounds and fires projectiles at a predefined rate. This class includes customizable movement bounds, damage handling, and projectile firing logic tailored for level four gameplay.
 Location:com.example.demo.actors.l4enemies.BlueJet
**GreenJet class**:
 Represents a level four enemy plane that moves vertically within specified bounds and fires projectiles at a defined rate. It includes functionality for customizable movement limits, projectile firing, and damage handling tailored to enhance level four gameplay.
**WhiteJet class**:
Represents a level four enemy plane that moves vertically within defined bounds and fires projectiles at a random firing rate. It is designed to challenge players with dynamic movement and targeted projectile attacks while handling damage and state updates efficiently.
Location:com.example.demo.actors.l4enemies.WhiteJet
**L4EnemyFactory class**:
Manages the creation, spawning, and behavior of enemies for level 4 of the game, including handling collisions and win conditions. It supports spawning three enemy types (BlueJet, WhiteJet, and GreenJet), tracks enemy kills, and determines when the level is complete.
Location:
**Main Menu class**:
 The MainMenu class manages the main menu interface of the game, providing options to play, view a guide, or quit the application. It features a visually engaging design with a fullscreen background, neon-styled buttons, and dynamic resizing for different screen dimensions.



**

## Modified Java Classes: 
List the Java classes you modified from the provided code
base. Describe the changes you made and explain why these modifications were
necessary.
**FighterPlane class**:
**Boss class**:
**ActiveActorDestructible class**:
**ActiveActor class**:
*Refactored Gamev Over Image* : The game over image's configuration was changed to show up properly, since it was very zoomed in before. 
*Implemented FullScreenHandler*: This class helps resize the stage the second the game opens, helped my game have a singular class dealing with the Screen Height and Width logic to lessen the issues that I was dealing with throughout the game.
*Better Precision for the Level One*: The objects' heights were decreased to a certain height so the bullets would be hitting targets more precisely.
*Package Organization*: The classes were arranged into packages according to how they are related.
*Refactored the Win Image*, I changed the position of the Win Image on the screen.

##  Unexpected Problems: 
Shield Image issues causing error boxes to show on the screen, even after changing the jpg to png.
Solution: After a long time of debugging the issues, I fixed them .
Transitioning from level one to level two, once the level one was done , the errors would show up and my IDE would crash fully.
Solution: Added a flag to check if levelTransition happened to make sure that it doesn't run the same loop a thousand times.

