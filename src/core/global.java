package core;

import core.interfaces.operand;
import core.interfaces.operation;
import core.interfaces.scanner;

public class global {
    private static Stack<scanner> scanners = new Stack<>();
    private static Stack<operand> operands = new Stack<>();
    private static Stack<operation> operations = new Stack<>();

    public static void pushScanner(scanner scan){
        scanners.push(scan);
    }

    public static Stack<scanner> getScanners(){
        return scanners;
    }

    public static void pushOperand(operand opnd){
        operands.push(opnd);
    }

    public static void pushOperation(operation op){
        operations.push(op);
    }

    public static Stack<operand> getOperands(){
        return operands;
    }

    public static Stack<operation> getOperations(){
        return operations;
    }
}
