package library.operations;

import library.scan_operation_adapter;
import library.operands.complex_number;
import library.operands.iota;
import library.operands.real;
import core.interfaces.operand;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class multiplication extends scan_operation_adapter{

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
                switch(right.getIdentity()){
                    case REAL -> {
                        resultOperand = left;
                        ((real)left).value *= ((real)right).value;
                    }
                    case IOTA -> {
                        resultOperand = right;
                        ((iota)right).value *= ((real)left).value;
                    }
                    case COMPLEX_NUMBER -> {
                        complex_number cn = (complex_number) right;
                        real rl = ((real) left);
                        cn.real_value *= rl.value;
                        cn.iota_value *= rl.value;
                        resultOperand = cn;
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
                    case COMPLEX_NUMBER -> {
                        complex_number cn = (complex_number) right;
                        iota io = (iota) left;
                        double real = cn.real_value;
                        double iota = cn.iota_value;
                        real *= io.value; //real is iota
                        iota *= io.value * -1; //iota is negative real
                        cn.real_value = iota;
                        cn.iota_value = real;
                        resultOperand = cn;
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
                    case COMPLEX_NUMBER -> {
                        complex_number c1 = (complex_number) left;
                        complex_number c2 = (complex_number) right;
                        double c1r = c1.real_value, c1i = c1.iota_value, c2r = c2.real_value,
                            c2i = c2.iota_value;
                        double d1 = c1r * c2r, d2 = c1r * c2i, d3 = c1i * c2r, d4 = c1i * c2i;
                        c2.real_value = d1 + (d4 * -1);
                        c2.iota_value = d2 + d3;
                        resultOperand = c2;
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
        return new operand[]{resultOperand};
    }
}
