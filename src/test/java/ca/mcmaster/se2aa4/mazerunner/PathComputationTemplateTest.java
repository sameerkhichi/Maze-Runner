package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


//testing the implementation of the template method
public class PathComputationTemplateTest{
    
    //mock class that would act as the path computation algorithm
    private class MockPathComputation extends PathComputationTemplate{

        //checkers to see if the right processes are called
        private boolean initCalled = false;
        private boolean findPathCalled = false;
        private boolean postProcessCalled = false;
        private String mockResult = "RRRLLLFF";
        private boolean pathFound = true;
        
        //testing if the algorithm gets initialized
        @Override
        protected void initializeAlgorithm(char[][] maze, int[] entry, int[] exit) {
            initCalled = true;
        }
        
        //testing the find path method
        @Override
        protected boolean findPath(char[][] maze, int[] entry, int[] exit, StringBuilder path) {
            findPathCalled = true;
            path.append(mockResult);
            return pathFound;
        }
        
        //making sure the path gets proccessed and compressed after computation
        @Override
        protected String postProcessPath(String fullPath) {
            postProcessCalled = true;
            return "R3L3F2";  // Mock compressed result
        }
        
        //check if the right things were called
        public boolean wasInitCalled() { return initCalled; }
        public boolean wasFindPathCalled() { return findPathCalled; }
        public boolean wasPostProcessCalled() { return postProcessCalled; }
        
        public void setPathFound(boolean found) {
            this.pathFound = found;
        }
    }
    
    //basic artifacts the template would need to run
    private MockPathComputation mockTemplate;
    private char[][] simpleMaze;
    private int[] entry;
    private int[] exit;
    
    //make a new instance of the template method, then initialize a new maze
    @BeforeEach
    public void setup() {
        mockTemplate = new MockPathComputation();
        simpleMaze = new char[][] {
            {'#', '#', '#'},
            {'#', ' ', '#'},
            {'#', '#', '#'}
        };
        entry = new int[] {1, 1};
        exit = new int[] {1, 1};
    }
    
    //testing if all the methods are called in the right order based off the template
    @Test
    @DisplayName("Template method should call all steps in correct order")
    public void testTemplateMethodFlow() {
    
        String result = mockTemplate.computePath(simpleMaze, entry, exit);
        
        assertTrue(mockTemplate.wasInitCalled(), "initializeAlgorithm should be called");
        assertTrue(mockTemplate.wasFindPathCalled(), "findPath should be called");
        assertTrue(mockTemplate.wasPostProcessCalled(), "postProcessPath should be called");
        assertEquals("R3L3F2", result, "Result should be the compressed path");
    }
    

    //testing if the template can also handle if the path computation fails
    @Test
    @DisplayName("Template method should handle failed path computation")
    public void testFailedPathComputation() {
    
        mockTemplate.setPathFound(false);
    
        String result = mockTemplate.computePath(simpleMaze, entry, exit);
        
        assertEquals("Path Computation Failed", result, "Should return failure message when no path found");
        assertTrue(mockTemplate.wasInitCalled(), "initializeAlgorithm should still be called");
        assertTrue(mockTemplate.wasFindPathCalled(), "findPath should still be called");
        assertFalse(mockTemplate.wasPostProcessCalled(), "postProcessPath should not be called when no path found");
    }
}