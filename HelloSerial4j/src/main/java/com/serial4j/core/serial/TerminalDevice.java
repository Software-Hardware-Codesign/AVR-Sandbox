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
package com.serial4j.core.serial;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.serial4j.core.errno.Errno;
import com.serial4j.core.util.natives.NativeImageLoader;
import com.serial4j.core.serial.Permissions;
import com.serial4j.core.errno.ErrnoToException;
import com.serial4j.core.serial.SerialPort;
import com.serial4j.core.serial.NativeTerminalDevice;
import com.serial4j.core.serial.ReadConfiguration;
import com.serial4j.core.serial.control.TerminalControlFlag;
import com.serial4j.core.serial.control.TerminalLocalFlag;
import com.serial4j.core.serial.control.TerminalInputFlag;
import com.serial4j.core.serial.control.TerminalOutputFlag;
import com.serial4j.core.serial.throwable.PermissionDeniedException;
import com.serial4j.core.serial.throwable.BrokenPipeException;
import com.serial4j.core.serial.throwable.NoSuchDeviceException;
import com.serial4j.core.serial.throwable.TooManyOpenedFilesException;
import com.serial4j.core.serial.throwable.ReadOnlyFileSystemException;
import com.serial4j.core.serial.throwable.FileAlreadyOpenedException;
import com.serial4j.core.serial.throwable.BadFileDescriptorException;
import com.serial4j.core.serial.throwable.NotTtyDeviceException;
import com.serial4j.core.serial.throwable.InvalidArgumentException;
import com.serial4j.core.serial.throwable.FileIsDirectoryException;
import com.serial4j.core.serial.throwable.InterruptedSystemCallException;
import com.serial4j.core.serial.throwable.FileTableOverflowException;
import com.serial4j.core.serial.throwable.NoSuchFileException;
import com.serial4j.core.serial.throwable.NoSpaceLeftException;
import com.serial4j.core.serial.throwable.InvalidPortException;
import com.serial4j.core.serial.throwable.NoAvailableTtyDevicesException;

/**
 * The main entry class for the native terminal device for Serial4j api.
 * 
 * @author pavl_g.
 */
public final class TerminalDevice {

    /**
     * Static initializer for loading and setting up the native image.
     * This initializer has a static context and therefore it runs only one
     * time when creating the first object.
     */
    static {
        NativeImageLoader.loadLibrary();
        TerminalDevice.setupJniEnvironment();
    }

    private static final Logger LOGGER = Logger.getLogger(TerminalDevice.class.getName());
    private final NativeTerminalDevice nativeTerminalDevice = new NativeTerminalDevice();
    
    private Permissions permissions = Permissions.O_RDWR.append(Permissions.O_NOCTTY)
                                                        .append(Permissions.O_NONBLOCK);
    private ReadConfiguration readConfiguration;
    private String permissionsDescription;
    private boolean loggingEnabled;
    private InputStream inputStream;
    private OutputStream outputStream;

    /**
     * Instantiates a Unix terminal device object.
     */
    public TerminalDevice() {
       
    }

    /**
     * Sets up the jvm pointer from jni environment pointer on the heap for the global 
     * multithreaded reference.
     *
     * @apiNote Native and jvm threads can access the jni pointer from the global jvm pointer without 
     * referencing one of the local jni env pointers stored on another thread stack (as this leads to a thread-transition error).
     */
    private static void setupJniEnvironment() {
        final int errno = NativeTerminalDevice.setupJniEnvironment0();
        ErrnoToException.throwFromErrno(errno, "Jni Environment passed is invalid !");
    }

