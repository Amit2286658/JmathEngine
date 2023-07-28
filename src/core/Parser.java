package core;

import core.interfaces.operand;
import core.interfaces.operation;
import core.interfaces.scanner;

import static core.CONSTANTS.*;

public class Parser {

    public operand[] Evaluate(String expression) {

        Stack<Object> st = createStack(expression);
        st = compileTheGroups(st);
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

        scanner curScanner = null;

        int resetCondition = DONE, resetIndex = 0;

        char[] arr = expression.toCharArray();

        outer_loop: 
        for (int i = 0; i <= arr.length; i++) {
            char c = (i != arr.length) ? arr[i] : '?';

            if (curScanner == null) {
                if (resetCondition != BREAK)
                    scanners.reset();
                while (scanners.hasNext()) {
                    scanner scn = scanners.pop();
                    int status = scn.scan(c);
                    switch (status) {
                        case CONTINUE -> {
                            curScanner = scn;
                            resetIndex = i;
                            continue outer_loop;
                        }
                        case DONE -> {
                            parts.push(scn.getScannedObject());
                            resetCondition = DONE;
                            continue outer_loop;
                        }
                        case IGNORE -> {
                            continue;
                        }
                        default -> {
                            throw new ExpressionException(unknown_st_init +
                                "\ni => " + i + "\nstatus => " + 
                                status + "\nscanner => " + scn.getClass(),
                                EXCEPTION_STEP_CREATE_SUBSTEP_INIT);
                        }
                    }
                }
                if (c == '?' || Character.isWhitespace(c))
                    continue;
                throw new ExpressionException(token_cl_no_1 + c + token_cl_no_2, 
                    EXCEPTION_STEP_CREATE_SUBSTEP_INIT);
                //continue;
            }

            int status = curScanner.scan(c);

            switch (status) {
                case CONTINUE -> {
                    continue;
                }
                case _DONE_ -> {
                    i--;
                    parts.push(curScanner.getScannedObject());
                    resetCondition = _DONE_;
                    curScanner = null;
                }
                case DONE -> {
                    parts.push(curScanner.getScannedObject());
                    resetCondition = DONE;
                    curScanner = null;
                }
                case BREAK -> {
                    i = --resetIndex;
                    resetIndex = 0;
                    resetCondition = BREAK;
                    curScanner = null;
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

    private Stack<Object> compileTheGroups(Stack<Object> types){
        while (types.hasNext()){
            Object obj = types.pop();
            if (obj instanceof operand op 
                    && op.getOperandType() == OPERAND_TYPE_GROUP){
                String exp = op.getString();
                Stack<Object> objs = compileTheGroups(createStack(exp));
                
                Object[] opnd = objs.getAsList();
                op.pushCompiledObjects(opnd);
            }
        }
        types.reset();
        return types;
    }

    private Stack<Object> convertToPostFix(Stack<Object> types) {
        Stack<Object> list = new Stack<>();
        Stack<operation> operators = new Stack<>();
        outer_loop: 
        while (types.hasNext()) {
            Object obj = types.pop();
            if (obj instanceof operand op) {
                if (op.getOperandType() == OPERAND_TYPE_GROUP){
                    Stack<Object> group_op_list = new Stack<>(op.getCompiledObjects());
                    group_op_list = group_op_list.reverse();
                    group_op_list = convertToPostFix(group_op_list);

                    if (op.pushOnStack()) {
                        while (group_op_list.hasNext()) {
                            list.push(group_op_list.pop());
                        }
                    } else {
                        op.pushCompiledObjects(group_op_list.getAsList());
                        list.push(op);
                    }
                }else
                    list.push(obj);
            }else {
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

                        if (left.getOperandType() == OPERAND_TYPE_GROUP){
                            Stack<Object> obs = new Stack<>(left.getCompiledObjects());
                            Stack<operand> opnds = postFixSolver(obs.reverse());

                            if (!left.pushOnStack()){
                                //operand[] oprnds = opnds.getAsList();
                                operand[] oprnds = new operand[opnds.getLength()];
                                int counter = 0;
                                while(opnds.hasNext()){
                                    oprnds[counter] = (operand)opnds.pop();
                                    counter++;
                                }
                                left.pushSolvedOperands(oprnds);
                            }else {
                                if (opnds.getLength() > 1)
                                    throw new ExpressionException(multiple_op_in_operations,
                                        EXCEPTION_STEP_SOLVE);
                                left = opnds.pop();
                            }
                        }
                        if (right.getOperandType() == OPERAND_TYPE_GROUP){
                            Stack<Object> obs = new Stack<>(right.getCompiledObjects());
                            Stack<operand> opnds = postFixSolver(obs.reverse());

                            if (!right.pushOnStack()){
                                //operand[] oprnds = opnds.getAsList();
                                operand[] oprnds = new operand[opnds.getLength()];
                                int counter = 0;
                                while(opnds.hasNext()){
                                    oprnds[counter] = (operand)opnds.pop();
                                    counter++;
                                }
                                right.pushSolvedOperands(oprnds);
                            }else {
                                if (opnds.getLength() > 1)
                                    throw new ExpressionException(multiple_op_in_operations,
                                        EXCEPTION_STEP_SOLVE);
                                right = opnds.pop();
                            }
                        }

                        op.function(left, right);
                    }
                    case OPERATION_TYPE_UNARY -> {
                        operand single = (operand) operands.pop();

                        if (single.getOperandType() == OPERAND_TYPE_GROUP){
                            Stack<Object> obs = new Stack<>(single.getCompiledObjects());
                            Stack<operand> opnds = postFixSolver(obs.reverse());

                            if (!single.pushOnStack()){
                                //operand[] oprnds = opnds.getAsList();
                                operand[] oprnds = new operand[opnds.getLength()];
                                int counter = 0;
                                while(opnds.hasNext()){
                                    oprnds[counter] = (operand)opnds.pop();
                                    counter++;
                                }
                                single.pushSolvedOperands(oprnds);
                            }else {
                                if (opnds.getLength() > 1)
                                    throw new ExpressionException(multiple_op_in_operations,
                                        EXCEPTION_STEP_SOLVE);
                                single = opnds.pop();
                            }
                        }

                        op.function(single);
                    }
                    case OPERATION_TYPE_FUNCTION -> {
                        operand bckt = operands.pop();
                        if (bckt.getOperandType() == OPERAND_TYPE_GROUP){
                            Stack<Object> obs = new Stack<>(bckt.getCompiledObjects());
                            Stack<operand> opnds = postFixSolver(obs.reverse());

                            operand[] oprnds = new operand[opnds.getLength()];
                            int counter = 0;
                            while(opnds.hasNext()){
                                oprnds[counter] = (operand)opnds.pop();
                                counter++;
                            }
                            op.function(oprnds);
                        }else 
                            op.function(bckt);
                    }
                }
                int resultStatus = op.getResultFlag();

                switch (resultStatus) {
                    case RESULT_SINGLE -> {
                        operand opnd = op.getSingleResult();
                        operands.push(opnd);
                    }
                    case RESULT_MULTIPLE -> {
                        for (operand opnd : op.getMultipleResult()) {
                            operands.push(opnd);
                        }
                    }
                }
            }else
                operands.push((operand) obj);
        }
        return operands.reverse();
    }
}
