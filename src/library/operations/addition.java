package library.operations;

import library.scan_operation_adapter;
import library.operands.complex_number;
import library.operands.iota;
import library.operands.real;
import core.interfaces.operand;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class addition extends scan_operation_adapter{

    StringBuilder builder = new StringBuilder();
    boolean scanning = false;

    int symbol_count = 0;

    operand resultOperand;

    double value;

    @Override
    public int getPrecedence() {
        return PRECEDENCE_LEAST;
    }

    @Override
    public int getOperationType() {
        return OPERATION_TYPE_BINARY;
    }

    @Override
    public void function(operand left, operand right) {
        switch(left.getIdentity()){
            case REAL -> {
                switch(right.getIdentity()){
                    case REAL -> {
                        resultOperand = left;
                        ((real)left).value += ((real)right).value;
                    }
                    case IOTA -> {
                        complex_number cn = new complex_number();
                        cn.real_value = ((real)left).value;
                        cn.iota_value = ((iota)right).value;
                        resultOperand = cn;
                    }
                    case COMPLEX_NUMBER -> {
                        complex_number cn = (complex_number)right;
                        cn.real_value += ((real)left).value;
                        resultOperand = cn;
                    }
                }
            }
            case IOTA -> {
                switch(right.getIdentity()){
                    case REAL -> {
                        complex_number cn = new complex_number();
                        cn.real_value = ((real)right).value;
                        cn.iota_value = ((iota)left).value;
                        resultOperand = cn;
                    }
                    case IOTA -> {
                        resultOperand = left;
                        ((iota)left).value += ((iota)right).value;
                    }
                    case COMPLEX_NUMBER -> {
                        complex_number cn = (complex_number)right;
                        cn.iota_value += ((iota)left).value;
                        resultOperand = cn;
                    }
                }
            }
            case COMPLEX_NUMBER -> {
                switch(right.getIdentity()){
                    case REAL -> {
                        ((complex_number)left).real_value += ((real)right).value;
                        resultOperand = left;
                    }
                    case IOTA -> {
                        ((complex_number)left).iota_value += ((iota)right).value;
                        resultOperand = left;
                    }
                    case COMPLEX_NUMBER -> {
                        complex_number cn = (complex_number)right;
                        complex_number cn_l = (complex_number)left;
                        cn_l.real_value += cn.real_value;
                        cn_l.iota_value += cn.iota_value;
                        resultOperand = cn_l;
                    }
                }
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
            if (symbol_count == 1){
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
        return new operand[]{
            resultOperand
        };
    }
}
