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
4. *Set Correct JDK*: Ensure the project uses a compatible JDK (Java 8+).  
5. *Build Project*: Select `Build > Build Project`.  
6. *Run Application*: Click the green Run button or press `Shift + F10`.  
7. *Play the Game*: The game window will open. Enjoy!  

### Dependencies:
- **Maven** for dependency management.  
- **JavaFX** for UI components.  

---

## Implemented and Working Properly

1. *Added Property Change Listener*: Removed `Observer` class and added `PropertyChangeListener` to carry out all the tasks that `Observer` class used to do.  
2. *Attached Shield to Boss*: Attached the shield image to the boss plane.  
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
15. *Added Level 4 Enemy Factory*: Handles enemy logic in Level Four.  
16. *Added Restart Window*: Displays options to restart from any level or quit the game, featuring randomized blood splatter effects.  
17. *Blood Splatter Effects*: Randomized patterns for added visual variety.  
18. *Arcade-like Font*: Implemented \"Astroz\" font for an authentic retro gaming feel.  
19. *Added Sounds*:  
    - Background music.  
    - Shooting sound effects.  
20. *Confetti in Win State*: Confetti falls from the top of the screen upon victory.  
21. *Victory Sounds*: Introduced clapping sounds to celebrate wins.  
22. *Multiple Explosions in Lose State*: Displays 10 explosions randomly across the screen when the player loses.  

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

1. **FullScreenHandler**: Resizes the stage when the game opens for better screen handling.  
      Location: com.example.demo.ui.FullScreenHandler
2. **PafPlane**: Specialized enemy plane inspired by the Pakistani Air Force (Level 3).  
      Location: com.example.demo.actors.l3enemies.PafPlane
3. **PafPlanePool**: Implements object pooling for PAF planes.  
      Location: com.example.demo.actors.l3enemies.PafPlanePool
4. **BlueJet**: Enemy plane in Level Four with vertical movement and projectile firing. 
      Location: com.example.demo.actors.l4enemies.BlueJet 
5. **GreenJet**: Similar to BlueJet but with unique behaviors.  
      Location: com.example.demo.actors.l4enemies.GreenJet
6. **WhiteJet**: Level Four enemy with random projectile firing rates. 
      Location: com.example.demo.actors.l4enemies.WhiteJet 
7. **L4EnemyFactory**: Manages enemy logic in Level Four.  

8. **MainMenu**: Handles the main menu interface with dynamic buttons.  
9. **Level Banners**: Manages GIF animations at the start of each level.  
10. **Level View**: Implements unique UI elements for Levels 3 and 4.  
11. **PauseMenuState**: Manages pause menu interactions.  
12. **RestartWindow**: Displays game-over options and visual effects.  
13. **Collision**: Handles all collision logic.  
14. **Projectile Classes**: Includes specialized projectiles for each enemy type.  
15. **PowerUpManager**: Manages activation and deactivation of power-ups.  
16. **ActorManager**: Centralizes actor management and interactions.  
17. **GameInputHandler**: Processes keyboard input for the user's plane.  
18. **ConfettiEffectManager**: Creates celebratory confetti animations.  
19. **ExplosionEffect**: Generates animated explosion effects with expanding, fading, and moving particles, enhancing the game's visual dynamics.

---

## Modified Java Classes

1. **Active Actor**: Refactored into an `actors` package.  
2. **Active Actor Destructible**: Added a `notDestroy()` method to reset destruction state and removed the unnecessary `Destructible` interface.  
3. **Boss**:  
   - Introduced shield mechanics with cooldowns.  
   - Adjusted health and added logging for better debugging.  
4. **l1EnemyPlane**:  
   - Reduced image height from 150 to 50 for better precision.  
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
10. **LevelTwo**:  
    - Fixed transition issues.  
    - Added shields and initialization for new elements.  
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
This README provides an extensive overview of the game, including implemented features, unresolved issues, and future possibilities. Follow the instructions above to enjoy the game!  

