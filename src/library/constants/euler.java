package library.constants;

import core.interfaces.scanner;
import library.operands.real;

import static core.CONSTANTS.*;

public class euler implements scanner{

    boolean found = false;
    @Override
    public int scan(char c) {
        if (c == 'e'){
            if (!found)
                found = true;
            return CONTINUE;
        }else {
            if (found){
                if (Character.isDigit(c)){
                    found = false;
                    return BREAK;
                }else{
                    found = false;
                    return _DONE_;
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
