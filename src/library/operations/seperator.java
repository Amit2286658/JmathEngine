package library.operations;

import core.interfaces.operand;
import library.scan_operation_adapter;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class seperator extends scan_operation_adapter{
    operand left, right;

    @Override
    public int getPrecedence() {
        return PRECEDENCE_LEAST - 1;
    }

    @Override
    public int getOperationType() {
        return OPERATION_TYPE_BINARY;
    }

    @Override
    public operand[] getResult() {
        return new operand[]{left, right};
    }

    @Override
    public void function(operand left, operand right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int scan(char c) {
        if (c == ',')
            return FINISH;
        
        return IGNORE;
    }

    @Override
    public Object getScannedObject() {
        return this;
    }
}
