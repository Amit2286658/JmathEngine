package library.operands;

import library.scan_adapter;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

import core.ExpressionException;
import core.interfaces.operand;

public class variable extends scan_adapter implements operand {

    StringBuilder builder = new StringBuilder();
    boolean scan = false, identify_scan = false;
    String identifier = "";
    operand value;

    public variable(){
        //empty
    }

    public variable (String identifier){
        this.identifier = identifier;
    }

    @Override
    public int scan(char c) {
        if ((c + "").matches("(?i:[a-z])")){
            builder.append(c);
            if (!scan) {
                scan = true;
                return LOCK;
            }else {
                return CONTINUE;
            }
        }else {
            if (scan){
                String str = builder.toString();
                builder.setLength(0);
                if (str.equalsIgnoreCase("let")){
                    if (identify_scan)
                        throw new ExpressionException("cannot have two lets",
                            EXCEPTION_INVALID_IDENTIFIER);
                    identify_scan = true;
                    return CONTINUE;
                }
                if (identify_scan){
                    identifier = str;
                    scan = false;
                    identify_scan = false;
                    return RELEASE;
                }
                scan = false;
                return INTERRUPT;
            }
        }
        return IGNORE;
    }

    @Override
    public Object getScannedObject() {
        return new variable(identifier);
    }

    @Override
    public int getIdentity() {
        return VARIABLE;
    }

    @Override
    public String getString() {
        String str = identifier + " => " + value.getString();
        return str;
    }

    public String getIdentifier(){
        return identifier;
    }

    public operand getValue(){
        return value;
    }

    public void setValue(operand value){
        this.value = value;
    }
}
