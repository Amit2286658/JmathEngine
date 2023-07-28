package core.interfaces;

public interface operand {
    int getOperandType();
    int getIdentity();

    String getString();

    boolean pushOnStack();

    void pushCompiledObjects(Object[] operands);
    Object[] getCompiledObjects();

    void pushSolvedOperands(operand[] operands);

    String displayString();
}
