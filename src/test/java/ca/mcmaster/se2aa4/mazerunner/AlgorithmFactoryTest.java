package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//testing the implementation of the factory method
public class AlgorithmFactoryTest{

    @Test
    @DisplayName("RightHandAlgorithmFactory should create a RightHand algorithm")
    public void testCreateAlgorithm(){
        //arrange
        AlgorithmFactory factory = new RightHandAlgorithmFactory();
        
        //complete action
        Algorithms algorithm = factory.createAlgorithm();
        
        //check instance
        assertTrue(algorithm instanceof RightHand, "Factory should create a RightHand algorithm instance");
    }
    
    //essentailly making sure that the correct overriden method of computePath is being called
    @Test
    @DisplayName("findPath should correctly delegate to algorithm's computePath")
    public void testFindPath(){
        
        char[][] maze = {
            {'#', '#', '#', '#', '#'},
            {'#', ' ', ' ', ' ', '#'},
            {'#', '#', '#', ' ', '#'},
            {'#', 'E', ' ', ' ', 'S'},
            {'#', '#', '#', '#', '#'}
        };
        int[] entry = {3, 4};  //S position
        int[] exit = {3, 1};   //E position
        
        AlgorithmFactory factory = new RightHandAlgorithmFactory();
        
        //find the path
        String path = factory.findPath(maze, entry, exit);
        
        //making sure a path is computed
        assertNotNull(path, "Path should not be null");
        assertFalse(path.equals("Path Computation Failed"), "Path computation should not fail");
        
        //validate the path
        Analyzer analyzer = new Analyzer();
        boolean isValid = analyzer.validatePath(maze, entry, exit, analyzer.expandPath(path));
        assertTrue(isValid, "The generated path should be valid");
    }
}
