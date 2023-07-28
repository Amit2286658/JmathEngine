package library.constants;

import core.interfaces.scanner;
import library.operands.real;

import static core.CONSTANTS.*;

public class pi implements scanner{

    StringBuilder builder = new StringBuilder();
    boolean scanning = false;

    @Override
    public int scan(char c) {
        if (c == 'π' && !scanning){
            return DONE;
        }
        if ((c + "").matches("(?i:[a-z])")){
            if (!scanning)
                scanning = true;
            builder.append(c);
            return CONTINUE;
        }else {
            if (scanning){
                String name = builder.toString();
                scanning = false;
                builder.setLength(0);
                if (name.equalsIgnoreCase("pi")){
                    return _DONE_;
                }
                return BREAK;
            }
            return IGNORE;
        }
    }

    @Override
    public Object getScannedObject() {
        return new real(Math.PI);
    }
    
}
