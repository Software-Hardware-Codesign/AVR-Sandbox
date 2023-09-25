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
package com.serial4j.core.serial.control;

import java.lang.Long;

/**
 * Represents the base implementation for the termios.h terminal flags values.
 * 
 * @author pavl_g.
 */
public abstract class TerminalFlag {

    private long value;

    /**
     * Instantiates a termios flag with a value to be settled from the native side.
     * @param value the value of the termios flag.
     */
    protected TerminalFlag(final long value) {
        this.value = value;
    }

    /**
     * Appends a flag's value into the current one's value.
     * 
     * @param flag a flag to append to this instance.
     * @return this flag instance for chained append.
     */
    public TerminalFlag append(final TerminalFlag flag) {
        this.value |= flag.getValue();
        return this;
    }

    /**
     * Disables a flag from the current one value.
     * 
     * @param flag a flag to disable.
     * @return this flag instance for chained call.
     */
    public TerminalFlag disable(final TerminalFlag flag) {
        this.value &= ~flag.getValue();
        return this;
    }

    /**
     * Appends a list of flags' values into the current one's value.
     * 
     * @param flags an args representing the flags list to append.
     * @return this flag instance for chained append.
     */
    public TerminalFlag append(final TerminalFlag...flags) {
        for (int i = 0; i < flags.length; i++) {
            append(flags[i]);
        }
        return this;
    }

    /**
     * Disables a list of flags from the current flag instance.
     * 
     * @param flags flags to disable.
     * @return this flag instance for chained call.
     */
    public TerminalFlag disable(final TerminalFlag...flags) {
        for (int i = 0; i < flags.length; i++) {
            disable(flags[i]);
        }
        return this;
    }

    /**
     * Enables all the terminal flags for this instance.
     * 
     * @return this instance for chained call.
     */
    public TerminalFlag enableAll() {
        this.value = Long.MAX_VALUE;
        return this;
    }

    /**
     * Disables all the terminal flags for this instance.
     * 
     * @return this instance for chained call.
     */
    public TerminalFlag disableAll() {
        this.value = 0;
        return this;
    }

    /**
     * Gets the value of the specified termios flag.
     * 
     * @return the value of the terminal flag.
     */
    public long getValue() {
        return value;
    }

    /**
     * Adjusts the value of the specified termios flag.
     * 
     * @param value the value of the terminal flag.
     */
    public void setValue(final long value) {
        this.value = value;
    }
}