____________________________________________________________________________________________________________
Assignment TicTacToe Learner 
____________________________________________________________________________________________________________

Task, T: Design a 3D TicTacToe player in a 5x5x5 board, which can learn from its own experience.
Performance, P: Percentage of games won.
Training Exp, E: 
Hard coded Logics:
	- if a block** has 4 values only from our player and one 
blank, fill the remaining box and win the match.
	- if a block has 4 values only from opponent and one empty, fill the remaining box to stop the 
opponent from winning.

Using LMS:
	- Target function: Board -> R
	- V(b) = w0 + w1x1 + w2x2 + w3x3 + w4x4 + w5x5 + w6x6 + w7x7 + w8x8 + w9x9 + w10x10
	where:
		*x1: no of rows that contain 1 X and remaining positions empty
		*x2: no of rows that contain 1 0 and remaining positions empty
		*x3: no of rows that contain 2 X and remaining positions empty
		*x4: no of rows that contain 2 0 and remaining positions empty
		*x5: no of rows that contain 3 X and remaining positions empty
		*x6: no of rows that contain 3 0 and remaining positions empty
		*x7: no of rows that contain 4 X and remaining positions empty
		*x8: no of rows that contain 4 0 and remaining positions empty
		*x9: no of rows that contain 5 X and remaining positions empty
		*x10: no of rows that contain 5 0 and remaining positions empty
	- V(b) = 10, win
	- v(b) = 0, tie
	- v(b) = -10, lost
	
	- The weights(w0-w10) of the equation are continously updated with evrey game
** A block represent a single continous vertical, horizontal or diagonal boxes which if filled with 5 
Xs/Os will result in a win or loss

____________________________________________________________________________________________________________
Code File Description 
____________________________________________________________________________________________________________

Coordinate.java: Specifies a position on the 3D board given by its x,y,z co-ordinates
Block.java: Specifies all the co-ordinates on the board that make a block. A block can be defined as a 
horizontal, vertical or diagonal 
Blocks.java: Collection of all blocks on the board.
GameStatus.java: Class the helps in evaluating the board state. Move that result in better satuts of game 
are choosen by the algorithm.
WaknaBoysLearner.java: Code used for Learning Player
WaknaBoysTournament.java: Code for Tournament Player

____________________________________________________________________________________________________________
Setting up of code 
____________________________________________________________________________________________________________
Java Version: 1.8 
Eclipse Version: Kepler(4.3 or higher)



WaknaBoys-log.csv is used for storing the learning information of the program. Whenever the program is ran, 
it reads the file and fetches the values. The very first run starts with weights as 0.5 and the log is also
created.

**The file path is needed to create the log file. The user should provide a valid path while running the 
WaknaBoys Learning program and he should have create, write and read access.
	- File Game Status	Line No: 55 	Method: InitialWeights()
	- Path path = Paths.get("C:\\Users\\Gaurav Sharma\\Documents\\WaknaBoys_log.csv");

	- File: WaknaBoysLearner.java 	Line No: 138	Method: onMatchEnds
	- File file = new File("C:\\Users\\Gaurav Sharma\\Documents\\WaknaBoys_log.csv" );
	
If the algorithm has to start again learning against a player, a new log file shoulf be created and its path
can be maintained accordingly.

**Toutnament player doesn't need log file to run. It has the hard coded values found after learning 
against the random player.
____________________________________________________________________________________________________________
Result of Learning
____________________________________________________________________________________________________________

Total Tests: 454
Wins: 294 (64.75%)
Draw: 98 (21.5%)
Lost: 62 (13.75%)

Last hundred matches with random player:
Wins: 81
Draws: 11
Lost: 9

Result Weights:w0-w10; W[] = [0.5018343685,-0.06360978446,-0.01260107203,-1.14092291,-0.04177118024,-0.4914943318,-0.02443088538,0.2539630328,0.2937776117,0.4948968313,0.9257038312]

Please have a look at docs/index.html for more information regarding the tictactoe package.