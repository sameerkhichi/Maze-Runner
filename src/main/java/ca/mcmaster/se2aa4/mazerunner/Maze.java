/*
 * Sameer Khichi
 * MacID: khichis student#: 400518172
 * 2AA4 - Assignment 3 - Maze Runner 
 */
package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Maze {
    private final Logger mazeLogger = LogManager.getLogger();

    //Private instance variables to hold maze information
    private char[][] maze;
    private int rows;
    private int cols;
    private int[] entry;
    private int[] exit;

    //Getters for all attributes of the maze object
    public char[][] getMaze(){
        return maze;
    }
    
    public int[] getEntry(){
        return entry;
    }

    public int[] getExit(){
        return exit;
    }

    //Function to create a maze
    public void createMaze(String filepath){
        try{
            mazeLogger.info("Starting Maze Runner");
            mazeLogger.info("Reading the maze from file " + filepath);
            
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line = reader.readLine(); // Reads the first line

            //In case the maze file that is passed is empty
            if (line == null) {
                reader.close();
                mazeLogger.debug("Maze file is Empty");
                throw new IllegalArgumentException("Maze does not exist");
            }
            
            //Reading the first line gives amount of columns
            cols = line.length(); 
            rows = 1; // First row (zeroth index) is already read
            
            //Figuring out how many rows there are
            while ((line = reader.readLine()) != null) {
                rows++;
            }

            //Remaking the reader to start from beginning of file 
            reader.close(); 
            reader = new BufferedReader(new FileReader(filepath));

            maze = new char[rows][cols];

            //Now store initialized maze array
            int row = 0;  
            while ((line = reader.readLine()) != null){
                //Pad uneven line lengths
                line = line + " ".repeat(Math.max(0, cols - line.length()));

                for (int col = 0; col < cols; col++) {
                    maze[row][col] = line.charAt(col);

                    //Check for and store the entry and exit points
                    if (col == 0 && line.charAt(col) == ' ') {
                        //Entry is on the leftmost edge, use actual array indices
                        entry = new int[]{row, 0};
                        mazeLogger.debug("Entry Stored at [" + row + "," + 0 + "]");
                    }
                    if (col == (cols - 1) && line.charAt(col) == ' ') {
                        //Exit is on the rightmost edge, use actual array indices
                        exit = new int[]{row, cols - 1};
                        mazeLogger.debug("Exit Stored at [" + row + "," + (cols-1) + "]");
                    }
                }
                row++;
            }
            mazeLogger.debug("Stored maze successfully");

            reader.close();
            mazeLogger.info("Maze has dimensions " + rows + " by " + cols);
            mazeLogger.info("Maze entry: " + entry[0] + "," + entry[1]);
            mazeLogger.info("Maze exit: " + exit[0] + "," + exit[1]);
        }
        catch (Exception e){
            mazeLogger.debug(e);
            mazeLogger.error("/!\\ An error has occurred /!\\");
        }
    }
}