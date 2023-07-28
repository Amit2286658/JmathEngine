package core.interfaces;

public interface operation{
    int getPrecedence();
    int getOperationType();

    int getResultFlag();

    operand getSingleResult();
    operand[] getMultipleResult();

    void function(operand[] params);
    void function(operand left, operand right);
    void function(operand single);
}
