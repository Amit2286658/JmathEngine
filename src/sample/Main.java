package sample;

import core.Parser;
import core.global;
import core.interfaces.operand;

import library.operands.*;
import library.operations.*;
import library.constants.*;
import library.functions.*;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        addScanners();
        operand[] results = parser.Evaluate("(4-4i) - (2 + 2i)");
        for(operand op : results){
            System.out.println(op.displayString());
        }
        System.out.println();
    }

    static void addScanners(){
        global.pushScanner(new real());
        global.pushScanner(new iota());
        global.pushScanner(new increement());
        global.pushScanner(new addition());
        global.pushScanner(new subtraction());
        global.pushScanner(new bracket());
        global.pushScanner(new factorial());
        global.pushScanner(new sine());
        global.pushScanner(new seperator());
        global.pushScanner(new decreement());
        global.pushScanner(new pi());
        global.pushScanner(new euler());
        global.pushScanner(new sumAll());
        global.pushScanner(new multiplication());
    }
}