    /**
     * Opens this device's serial port using the path from [serialPort] instance.
     *
     * @param serialPort the serial port instance to open.
     *
     * @throws NoSuchDeviceException if an attempt is made to open a non-device file.
     * @throws NoSuchFileException if an attempt is made to access a file that doesn't exist.
     * @throws FileAlreadyOpenedException if an attempt is made to open an already opened terminal device.
     * @throws InterruptedSystemCallException if there is a process interruption while opening the port.
     * @throws FileIsDirectoryException if an attempt is made to open a directory instead of a device.
     * @throws TooManyOpenedFilesException if an attempt is made to open too many devices exceeding the system limit.
     * @throws FileTableOverflowException if an attempt is made to open a device while system io is halt.
     * @throws NoSpaceLeftException if an attempt is made to open a device while there is no space left.
     * @throws ReadOnlyFileSystemException if an attempt is made to open a read-only device.
     * @throws PermissionDeniedException if an unauthorized user attempts to open this device.
     */
    public void openPort(final SerialPort serialPort) throws NoSuchDeviceException,
                                                             NoSuchFileException,
                                                             FileAlreadyOpenedException,
                                                             InterruptedSystemCallException,
                                                             FileIsDirectoryException,
                                                             TooManyOpenedFilesException,
                                                             FileTableOverflowException,
                                                             NoSpaceLeftException,
                                                             ReadOnlyFileSystemException,
                                                             PermissionDeniedException {                                                           
        if (isSerial4jLoggingEnabled()) {
            LOGGER.log(Level.INFO, "Opening serial device " + serialPort.getPath());
        }
        this.nativeTerminalDevice.setSerialPort(serialPort);
        final int returnValue = nativeTerminalDevice.openPort0(serialPort.getPath(), permissions.getValue());
        if (isOperationFailed(returnValue)) {
            ErrnoToException.throwFromErrno(nativeTerminalDevice.getErrno0(), serialPort.getPath());
        }
        /* update port data natively */
        /* ... */
    }

    /**
     * Initializes this terminal device with the default flags and the default 
     * read timeout configuration.
     * 
     * @throws InvalidPortException if an attempt is made to initialize an invalid port (non-opened/still-closed device).
     * @throws FileNotFoundException if the device opened before has been ejected.
     * @throws NoAvailableTtyDevicesException if the port is closed while performing the initialization operation.
     */
    public void initTermios() throws InvalidPortException,
                                     FileNotFoundException,
                                     NoAvailableTtyDevicesException {
        if (isSerial4jLoggingEnabled()) {
            LOGGER.log(Level.INFO, "Initializing serial device " + getSerialPort().getPath());
        }
        int returnValue = nativeTerminalDevice.initTermios0();
        if (isOperationFailed(returnValue)) {
            returnValue = nativeTerminalDevice.getErrno0();
        }
        ErrnoToException.throwFromErrno(returnValue, getSerialPort().getPath());
        /* get the java streams from the port after initializing it with the native terminal */
        inputStream = new FileInputStream(getSerialPort().getPath());
        outputStream = new FileOutputStream(getSerialPort().getPath());
    }

    /**
     * Adjusts the terminal control flag of the termios, the terminal control flag controls the 
     * characters behavior in the local terminal.
     * 
     * @param flag the terminal control flag to adjust.
     * @throws BadFileDescriptorException if the filedes is not a valid file descriptor.
     * @throws InvalidPortException if the port is null or has not been initialized yet.
     * @throws NotTtyDeviceException if the filedes is not associated with a terminal device.
     * @throws InvalidArgumentException if the value of the when argument is not valid, 
     *                                  or there is something wrong with the data in the termios-p argument.
     */
    public void setTerminalControlFlag(final TerminalControlFlag flag) throws BadFileDescriptorException,
                                                                              InvalidPortException,
                                                                              NotTtyDeviceException,
                                                                              InvalidArgumentException {
                                       
        int returnValue = nativeTerminalDevice.setTerminalControlFlag(flag.getValue());
        if (isOperationFailed(returnValue)) {
            returnValue = nativeTerminalDevice.getErrno0();
        }
        ErrnoToException.throwFromErrno(returnValue, getSerialPort().getPath());
    }

