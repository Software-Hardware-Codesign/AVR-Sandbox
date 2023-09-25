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

/**
 * Represents a serial write capsule for the {@link SerialWriteEntity}.
 *
 * @author pavl_g.
 */
public final class WritableCapsule implements Cloneable {

    private String data;
    private boolean dataWritten;

    /**
     * Specifies the data to be written on the next update of the
     * SerialWriteEntity.
     *
     * @param data the data to be written to the UART.
     */
    public void write(final String data) {
        this.data = data;
    }

    /**
     * Retrieves the data of the capsule.
     *
     * @return the data in string.
     */
    public String getData() {
        return data;
    }

    /**
     * Tests whether data is written to the UART.
     *
     * @return true if the data is written to the UART, false otherwise.
     */
    public boolean isDataWritten() {
        return dataWritten;
    }

    /**
     * Sets the flag for data write to the serial UART, where; true commands and
     * stops the data write to the serial UART, false otherwise.
     *
     * @param dataWritten true to keep writing data in the next update, false otherwise.
     */
    public void setDataWritten(boolean dataWritten) {
        this.dataWritten = dataWritten;
    }
}
