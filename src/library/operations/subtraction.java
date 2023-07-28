package library.operations;

import core.interfaces.operand;
import core.interfaces.operation;
import core.interfaces.scanner;
import library.operands.complex_number;
import library.operands.iota;
import library.operands.real;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class subtraction implements scanner, operation{

    StringBuilder builder = new StringBuilder();
    boolean scanning = false;
    int symbol_count = 0;

    operand resultOperand;

    public double value;

    @Override
    public int getPrecedence() {
        return PRECEDENCE_LEAST;
    }

    @Override
    public int getOperationType() {
        return OPERATION_TYPE_BINARY;
    }

    @Override
    public int scan(char c) {
        if (c == '-'){
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
    public void function(operand left, operand right) {
        switch(left.getIdentity()){
            case REAL -> {
                real rl_left = (real)left;
                switch(right.getIdentity()){
                    case REAL -> {
                        resultOperand = left;
                        rl_left.value -= ((real)right).value;
                    }
                    case IOTA -> {
                        complex_number cn = new complex_number();
                        cn.real_value = rl_left.value;
                        ((iota)right).value *= -1;
                        cn.iota_value = ((iota)right).value;
                        resultOperand = cn;
                    }
                    case COMPLEX_NUMBER -> {
                        complex_number cn = (complex_number)right;
                        cn.real_value = rl_left.value - cn.real_value;
                        cn.iota_value = 0 - cn.iota_value;
                        resultOperand = cn;
                    }
                }
            }
            case IOTA -> {
                iota i_left = (iota)left;
                switch(right.getIdentity()){
                    case REAL -> {
                        complex_number cn = new complex_number();
                        ((real)right).value *= -1;
                        cn.real_value = ((real)right).value;
                        cn.iota_value = i_left.value;
                        resultOperand = cn;
                    }
                    case IOTA -> {
                        resultOperand = left;
                        i_left.value -= ((iota)right).value;
                    }
                    case COMPLEX_NUMBER -> {
                        complex_number cn = (complex_number)right;
                        cn.real_value = 0 - cn.real_value;
                        cn.iota_value = i_left.value - cn.iota_value;
                        resultOperand = cn;
                    }
                }
            }
            case COMPLEX_NUMBER -> {
                complex_number cn_left = (complex_number)left;
                switch(right.getIdentity()){
                    case REAL -> {
                        cn_left.real_value -= ((real)right).value;
                        resultOperand = left;
                    }
                    case IOTA -> {
                        cn_left.iota_value -= ((iota)right).value;
                        resultOperand = left;
                    }
                    case COMPLEX_NUMBER -> {
                        complex_number cn = (complex_number)right;
                        cn_left.real_value -= cn.real_value;
                        cn_left.iota_value -= cn.iota_value;
                        resultOperand = cn_left;
                    }
                }
            }
        }
    }

    @Override
    public void function(operand single) {
        // empty
    }

    @Override
    public void function(operand[] params) {
        // empty
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
