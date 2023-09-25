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

import java.util.Arrays;
import com.serial4j.core.errno.Errno;
import com.serial4j.core.serial.throwable.InvalidPortException;

/**
 * Provides a Unix terminal read configuration for the specified serial port of the 
 * terminal device.
 * 
 * @author pavl_g.
 */
public enum ReadConfiguration {
    POLLING_READ(new int[] {0, 0}, "Polling Read"),
    BLOCKING_READ_ONE_CHAR(new int[] {0, 1}, "Blocking read one charachter at a time"),
    READ_WITH_TIMEOUT(new int[] {1, 0}, "Polling Read with timeout"),
    READ_WITH_INTERBYTE_TIMEOUT(new int[] {1, 1}, "Blocking read with timeout"),
    ERR_INVALID_PORT(new int[] {(int) Errno.ERR_INVALID_PORT.getValue(), (int) Errno.ERR_INVALID_PORT.getValue()}, "Error invalid port");

    private int[] mode;
    private final String description;

    ReadConfiguration(final int[] mode, final String description) {
        this.mode = mode;
        this.description = description;
    }

    public int[] getMode() {
        return mode;
    }

    public String getDescription() {
        return description;
    }

    public static ReadConfiguration getFromNativeReadConfig(final int[] nativeReadConfig) {
        ReadConfiguration readConfiguration;
        if (nativeReadConfig[0] < 1 && nativeReadConfig[1] >= 1) {
            readConfiguration = ReadConfiguration.BLOCKING_READ_ONE_CHAR;
        } else if ((nativeReadConfig[0] | nativeReadConfig[1]) == 0) {
            readConfiguration = ReadConfiguration.POLLING_READ;
        } else if (nativeReadConfig[0] >= 1 && nativeReadConfig[1] >= 1) {
            readConfiguration = ReadConfiguration.READ_WITH_INTERBYTE_TIMEOUT;
        } else if ((nativeReadConfig[0] & nativeReadConfig[1]) == Errno.ERR_INVALID_PORT.getValue()) {
            throw new InvalidPortException("Cannot get read configuration for an invalid port !");
        } else {
            readConfiguration = ReadConfiguration.READ_WITH_TIMEOUT;
        }
        /* update the mode value with the native readConfig value */
        readConfiguration.mode = nativeReadConfig;
        return readConfiguration;
    }
}
