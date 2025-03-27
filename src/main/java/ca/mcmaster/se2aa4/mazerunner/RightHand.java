/*
 * Sameer Khichi
 * MacID: khichis student#: 400518172
 * 2AA4 - Assignment 3 - Maze Runner 
 */

// Fixed RightHand class
package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RightHand implements Algorithms{

    private final Logger righthandLogger = LogManager.getLogger();

    //Helper method to check if the move made in the algorithm is valid
    private boolean moveChecker(char[][] maze, int row, int col) {
        return row < maze.length && row >= 0 && col < maze[0].length && col >= 0 && maze[row][col] != '#';
    }

    //Function to compute a path through the maze using the right hand algorithm
    @Override
    public String computePath(char[][] maze, int[] entry, int[] exit){
        righthandLogger.info("Computing path using Right Hand Algorithm");
        righthandLogger.debug("Entry: [" + entry[0] + "," + entry[1] + "]");
        righthandLogger.debug("Exit: [" + exit[0] + "," + exit[1] + "]");

        StringBuilder buildingPath = new StringBuilder();

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
            righthandLogger.debug("Current path: " + buildingPath.toString());

            //Check if the exit is directly in front of us
            int nextRow = row + moves[directionFacing][0];
            int nextCol = col + moves[directionFacing][1];
            
            if (nextRow == exit[0] && nextCol == exit[1]){
                buildingPath.append("F");
                righthandLogger.debug("Exit is directly ahead, moving forward");
                break;
            }

            //Right hand rule: Try to turn right first
            int rightDirection = (directionFacing + 1) % 4;
            int rightRow = row + moves[rightDirection][0];
            int rightCol = col + moves[rightDirection][1];

            //Check if can turn right and move
            if (moveChecker(maze, rightRow, rightCol)){
                //Turn right and move forward
                directionFacing = rightDirection;
                row = rightRow;
                col = rightCol;
                buildingPath.append("RF");
                righthandLogger.debug("Turned right and moved to [" + row + "," + col + "]");
                continue;
            }

            //If can't turn right, try moving forward
            if (moveChecker(maze, nextRow, nextCol)){
                row = nextRow;
                col = nextCol;
                buildingPath.append("F");
                righthandLogger.debug("Moved forward to [" + row + "," + col + "]");
                continue;
            }

            //If can't move forward, try turning left
            int leftDirection = (directionFacing + 3) % 4;
            int leftRow = row + moves[leftDirection][0];
            int leftCol = col + moves[leftDirection][1];

            if (moveChecker(maze, leftRow, leftCol)){
                directionFacing = leftDirection;
                row = leftRow;
                col = leftCol;
                buildingPath.append("LF");
                righthandLogger.debug("Turned left and moved to [" + row + "," + col + "]");
                continue;
            }

            //If at a dead end, turn around and move
            directionFacing = (directionFacing + 2) % 4;
            int backRow = row + moves[directionFacing][0];
            int backCol = col + moves[directionFacing][1];
            
            if (moveChecker(maze, backRow, backCol)){
                row = backRow;
                col = backCol;
                buildingPath.append("RRF"); // Turn around (two rights) and move
                righthandLogger.debug("Turned around and moved to [" + row + "," + col + "]");
            } 
            else{
                // We're trapped! This shouldn't happen in a valid maze
                righthandLogger.error("Trapped in the maze at [" + row + "," + col + "]");
                break;
            }
        }

        String finalPath = buildingPath.toString();
        righthandLogger.debug("Final uncompressed path: " + finalPath);

        Analyzer analyzer = new Analyzer();
        
        if (analyzer.validatePath(maze, entry, exit, finalPath)){
            righthandLogger.info("Path validation successful");
            // Compress the path for output
            return analyzer.compressPath(finalPath);
        } 
        else{
            righthandLogger.error("Path validation failed! Path doesn't lead to exit.");
            // Still set a path even if validation fails
            return ("Path Computation Failed");
        }
    }
}