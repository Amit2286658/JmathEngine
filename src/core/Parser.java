package core;

import core.interfaces.operand;
import core.interfaces.groupOperand;
import core.interfaces.operation;
import core.interfaces.scanner;

import static core.CONSTANTS.*;

public class Parser {

    public operand[] Evaluate(String expression) {

        Stack<Object> st = createStack(expression);
        st = convertToPostFix(st);
        Stack<operand> operands = postFixSolver(st);

        int index = 0;
        operand[] list = new operand[operands.getLength()];
        while(operands.hasNext()){
            list[index] = operands.pop();
            index++;
        }
        return list;
    }

    private Stack<Object> createStack(String expression) {
        Stack<scanner> scanners = new Stack<>();
        scanners = global.getScanners();
        // the wildcard
        Stack<Object> parts = new Stack<>();

        StringBuilder builder = new StringBuilder();
        boolean isGroup = false;

        scanner curScanner = null;

        int resetCondition = RELEASE, resetIndex = 0;

        char[] arr = expression.toCharArray();

        outer_loop: 
        for (int i = 0; i <= arr.length; i++) {
            char c = (i != arr.length) ? arr[i] : EOF;

            if (curScanner == null) {
                if (resetCondition != INTERRUPT)
                    scanners.reset();
                while (scanners.hasNext()) {
                    scanner scn = scanners.pop();
                    int status = scn.scan(c);
                    switch (status) {
                        case _LOCK_ :
                            isGroup = true;
                        case LOCK : 
                            curScanner = scn;
                            resetIndex = i;
                            continue outer_loop;
                        case FINISH :
                            parts.push(scn.getScannedObject());
                            resetCondition = RELEASE;
                            continue outer_loop;
                        case IGNORE :
                            continue;                      
                        default :
                            throw new ExpressionException(unknown_st_init +
                                "\ni => " + i + "\nstatus => " + 
                                status + "\nscanner => " + scn.getClass(),
                                EXCEPTION_STEP_CREATE_SUBSTEP_INIT);
                    }
                }
                if (checkSkippingChars(c))
                    continue;
                throw new ExpressionException(token_cl_no_1 + c + token_cl_no_2, 
                    EXCEPTION_STEP_CREATE_SUBSTEP_INIT);
                //continue;
            }

            int status = curScanner.scan(c);

            switch (status) {
                case CONTINUE -> {
                    if (isGroup)
                        builder.append(c);
                    continue;
                }
                case _RELEASE_ -> {
                    if (isGroup){
                        Stack<Object> sub_parts = createStack(builder.toString());
                        Object[] subs = sub_parts.getAsList();
                        curScanner.pushSubCompiledParts(subs);
                        builder.setLength(0);
                        isGroup = false;
                    }
                    i--;
                    parts.push(curScanner.getScannedObject());
                    resetCondition = _RELEASE_;
                    curScanner = null;
                }
                case RELEASE -> {
                    if (isGroup){
                        Stack<Object> sub_parts = createStack(builder.toString());
                        Object[] subs = sub_parts.getAsList();
                        curScanner.pushSubCompiledParts(subs);
                        builder.setLength(0);
                        isGroup = false;
                    }
                    parts.push(curScanner.getScannedObject());
                    resetCondition = RELEASE;
                    curScanner = null;
                }
                case INTERRUPT -> {
                    i = --resetIndex;
                    resetIndex = 0;
                    resetCondition = INTERRUPT;
                    curScanner = null;
                    isGroup = false;
                }
                default -> {
                    throw new ExpressionException(unknown_st_sc +
                        "\ni => " + i + "\nc => " + c + "\nstatus => " + status +
                        "\nscanner => " + curScanner.getClass(),
                        EXCEPTION_STEP_CREATE_SUBSTEP_SCAN);
                }
            }
        }
        scanners.reset();
        parts = parts.reverse();
        return parts;
    }

