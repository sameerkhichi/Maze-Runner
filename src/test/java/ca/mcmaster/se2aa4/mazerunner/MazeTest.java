package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class MazeTest {
    
    private Maze maze;

    @BeforeEach
    public void setup(){
        maze = new Maze();
    }


    //makes a maze in a temporary direct
    @Test
    public void testCreateMaze(@TempDir Path tempDir) throws IOException {
        //Create a test maze file
        File testMaze = tempDir.resolve("testMaze.txt").toFile();
        FileWriter writer = new FileWriter(testMaze);
        writer.write("####\n");
        writer.write("#  #\n");
        writer.write("####\n");
        writer.close();
        
        //Load the maze
        maze.createMaze(testMaze.getAbsolutePath());
        
        //Verify maze dimensions
        char[][] mazeArray = maze.getMaze();
        assertEquals(3, mazeArray.length); //3 rows
        assertEquals(4, mazeArray[0].length); //4 columns
        
        // Verify maze content
        assertEquals('#', mazeArray[0][0]); //Top-left corner is a wall
        assertEquals(' ', mazeArray[1][1]); //Middle is a space
        assertEquals('#', mazeArray[2][3]); //Bottom-right is a wall
    }


    //testing the accuracy of the entry and exit detection and storage
    @Test
    public void testEntryExitDetection(@TempDir Path tempDir) throws IOException {
        // Create a maze with clear entry and exit
        File testMaze = tempDir.resolve("entryExitMaze.txt").toFile();
        FileWriter writer = new FileWriter(testMaze);
        writer.write("#####\n");
        writer.write("   ##\n"); //Entry on left
        writer.write("##   \n"); //Exit on right
        writer.write("#####\n"); 
        writer.close();
        
        //Load the maze
        maze.createMaze(testMaze.getAbsolutePath());
        
        //remember indexes start at 0

        //Verify entry point
        int[] entry = maze.getEntry();
        assertNotNull(entry);
        assertEquals(1, entry[0]); //Row 1
        assertEquals(0, entry[1]); //Column 0
        
        //Verify exit point
        int[] exit = maze.getExit();
        assertNotNull(exit);
        assertEquals(2, exit[0]); //Row 2
        assertEquals(4, exit[1]); //Column 4
    }



}
