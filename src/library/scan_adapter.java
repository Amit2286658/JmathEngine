package library;

import core.interfaces.scanner;

public abstract class scan_adapter implements scanner {

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
