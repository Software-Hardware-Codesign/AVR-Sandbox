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
package com.serial4j.core.serial.entity.impl;

import com.serial4j.core.serial.entity.EntityStatus;
import com.serial4j.core.serial.entity.SerialMonitorEntity;
import com.serial4j.core.serial.monitor.SerialMonitor;
import com.serial4j.core.serial.monitor.SerialMonitorException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Represents a Read entity for the {@link SerialMonitor}.
 * Use {@link SerialMonitor#startDataMonitoring(String, int)} to start this entity.
 *
 * @author pavl_g.
 */
public class SerialReadEntity extends SerialMonitorEntity {

    private StringBuffer stringBuffer = new StringBuffer();

    /**
     * Defines a read entity instance to read serial data from UART.
     * Use {@link SerialMonitor#startDataMonitoring(String, int)} to start this entity.
     *
     * @param serialMonitor the head serial monitor object.
     */
    public SerialReadEntity(final SerialMonitor serialMonitor) {
        super(serialMonitor, SerialReadEntity.class.getName());
    }

    @Override
    protected void onDataMonitored(SerialMonitor serialMonitor) {
        for (; ; ) {
            /* throws if not initialized yet */
            if (getTerminalDevice() == null) {
                throw new SerialMonitorException(SerialMonitorException.DEFAULT_MSG);
            }

            /* sanity check [terminate] flag */
            if (isTerminate()) {
                terminate();
                if (getSerialEntityStatusListener() != null) {
                    getSerialEntityStatusListener().onSerialEntityTerminated(this);
                }
                return;
            }

            if (!isSerialEntityInitialized()) {
                if (getSerialEntityStatusListener() != null) {
                    getSerialEntityStatusListener().onSerialEntityInitialized(this);
                }
                setSerialEntityInitialized(true);
            }

            if (getSerialEntityStatusListener() != null) {
                getSerialEntityStatusListener().onUpdate(this);
            }

            /* execute serial data tasks */
            for (int i = 0; i < getSerialDataListeners().size(); i++) {
                try {
                    final int count = getEntityStream().available();
                    if (count != 0) {
                        final char data = (char) getEntityStream().read();

                        /* send characters serially */
                        getSerialDataListeners().get(i).onDataReceived(data);

                        /* get a string buffer from a data frame */
                        /* send data frames separated by [\n\r] the return carriage/newline */
                        stringBuffer.append(data);
        
                        if (isUsingReturnCarriage()) {
                            if (stringBuffer.toString().contains("\n\r")) {
                                getSerialDataListeners().get(i).onDataReceived(stringBuffer.toString());
                                stringBuffer = new StringBuffer();
                            }
                        } else {
                            getSerialDataListeners().get(i).onDataReceived(stringBuffer.toString());
                            stringBuffer = new StringBuffer();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (!isMonitoringStarted()) {
                setMonitoringStarted(true);
            }
        }
    }

    @Override
    protected InputStream getEntityStream() {
        return getSerialMonitor().getReadEntityStream();
    }

    @Override
    protected boolean isSerialEntityInitialized() {
        return getSerialMonitor().isReadSerialEntityInitialized;
    }

    @Override
    protected void setSerialEntityInitialized(final boolean state) {
        getSerialMonitor().isReadSerialEntityInitialized = state;
    }

    @Override
    protected EntityStatus<SerialReadEntity> getSerialEntityStatusListener() {
        return getSerialMonitor().serialReadEntityEntityStatus;
    }
}
