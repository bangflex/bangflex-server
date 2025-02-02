package springbootmonolithic.exception;

public class NicknameDuplicatedException extends RuntimeException {
        public NicknameDuplicatedException(String message) {
            super(message);
        }
}
