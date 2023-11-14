package library.functions;

import core.ExpressionException;
import core.interfaces.groupOperand;
import core.interfaces.operand;
import library.scan_operation_adapter;
import library.operands.real;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class sine extends scan_operation_adapter{

    StringBuilder builder = new StringBuilder();
    boolean scanning = false;

    operand opnd;

    @Override
    public int getPrecedence() {
        return PRECEDENCE_FUNCTION;
    }

    @Override
    public int getOperationType() {
        return OPERATION_TYPE_UNARY;
    }

    @Override
    public operand[] getResult() {
        return new operand[]{
            opnd
        };
    }

    @Override
    public void function(groupOperand params) {
        if (params.getLength() > 1)
            throw new ExpressionException("invalid number of parameters to function => " 
                + this.getClass(),
                EXCEPTION_INVALID_PARAMETERS_TO_FUNCTIONS);

        switch (params.getOperands()[0].getIdentity()){
            case REAL -> {
                real rl = ((real)params.getOperands()[0]);
                double d = Math.sin(Math.toRadians(rl.value));
                rl.value = d;
                opnd = rl;
            }
        }
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
            builder.append(c);
            if (!scanning){
                scanning = true;
                return LOCK;
            }
            return CONTINUE;
        }else {
            if (scanning){
                String name = builder.toString();
                scanning = false;
                builder.setLength(0);
                if (name.equalsIgnoreCase("sin")){
                    return _RELEASE_;
                }
                return INTERRUPT;
            }
            return IGNORE;
        }
    }

    @Override
    public Object getScannedObject() {
        return this;
    }
}
