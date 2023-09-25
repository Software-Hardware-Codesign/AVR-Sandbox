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

public final class TerminalOutputFlag extends TerminalFlag {

    public static final TerminalOutputFlag EMPTY_INSTANCE = new TerminalOutputFlag(0);
    public static final TerminalOutputFlag BS0 = new TerminalOutputFlag(0000000);
    public static final TerminalOutputFlag BS1 = new TerminalOutputFlag(0020000);
    public static final TerminalOutputFlag CR0 = new TerminalOutputFlag(0000000);
    public static final TerminalOutputFlag CR1 = new TerminalOutputFlag(0001000);
    public static final TerminalOutputFlag CR2 = new TerminalOutputFlag(0002000);
    public static final TerminalOutputFlag CR3 = new TerminalOutputFlag(0003000);
    public static final TerminalOutputFlag FF0 = new TerminalOutputFlag(0000000);
    public static final TerminalOutputFlag FF1 = new TerminalOutputFlag(0100000);
    public static final TerminalOutputFlag NL0 = new TerminalOutputFlag(0000000);
    public static final TerminalOutputFlag NL1 = new TerminalOutputFlag(0000400);
    public static final TerminalOutputFlag OCRNL = new TerminalOutputFlag(0000010);
    public static final TerminalOutputFlag OFDEL = new TerminalOutputFlag(0000200);
    public static final TerminalOutputFlag OFILL = new TerminalOutputFlag(0000100);
    public static final TerminalOutputFlag OLCUC = new TerminalOutputFlag(0000002);
    public static final TerminalOutputFlag ONLCR = new TerminalOutputFlag(0000004);
    public static final TerminalOutputFlag ONLRET = new TerminalOutputFlag(0000040);
    public static final TerminalOutputFlag ONOCR = new TerminalOutputFlag(0000020);
    public static final TerminalOutputFlag OPOST = new TerminalOutputFlag(0000001);
    public static final TerminalOutputFlag TAB0 = new TerminalOutputFlag(0000000);
    public static final TerminalOutputFlag TAB1 = new TerminalOutputFlag(0004000);
    public static final TerminalOutputFlag TAB2 = new TerminalOutputFlag(0010000);
    public static final TerminalOutputFlag TAB3 = new TerminalOutputFlag(0014000);
    public static final TerminalOutputFlag VT0 = new TerminalOutputFlag(0000000);
    public static final TerminalOutputFlag VT1 = new TerminalOutputFlag(0040000);

    protected TerminalOutputFlag(final long value) {
        super(value);
    }
}
