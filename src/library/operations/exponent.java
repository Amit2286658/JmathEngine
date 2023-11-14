package library.operations;

import library.scan_operation_adapter;
import library.operands.iota;
import library.operands.real;
import core.interfaces.operand;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class exponent extends scan_operation_adapter{

    StringBuilder builder = new StringBuilder();
    boolean scanning = false;

    int symbol_count = 0;

    operand resultOperand;

    double value;

    @Override
    public int getPrecedence() {
        return PRECEDENCE_MEDIUM;
    }

    @Override
    public int getOperationType() {
        return OPERATION_TYPE_BINARY;
    }

    @Override
    public void function(operand left, operand right) {
        switch(left.getIdentity()){
            case REAL -> {
                real rl_l = (real)left;
                switch(right.getIdentity()){
                    case REAL -> {
                        real rl_r = (real)right;
                        double d = rl_l.value;
                        for (int i = 1; i < (int)rl_r.value; i++){
                            rl_l.value *= d;
                        }
                        resultOperand = left;
                    }
                }
            }
            case IOTA -> {
                iota io_l = (iota)left;
                switch(right.getIdentity()){
                    case REAL -> {
                        real rl_r = (real)right;
                        double d = io_l.value;
                        for(int i = 1; i < (int)rl_r.value; i++){
                            io_l.value *= d;
                        }
                        switch((int)rl_r.value % 4){
                            case 0 -> {
                                real rl = new real(io_l.value);
                                resultOperand = rl;
                            }
                            case 1 -> {
                                resultOperand = io_l;
                            }
                            case 2 -> {
                                real rl = new real(io_l.value);
                                rl.value *= -1;
                                resultOperand = rl;
                            }
                            case 3 -> {
                                io_l.value *= -1;
                                resultOperand = io_l;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public int scan(char c) {
        if (c == '*'){
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

    @Override
    public operand[] getResult() {
        return new operand[]{resultOperand};
    }
}
