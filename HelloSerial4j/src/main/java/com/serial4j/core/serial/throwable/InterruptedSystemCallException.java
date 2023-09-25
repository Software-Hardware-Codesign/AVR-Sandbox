package com.serial4j.core.serial.throwable;

import com.serial4j.core.errno.Errno;

public class InterruptedSystemCallException extends SerialThrowable {
    
    public InterruptedSystemCallException(final String additionalText) {
        super(Errno.EINTR.getDescription() + "\n" + additionalText);
    }
    
    @Override
    public Errno getCausingErrno() {
        return Errno.EINTR;
    }
}