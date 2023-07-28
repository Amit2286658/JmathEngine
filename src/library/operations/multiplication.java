package library.operations;

import core.interfaces.operation;
import core.interfaces.scanner;
import library.operands.complex_number;
import library.operands.iota;
import library.operands.real;
import core.interfaces.operand;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class multiplication implements scanner, operation{

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
    public void function(operand[] params) {

    }

    @Override
    public void function(operand left, operand right) {
        switch(left.getIdentity()){
            case REAL -> {
                switch(right.getIdentity()){
                    case REAL -> {
                        resultOperand = left;
                        ((real)left).value *= ((real)right).value;
                    }
                    case IOTA -> {
                        resultOperand = right;
                        ((iota)right).value *= ((real)left).value;
                    }
                }
            }
            case IOTA -> {
                switch(right.getIdentity()){
                    case REAL -> {
                        resultOperand = left;
                        ((iota)left).value *= ((real)right).value;
                    }
                    case IOTA -> {
                        real rl = new real();
                        rl.value = ((iota)left).value * ((iota)right).value * -1;
                        resultOperand = rl;
                    }
                }
            }
            case COMPLEX_NUMBER -> {
                switch(right.getIdentity()){
                    case REAL -> {
                        complex_number cn = (complex_number)left;
                        real rl = (real)right;
                        cn.real_value *= rl.value;
                        cn.iota_value *= rl.value;
                        resultOperand = left;
                    }
                    case IOTA -> {
                        complex_number cn = (complex_number)left;
                        iota io = (iota)right;
                        double rl_v = cn.real_value, io_v = cn.iota_value;
                        cn.real_value = io_v * io.value * -1;
                        cn.iota_value = rl_v * io.value;
                        resultOperand = left;
                    }
                }
            }
        }
    }

    @Override
    public void function(operand single) {
        
    }

    @Override
    public int scan(char c) {
        if (c == '*'){
            symbol_count++;
            return CONTINUE;
        }else {
            if (symbol_count == 1){
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

    @Override
    public int getResultFlag() {
        return RESULT_SINGLE;
    }

    @Override
    public operand getSingleResult() {
        return resultOperand;
    }

    @Override
    public operand[] getMultipleResult() {
        return null;
    }
}