    /**
     * Adjusts the terminal local flag of the termios, the terminal local flag controls how the
     * terminal interprets the characters on the local console.
     * 
     * @param flag the local flag to adjust.
     * @throws BadFileDescriptorException if the filedes is not a valid file descriptor.
     * @throws InvalidPortException if the port is null or has not been initialized yet.
     * @throws NotTtyDeviceException if the filedes is not associated with a terminal device.
     * @throws InvalidArgumentException if the value of the when argument is not valid, 
     *                                  or there is something wrong with the data in the termios-p argument.
     */
    public void setTerminalLocalFlag(final TerminalLocalFlag flag) throws BadFileDescriptorException,
                                                                          InvalidPortException,
                                                                          NotTtyDeviceException,
                                                                          InvalidArgumentException {
        int returnValue = nativeTerminalDevice.setTerminalLocalFlag(flag.getValue());
        if (isOperationFailed(returnValue)) {
            returnValue = nativeTerminalDevice.getErrno0();
        }
        ErrnoToException.throwFromErrno(returnValue, getSerialPort().getPath());                                                     
    }

    /**
     * Adjusts the terminal input flag for the termios, the terminal input flag controls how 
     * the terminal interpret the characters at the input from the terminal device.
     * 
     * @param flag the terminal input flag to adjust.
     * @throws BadFileDescriptorException if the filedes is not a valid file descriptor.
     * @throws InvalidPortException if the port is null or has not been initialized yet.
     * @throws NotTtyDeviceException if the filedes is not associated with a terminal device.
     * @throws InvalidArgumentException if the value of the when argument is not valid, 
     *                                  or there is something wrong with the data in the termios-p argument.
     */
    public void setTerminalInputFlag(final TerminalInputFlag flag) throws BadFileDescriptorException,
                                                                          InvalidPortException,
                                                                          NotTtyDeviceException,
                                                                          InvalidArgumentException {
        int returnValue = nativeTerminalDevice.setTerminalInputFlag(flag.getValue());
        if (isOperationFailed(returnValue)) {
            returnValue = nativeTerminalDevice.getErrno0();
        }
        ErrnoToException.throwFromErrno(returnValue, getSerialPort().getPath());                                                                                           
    }

    /**
     * Adjusts the terminal output flag for this terminal device, the terminal output flag controls
     * how the terminal interpret the charachters at the output to the terminal device.
     * 
     * @throws BadFileDescriptorException if the filedes is not a valid file descriptor.
     * @throws InvalidPortException if the port is null or has not been initialized yet.
     * @throws NotTtyDeviceException if the filedes is not associated with a terminal device.
     * @throws InvalidArgumentException if the value of the when argument is not valid, 
     *                                  or there is something wrong with the data in the termios-p argument.
     */
    public void setTerminalOutputFlag(final TerminalOutputFlag flag) throws BadFileDescriptorException,
                                                                          InvalidPortException,
                                                                          NotTtyDeviceException,
                                                                          InvalidArgumentException {
        int returnValue = nativeTerminalDevice.setTerminalOutputFlag(flag.getValue());
        if (isOperationFailed(returnValue)) {
            returnValue = nativeTerminalDevice.getErrno0();
        }
        ErrnoToException.throwFromErrno(returnValue, getSerialPort().getPath());   
    }

    public TerminalControlFlag getTerminalControlFlag() throws BadFileDescriptorException,
                                                               InvalidPortException,
                                                               NotTtyDeviceException {
        final TerminalControlFlag TCF = TerminalControlFlag.EMPTY_INSTANCE;                                            
        final long returnValue = nativeTerminalDevice.getTerminalControlFlag();
        /* Warning: Force cast the errno to (int) */
        ErrnoToException.throwFromErrno((int) returnValue, getSerialPort().getPath());   
        TCF.setValue(returnValue);
        return TCF;                                        
    }

    public TerminalLocalFlag getTerminalLocalFlag() throws BadFileDescriptorException,
                                                            InvalidPortException,
                                                            NotTtyDeviceException {
        final TerminalLocalFlag TLF = TerminalLocalFlag.EMPTY_INSTANCE;                                            
        final long returnValue = nativeTerminalDevice.getTerminalLocalFlag();
        /* Warning: Force cast the errno to (int) */
        ErrnoToException.throwFromErrno((int) returnValue, getSerialPort().getPath());   
        TLF.setValue(returnValue);
        return TLF;                                        
    }

