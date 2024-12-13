# Game Development Documentation

## Author:
Daliya Safdar Ahmed

## GitHub:
[GitHub Repository](https://github.com/daliyaahmed/CW2024)

---

## Compilation Instructions

1. *Install IntelliJ IDEA Community* if not already installed.  
2. *Open Project* in IntelliJ via `File > Open...`.  
3. *Load Maven Dependencies*: IntelliJ auto-loads dependencies. If not, click the Maven tool window and refresh.  
4. *Set Correct JDK*: Ensure the project uses a compatible JDK (Java 19+).  
5. *Build Project*: Select `Build > Build Project`.  
6. *Run Application*: Click the green Run button or press `Shift + F10`.  
7. *Play the Game*: The game window will open. Enjoy!  

### Dependencies:
- **Maven** for dependency management.  
- **JavaFX** for UI components.  

---

## Implemented and Working Properly

1. *Added Property Change Listener*: Removed `Observer` class and added `PropertyChangeListener` to carry out all the tasks that `Observer` class used to do.  
2. *Attached Shield to Boss*: Attached is the shield image to the boss plane.  
3. *Added Main Menu*: Introduced a main menu with interactive buttons:  
   - `Play` button: Starts Level One.  
   - `Guide To Play`: Displays the guide window.  
   - `Quit`: Exits the game.  
4. *Added Collision Class*: Refactored collision logic from `LevelParent` into a dedicated `Collision` class, improving code organization.  
5. *Added Pause Menu and Pause Button*: Introduced a pause menu with options:  
   - `Resume`: Resumes gameplay.  
   - `Quit`: Returns to the main menu.  
6. *Added Level Banners*: Displays GIF animations at the start of each level.  
7. *Resume Countdown*: Displays a `3, 2, 1` countdown in neon and arcade-style font upon resuming the game.  
8. *Added Level Three*: Players must destroy 15 PAF planes to advance to the next level.  
9. *Added Power-Up Button*: Enables maximum user plane speed for 20 seconds in Levels 3 and 4.  
10. *Added PAF Plane Object Pool*: Implemented object pooling to manage enemy planes in Level 3, optimizing memory usage.  
11. *Added Enemy Missiles for PAF Plane*: Included missiles with enhanced UI visuals.  
12. *Added Kill Count*: Tracks and displays the number of enemies shot down (not applicable in Level 4).  
13. *Added Level Four*:  
    - Features three types of fast-moving enemies (Blue Jets, White Jets, Green Jets).  
    - Players must destroy 4 of each type within 60 seconds.  
    - Increased user health to 10 for this challenging level.  
14. *Added Countdown Timer*: Displays a neon-styled countdown timer for Level Four (does not pause).  
15. *Added Level 4 Enemy Factory*: Handles enemy logic through the factory method as one of the design patterns in Level Four.  
16. *Added Restart Window*: Displays options to restart from any level or quit the game, featuring randomized blood splatter effects.  
17. *Blood Splatter Effects*: Randomized patterns for added visual variety.  
18. *Arcade-like Font*: Implemented "Astroz" font for an authentic retro gaming feel.  
19. *Added Sounds*:  
    - Background music.  
    - Shooting sound effects.  
    - Win sound effects
20. *Confetti in Win State*: Confetti falls from the top of the screen upon victory.  Side note,the confetti was inserted through coding them in, rather than using a video for it.
21. *Victory Sounds*: Introduced celebratory sounds to celebrate wins.  
22. *Multiple Explosions in Lose State*: Displays 10 explosions randomly across the screen when the player loses.  Side note, the explosions was inserted through coding them in, rather than using a video for it.
23. *Added a Separate Input Handler**: Refactored Input Handler logic from `LevelParent` into a dedicated `GameInputHandler` class, improving code organization.


---

## Implemented but Not Working Properly

1. *Banners Overlap with Bullets and Planes*.  
2. *Countdown Timer Issue*: In Level Four, the timer continues even when the game is paused.  
3. *Lose Game Sound*: Initially caused issues with user health and was removed.  
4. *Level 3 Experimental Design*:  
   - Introduced a mini-game with bombs, power-ups, and tornadoes.  
   - Ultimately removed due to significant issues.  

---

## Features Not Implemented

1. *User's Shield*: A protective shield to block enemy projectiles.  
2. *Explosion Animation*: Planned for when user and enemy planes collide.  
3. *Tornado Effect*: Attempted but caused heap storage errors.  
4. *Player-Designed Bosses*: Allow players to design their own bosses.  
5. *Black Hole Power-Up*: Creates a black hole, sucking in enemies and projectiles.  
6. *Hidden Level Triggers*: Unlock secret levels or bosses by completing hidden objectives.  
7. *Zombie Mode*: Destroyed planes resurrect as zombie planes with erratic movements.  

---

## New Java Classes

1. **FullScreenHandler**: 
This class helps resize the stage the second the game opens, helping the game have a singular class dealing with the screen height and width logic.  
Location: `com.example.demo.ui.FullScreenHandler`
2. **PafPlane**: 
Represents a specialized enemy plane in Level 3 of the game, inspired by the Pakistani Air Force.  
Location: `com.example.demo.actors.l3enemies.PafPlane`
3. **PafPlanePool**: Implements an object pool for managing PAF plane instances, optimizing performance and memory usage.  
Location: `com.example.demo.actors.l3enemies.PafPlanePool`
4. **BlueJet**: Represents a Level Four enemy plane with vertical movement and projectile firing.  
Location: `com.example.demo.actors.l4enemies.BlueJet`
5. **GreenJet**: Similar to BlueJet, featuring vertical movement and unique projectile firing. 
Location: `com.example.demo.actors.l4enemies.GreenJet`
6. **WhiteJet**: Represents a Level Four enemy plane with random firing rates and dynamic behavior.  
Location: `com.example.demo.actors.l4enemies.WhiteJet`
7. **L4EnemyFactory**: Manages the creation and behavior of enemies for Level Four.  
Location: `com.example.demo.actors.l4enemies.L4EnemyFactory`
8. **MainMenu**: Manages the main menu interface with interactive buttons for starting, quitting, and displaying the game guide.  
Location: `com.example.demo.controller.MainMenu`
9. **LevelOneBanner**: Handles the display of the level one banner with smooth animations at the start of each level. 
Location: `com.example.demo.levels.banners.LevelOneBanner`
10. **LevelTwoBanner**: Handles the display of the level two banner with smooth animations at the start of each level. 
Location: `com.example.demo.levels.banners.LevelTwoBanner`
10. **LevelThirdBanner**: Handles the display of the level three banner with smooth animations at the start of each level. 
Location: `com.example.demo.levels.banners.LevelThreeBanner`
10. **LevelFourBanner**: Handles the display of the level four banner with smooth animations at the start of each level. 
Location: `com.example.demo.levels.banners.LevelFourBanner`
11. **Level View**: Manages UI elements and gameplay-specific features for Levels Three and Four. 
Location: `com.example.demo.levels.views.LevelViewLevelThree`
Location: `com.example.demo.levels.views.LevelViewLevelFour`
12. **PauseMenuState**: Handles the pause menu functionality, including options to resume or quit the game.  
Location: `com.example.demo.menus.PauseMenuState`
13. **RestartWindow**: Displays options to restart specific levels or quit the game with a blood splatter background effect. 
Location: `com.example.demo.menus.RestartWindow`
14. **Collision**: Handles collision detection and resolution between game entities like planes, projectiles, and enemies.  
Location: `com.example.demo.physics.Collision`
15. **Projectile Classes**: Specialized classes for BlueJet, GreenJet, WhiteJet, and PAF projectiles, each with unique behaviors.  
Location: `com.example.demo.projectiles.BlueJetProjectile`
Location: `com.example.demo.projectiles.GreenJetProjectile`
Location: `com.example.demo.projectiles.WhiteJetProjectile`
Location: `com.example.demo.projectiles.PafProjectile`
16. **PowerUpManager**: Manages the functionality and display of power-ups in the game.  
Location: `com.example.demo.ui.PowerUpManager`
17. **GameInputHandler**: Processes keyboard input to control the player's plane and fire projectiles.  
Location:  `com.example.demo.controller.GameInputHandler`
18. **ConfettiEffectManager**: Generates celebratory confetti effects during win states.  
Location: `com.example.demo.ui.ConfettiEffectManager`
19. **ExplosionEffect**: Generates animated explosion effects with expanding, fading, and moving particles, enhancing the game's visual dynamics. 
Location: `com.example.demo.ui.ExplosionEffect`

---

## Modified Java Classes

1. **Active Actor**: Refactored into an `actors` package.  
2. **Active Actor Destructible**: Added a `notDestroy()` method to reset destruction state and removed the unnecessary `Destructible` interface.  
3. **Boss**:  
   - Introduced shield mechanics with cooldowns.  
   - Adjusted health and added logging for better debugging.  
4. **l1EnemyPlane**:  
   - Reduced image height from 150 to 50 for better precision. 
   - Refactored the class's name from EnemyPlane to l1EnemyPlane. 
5. **FighterPlane**:  
   - Added a boolean flag to prevent destruction if already destroyed.  
   - Adjusted health comparison logic to fix bugs.  
6. **UserPlane**:  
   - Reduced image size for improved hitbox accuracy.  
   - Added a `PowerUpActive` boolean flag to handle power-up functionality.  
7. **Controller**:  
   - Integrated `FullScreenHandler`.  
   - Replaced the deprecated `Observer` with `PropertyChangeListener`.  
8. **Main**:  
   - Adjusted screen dimensions.  
   - Added looping background sound.  
9. **LevelParent**:  
   - Added confetti for the win state.  
   - Refactored collision logic to a new `Collision` class.
   - Refactored input handler logic to a new `GameInputHandler` class.  
10. **LevelTwo**:  
    - Fixed transition issues.  
    - Added shield and initialization for new elements.  
11. **Projectile Classes**: Adjusted image sizes and cropped unnecessary whitespace.  
12. **GameOverImage & WinImage**: Updated display positions for better visuals.  

---

## Unexpected Problems

1. *Shield Image Issues*: Error boxes appeared on the screen.  
   **Solution**: Fixed by converting image formats and debugging.  
2. *Level Transition Crashes*: IDE crashed during transitions between levels.  
   **Solution**: Added flags to prevent infinite loops.  
3. *Precision Issues in Level One*: Bullets were not hitting targets accurately.  
   **Solution**: Reduced object heights and cropped image whitespace.  

---

## Final Notes
I hope you like my game!
Enjoyyy :) 

