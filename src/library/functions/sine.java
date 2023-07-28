package library.functions;

import core.ExpressionException;
import core.interfaces.operand;
import core.interfaces.operation;
import core.interfaces.scanner;

import library.operands.real;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class sine implements scanner, operation{

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
        if (params.length > 1)
            throw new ExpressionException("invalid number of parameters to function => " 
                + this.getClass(),
                EXCEPTION_INVALID_PARAMETERS_TO_FUNCTIONS);

        switch (params[0].getIdentity()){
            case REAL -> {
                real rl = ((real)params[0]);
                double d = Math.sin(Math.toRadians(rl.value));
                rl.value = d;
                opnd = rl;
            }
        }
    }

    @Override
    public void function(operand left, operand right) {
        // empty
    }

    @Override
    public void function(operand single) {
        switch (single.getIdentity()){
            case REAL -> {
                real rl = ((real)single);
                double d = Math.sin(Math.toRadians(rl.value));
                rl.value = d;
                opnd = rl;
            }
        }
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
                if (name.equalsIgnoreCase("sin")){
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
