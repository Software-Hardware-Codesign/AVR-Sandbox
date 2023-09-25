package com.serial4j.core.serial.throwable;

import com.serial4j.core.errno.Errno;

public class InputOutputException extends SerialThrowable {
    
    public InputOutputException(final String additionalText) {
        super(Errno.EIO.getDescription() + "\n" + additionalText);
    }
    
    @Override
    public Errno getCausingErrno() {
        return Errno.EIO;
    }
}