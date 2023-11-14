package core.interfaces;

public interface operation{
    int getPrecedence();
    int getOperationType();

    operand[] getResult();

    void function(operand left, operand right);
    void function(groupOperand left, groupOperand right);
    
    void function(operand left, groupOperand right);
    void function(groupOperand left, operand right);

    void function(operand single);
    void function(groupOperand single);
}