    public TerminalInputFlag getTerminalInputFlag() throws BadFileDescriptorException,
                                                            InvalidPortException,
                                                            NotTtyDeviceException {
        final TerminalInputFlag TIF = TerminalInputFlag.EMPTY_INSTANCE;
        final long returnValue = nativeTerminalDevice.getTerminalInputFlag();
        /* Warning: Force cast the errno to (int) */
        ErrnoToException.throwFromErrno((int) returnValue, getSerialPort().getPath());   
        TIF.setValue(returnValue);
        return TIF;                                        
    }

    public TerminalOutputFlag getTerminalOutputFlag() throws BadFileDescriptorException,
                                                            InvalidPortException,
                                                            NotTtyDeviceException {
        final TerminalOutputFlag TOF = TerminalOutputFlag.EMPTY_INSTANCE;
        final long returnValue = nativeTerminalDevice.getTerminalOutputFlag();
        /* Warning: Force cast the errno to (int) */
        ErrnoToException.throwFromErrno((int) returnValue, getSerialPort().getPath());   
        TOF.setValue(returnValue);
        return TOF;                                        
    }

    public void setPermissions(final Permissions permissions) {
        this.permissions = permissions;
    }

    public final Permissions getPermissions() {
        return permissions;
    }

    public void setBaudRate(final BaudRate baudRate) throws BadFileDescriptorException,
                                                            InvalidPortException,
                                                            NotTtyDeviceException {
        if (isSerial4jLoggingEnabled()) {
            LOGGER.log(Level.INFO, "Setting device baud rate to " + baudRate.getRealBaud());
        }
        int returnValue = nativeTerminalDevice.setBaudRate0(baudRate.getBaudRate());
        if (isOperationFailed(returnValue)) {
            returnValue = nativeTerminalDevice.getErrno0();
        }
        ErrnoToException.throwFromErrno(returnValue, getSerialPort().getPath()); 
    } 

    public void setReadConfigurationMode(final ReadConfiguration readConfiguration, final int timeoutValue, final int minimumBytes) throws NoSuchDeviceException,
                                                                                                                                                PermissionDeniedException,
                                                                                                                                                BrokenPipeException,
                                                                                                                                                InvalidPortException,
                                                                                                                                                NoAvailableTtyDevicesException {
         
        if (isSerial4jLoggingEnabled()) {
            LOGGER.log(Level.INFO, "Setting reading config to " + readConfiguration.getDescription());
        }
        final int timoutByteValue = readConfiguration.getMode()[0] * timeoutValue;
        final int minimumBytesValue = readConfiguration.getMode()[1] * minimumBytes;
        final int errno = nativeTerminalDevice.setReadConfigurationMode0(Math.min(255, timoutByteValue), Math.min(255, minimumBytesValue));
        ErrnoToException.throwFromErrno(errno, "port is not invalid.");                                                                            
    }

    public final ReadConfiguration getReadConfigurationMode() {
        return ReadConfiguration.getFromNativeReadConfig(nativeTerminalDevice.getReadConfigurationMode0());
    }
    
    public final long writeBuffer(final String buffer) throws NoSuchDeviceException,
                                                          PermissionDeniedException,
                                                          BrokenPipeException,
                                                          InvalidPortException,
                                                          NoAvailableTtyDevicesException {
        final long numberOfWrittenBytes = nativeTerminalDevice.writeBuffer0(buffer, buffer.length());
        String message;
        if (numberOfWrittenBytes == -1) {
            message = "Write Permission [O_WRONLY] isnot granted, [Permissions: " + permissionsDescription + "]";
        } else {
            message = "Invalid Port " + nativeTerminalDevice.getSerialPort().getPath(); 
        }
        if (numberOfWrittenBytes < 1) {
            ErrnoToException.throwFromErrno(nativeTerminalDevice.getErrno0(), message);                                                                        
        }
        return numberOfWrittenBytes;
    }

