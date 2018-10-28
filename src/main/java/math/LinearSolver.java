package math;

public interface LinearSolver {
    void setInput(Matrix system) throws SolutionException;
    Matrix solve() throws SolutionException;
    String getLog();
}
