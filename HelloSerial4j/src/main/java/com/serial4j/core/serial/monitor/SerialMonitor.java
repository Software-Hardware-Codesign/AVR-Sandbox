/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2022, Scrappers Team, The AVR-Sandbox Project
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
package com.serial4j.core.serial.monitor;

import com.serial4j.core.serial.control.TerminalControlFlag;
import com.serial4j.core.serial.control.TerminalLocalFlag;
import com.serial4j.core.serial.control.TerminalOutputFlag;
import com.serial4j.core.serial.control.TerminalInputFlag;
import com.serial4j.core.serial.ReadConfiguration;
import com.serial4j.core.serial.Permissions;
import com.serial4j.core.serial.BaudRate;
import com.serial4j.core.serial.SerialPort;
import com.serial4j.core.serial.TerminalDevice;
import com.serial4j.core.serial.throwable.PermissionDeniedException;
import com.serial4j.core.serial.throwable.NoAvailableTtyDevicesException;
import com.serial4j.core.serial.throwable.BrokenPipeException;
import com.serial4j.core.serial.throwable.NoSuchDeviceException;
import com.serial4j.core.serial.throwable.InvalidPortException;
import com.serial4j.core.serial.throwable.OperationFailedException;
import com.serial4j.core.serial.entity.impl.SerialReadEntity;
import com.serial4j.core.serial.entity.impl.SerialWriteEntity;
import com.serial4j.core.serial.entity.EntityStatus;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Monitors the UART Data Port using {@link SerialReadEntity} for serial data read and {@link SerialWriteEntity} for serial
 * data write.
 *
 * @author pavl_g.
 */
public final class SerialMonitor {

    private final ArrayList<SerialDataListener> serialDataListeners = new ArrayList<>();
    private final String monitorName;

    public volatile EntityStatus<SerialReadEntity> serialReadEntityEntityStatus;
    public volatile EntityStatus<SerialWriteEntity> serialWriteEntityEntityStatus;
    public volatile boolean isReadSerialEntityInitialized;
    public volatile boolean isWriteSerialEntityInitialized;
    public volatile boolean isMonitoringStarted;
    public volatile boolean useReturnCarriage = true;

    private volatile InputStream readEntityStream;
    private volatile OutputStream writeEntityStream;
    private volatile TerminalDevice terminalDevice;
    private volatile boolean terminate = false;
    private volatile SerialReadEntity serialReadEntity;
    private volatile SerialWriteEntity serialWriteEntity;

    /**
     * Instantiates a new SerialMonitor with a name.
     *
     * Use {@link SerialMonitor#startDataMonitoring(String, int)} to initialize and start
     * data monitoring.
     *
     * @param monitorName the name for this monitor.
     */
    public SerialMonitor(final String monitorName) {
        this.monitorName = monitorName;
    }

    /**
     * Initializes and starts data monitoring on a [port] and with a [baudRate]
     *
     * @param port specify the serial port.
     * @param baudRate specify the baud rate aka bits/seconds based for the connected FOsc.
     */
    public void startDataMonitoring(final String port, final BaudRate baudRate, final Permissions permissions)  throws NoSuchDeviceException,
                                                                                                                       PermissionDeniedException,
                                                                                                                       BrokenPipeException,
                                                                                                                       InvalidPortException,
                                                                                                                       OperationFailedException,
                                                                                                                       NoAvailableTtyDevicesException,
                                                                                                                       FileNotFoundException {  
        /* ignore timeout strategy */
        terminalDevice = new TerminalDevice();
        terminalDevice.setSerial4jLoggingEnabled(true);
        if (permissions != null) {
            terminalDevice.setPermissions(permissions);
        }
        terminalDevice.openPort(new SerialPort(port));
        terminalDevice.setBaudRate(baudRate);
        terminalDevice.initTermios();
        /* define terminal flags */
        final TerminalControlFlag TCF_VALUE = (TerminalControlFlag) TerminalControlFlag.CLOCAL
                                                            .append(TerminalControlFlag.CS8, TerminalControlFlag.CREAD);
        final TerminalLocalFlag TLF_VALUE = (TerminalLocalFlag) TerminalLocalFlag.EMPTY_INSTANCE
                                                            .disable(TerminalLocalFlag.ECHO, TerminalLocalFlag.ECHOK,
                                                                    TerminalLocalFlag.ECHOE, TerminalLocalFlag.ECHOKE,
                                                                    TerminalLocalFlag.ECHONL, TerminalLocalFlag.ECHOPRT,
                                                                    TerminalLocalFlag.ECHOCTL, TerminalLocalFlag.ISIG,
                                                                    TerminalLocalFlag.IEXTEN, TerminalLocalFlag.ICANON);
        final TerminalOutputFlag TOF_VALUE = (TerminalOutputFlag) TerminalOutputFlag.EMPTY_INSTANCE
                                                            .disable(TerminalOutputFlag.OPOST, TerminalOutputFlag.ONLCR);
        final TerminalInputFlag TIF_VALUE = (TerminalInputFlag) TerminalInputFlag.EMPTY_INSTANCE.disableAll();
        
        terminalDevice.setTerminalControlFlag(TCF_VALUE);
        terminalDevice.setTerminalLocalFlag(TLF_VALUE);
        terminalDevice.setTerminalOutputFlag(TOF_VALUE);
        terminalDevice.setTerminalInputFlag(TIF_VALUE);

        readEntityStream = terminalDevice.getInputStream();
        writeEntityStream = terminalDevice.getOutputStream();

        serialWriteEntity = new SerialWriteEntity(this);
        Thread.ofPlatform().start(serialWriteEntity);

        serialReadEntity = new SerialReadEntity(this);
        Thread.ofPlatform().start(serialReadEntity);

    }

