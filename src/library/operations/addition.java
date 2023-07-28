package library.operations;

import core.interfaces.operation;
import core.interfaces.scanner;
import library.operands.complex_number;
import library.operands.iota;
import library.operands.real;
import core.interfaces.operand;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class addition implements scanner, operation{

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
    public void function(operand[] params) {

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
    public void function(operand single) {
        
    }

    @Override
    public int scan(char c) {
        // if (Character.isLetter(c)){
        //     if (!scanning)
        //         scanning = true;
        //     builder.append(c);
        //     return CONTINUE;
        // }else if (Character.isDigit(c)){
        //     if (!scanning){
        //         return IGNORE;
        //     }else {
        //         String name = builder.toString();
        //         builder.setLength(0);
        //         scanning = false;
        //         if (name.equalsIgnoreCase("Addition")){
        //             return _DONE_;
        //         }
        //         return BREAK;
        //     }
        // }
        if (c == '+'){
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
