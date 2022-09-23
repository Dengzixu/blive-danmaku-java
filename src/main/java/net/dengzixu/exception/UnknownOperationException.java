package net.dengzixu.exception;

public class UnknownOperationException extends RuntimeException {

    public UnknownOperationException() {
        super("未知操作值");
    }
}
