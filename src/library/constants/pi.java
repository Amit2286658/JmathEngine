package library.constants;

import library.scan_adapter;
import library.operands.real;

import static core.CONSTANTS.*;

public class pi extends scan_adapter{

    StringBuilder builder = new StringBuilder();
    boolean scanning = false;

    @Override
    public int scan(char c) {
        if (c == 'π' && !scanning){
            return FINISH;
        }
        if ((c + "").matches("(?i:[a-z])")){
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
                if (name.equalsIgnoreCase("pi")){
                    return _RELEASE_;
                }
                return INTERRUPT;
            }
            return IGNORE;
        }
    }

    @Override
    public Object getScannedObject() {
        return new real(Math.PI);
    }
    
}
