package library.constants;

import library.scan_adapter;
import library.operands.real;

import static core.CONSTANTS.*;

public class euler extends scan_adapter{

    boolean found = false;
    @Override
    public int scan(char c) {
        if (c == 'e'){
            if (!found)
                found = true;
            return LOCK;
        }else {
            if (found){
                if (Character.isDigit(c)){
                    found = false;
                    return INTERRUPT;
                }else{
                    found = false;
                    return _RELEASE_;
                }
            }else
                return IGNORE;
        }
    }

    @Override
    public Object getScannedObject() {
        return new real(Math.E);
    }
    
}
