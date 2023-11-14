package library.operands;

import library.scan_adapter;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

import core.interfaces.operand;

public class real extends scan_adapter implements operand{

    StringBuilder builder = new StringBuilder();

    public double value;

    boolean scanning = false;

    public real(){}

    public real(double value){
        this.value = value;
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
                return RELEASE;
            }else 
                return IGNORE;
        }
        if ((c + "").matches("[0-9e.]")){
            if ((c + "").matches("[e.]") && !scanning)
                return IGNORE;
            builder.append(c);
            if (!scanning){
                scanning = true;
                return LOCK;
            }
            return CONTINUE;
        }else if (c == 'i'){
            if (scanning){
                builder.setLength(0);
                scanning = false;
                return INTERRUPT;
            }
            else
                return IGNORE;
        }else {
            if (scanning){
                scanning = false;
                String num = builder.toString();
                builder.setLength(0);
                value = Double.parseDouble(num);
                return _RELEASE_;
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
    public String getString() {
        if (value % 1 != 0){
            return value + "";
        }else {
            String[] two = (value + "").split("\\.");
            return two[0];
        }
    }
}
