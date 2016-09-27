# APCS-Hivolts
Harrison Frahn & Daniel Jin

Introduction:
    This program allows the player to play the game Hivolts, using the java.awt and java.swing libraries.

Specifications:
    This project fulfills all of the specifications required by the assignment, because it allows the user to play a version of Hivolts with modernized Graphics. It also gives the player feedback when they have either won or lost the game. The game allows the player to move using the QWEASDZXC keys and all gameplay fits the required specifications (for example how fences and the movement of Mhos work).

Discovered Errors:
    There are no major errors in our game, and it is 100% playable. However, we have noticed that about every 3 or 4 games, an Mho appears to move through a fence instead of dieing when it collides with the fence. We don’t know what is causing this bug, but it most likely relates to the way the Mho and Fence classes check collision. However, since this bug only happens once every couple games, it is very hard to reproduce, and we had a lot of trouble trying to figure it out. The bug does not impact the gameplay very much, however, because it only happens to one Mho every few games.

Code Overview:
    Our project has 5 classes: Game, Tile, Fence, Mho, and Player. Fence, Mho, and Player all inherit from Tile, and Fence and Player are relatively basic classes. The Mho class is similar to Fence and Player, but it also contains a method to determine where the Mho is in relation to the player. It also contains a boolean called isAlive which is used to determine whether the Mho is alive. The Tile class handles the displaying of all of its subclasses with the draw() method, which is inherited by each of its subclasses. It contains the x and y coordinates of the Tile, and also the image used when the Tile is drawn. 
    The Game class contains the majority of the code. It holds the board, which is 12 by 12 array of Tiles, and also a Player and an array of 12 Mhos. It also has 2 buttons, for quitting and starting the game, and also a String called gameState, which is used to determine the state of the game (for example won, lost, start menu, etc.). In the Game() constructor, which is called in the main() method, the buttons are set up, and also a KeyListener is set up to receive the player input.  Additionally, the init() method is called, which initializes the board, fences, and Mhos to their proper positions (randomly generated). After this, the paint() method is called, which draws to the JFrame based on the gameState, which is startMenu at the beginning of the game. Once the player clicks on the ‘start’ button, the game begins, and the player and Mhos are updated every time the player enters a valid key. Once the player wins or loses, he can choose to either quit, in which case the window is closed, or play again, in which case the game is re-randomly generated using init(), and the cycle repeats.

Major Challenges:
    The first major challenge we faced while doing this project was figuring out how to get the keyboard input. Neither of us had a lot of experience with KeyListeners, but using the Oracle Java documentation online, we were able to figure it out. The second major challenge that we faced was making the Mho AI. This was a lot tougher than we originally anticipated, because the AI is actually quite sophisticated and more complicated than one would think, with many factors besides just the player position(for example the fence positions and the position of the other Mhos). However, after a while Daniel was able to finish the AI, and there were no major challenges after that. 

Acknowledgements:
    We would like to thank online resources such as StackOverflow and the Oracle Java documentation for help in the syntax for writing our KeyListener.  We would also like to thank the following websites for the use of images for our game:
    
Player.png: http://cliparting.com/wp-content/uploads/2016/06/Happy-face-smiley-face-emotions-clip-art-images-image-7.jpg

Mho Image: http://vector.me/browse/388266/scary_face

Fence:https://www.iconfinder.com/icons/1242583/fence_garden_house_nature_outdoor_wood_yard_icon#size=128

Hivolts Title: http://homepage.cs.uiowa.edu/~jones/plato/


