package library.operations;

import core.interfaces.operand;
import core.interfaces.operation;
import core.interfaces.scanner;

import library.operands.real;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class factorial implements scanner, operation{

    operand resultOperand;

    @Override
    public int getPrecedence() {
        return PRECEDENCE_MEDIUM + 1;
    }

    @Override
    public int getOperationType() {
        return OPERATION_TYPE_UNARY;
    }

    @Override
    public int getResultFlag() {
        return RESULT_SINGLE;
    }

    @Override
    public operand getSingleResult() {
        return resultOperand;
    }

    @Override
    public operand[] getMultipleResult() {
        return null;
    }

    @Override
    public void function(operand[] params) {
        //empty
    }

    @Override
    public void function(operand left, operand right) {
        //empty
    }

    @Override
    public void function(operand single) {
        int value = (int)((real)single).value;
        double new_value = 1;
        while(value > 0){
            new_value *= value--;
        }
        ((real)single).value = new_value;
        resultOperand = single;
    }

    @Override
    public int scan(char c) {
        if (c == '!'){
            return DONE;
        }
        return IGNORE;
    }

    @Override
    public Object getScannedObject() {
        return this;
    }
    
}
