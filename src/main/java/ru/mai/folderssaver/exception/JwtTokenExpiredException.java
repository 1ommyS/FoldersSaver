package ru.mai.folderssaver.exception;

/**
 * @author 1ommy
 * @version 28.02.2023
 */
public class JwtTokenExpiredException extends Exception{
    public JwtTokenExpiredException(String message) {
        super(message);
    }
}