    public String getMonitorName() {
        return monitorName;
    }

    /**
     * Tests whether the monitor is terminated by external means.
     *
     * @return true if terminated by the user, false otherwise.
     */
    public boolean isTerminate() {
        return terminate;
    }

    /**
     * Sets the termination flag to trigger termination on the next update.
     */
    public void setTerminate() {
        this.terminate = true;
    }

    /**
     * Gets the serial read input stream.
     *
     * @return the serial read input stream instance.
     */
    public InputStream getReadEntityStream() {
        return readEntityStream;
    }

    /**
     * Gets the terminal device controlling this monitor.
     *
     * @return the terminal device instance associated with this monitor.
     */
    public TerminalDevice getTerminalDevice() {
        return terminalDevice;
    }

    /**
     * Gets the Serial write output stream.
     *
     * @return an output stream representing this.
     */
    public OutputStream getWriteEntityStream() {
        return writeEntityStream;
    }

    /**
     * Gets the serial read entity used for reading data from the serial port using
     * {@link SerialMonitor#readEntityStream}.
     *
     * @see SerialReadEntity
     *
     * @return the serial monitor read instance.
     */
    public SerialReadEntity getSerialReadEntity() {
        return serialReadEntity;
    }

    /**
     * Gets the serial write entity used for writing data to the serial port using
     * {@link SerialMonitor#writeEntityStream}.
     *
     * @see SerialWriteEntity
     *
     * @return the serial monitor write instance.
     */
    public SerialWriteEntity getSerialWriteEntity() {
        return serialWriteEntity;
    }

    /**
     * Adds a new serial data listener to the list of the updatable listeners.
     *
     * @param serialDataListener the new serial data listener to add.
     */
    public void addSerialDataListener(final SerialDataListener serialDataListener) {
        if (serialDataListeners.contains(serialDataListener)) {
            return;
        }
        serialDataListeners.add(serialDataListener);
    }

    /**
     * Removes a serial data listener instance from the list of the list of the updatable listeners.
     *
     * @param serialDataListener the serial data listener to remove.
     */
    public void removeSerialDataListener(final SerialDataListener serialDataListener) {
        if (!serialDataListeners.contains(serialDataListener)) {
            return;
        }
        serialDataListeners.remove(serialDataListener);
    }

    /**
     * Sets the read entity status listener instance to listen for {@link SerialReadEntity} lifecycle.
     *
     * @param readThreadEntityStatus an instance to set.
     */
    public void setReadEntityListener(EntityStatus<SerialReadEntity> readThreadEntityStatus) {
        this.serialReadEntityEntityStatus = readThreadEntityStatus;
    }

    /**
     * Sets the write entity status listener instance to listen for {@link SerialWriteEntity} lifecycle.
     *
     * @param writeThreadEntityStatus an instance to set.
     */
    public void setWriteEntityStatus(EntityStatus<SerialWriteEntity> writeThreadEntityStatus) {
        this.serialWriteEntityEntityStatus = writeThreadEntityStatus;
    }

    /**
     * Tests whether [CR/LF] check is enabled.
     *
     * @apiNote
     * CR: Carriage return, defined by '\r'
     * LF: Line Feed, defined by '\n'
     *
     * @return true if [CR/LF] is enabled, false otherwise.
     */
    public boolean isUsingReturnCarriage() {
        return useReturnCarriage;
    }

    /**
     * Triggers the [CR/LF] check state flag.
     *
     * @param useReturnCarriage true to enable [CR/LF] and return data frames
     *                          at {@link SerialDataListener#onDataReceived(String)}, false to disable
     *                          both the [CR/LF] check and disable {@link SerialDataListener#onDataReceived(String)}.
     */
    public void setUseReturnCarriage(boolean useReturnCarriage) {
        this.useReturnCarriage = useReturnCarriage;
    }

    /**
     * Gets the serial data listeners used for listening to data changes at the
     * this monitor port.
     *
     * @return a list of serial data listeners for this monitor.
     */
    public ArrayList<SerialDataListener> getSerialDataListeners() {
        return serialDataListeners;
    }
}
