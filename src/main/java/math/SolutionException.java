package math;

public class SolutionException extends Exception {
    private String msg;
    
    public SolutionException(String msg) {
        this.msg = msg;
    }
    
    public String getMsg() {
        return msg;
    }
}
