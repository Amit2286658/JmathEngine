package core;

public class ExpressionException extends RuntimeException {
    private int exception_stage = 0;

    public ExpressionException (String message, int step){
        super(message);
        this.exception_stage = step;
    }

    public int getStage(){
        return exception_stage;
    }

}
