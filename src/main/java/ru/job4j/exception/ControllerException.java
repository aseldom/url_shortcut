package ru.job4j.exception;

public class ControllerException extends Exception {
    public ControllerException(String s, Exception e) {
        super(s, e);
    }
}
