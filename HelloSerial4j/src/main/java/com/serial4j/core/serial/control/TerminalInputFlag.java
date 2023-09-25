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

/**
 * Provides Unix [c_iflag] termios bits.
 * 
 * @author pavl_g.
 */
public final class TerminalInputFlag extends TerminalFlag {

    public static final TerminalInputFlag EMPTY_INSTANCE = new TerminalInputFlag(0);
    public static final TerminalInputFlag BRKINT = new TerminalInputFlag(0000002);
    public static final TerminalInputFlag ICRNL = new TerminalInputFlag(0000400);
    public static final TerminalInputFlag IGNBRK = new TerminalInputFlag(0000001);
    public static final TerminalInputFlag IGNCR = new TerminalInputFlag(0000200);
    public static final TerminalInputFlag IMAXBEL = new TerminalInputFlag(0020000);
    public static final TerminalInputFlag INLCR = new TerminalInputFlag(0000100);
    public static final TerminalInputFlag INPCK = new TerminalInputFlag(0000020);
    public static final TerminalInputFlag ISTRIP = new TerminalInputFlag(0000040);
    public static final TerminalInputFlag IUTF8 = new TerminalInputFlag(0040000);
    public static final TerminalInputFlag IXANY = new TerminalInputFlag(0004000);
    public static final TerminalInputFlag IXOFF = new TerminalInputFlag(0010000);
    public static final TerminalInputFlag IXON = new TerminalInputFlag(0002000);
    public static final TerminalInputFlag PARMRK = new TerminalInputFlag(0000010);

    protected TerminalInputFlag(final long value) {
        super(value);
    }
}
