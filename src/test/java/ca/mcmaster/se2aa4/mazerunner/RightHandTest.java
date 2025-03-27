package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RightHandTest {
    
    private RightHand rightHand;

    //essentially creates a new RightHand object for each test case
    //runs before each test case
    @BeforeEach
    void setUp(){
        rightHand = new RightHand();
    }


    //test annotation marks the method as a test
    @Test
    void testComputerPath(){
        char[][] maze = {
            {'#','#','#','#','#'},
            {'#',' ',' ',' ','#'},
            {' ',' ','#',' ','#'},
            {'#','#','#',' ',' '},
            {'#','#','#','#','#'}
        };

        int[] entry = {3,0};
        int[] exit = {3,5};

        assertEquals("LFRFLFR2FR2FL2F", rightHand.computePath(maze, entry, exit), "Should keep right hand on a wall and move forward till it finds an exit");


    }

    @Test 
    void testStraightMaze(){
        char[][] maze = {
            {'#', '#', '#', '#'},
            {' ', ' ', ' ', ' '},
            {'#', '#', '#', '#'}
        };
        int[] entry = {1, 0};
        int[] exit = {1, 3};

        assertEquals("3F", rightHand.computePath(maze, entry, exit), "Should move straight to exit");
    }

    @Test
    void testStartAtExit(){
        char[][] maze = {
            {'#', '#', '#', '#'},
            {' ', ' ', ' ', ' '},
            {'#', '#', '#', '#'}
        };

        int[] entry = {1,3};
        int[] exit = {1,3};

        assertEquals("", rightHand.computePath(maze, entry, exit), "Should compute an empty path.");
    }

    @Test
    void testBlockedMaze() {
        char[][] maze = {
            {'#', '#', '#'},
            {'#', '.', '#'},
            {'#', '#', '#'}
        };
        int[] entry = {1, 1};
        int[] exit = {0, 2};

        assertEquals("Path Computation Failed", rightHand.computePath(maze, entry, exit), "Should fail when no path exists");
    }

    @Test
    public void testMazeWithDeadEnds() {
        //maze with some deadends to test backtracking 
        char[][] testMaze = {
            {'#', '#', '#', '#', '#', '#', '#'},
            {' ', ' ', ' ', '#', ' ', ' ', '#'},
            {'#', '#', ' ', '#', ' ', '#', '#'},
            {'#', ' ', ' ', ' ', ' ', ' ', '#'},
            {'#', ' ', '#', '#', '#', ' ', '#'},
            {'#', ' ', ' ', ' ', '#', ' ', '#'},
            {'#', '#', '#', '#', '#', '#', '#'}
        };
    
        int[] entry = {1, 0}; 
        int[] exit = {5, 5}; 

        Analyzer analyzer = new Analyzer();
        
        //The test passes if computedPath is not a failure message
        assertNotEquals("Path Computation Failed", rightHand.computePath(testMaze, entry, exit));
        
        //expands the path to check it
        String expandedPath = analyzer.expandPath(rightHand.computePath(testMaze, entry, exit));
        
        //validates the path that it generated
        assertTrue(analyzer.validatePath(testMaze, entry, exit, expandedPath));
    }








}
