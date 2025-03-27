package ca.mcmaster.se2aa4.mazerunner;

public abstract class AlgorithmFactory {
    
    //factory abstract method to create algorithms
    public abstract Algorithms createAlgorithm();

    //helper function
    public String findPath(char[][] maze, int[] entry, int[] exit){
        Algorithms algorithm = createAlgorithm();
        return algorithm.computePath(maze, entry, exit);
    }

}
