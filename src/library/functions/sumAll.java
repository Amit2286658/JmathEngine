package library.functions;

import core.interfaces.operand;
import core.interfaces.operation;
import core.interfaces.scanner;

import library.operands.real;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class sumAll implements scanner, operation{

    StringBuilder builder = new StringBuilder();
    boolean scanning = false;

    operand opnd;

    @Override
    public int getPrecedence() {
        return PRECEDENCE_FUNCTION;
    }

    @Override
    public int getOperationType() {
        return OPERATION_TYPE_FUNCTION;
    }

    @Override
    public int getResultFlag() {
        return RESULT_SINGLE;
    }

    @Override
    public operand getSingleResult() {
        return opnd;
    }

    @Override
    public operand[] getMultipleResult() {
        return null;
    }

    @Override
    public void function(operand[] params) {
        double sum = 0;
        for (operand op : params){
            sum += ((real)op).value;
        }
        opnd = params[0];
        ((real)opnd).value = sum;
    }

    @Override
    public void function(operand left, operand right) {
        // empty
    }

    @Override
    public void function(operand single) {
        // empty
    }

    @Override
    public int scan(char c) {
        if (Character.isLetter(c)){
            if (!scanning)
                scanning = true;
            builder.append(c);
            return CONTINUE;
        }else {
            if (scanning){
                String name = builder.toString();
                scanning = false;
                builder.setLength(0);
                if (name.equalsIgnoreCase("sumall")){
                    return _DONE_;
                }
                return BREAK;
            }
            return IGNORE;
        }
    }

    @Override
    public Object getScannedObject() {
        return this;
    }
}
