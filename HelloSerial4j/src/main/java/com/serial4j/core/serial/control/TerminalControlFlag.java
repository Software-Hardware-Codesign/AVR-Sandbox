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

public final class TerminalControlFlag extends TerminalFlag {

    public static final TerminalControlFlag EMPTY_INSTANCE = new TerminalControlFlag(0);
    public static final TerminalControlFlag CIBAUD = new TerminalControlFlag(002003600000);
    public static final TerminalControlFlag CS5 = new TerminalControlFlag(0000000);
    public static final TerminalControlFlag CS6 = new TerminalControlFlag(0000020);
    public static final TerminalControlFlag CS7 = new TerminalControlFlag(0000040);
    public static final TerminalControlFlag CS8 = new TerminalControlFlag(0000060);
    public static final TerminalControlFlag CLOCAL = new TerminalControlFlag(0004000);
    public static final TerminalControlFlag CREAD = new TerminalControlFlag(0000200);
    public static final TerminalControlFlag CMSPAR = new TerminalControlFlag(010000000000);
    public static final TerminalControlFlag CRTSCTS = new TerminalControlFlag(020000000000);
    public static final TerminalControlFlag CSTOPB = new TerminalControlFlag(0000100);
    public static final TerminalControlFlag HUPCL = new TerminalControlFlag(0002000);
    public static final TerminalControlFlag PARENB = new TerminalControlFlag(0000400);
    public static final TerminalControlFlag PARODD = new TerminalControlFlag(0001000);

    protected TerminalControlFlag(final long value) {
        super(value);
    }
}
