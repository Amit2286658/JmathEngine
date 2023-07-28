package library.operations;

import core.interfaces.operand;
import core.interfaces.operation;
import core.interfaces.scanner;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class seperator implements scanner, operation{
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
    public int getResultFlag() {
        return RESULT_MULTIPLE;
    }

    @Override
    public operand getSingleResult() {
        return null;
    }

    @Override
    public operand[] getMultipleResult() {
        return new operand[]{left, right};
    }

    @Override
    public void function(operand[] params) {
        // empty
    }

    @Override
    public void function(operand left, operand right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void function(operand single) {
        // empty
    }

    @Override
    public int scan(char c) {
        if (c == ',')
            return DONE;
        
        return IGNORE;
    }

    @Override
    public Object getScannedObject() {
        return this;
    }
    
}
