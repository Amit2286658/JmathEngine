package library.operands;

import core.interfaces.operand;
import core.interfaces.scanner;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class real implements scanner, operand{

    StringBuilder builder = new StringBuilder();

    public double value;

    boolean scanning = false;

    public real(){}

    public real(double value){
        this.value = value;
    }

    @Override
    public int getOperandType() {
        return OPERAND_TYPE_SINGULAR;
    }

    @Override
    public int getIdentity() {
        return REAL;
    }

    @Override
    public int scan(char c) {
        if (c == '?'){
            if (scanning){
                value = Double.parseDouble(builder.toString());
                scanning = false;
                return DONE;
            }else 
                return IGNORE;
        }
        if ((c + "").matches("[0-9e.]")){
            if ((c + "").matches("[e.]") && !scanning)
                return IGNORE;
            if (!scanning)
                scanning = true;
            builder.append(c);
            return CONTINUE;
        }else if (c == 'i'){
            if (scanning){
                builder.setLength(0);
                scanning = false;
                return BREAK;
            }
            else
                return IGNORE;
        }else {
            if (scanning){
                scanning = false;
                String num = builder.toString();
                builder.setLength(0);
                value = Double.parseDouble(num);
                return _DONE_;
            }else 
                return IGNORE;
        }
    }

    @Override
    public Object getScannedObject() {
        real rl = new real();
        rl.value = this.value;
        this.value = 0;
        builder.setLength(0);
        return rl;
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
    public String getString() {
        if (value % 1 != 0){
            return value + "";
        }else {
            String[] two = (value + "").split("\\.");
            return two[0];
        }
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
