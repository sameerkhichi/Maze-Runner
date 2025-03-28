package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class PathComputationTemplate implements Algorithms {
    
    private final Logger logger = LogManager.getLogger();

    public String computePath(char[][] maze, int[] entry, int[] exit){

        /*
         * this is used for special case algorithms
         * if they needed to use the data to set something up
         * that is crucial to the method it uses
         * then this method would implement that
         */
        initializeAlgorithm(maze, entry, exit);

        StringBuilder path = new StringBuilder();
        boolean pathFound = findPath(maze, entry, exit, path);

        if (pathFound){
            //if a path is found it compresses it and returns it
            return postProcessPath(path.toString());
        } 
        else{
            logger.warn("No path found");
            return "Path Computation Failed";
        }
    }

    /*
     * The methods below are to be implemented by the subclasses
     * they essentially are what let them customize the searching experience for specific strategies
     */

    //this is really needed for the right hand algorithm but is useful if an algorithm requires some sort of setup
    //to be overrided
    protected void initializeAlgorithm(char[][] maze, int[] entry, int[] exit){
        logger.debug("Initializing Searching Algorithm");
    }

    //main path finding logic
    protected abstract boolean findPath(char[][] maze, int[] entry, int[] exit, StringBuilder path);

    //default way to check for a valid move - can be changed for whatever reason required
    protected boolean isValidMove(char[][] maze, int row, int col){
        //making sure the position is in bounds and not a wall
        return row >= 0 && row < maze.length 
                && 
               col >= 0 && col < maze[0].length 
                && 
               maze[row][col] != '#';
    }

    //compresses the path after it builds one.
    protected String postProcessPath(String fullPath) {
        //compresses the path using the analyzer method
        Analyzer analyzer = new Analyzer();
        logger.info("Final uncompressed path: " + fullPath);
        return analyzer.compressPath(fullPath);
    }

    //incase validation is required seperately
    protected boolean validateFinalPath(char[][] maze, int[] entry, int[] exit, String path){
        Analyzer analyzer = new Analyzer();
        return analyzer.validatePath(maze, entry, exit, path);
    }
}