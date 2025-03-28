/*
 * Sameer Khichi
 * MacID: khichis student#: 400518172
 * 2AA4 - Assignment 3 - Maze Runner 
 */

package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RightHand extends PathComputationTemplate{

    private final Logger righthandLogger = LogManager.getLogger();

    //initialization not required - but it would go here if required

    @Override
    protected boolean findPath(char[][] maze, int[] entry, int[] exit, StringBuilder path){
        righthandLogger.info("Now computing path using Right Hand Algorithm");

        righthandLogger.info("Computing path using Right Hand Algorithm");
        righthandLogger.debug("Entry: [" + entry[0] + "," + entry[1] + "]");
        righthandLogger.debug("Exit: [" + exit[0] + "," + exit[1] + "]");

        //Starting position
        int row = entry[0];
        int col = entry[1];
        
        //Start facing east (direction: 0=north, 1=east, 2=south, 3=west)
        int directionFacing = 1;

        //Moves to 'walk' across the maze {north, east, south, west}
        int[][] moves = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        //Prevents infinite loops
        int maxIterations = maze.length * maze[0].length * 4; 
        int iterations = 0;

        while ((row != exit[0] || col != exit[1]) && iterations < maxIterations){
            iterations++;
            righthandLogger.debug("Current position: [" + row + "," + col + "] facing: " + directionFacing);
            righthandLogger.debug("Current path: " + path.toString());

            //Check if the exit is directly in front of us
            int nextRow = row + moves[directionFacing][0];
            int nextCol = col + moves[directionFacing][1];
            
            if (nextRow == exit[0] && nextCol == exit[1]){
                path.append("F");
                righthandLogger.debug("Exit is directly ahead, moving forward");
                break;
            }

            //Right hand rule: Try to turn right first
            int rightDirection = (directionFacing + 1) % 4;
            int rightRow = row + moves[rightDirection][0];
            int rightCol = col + moves[rightDirection][1];

            //Check if can turn right and move
            if (isValidMove(maze, rightRow, rightCol)){
                //Turn right and move forward
                directionFacing = rightDirection;
                row = rightRow;
                col = rightCol;
                path.append("RF");
                righthandLogger.debug("Turned right and moved to [" + row + "," + col + "]");
                continue;
            }

            //If can't turn right, try moving forward
            if (isValidMove(maze, nextRow, nextCol)){
                row = nextRow;
                col = nextCol;
                path.append("F");
                righthandLogger.debug("Moved forward to [" + row + "," + col + "]");
                continue;
            }

            //If can't move forward, try turning left
            int leftDirection = (directionFacing + 3) % 4;
            int leftRow = row + moves[leftDirection][0];
            int leftCol = col + moves[leftDirection][1];

            if (isValidMove(maze, leftRow, leftCol)){
                directionFacing = leftDirection;
                row = leftRow;
                col = leftCol;
                path.append("LF");
                righthandLogger.debug("Turned left and moved to [" + row + "," + col + "]");
                continue;
            }

            //If at a dead end, turn around and move
            directionFacing = (directionFacing + 2) % 4;
            int backRow = row + moves[directionFacing][0];
            int backCol = col + moves[directionFacing][1];
            
            if (isValidMove(maze, backRow, backCol)){
                row = backRow;
                col = backCol;
                path.append("RRF"); //Turn around (two rights) and move
                righthandLogger.debug("Turned around and moved to [" + row + "," + col + "]");
            } 
            else{
                //currently trapped which isnt possible in a valid maze
                righthandLogger.error("Trapped in the maze at [" + row + "," + col + "]");
                break;
            }
        }

        return validateFinalPath(maze, entry, exit, path.toString());
    }
}