    private Stack<Object> convertToPostFix(Stack<Object> types) {
        Stack<Object> list = new Stack<>();
        Stack<operation> operators = new Stack<>();
        outer_loop: 
        while (types.hasNext()) {
            Object obj = types.pop();
            if (obj instanceof operand op && !(obj instanceof groupOperand)) {    
                list.push(obj);
            } else if(obj instanceof groupOperand op){
                Stack<Object> group_op_list = new Stack<>(op.getCompiledObjects());
                group_op_list = group_op_list.reverse();
                group_op_list = convertToPostFix(group_op_list);

                op.pushPostFixedObjects(group_op_list.getAsList());
                list.push(op);
            } else {
                operation obj_op = (operation) obj;
                if (operators.getLength() == 0) {
                    operators.push(obj_op);
                    continue;
                }
                while (operators.hasNext()) {
                    operation op = operators.peek();
                    if (op.getPrecedence() >= obj_op.getPrecedence()) {
                        operators.pop();
                        list.push(op);
                    } else {
                        operators.push(obj_op);
                        continue outer_loop;
                    }
                }
                operators.push(obj_op);
            }
        }
        while (operators.hasNext()) {
            list.push(operators.pop());
        }
        return list.reverse();
    }

    private Stack<operand> postFixSolver(Stack<Object> types) {
        Stack<operand> operands = new Stack<>();
        while (types.hasNext()) {
            Object obj = types.pop();
            if (obj instanceof operation op) {
                switch (op.getOperationType()) {
                    case OPERATION_TYPE_BINARY -> {
                        operand right = (operand) operands.pop();
                        operand left = (operand) operands.pop();
                        int state = 4;

                        if (left instanceof groupOperand left_gp){
                            operand[] oprnds = groupOperandSolver(left_gp);
                            if (oprnds.length == 1 && left_gp.pushOnStack_whenSingleElement())
                                left = oprnds[0];
                            else {
                                left_gp.pushEvaluatedObjects(oprnds);
                                state = 1;
                            }
                        }

                        if (right instanceof groupOperand right_gp){
                            operand[] oprnds = groupOperandSolver(right_gp);
                            if (oprnds.length == 1 && right_gp.pushOnStack_whenSingleElement())
                                right = oprnds[0];
                            else {
                                right_gp.pushEvaluatedObjects(oprnds);
                                state = state == 1 ? 3 : 2;
                            }
                        }

                        switch(state){
                            case 4 -> op.function(left, right);
                            case 3 -> op.function((groupOperand)left, (groupOperand)right);
                            case 2 -> op.function(left, (groupOperand)right);
                            case 1 -> op.function((groupOperand)left, right);
                        }
                    }
                    case OPERATION_TYPE_UNARY -> {
                        operand single = (operand) operands.pop();
                        int state = 0;
                        if (single instanceof groupOperand single_gp){
                            operand[] oprnds = groupOperandSolver(single_gp);
                            if (oprnds.length == 1 && single_gp.pushOnStack_whenSingleElement())
                                single = oprnds[0];
                            else {
                                single_gp.pushEvaluatedObjects(oprnds);
                                state = 1;
                            }
                        }

                        if (state == 1)
                            op.function((groupOperand)single);
                        else
                            op.function(single);
                    }
                }
                
                for (operand opnd : op.getResult()) {
                    operands.push(opnd);
                }
            }else
                operands.push((operand) obj);
        }
        return operands.reverse();
    }

    private operand[] groupOperandSolver(groupOperand op){
        Stack<Object> obs = new Stack<>(op.getCompiledObjects());
        Stack<operand> opnds = postFixSolver(obs.reverse());

        operand[] oprnds = new operand[opnds.getLength()];
        int counter = 0;
        while (opnds.hasNext()) {
            oprnds[counter] = (operand) opnds.pop();
            counter++;
        }
        if (oprnds.length == 1 && op.pushOnStack_whenSingleElement()){
            if (oprnds[0] instanceof groupOperand){
                return groupOperandSolver((groupOperand)oprnds[0]);
            }
            return new operand[]{oprnds[0]};
        }
        return oprnds;
    }

    private boolean checkSkippingChars(char c){
        if (c == EOF || c == NEW_LINE || c == SPACE)
            return true;
        return false;
    }
}
