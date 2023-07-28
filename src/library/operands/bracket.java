package library.operands;

import core.interfaces.operand;
import core.interfaces.operation;
import core.interfaces.scanner;
import library.operations.seperator;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class bracket implements scanner, operand {

    public String value;
    StringBuilder builder = new StringBuilder();
    boolean scanning = false;

    Object[] compiled;

    boolean pushOnStack = true;

    int bracket_count = 0;
    @Override
    public int scan(char c) {
        if (c == '('){
            bracket_count++;
            if (!scanning){
                scanning = true;
                return CONTINUE;
            }
            builder.append(c);
            return CONTINUE;
        }else if (c == ')'){
            bracket_count--;
            if (bracket_count == 0){
                scanning = false;
                return DONE;
            }
            builder.append(c);
            return CONTINUE;
        }else {
            if (scanning){
                builder.append(c);
                return CONTINUE;
            }else {
                return IGNORE;
            }
        }
    }

    @Override
    public Object getScannedObject() {
        bracket str = new bracket();
        str.value = builder.toString();
        builder.setLength(0);
        return str;
    }

    @Override
    public int getOperandType() {
        return OPERAND_TYPE_GROUP;
    }

    @Override
    public int getIdentity() {
        return BRACKET;
    }

    @Override
    public boolean pushOnStack() {
        return pushOnStack;
    }

    @Override
    public void pushCompiledObjects(Object[] objects) {
        this.compiled = objects;
        for(Object obj : objects){
            if (obj instanceof operation op && op instanceof seperator){
                pushOnStack = false;
                break;
            }
        }
    }

    @Override
    public String getString() {
        return value;
    }

    @Override
    public Object[] getCompiledObjects() {
        return compiled;
    }

    @Override
    public String displayString() {
        return value;
    }

    @Override
    public void pushSolvedOperands(operand[] operands) {
        
    }
}
