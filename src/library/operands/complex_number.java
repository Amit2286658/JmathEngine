package library.operands;

import static library.CONSTANTS.*;

import core.interfaces.operand;
import library.scan_adapter;

public class complex_number extends scan_adapter implements operand{

    public double real_value;
    public double iota_value;

    @Override
    public int getIdentity() {
        return COMPLEX_NUMBER;
    }

    @Override
    public String getString() {
        return (real_value != 0 ? real_value % 1 != 0 ? real_value : 
                    (real_value + "").split("\\.")[0] : "")
            + ((iota_value > 0 && real_value != 0) ? "+" : "")
            + (iota_value != 0 ? (iota_value % 1 != 0 ? iota_value : 
                (iota_value + "").split("\\.")[0]) +"i" : "");
    }
}
