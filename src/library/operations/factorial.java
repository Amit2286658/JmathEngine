package library.operations;

import core.interfaces.operand;
import library.scan_operation_adapter;
import library.operands.real;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class factorial extends scan_operation_adapter{

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
    public operand[] getResult() {
        return new operand[]{resultOperand};
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
            return FINISH;
        }
        return IGNORE;
    }

    @Override
    public Object getScannedObject() {
        return this;
    }
}
