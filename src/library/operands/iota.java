package library.operands;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

import core.interfaces.operand;
import library.scan_adapter;

public class iota extends scan_adapter implements operand{

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
    public int scan(char c) {
        if (c == 'i' && scanning) {
            scanning = false;
            value = Double.parseDouble(builder.toString());
            return RELEASE;
        }
        if ((c + "").matches("[0-9e.]")) {
            if ((c + "").matches("[e.]") && !scanning)
                return IGNORE;
            builder.append(c);
            if (!scanning){
                scanning = true;
                return LOCK;
            }
            return CONTINUE;
        }

        if (scanning){
            builder.setLength(0);
            scanning = false;
            return INTERRUPT;
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
}
