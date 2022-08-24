# 15-Puzzle-Game

### Explanation
This is a project I did in the summer of 2020 to learn how to make a GUI and executable application for a simple game. For the game I chose the classic 15 puzzle and for the GUI I chose to use JavaFX. While I was working on this project, I spent a lot of time navigating the JavaFX API to look for objects and classes that I could use to make a presentable interface. I eventually realized that I could use a class from JavaFX that made a grid and inherit it into my own class to make the game board to hold the tiles of the 15 puzzle game. The game tiles themselves are also their own class that inherits a JavaFX class. Currently the program creates a window with a small menu of 2 buttons at the top and the game board in the middle. The arrow keys are used to slide tiles and a "win" screen is displayed when the puzzle is solved. 

Be warned: In the current version tiles are shuffled completely randomly, so the tiles may get shuffled into an unsolvable position as completely randomized tiles do not always result in a possible puzzle. Looking back at this project, I would definitely fix this so shuffling ensures a solvable position. Also, looking at the internal code of the GameBoard, I would combine the up, right, down, and left methods utilizing an array of directions. Overall I am proud of how this turned out, being able to make an executable program that shows up in a window.

Demo:
https://youtu.be/06M6_ggqbzg
