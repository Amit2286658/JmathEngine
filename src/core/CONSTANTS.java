package core;

public class CONSTANTS {
        //core specific constants
        
        //scanner states
        public static final int 
                        CONTINUE = 1,
                        LOCK = 2,
                        _LOCK_ = 3,
                        IGNORE = 4,
                        RELEASE = 5,
                        _RELEASE_ = 6,
                        INTERRUPT = 7,
                        FINISH = 8;

        //operand types 
        //deprecated
        public static final int
                        OPERAND_TYPE_SINGULAR = 1,
                        OPERAND_TYPE_GROUP = 2;

        //operation types
        public static final int 
                        OPERATION_TYPE_BINARY = 1,
                        OPERATION_TYPE_UNARY = 2;
                        // OPERATION_TYPE_FUNCTION = 3;

        public static final char 
                        EOF = '\0',
                        NEW_LINE = '\n',
                        SPACE = ' ';

        
        //result flags
        // public static final int
        //                 RESULT_SINGLE = 1,
        //                 RESULT_MULTIPLE = 2;

        //Exception constants
        public static final int
                        EXCEPTION_STEP_CREATE = 1,
                                EXCEPTION_STEP_CREATE_SUBSTEP_INIT = 2,
                                EXCEPTION_STEP_CREATE_SUBSTEP_SCAN = 3,
                        EXCPETION_STEP_CONVERT = 4,
                        EXCEPTION_STEP_SOLVE = 5;

        //messages
        public static final String
                        multiple_op_in_operations = "an Operation that " + 
                                "is not a function shouldn't receive multiple operands " + 
                                "from a group that is to be pushed onto the stack, such as brackets",
                        unknown_st_sc = "unknown state during scan",
                        unknown_st_init = "unknown state during initialization",
                        token_cl_no_1 = "the current token ",
                        token_cl_no_2 = " is claimed by no one";
}