    public long writeData(final int data) throws NoSuchDeviceException,
                                                 PermissionDeniedException,
                                                 BrokenPipeException,
                                                 InvalidPortException,
                                                 NoAvailableTtyDevicesException {
        final long numberOfWrittenBytes = nativeTerminalDevice.writeData0(data);
        String message;
        if (numberOfWrittenBytes == -1) {
            message = "Write Permission [O_WRONLY] isnot granted.";
        } else {
            message = "Invalid Port " + nativeTerminalDevice.getSerialPort().getPath(); 
        }
        if (numberOfWrittenBytes < 0) {
            ErrnoToException.throwFromErrno(nativeTerminalDevice.getErrno0(), message);                                                                            
        }
        return numberOfWrittenBytes;
    }

    public long writeData(final int[] data) throws NoSuchDeviceException,
                                                   PermissionDeniedException,
                                                   BrokenPipeException,
                                                   InvalidPortException,
                                                   NoAvailableTtyDevicesException {
        long numberOfWrittenBytes = 0;
        for (int i = 0; i < data.length; i++) {
           numberOfWrittenBytes += this.writeData(data[i]);
           if (numberOfWrittenBytes < 0) {
               break;
           }
        }
        return numberOfWrittenBytes;
    }

    public final long readData() {
        return nativeTerminalDevice.readData0();
    }

    public final long readBuffer() {
        return nativeTerminalDevice.readBuffer0();
    }

    public final String getReadBuffer() {
        return nativeTerminalDevice.getReadBuffer();
    }

    public final int getBaudRate() throws NoSuchDeviceException,
                                    PermissionDeniedException,
                                    BrokenPipeException,
                                    InvalidPortException,
                                    NoAvailableTtyDevicesException {
        if (isSerial4jLoggingEnabled()) {
            LOGGER.log(Level.INFO, "Getting device baud");
        }
        final int errno = nativeTerminalDevice.getBaudRate0();
        ErrnoToException.throwFromErrno(errno, getSerialPort().getPath()); 
        return errno;
    }

    public final String[] getSerialPorts() throws NoSuchDeviceException,
                                                  PermissionDeniedException,
                                                  BrokenPipeException,
                                                  InvalidPortException,
                                                  NoAvailableTtyDevicesException {
        fetchSerialPorts();
        return nativeTerminalDevice.getSerialPorts();
    }

    public void throwExceptionFromNativeErrno() throws NoSuchDeviceException,
                                                       PermissionDeniedException,
                                                       BrokenPipeException,
                                                       InvalidPortException,
                                                       NoAvailableTtyDevicesException {                                                
        final int errno = nativeTerminalDevice.getErrno0();
        ErrnoToException.throwFromErrno(errno, "Native Errno: " + errno);
    }

    public void closePort() throws NoSuchDeviceException,
                                   PermissionDeniedException,
                                   BrokenPipeException,
                                   InvalidPortException,
                                   NoAvailableTtyDevicesException {
        if (isSerial4jLoggingEnabled()) {
            LOGGER.log(Level.INFO, "Closing port: " + getSerialPort().getPath());
        }
        final int errno = nativeTerminalDevice.closePort0();
        ErrnoToException.throwFromErrno(errno, getSerialPort().getPath());
    }
    
    public void setSerial4jLoggingEnabled(final boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
    }

    public final boolean isSerial4jLoggingEnabled() {
        return loggingEnabled;
    }

    public final char[] getReadData() {
        return nativeTerminalDevice.getReadData();
    }

    public final InputStream getInputStream() {
        return inputStream;
    }

    public final OutputStream getOutputStream() {
        return outputStream;
    }

    public final SerialPort getSerialPort() {
        return nativeTerminalDevice.getSerialPort();
    }

    private void fetchSerialPorts() throws NoSuchDeviceException,
                                           PermissionDeniedException,
                                           BrokenPipeException,
                                           InvalidPortException,
                                           NoAvailableTtyDevicesException {
        if (isSerial4jLoggingEnabled()) {
            LOGGER.log(Level.INFO, "Fetching Serial ports.");
        }
        final int errno = nativeTerminalDevice.fetchSerialPorts0();
        ErrnoToException.throwFromErrno(errno, "No available devices !");                                    
    }

    private boolean isOperationFailed(final int returnValue) {
        return returnValue == Errno.ERR_OPERATION_FAILED.getValue();
    }
    
    private boolean isErrnoAvailable(final int errno) {
        return errno > 0;
    }
}