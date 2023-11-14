package library.functions;

import core.interfaces.groupOperand;
import core.interfaces.operand;
import library.scan_operation_adapter;
import library.operands.real;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class sumAll extends scan_operation_adapter{

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
        double sum = 0;
        for (operand op : params.getOperands()){
            sum += ((real)op).value;
        }
        opnd = params.getOperands()[0];
        ((real)opnd).value = sum;
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
                if (name.equalsIgnoreCase("sumall")){
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
