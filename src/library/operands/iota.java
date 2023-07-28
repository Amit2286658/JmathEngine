package library.operands;

import core.interfaces.operand;
import core.interfaces.scanner;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class iota implements scanner, operand{

    private StringBuilder builder = new StringBuilder();

    private boolean scanning = false;

    public double value;

    public iota(){
        //empty
    }

    public iota(double value){
        this.value = value;
    }

    @Override
    public int getOperandType() {
        return OPERAND_TYPE_SINGULAR;
    }

    @Override
    public int getIdentity() {
        return IOTA;
    }

    @Override
    public String getString() {
        if (value % 1 != 0){
            return value + "i";
        }else {
            String[] two = (value + "").split("\\.");
            return two[0] + "i";
        }
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
    public int scan(char c) {
        if (c == 'i' && scanning) {
            scanning = false;
            value = Double.parseDouble(builder.toString());
            return DONE;
        }
        if ((c + "").matches("[0-9e.]")) {
            if ((c + "").matches("[e.]") && !scanning)
                return IGNORE;
            if (!scanning)
                scanning = true;
            builder.append(c);
            return CONTINUE;
        }

        if (scanning){
            builder.setLength(0);
            scanning = false;
            return BREAK;
        }else 
            return IGNORE;
    }

    @Override
    public Object getScannedObject() {
        iota i = new iota(value);
        value = 0;
        builder.setLength(0);
        return i;
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
