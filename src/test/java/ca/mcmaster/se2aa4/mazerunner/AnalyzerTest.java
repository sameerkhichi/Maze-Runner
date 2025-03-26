package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AnalyzerTest {
    

    private Analyzer analyzer;

    @BeforeEach
    void setup(){
        analyzer = new Analyzer();
    }

    @Test
    public void testPathCompression(){
       
        assertEquals("3F2R", analyzer.compressPath("FFFRR"));

        assertEquals("FRFL", analyzer.compressPath("FRFL"));
        
        assertEquals("5F3R4L3F", analyzer.compressPath("FFFFFRRRLLLLFFF"));

        assertEquals("4FR2F2L6FR3F2L2F2RF", analyzer.compressPath("FFFFRFFLLFFFFFFRFFFLLFFRRF"));
    }

    @Test
    public void testPathExpansion(){

        assertEquals("FFFFFRRFFFLLLFFRFF", analyzer.expandPath("5F2R3F3L2FR2F"));

        assertEquals("FRFL", analyzer.expandPath("FRFL"));

        assertEquals("FFFRR", analyzer.expandPath("3F2R"));

        assertEquals("FFF", analyzer.expandPath("3F"));

        assertEquals("RR", analyzer.expandPath("2R"));

        assertEquals("LL", analyzer.expandPath("2L"));
    }


    @Test
    public void testValidPathValidation() {
        //sample maze
        char[][] testMaze = {
            {'#', '#', '#', '#', '#'},
            {' ', ' ', ' ', ' ', '#'},
            {'#', '#', '#', ' ', '#'},
            {'#', ' ', ' ', ' ', '#'},
            {'#', ' ', '#', '#', '#'},
            {'#', ' ', ' ', ' ', ' '},
            {'#', '#', '#', '#', '#'}
        };
        
        int[] entry = {1, 0}; //Starting at (1,0)
        int[] exit = {5, 4}; //Exit at (3,4)
        
        //Testing a valid path to see if it validates it correctly
        String validPath = "FFFRFFRFFLFFLFFF";
        assertTrue(analyzer.validatePath(testMaze, entry, exit, validPath));
    }

    @Test
    public void testInvalidPathValidation() {
        //sample maze
        char[][] testMaze = {
            {'#', '#', '#', '#', '#'},
            {' ', ' ', ' ', '#', '#'},
            {'#', '#', ' ', '#', '#'},
            {'#', ' ', ' ', '#', '#'},
            {'#', ' ', '#', '#', '#'},
            {'#', ' ', ' ', ' ', ' '},
            {'#', '#', '#', '#', '#'}
        };
        
        int[] entry = {1, 0}; //Starting at (1,0)
        int[] exit = {5, 4}; //Exit at (3,4)
        
        //Testing a valid path to see if it validates it correctly
        String invalidPath = "FFRFFRFLFFRF";
        assertFalse(analyzer.validatePath(testMaze, entry, exit, invalidPath));
    }
}   