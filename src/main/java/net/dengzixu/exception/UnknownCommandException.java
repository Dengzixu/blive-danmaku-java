package net.dengzixu.exception;

public class UnknownCommandException extends RuntimeException {
    public UnknownCommandException() {
        super("未知的 Command");
    }

    public UnknownCommandException(String command) {
        super("未知的 Command [" + command + "]");
    }
}
