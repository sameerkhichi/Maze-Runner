/*
 * Sameer Khichi
 * MacID: khichis student#: 400518172
 * 2AA4 - Assignment 3 - Maze Runner 
 */

package ca.mcmaster.se2aa4.mazerunner;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
class that analyzes mazes given a path either from user or an algorithm.
ability to expand or compress paths for simplification or computation
*/

public class Analyzer{
    private final Logger analyzerLogger = LogManager.getLogger();

    //This was made static so it could be changed after the object was created
    private static String computedPath;

    //Setter to update the computed path attribute
    public void setComputedPath(String computedPath){
        Analyzer.computedPath = computedPath;
    }

    //Getter for the path computed by the computer
    public String getComputedPath(){
        return computedPath;
    }

    //Method that converts paths to factorized form for output
    public String compressPath(String path){
        StringBuilder factorizedPath = new StringBuilder();
        
        int pathLength = path.length();
        int iterations = 0;

        //Iterate through the path
        while (iterations < pathLength){
            char currentCharacter = path.charAt(iterations);
            int counter = 1;

            //If the next character is the same as the current then count how many
            while (((iterations + 1) < pathLength) && (path.charAt(iterations + 1) == currentCharacter)){
                counter++;
                iterations++;
            }

            //Append how many if not one and what character
            if (counter > 1){
                factorizedPath.append(counter);
            }
            factorizedPath.append(currentCharacter);
            iterations++;
        }
        analyzerLogger.debug("Factorized Path: " + factorizedPath.toString());
        return factorizedPath.toString();
    }

    // This function uses regular expression matches to convert the factorized path to a canonical one for validation
    public String expandPath(String path){
        StringBuilder fullPath = new StringBuilder();
        
        //Process factorized paths like "2R3F" -> "RRFFF"
        Pattern pattern = Pattern.compile("(\\d+)([FLR])");
        Matcher matcher = pattern.matcher(path);

        int lastIndex = 0;
        
        //Process each match
        while (matcher.find()){
            //This handles any non factorized path by adding any text before this matches to the string
            fullPath.append(path.substring(lastIndex, matcher.start()));

            //Extracting the number from the regex match
            int factorizedAmount = Integer.parseInt(matcher.group(1));
            //Getting the direction from the regex match
            char factorizedDirection = matcher.group(2).charAt(0);

            //Adding the right direction the same number of times as the value of the number before it
            fullPath.append(String.valueOf(factorizedDirection).repeat(factorizedAmount));

            lastIndex = matcher.end();
        }

        //Add any remaining characters
        fullPath.append(path.substring(lastIndex));
        analyzerLogger.debug("Expanded path: " + fullPath.toString());
        return fullPath.toString();
    }

    // Function to validate paths given to the program
    public boolean validatePath(char[][] maze, int[] entry, int[] exit, String path){
        analyzerLogger.debug("Path before validation: " + path);
        analyzerLogger.debug("Entry point: [" + entry[0] + "," + entry[1] + "]");
        analyzerLogger.debug("Exit point: [" + exit[0] + "," + exit[1] + "]");

        // Retrieving starting position from entry array
        int row = entry[0];
        int col = entry[1];

        // Direction: 0=north, 1=east, 2=south, 3=west
        // Assuming starting direction is east
        int directionFacing = 1;
        
        // Moves to 'walk' across the maze {north, east, south, west}
        int[][] moves = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        int movementCount = 0;
        while (movementCount < path.length()){
            char move = path.charAt(movementCount);
            
            analyzerLogger.debug("Processing move: " + move + " at position [" + row + "," + col + "] facing direction " + directionFacing);

            //Turn right
            if (move == 'R'){
                directionFacing = ((directionFacing + 1) % 4);
                analyzerLogger.debug("Turned right, now facing direction " + directionFacing);
            }
            //Turn left
            else if (move == 'L'){
                directionFacing = ((directionFacing + 3) % 4);
                analyzerLogger.debug("Turned left, now facing direction " + directionFacing);
            }
            //Ignore spaces
            else if (move == ' '){
                //Skip this iteration
                movementCount++;
                continue;
            }
            //Move forward
            else if (move == 'F'){
                int nextRow = row + moves[directionFacing][0];
                int nextCol = col + moves[directionFacing][1];
                
                analyzerLogger.debug("Attempting to move to [" + nextRow + "," + nextCol + "]");

                //Check if the next position is the exit
                if (nextRow == exit[0] && nextCol == exit[1]){
                    analyzerLogger.info("Reached exit successfully!");
                    return true;
                }

                //Check if we're trying to move outside the maze
                if (nextRow < 0 || nextRow >= maze.length || nextCol < 0 || nextCol >= maze[0].length){
                    //If we're at a boundary but not at the exit, the path is invalid
                    analyzerLogger.info("Path led out of bounds at position [" + row + "," + col + "]");
                    return false;
                }

                //Check if we hit a wall
                if (maze[nextRow][nextCol] == '#'){
                    analyzerLogger.info("Hit a wall at [" + nextRow + "," + nextCol + "]");
                    return false;
                }

                //Update position
                row = nextRow;
                col = nextCol;
                analyzerLogger.debug("Moved to [" + row + "," + col + "]");
            }
            else {
                analyzerLogger.debug("Illegal move: " + move);
                throw new IllegalArgumentException("Invalid move: " + move);
            }
            movementCount++;
        }
        
        //Check if we've reached the exit after processing all moves
        if (row == exit[0] && col == exit[1]){
            analyzerLogger.info("Valid path - reached exit");
            return true;
        }
        
        //If we've gone through all moves but haven't reached the exit
        analyzerLogger.info("Invalid path - didn't reach exit, ended at [" + row + "," + col + "]");
        return false;
    }
}