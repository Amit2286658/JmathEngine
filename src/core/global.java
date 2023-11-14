package core;

import core.interfaces.scanner;

public class global {
    private static Stack<scanner> scanners = new Stack<>();

    public static void pushScanner(scanner scan){
        scanners.push(scan);
    }

    public static Stack<scanner> getScanners(){
        return scanners;
    }
}
