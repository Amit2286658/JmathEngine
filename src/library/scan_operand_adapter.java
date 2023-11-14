package library;

import core.interfaces.operand;
import core.interfaces.groupOperand;
import core.interfaces.scanner;

public abstract class scan_operand_adapter implements scanner, groupOperand {

    @Override
    public int getIdentity() {
        throw new UnsupportedOperationException("Unimplemented method 'getIdentity'");
    }

    @Override
    public String getString() {
        throw new UnsupportedOperationException("Unimplemented method 'getString'");
    }

    @Override
    public int getLength() {
        throw new UnsupportedOperationException("Unimplemented method 'getLength'");
    }

    @Override
    public operand[] getOperands() {
        throw new UnsupportedOperationException("Unimplemented method 'getOperands'");
    }

    @Override
    public void pushPostFixedObjects(Object[] objects) {
        throw new UnsupportedOperationException("Unimplemented method 'pushPostFixedObjects'");
    }

    @Override
    public void pushEvaluatedObjects(operand[] objects) {
        throw new UnsupportedOperationException("Unimplemented method 'pushEvaluatedObjects'");
    }

    @Override
    public Object[] getCompiledObjects() {
        throw new UnsupportedOperationException("Unimplemented method 'getCompiledObjects'");
    }

    @Override
    public void pushCompiledObjects(Object[] objects) {
        throw new UnsupportedOperationException("Unimplemented method 'pushCompiledObjects'");
    }

    @Override
    public boolean pushOnStack_whenSingleElement() {
        throw new UnsupportedOperationException("Unimplemented method 'pushOnStack_whenSingleElement'");
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
