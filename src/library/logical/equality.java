package library.logical;

import library.scan_operation_adapter;
import library.operands.complex_number;
import library.operands.iota;
import library.operands.real;
import core.interfaces.operand;

import static core.CONSTANTS.*;
import static library.CONSTANTS.*;

public class equality extends scan_operation_adapter{

    StringBuilder builder = new StringBuilder();
    boolean scanning = false;

    int symbol_count = 0;

    operand retOperand;

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
                        real l = ((real)left);
                        real r = ((real)right);
                        if (l.value == r.value)
                            l.value = 1;
                        else
                            l.value = 0;
                        retOperand = l;
                    }
                    case IOTA -> {
                        real l = ((real)left);
                        l.value = 0;
                        retOperand = l;
                    }
                    case COMPLEX_NUMBER -> {
                        real l = ((real)left);
                        l.value = 0;
                        retOperand = l;
                    }
                }
            }
            case IOTA -> {
                switch(right.getIdentity()){
                    case REAL -> {
                        real r = ((real)right);
                        r.value = 0;
                        retOperand = r;
                    }
                    case IOTA -> {
                        iota l = ((iota)left);
                        iota r = ((iota)right);
                        real rl = new real();
                        if (l.value == r.value)
                            rl.value = 1;
                        else
                            rl.value = 0;
                        retOperand = rl;
                    }
                    case COMPLEX_NUMBER -> {
                        real rl = new real();
                        rl.value = 0;
                        retOperand = rl;
                    }
                }
            }
            case COMPLEX_NUMBER -> {
                switch(right.getIdentity()){
                    case REAL -> {
                        real rl = new real();
                        rl.value = 0;
                        retOperand = rl;
                    }
                    case IOTA -> {
                        real rl = new real();
                        rl.value = 0;
                        retOperand = rl;
                    }
                    case COMPLEX_NUMBER -> {
                        real rl = new real();

                        complex_number cl = (complex_number)left;
                        complex_number cr = (complex_number)right;
                        if (cl.real_value == cr.real_value && 
                                cl.iota_value == cr.iota_value)
                            rl.value = 1;
                        else
                            rl.value = 0;

                        retOperand = rl;
                    }
                }
            }
        }
    }

    @Override
    public int scan(char c) {
        if (c == '='){
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
            retOperand
        };
    }
}
