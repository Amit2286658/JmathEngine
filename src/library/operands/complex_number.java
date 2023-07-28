package library.operands;

import core.interfaces.operand;

import static library.CONSTANTS.*;
import static core.CONSTANTS.*;

public class complex_number implements operand{

    public double real_value;
    public double iota_value;

    @Override
    public int getOperandType() {
        return OPERAND_TYPE_SINGULAR;
    }

    @Override
    public int getIdentity() {
        return COMPLEX_NUMBER;
    }

    @Override
    public String getString() {
        return (real_value != 0 ? real_value % 1 != 0 ? real_value : 
                    (real_value + "").split("\\.")[0] : "")
            + ((iota_value > 0 && real_value != 0) ? " + " : "")
            + (iota_value != 0 ? (iota_value % 1 != 0 ? iota_value : 
                (iota_value + "").split("\\.")[0]) +"i" : "");
    }

    @Override
    public boolean pushOnStack() {
        return false;
    }

    @Override
    public void pushCompiledObjects(Object[] operands) {
        return;
    }

    @Override
    public Object[] getCompiledObjects() {
        return null;
    }

    @Override
    public String displayString() {
        return getString();
    }

    @Override
    public void pushSolvedOperands(operand[] operands) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pushSolvedOperands'");
    }
    
}
