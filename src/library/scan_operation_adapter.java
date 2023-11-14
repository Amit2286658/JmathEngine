package library;

import core.interfaces.groupOperand;
import core.interfaces.operand;
import core.interfaces.operation;
import core.interfaces.scanner;

public abstract class scan_operation_adapter implements scanner, operation{

    @Override
    public int getPrecedence() {
        throw new UnsupportedOperationException("Unimplemented method 'getPrecedence'");
    }

    @Override
    public int getOperationType() {
        throw new UnsupportedOperationException("Unimplemented method 'getOperationType'");
    }

    @Override
    public operand[] getResult() {
        throw new UnsupportedOperationException("Unimplemented method 'getMultipleResult'");
    }

    @Override
    public void function(operand left, operand right) {
        throw new UnsupportedOperationException("Unimplemented method 'function'");
    }

    @Override
    public void function(groupOperand left, groupOperand right) {
        throw new UnsupportedOperationException("Unimplemented method 'function'");
    }

    @Override
    public void function(groupOperand left, operand right) {
        throw new UnsupportedOperationException("Unimplemented method 'function'");
    }

    @Override
    public void function(operand left, groupOperand right) {
        throw new UnsupportedOperationException("Unimplemented method 'function'");
    }

    @Override
    public void function(operand single) {
        throw new UnsupportedOperationException("Unimplemented method 'function'");
    }

    @Override
    public void function(groupOperand single) {
        throw new UnsupportedOperationException("Unimplemented method 'function'");
    }

    @Override
    public int scan(char c) {
        throw new UnsupportedOperationException("Unimplemented method 'scan'");
    }

    @Override
    public Object getScannedObject() {
        throw new UnsupportedOperationException("Unimplemented method 'getScannedObject'");
    }

    @Override
    public void pushSubCompiledParts(Object[] parts) {
        throw new UnsupportedOperationException("Unimplemented method 'pushSubParts'");
    }
    
}
