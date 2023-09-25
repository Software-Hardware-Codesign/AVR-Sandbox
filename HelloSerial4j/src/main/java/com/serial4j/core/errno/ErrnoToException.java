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

import com.serial4j.core.serial.throwable.PermissionDeniedException;
import com.serial4j.core.serial.throwable.BadFileDescriptorException;
import com.serial4j.core.serial.throwable.FileAlreadyOpenedException;
import com.serial4j.core.serial.throwable.FileIsDirectoryException;
import com.serial4j.core.serial.throwable.FileTableOverflowException;
import com.serial4j.core.serial.throwable.FileTooLargeException;
import com.serial4j.core.serial.throwable.InputOutputException;
import com.serial4j.core.serial.throwable.InterruptedSystemCallException;
import com.serial4j.core.serial.throwable.ErrnoNotFoundError;
import com.serial4j.core.serial.throwable.NoSuchFileException;
import com.serial4j.core.serial.throwable.InvalidArgumentException;
import com.serial4j.core.serial.throwable.NoSpaceLeftException;
import com.serial4j.core.serial.throwable.ReadOnlyFileSystemException;
import com.serial4j.core.serial.throwable.NotTtyDeviceException;
import com.serial4j.core.serial.throwable.TooManyOpenedFilesException;
import com.serial4j.core.serial.throwable.TryAgainException;
import com.serial4j.core.serial.throwable.BrokenPipeException;
import com.serial4j.core.serial.throwable.NoSuchDeviceException;
import com.serial4j.core.serial.throwable.InvalidPortException;
import com.serial4j.core.serial.throwable.OperationFailedException;
import com.serial4j.core.serial.throwable.NoAvailableTtyDevicesException;
import com.serial4j.core.errno.Errno;

/**
 * Reflects C native errnos into real java exceptions.
 * 
 * @author pavl_g.
 */
public final class ErrnoToException {
    
    private ErrnoToException() {
    }

    /**
     * Throws a java exception from a native errno.
     * 
     * @param errno the native error code to which the exception will be thrown against.
     * @param additionalText additional text in string format to be displayed within the throwable message.
     *
     * @throws NoSuchDeviceException in case of errno(2).
     * @throws PermissionDeniedException in case of errno(13).
     * @throws BrokenPipeException in case of errno(32).
     * @throws InvalidPortException in case of errno(-2).
     * @throws OperationFailedException in case of errno(-1).
     * @throws NoAvailableTtyDevicesException in case of errno(-4).
     */
    public static void throwFromErrno(final int errno, final String additionalText) { 
        if (errno == Errno.ENOENT.getValue()) {
            throw new NoSuchFileException(additionalText);
        } else if (errno == Errno.EACCES.getValue()) {
            throw new PermissionDeniedException(additionalText);
        } else if (errno == Errno.EPIPE.getValue()) {
            throw new BrokenPipeException(additionalText);
        } else if (errno == Errno.ERR_INVALID_PORT.getValue()) {
            throw new InvalidPortException(additionalText);
        } else if (errno == Errno.ERR_NO_AVAILABLE_TTY_DEVICES.getValue()) {
            throw new NoAvailableTtyDevicesException(additionalText);
        } else if (errno == Errno.EBADF.getValue()) {
            throw new BadFileDescriptorException(additionalText);
        } else if (errno == Errno.EEXIST.getValue()) {
            throw new FileAlreadyOpenedException(additionalText);
        } else if (errno == Errno.EISDIR.getValue()) {
            throw new FileIsDirectoryException(additionalText);
        } else if (errno == Errno.ENFILE.getValue()) {
            throw new FileTableOverflowException(additionalText);
        } else if (errno == Errno.EFBIG.getValue()) {
            throw new FileTooLargeException(additionalText);
        } else if (errno == Errno.EIO.getValue()) {
            throw new InputOutputException(additionalText);
        } else if (errno == Errno.EINTR.getValue()) {
            throw new InterruptedSystemCallException(additionalText);
        } else if (errno == Errno.EINVAL.getValue()) {
            throw new InvalidArgumentException(additionalText);
        } else if (errno == Errno.ENOSPC.getValue()) {
            throw new NoSpaceLeftException(additionalText);
        } else if (errno == Errno.ENXIO.getValue()) {
            throw new NoSuchDeviceException(additionalText);
        } else if (errno == Errno.EROFS.getValue()) {
            throw new ReadOnlyFileSystemException(additionalText);
        } else if (errno == Errno.ENOTTY.getValue()) {
            throw new NotTtyDeviceException(additionalText);
        } else if (errno == Errno.EMFILE.getValue()) {
            throw new TooManyOpenedFilesException(additionalText);
        } else if (errno == Errno.EAGAIN.getValue()) {
            throw new TryAgainException(additionalText);
        } else {
            throw new ErrnoNotFoundError("Cannot interpret this error code !");
        }
    }

}
