package library.operations;

import core.ExpressionException;
import core.interfaces.operand;

import library.operands.real;
import library.scan_operation_adapter;
import library.operands.iota;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class increement extends scan_operation_adapter{

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
    public operand[] getResult() {
        return new operand[]{opnd};
    }

    @Override
    public void function(operand single) {
        switch(single.getIdentity()){
            case REAL -> {
                ((real)single).value++;
                opnd = single;
            }
            case IOTA -> {
                ((iota)single).value++;
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
        if (c == '+'){
            symbol_count++;
            if (symbol_count == 1){
                return LOCK;
            }
            return CONTINUE;
        }else {
            if (symbol_count == 2){
                symbol_count = 0;
                return _RELEASE_;
            }else if (symbol_count != 0){
                symbol_count = 0;
                return INTERRUPT;
            }
            return IGNORE;
        }
    }

    @Override
    public Object getScannedObject() {
        return this;
    }
    
}
