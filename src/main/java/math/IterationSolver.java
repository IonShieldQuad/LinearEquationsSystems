package math;

import java.util.ArrayList;
import java.util.List;

public class IterationSolver implements LinearSolver {
    private Matrix system;
    private final List<String> log = new ArrayList<>();
    
    public IterationSolver() {
        system = Matrix.makeEmptyMatrix(1, 2);
    }
    
    @Override
    public void setInput(Matrix system) throws SolutionException {
        if (system.sizeX() != system.sizeY() + 1) {
            throw new SolutionException("Invalid matrix dimensions: Must have one column more than rows");
        }
        this.system = system;
    }
    
    @Override
    public Matrix solve() throws SolutionException {
        log.clear();
        
        Matrix a = system.submatrix(0, system.sizeY(), 0, system.sizeY());
        Matrix b = system.submatrix(system.sizeY(), system.sizeX(), 0, system.sizeY());
        
        assert a.isSquare() : "Matrix A must be square";
        assert b.sizeX() == 1 : "Matrix B must have a single column";
        
        log.add("Initial coefficient matrix:\n" + a);
        log.add("Initial extras matrix:\n" + b);
    
        for (int i = 0; i < a.sizeX(); i++) {
            if (a.get(i, i) == 0) {
                throw new SolutionException("Failure: main diagonal must not contain zeros");
            }
        }
        
        Matrix alpha = a.fill((i, j) -> {
            if (i.equals(j)) {
                return 0.0;
            }
            else {
                return - a.get(i, j) / a.get(j, j);
            }
        });
        
        Matrix beta = b.fill((i, j) -> b.get(0, j) / a.get(j, j));
    
        log.add("Converted coefficient matrix:\n" + alpha);
        log.add("Converted extras matrix:\n" + beta);

        List<Double> norms = new ArrayList<>();
        Double norm;
        
        norms.add(alpha.columnNorm());
        norms.add(alpha.rowNorm());
        norms.add(alpha.abs());
        
        norms.forEach((n) -> log.add("Norm value: " + n));
        
        norm = norms.stream().reduce(norms.get(0), Math::min);
    
        log.add("Min norm value: " + norm);
        
        if (Constants.CHECK_NORMS && norm >= 1.0) {
            throw new SolutionException("Matrix norm >= 1: cannot solve");
        }
        
        Matrix x = beta;
        Matrix prevX = null;
        boolean finished = false;
        int iteration = 0;
        
        while (prevX == null || !finished) {
            
            if (iteration > 1000) {
                throw new SolutionException("Iteration limit exceeded");
            }
            
            prevX = x;
            x = alpha.multiply(prevX).add(beta);
            iteration++;
            
            log.add("Iteration " + iteration + ":");
            log.add("Result matrix:\n" + x);
            
            Matrix e = a.multiply(x).add(b.negative());
            log.add("Error matrix:\n" + e);
            
            double targetDiff = Constants.CHECK_NORMS ? (Constants.EPSILON * (1 - norm) / norm) : Constants.EPSILON;
            
            log.add("Difference: " + x.add(prevX.negative()).rowNorm());
            log.add("Target difference: " + targetDiff);
            log.add("");
            
            if (x.add(prevX.negative()).rowNorm() <= targetDiff) {
                finished = true;
            }
        }
        
        return x;
    }
    
    @Override
    public String getLog() {
        StringBuilder s = new StringBuilder();
    
        log.forEach((line) -> s.append(line).append("\n"));
        
        return s.toString();
    }
}
