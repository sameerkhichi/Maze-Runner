package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RightHandTest {
    
    private RightHand rightHand;

    //essentially creates a new RightHand object for each test case
    @BeforeEach
    void setUp(){
        rightHand = new RightHand();
    }


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

        rightHand.computePath(maze, entry, exit);
        assertEquals("LFRFLFR2FR2FL2F", rightHand.getComputedPath(), "Should keep right hand on a wall and move forward till it finds an exit");


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

        rightHand.computePath(maze, entry, exit);
        assertEquals("3F", rightHand.getComputedPath(), "Should move straight to exit");
    }









}
