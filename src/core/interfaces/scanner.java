package core.interfaces;

public interface scanner {
    int scan(char c);

    Object getScannedObject();
    
    void pushSubCompiledParts(Object[] parts);
}
