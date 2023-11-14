package core.interfaces;

public interface groupOperand extends operand{
    int getLength();

    operand[] getOperands();

    void pushPostFixedObjects(Object[] objects);
    void pushEvaluatedObjects(operand[] objects);

    void pushCompiledObjects(Object[] objects);
    Object[] getCompiledObjects();

    boolean pushOnStack_whenSingleElement();
}
