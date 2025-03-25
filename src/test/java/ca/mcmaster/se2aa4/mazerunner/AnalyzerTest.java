package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

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




}
