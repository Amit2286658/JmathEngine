package library.operands;

import core.interfaces.operand;
import core.interfaces.operation;
import library.scan_operand_adapter;
import library.operations.seperator;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class bracket extends scan_operand_adapter {

    public String value;
    boolean scanning = false;

    Object[] compiled;

    boolean pushOnStack = true;

    int bracket_count = 0;

    public bracket(){
        //empty
    }

    @Override
    public int scan(char c) {
        if (c == '('){
            bracket_count++;
            if (!scanning){
                scanning = true;
                return _LOCK_;
            }
            return CONTINUE;
        }else if (c == ')'){
            bracket_count--;
            if (bracket_count == 0){
                scanning = false;
                return RELEASE;
            }
            return CONTINUE;
        }else {
            if (scanning){
                return CONTINUE;
            }else {
                return IGNORE;
            }
        }
    }

    @Override
    public int getLength() {
        return this.compiled.length;
    }

    @Override
    public Object getScannedObject() {
        if (pushOnStack)
            return this.compiled[0];

        bracket br = new bracket();
        br.pushCompiledObjects(compiled);
        br.pushOnStack = this.pushOnStack;
        return br;
    }

    @Override
    public int getIdentity() {
        return BRACKET;
    }

    @Override
    public operand[] getOperands() {
        return (operand[]) this.compiled;
    }

    @Override
    public void pushCompiledObjects(Object[] objects) {
        this.compiled = objects;
    }

    @Override
    public void pushPostFixedObjects(Object[] objects) {
        this.compiled = objects;
    }

    @Override
    public void pushEvaluatedObjects(operand[] objects) {
        this.compiled = objects;
    }

    @Override
    public boolean pushOnStack_whenSingleElement() {
        return true;
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
    public void pushSubCompiledParts(Object[] parts) {
        pushOnStack = parts.length == 1;
        this.compiled = parts;
        for(Object obj : parts){
            if (obj instanceof operation op && op instanceof seperator){
                pushOnStack = false;
                break;
            }
        }
    }
}
