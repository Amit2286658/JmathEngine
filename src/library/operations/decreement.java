package library.operations;

import core.ExpressionException;
import core.interfaces.operand;
import core.interfaces.operation;
import core.interfaces.scanner;

import library.operands.real;
import library.operands.iota;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class decreement implements scanner, operation{

    int symbol_count = 0;

    private operand opnd;

    @Override
    public int getPrecedence() {
        return PRECEDENCE_LEAST + 1;
    }

    @Override
    public int getOperationType() {
        return OPERATION_TYPE_UNARY;
    }

    @Override
    public int getResultFlag() {
        return RESULT_SINGLE;
    }

    @Override
    public operand getSingleResult() {
        return opnd;
    }

    @Override
    public operand[] getMultipleResult() {
        return null;
    }

    @Override
    public void function(operand[] params) {
        //empty
    }

    @Override
    public void function(operand left, operand right) {
        // empty
    }

    @Override
    public void function(operand single) {
        switch(single.getIdentity()){
            case REAL -> {
                ((real)single).value--;
                opnd = single;
            }
            case IOTA -> {
                ((iota)single).value--;
                opnd = single;
            }
            default -> {
                throw new ExpressionException("the operand => " + single.getClass() +
                    " is incompatible with operator => " + this.getClass(),
                    EXCEPTION_INCOMPATIBLE_OPERAND_UNARY);
            }
        }
    }

    @Override
    public int scan(char c) {
        if (c == '-'){
            symbol_count++;
            return CONTINUE;
        }else {
            if (symbol_count == 2){
                symbol_count = 0;
                return _DONE_;
            }else if (symbol_count != 0){
                symbol_count = 0;
                return BREAK;
            }
            return IGNORE;
        }
    }

    @Override
    public Object getScannedObject() {
        return this;
    }
    
}
