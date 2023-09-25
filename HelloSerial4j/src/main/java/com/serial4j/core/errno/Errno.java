/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2022, Scrappers Team, The AVR-Sandbox Project, Serial4j API.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.

 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.serial4j.core.errno;

import com.serial4j.core.serial.NativeTerminalDevice;

/**
 * Interprets the native methods bad return values into error codes,
 * used by the java {@link com.serial4j.core.errno.ErrnoToException} 
 * to throw exceptions against the JRE.
 * 
 * @author pavl_g.
 */
public enum Errno {
    /**
     * Serial4j business errors.
     */
    ERR_INVALID_PORT(NativeErrno.getInvalidPortErrno(), "Invalid Port"),
    ERR_OPERATION_FAILED(NativeErrno.getOperationFailedErrno(), "Operation Failed"),
    ERR_NO_AVAILABLE_TTY_DEVICES(NativeErrno.getNoAvailableTtyDevicesErrno(), "No available teletype devices"),

    /**
     * Error codes for open(const char*, int), file names and IO.
     */
    EACCES(NativeErrno.getPermissionDeniedErrno(), "Permission denied"),
    EEXIST(NativeErrno.getFileAlreadyOpenedErrno(), "File exists"),
    EINTR(NativeErrno.getInterruptedSystemCallErrno(), "Interrupted system call"),
    EISDIR(NativeErrno.getFileIsDirectoryErrno(), "Is a directory"),
    EMFILE(NativeErrno.getTooManyOpenedFilesErrno(), "Too many open files"),
    ENFILE(NativeErrno.getFileTableOverflowErrno(), "File table overflow"),
    ENOENT(NativeErrno.getNoSuchFileErrno(), "No Such file or directory"),
    ENOSPC(NativeErrno.getNoSpaceLeftErrno(), "No space left on device"),
    ENXIO(NativeErrno.getNoSuchDeviceErrno(), "No such device or address"),
    EROFS(NativeErrno.getReadOnlyFileSystemErrno(), "Read-only file system"),
    EPIPE(NativeErrno.getBrokenPipeErrno(), "Broken pipe"),

    /**
     * Error codes for tcgetattr(int, struct termios*) and tcsetattr(int, struct termios*).
     */
    EBADF(NativeErrno.getBadFileDescriptorErrno(), "File descriptor in bad state"),
    ENOTTY(NativeErrno.getNotTtyDeviceErrno(), "Not a typewriter device"),

    /**
     * tcsetattr(int, struct termios*) only.
     */
    EINVAL(NativeErrno.getInvalidArgumentErrno(), "Invalid argumnet"),

    /**
     * Additional error codes for basic R/W from <fcntl.h>
     */
    EAGAIN(NativeErrno.getTryAgainErrno(), "Try again"),
    EIO(NativeErrno.getInputOutputErrno(), "I/O Error"),

    /**
     * For write(int, void*, int); only.
     */
    EFBIG(NativeErrno.getFileTooLargeErrno(), "File too large");

    private final int value;
    private final String description;

    /**
     * Creates an error code constant with a value and a description.
     * 
     * @param value the errno value.
     * @param description the errno description.
     */
    Errno(final int value, final String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * Gets the native error code of the Err.
     *
     * @return an integer reference to the error code.
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Gets the native error code descritption.
     *
     * @return the error code description in String format.
     */
    public String getDescription() {
        return description;
    }
}
