package ca.mcmaster.se2aa4.mazerunner;

//the concrete implementation of the algorithm factory
public class RightHandAlgorithmFactory extends AlgorithmFactory{
    
    @Override
    public Algorithms createAlgorithm(){
        return new RightHand();
    }
}
