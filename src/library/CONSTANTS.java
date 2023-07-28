package library;

public class CONSTANTS {
        //library specific constants

        //operand types
        public static final int 
                        REAL = 1,
                        IOTA = 2,
                        STRING = 3,
                        BRACKET = 4,
                        COMPLEX_NUMBER = 5;

        //precedence constant
        //these constants are only for convenience and is not required
        //like in the sense of other constants
        //this point had to be made clear, since basically no constant is required anyways.
        public static final int 
                        PRECEDENCE_LEAST = 1,
                        PRECEDENCE_MEDIUM = 250,
                        PRECEDENCE_MAX = 500,
                        PRECEDENCE_FUNCTION = 1000;

        //exception constants
        public static final int
                        EXCEPTION_INCOMPATIBLE_OPERAND_UNARY = 1,
                        EXCEPTION_INVALID_PARAMETERS_TO_FUNCTIONS = 2;
}
