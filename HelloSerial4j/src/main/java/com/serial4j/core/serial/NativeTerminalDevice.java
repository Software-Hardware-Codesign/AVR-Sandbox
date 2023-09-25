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

import com.serial4j.core.serial.SerialPort;
import com.serial4j.core.util.natives.NativeImageLoader;

/**
 * Represents the native java binding for Serial4j API, represented by 
 * [com_serial4j_core_serial_NativeTerminalDevice.h] natively.
 * 
 * @author pavl_g.
 */
public final class NativeTerminalDevice {

    /**
     * Static initializer: Loads the native image when this object is created or referenced.
     */
    static {
        NativeImageLoader.loadLibrary();
    }

    private SerialPort serialPort;
    private String[] serialPorts;
    private char[] readData;
    private String readBuffer = new String();

    protected NativeTerminalDevice() {
    }

    /**
     * Initializes the serial port path of the native terminal device before opening the terminal device port.
     * 
     * @param serialPort the object to initialize the serial port with.
     */
    protected void setSerialPort(final SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    /**
     * Retrieves the serial port associated with this terminal device.
     * 
     * @return the serial port object associated with this terminal device.
     */
    public SerialPort getSerialPort() {
        return this.serialPort;
    }

    /**
     * Retrieves all the available teletype terminal (tty) devices from the filesystem "/dev".
     * 
     * @return the available tty devices in an array of strings format.
     */
    public String[] getSerialPorts() {
        return this.serialPorts;
    }

    /**
     * Gets the read data in an up-to 32-bit integer format resulting from using the native method {@link NativeTerminalDevice#readData0()}.
     *
     * @return the read data from this terminal device in an up-to 32-bit integer format.
     */
    public char[] getReadData() {
        return this.readData;
    }

    public String getReadBuffer() {
        return this.readBuffer;
    }

    /**
     * Sets up the native environment for this terminal device.
     * 
     * @return (-1) if the jni env pointer is NULL, (1) for successful initialization.
     */
    protected static native int setupJniEnvironment0(); 

    /**
     * Adjusts the native terminal control [c_cflag] of the [termios] structure variable for this terminal device.
     * @apiNote The terminal control flag controls the way the terminal device r/w the charachters on the console.
     * For more, refer to the POSIX terminal control guide.
     * 
     * Default value = tty->c_cflag |= (CREAD | CS8 | CLOCAL); defined by {@link NativeTerminalDevice#initTermios0()}.
     * 
     * @param flag the flag to set the [c_cflag] to.
     * @return errno(-1) for failure, errno(-2) for invalid port, errno(1) for success.
     * @see com.serial4j.core.serial.control.TerminalControlFlag
     */
    protected native int setTerminalControlFlag(final long flag);

    /**
     * Adjusts the native terminal local [c_lflag] of the [termios] structure variable for this terminal device.
     * @apiNote The terminal local flag controls the way the terminal device interprets and displays the charachters on the console (i.e local console).
     * For more, refer to the POSIX terminal control guide.
     *
     * Default value = tty->c_lflag &= ~(ICANON | ECHO | ECHOE | ECHOK | ECHOKE | ECHONL | ECHOPRT | ECHOCTL | ISIG | IEXTEN); 
     * defined by {@link NativeTerminalDevice#initTermios0()}.
     *
     * @param flag the flag to set the [c_lflag] to.
     * @return errno(-1) for failure, errno(-2) for invalid port, errno(1) for success.
     * @see com.serial4j.core.serial.control.TerminalLocalFlag
     */    
    protected native int setTerminalLocalFlag(final long flag);

    /**
     * Adjusts the native terminal input [c_iflag] of the [termios] structure variable for this terminal device.
     * @apiNote The terminal input flag controls the way the terminal device receives and interprets the charachters on input.
     * For more, refer to the POSIX terminal control guide.
     * 
     * Default value = tty->c_iflag = 0x00; defined by {@link NativeTerminalDevice#initTermios0()}.
     *
     * @param flag the flag to set the [c_iflag] to.
     * @return errno(-1) for failure, errno(-2) for invalid port, errno(1) for success.
     * @see com.serial4j.core.serial.control.TerminalInputFlag
     */    
    protected native int setTerminalInputFlag(final long flag);

    /**
     * Adjusts the native terminal output [c_oflag] of the [termios] structure variable for this terminal device.
     * @apiNote The terminal output flag controls the way the terminal device transmits and interprets the charachters on output.
     * For more, refer to the POSIX terminal control guide.
     * 
     * Default value = tty->c_oflag &= ~(OPOST | ONLCR); defined by {@link NativeTerminalDevice#initTermios0()}.
     *
     * @param flag the flag to set the [c_oflag] to.
     * @return errno(-1) for failure, errno(-2) for invalid port, errno(1) for success.
     * @see com.serial4j.core.serial.control.TerminalOutputFlag
     */    
    protected native int setTerminalOutputFlag(final long flag);

    /**
     * Retrieves the terminal control flag from this terminal device port descriptor in 64-bit format.
     * 
     * @return the [c_cflag] value in longs.
     */
    protected native long getTerminalControlFlag();

    /**
     * Retrieves the terminal local flag from this terminal device port descriptor in 64-bit format.
     * 
     * @return the [c_lflag] value in longs.
     */
    protected native long getTerminalLocalFlag();

    /**
     * Retrieves the terminal input flag from this terminal device port descriptor in 64-bit format.
     * 
     * @return the [c_iflag] value in longs.
     */
    protected native long getTerminalInputFlag();

    /**
     * Retrieves the terminal output flag from this terminal device port descriptor in 64-bit format.
     * 
     * @return the [c_oflag] value in longs.
     */
    protected native long getTerminalOutputFlag();

    /**
     * Adjusts the read mode for this terminal device, the read mode is defined by the read timeout value and the minimum
     * number of bytes to read at that time.
     * 
     * Default value = BLOCKING_READ_ONE_CHAR {0, 1}; defined by {@link NativeTerminalDevice#initTermios0()}.
     *
     * @param mode the read mode in a 2 lengthed array format, where first index refers to the timeout config and second index
     *             refers to the bytes config.
     * @param VTIME_VALUE the value of the read timeout, applied only when the first index of [mode] is 1 (aka read timeout is activated).
     * @param VMIN_VALUE the value of the minimum byte to read in this time.
     * @return errno(-1) for failure, errno(-2) for invalid port, errno(1) for success.
     * @see com.serial4j.core.serial.ReadConfiguration
     */
    protected native int setReadConfigurationMode0(final int VTIME_VALUE, final int VMIN_VALUE);

    /**
     * Gets the read configuration for this terminal device defining the timeout value as the first index and 
     * the minimum number of read byte in this timeout as the second index.
     *
     * Default value = BLOCKING_READ_ONE_CHAR {0, 1}; defined by {@link NativeTerminalDevice#initTermios0()}.
     *
     * @return an array refering to the read mode, where index [0] represents the read timeout, index [1] represents
     *         the minimum bytes to read.
     */
    protected native int[] getReadConfigurationMode0();

    /**
     * Retrieves the last error encountered by the native code,
     *
     * @return the last error code from the native <errno.h>.
     */
    protected native int getErrno0();

    /**
     * Fetches the available system teletype terminal devices (tty) located within "/dev" directory
     * and insert the result into {@link NativeTerminalDevice#serialPorts}.
     *
     * @return errno(-1) for failure, errno(-2) for invalid port, errno(1) for success.
     */
    protected native int fetchSerialPorts0();

    /**
     * Retrieves the baud rate POSIX code for this terminal process, find more at <./usr/include/x86_64-linux-gnu/bits/termios.h>.
     * 
     * @return the baud rate code in integers.
     */
    protected native int getBaudRate0();

    /**
     * Writes an integer buffer to this terminal device.
     * 
     * @param data an integer data buffer to write up-to 32-bit.
     * @return the number of written bytes in long format.
     */
    protected native long writeData0(final int data);

    /**
     * Reads the data from the terminal device and inserts the result into an integer buffer 
     * {@link NativeTerminalDevice#readData}.
     *
     * @return the number of read bytes.
     */
    protected native long readData0();

    /**
     * Writes a string buffer (const char*) with a length to this terminal device.
     * 
     * @param buffer a string buffer to write to this terminal device.
     * @param length the string buffer length in integers, this minimizes the jni native calls.
     * @return the number of written bytes to this terminal device.
     */
    protected native long writeBuffer0(final String buffer, final int length);

    /**
     * Reads the data from this terminal device and insert the result into the {@link NativeTerminalDevice#readBuffer}
     * string buffer.
     *
     * @return the number of read bytes from this terminal device.
     */
    protected native long readBuffer0();

    /**
     * Opens this terminal device using the path to the port [port] in strings and the port permissions [flag] in integers.
     *
     * @param port the port path in strings. 
     * @param int the flag for the base file control native api [fcntl].
     * @return errno(-1) for failure, errno(1) for success.
     */
    protected native int openPort0(final String port, final int flag);

    /**
     * Initializes this terminal device with the default terminal flags and read timeout properties:
     * -----------
     * # c_cflag: for control mode flags.
     * *** Enable these bits:
     * - [CREAD]: Allow input to be received.
     * - [CS8]: For charachter size 8-bit, you can use the bit mask CSIZE to read this value.
     * - [CLOCAL]: Ignore modem status lines (dont check carrier signal).
     * -----------
     * # c_lflag: for local mode flags.
     * ***Disable these bits:
     * - [ICANON]: Canonical mode (line-by-line) input.
     * - [ECHO]: Echo input characters.
     * - [ECHOE]: Perform ERASE visually.
     * - [ECHOK]: Echo KILL visually.
     * - [ECHOKE]: Dont output a newline after echoed KILL.
     * - [ECHONL]: Echo NL (in canonical mode) even if echoing is disabled.
     * - [ECHOPRT]: Echo deleted characters backward (between \ and / ).
     * - [ECHOCTL]: Echo control characters visually (e.g., ^L ).
     * - [ISIG]: Enable signal-generating characters (INTR, QUIT, SUSP).
     * - [IEXTEN]: Enable extended processing of input characters.
     * -----------
     * # c_oflag: for output mode flags.
     * ***Disable these bits:
     * - [OPOST]: Perform output postprocessing.
     * - [ONLCR]: Map NL to CR-NL on output.
     * -----------
     * # c_iflag: for input mode flags.
     * ***Disable all input bit masks.
     * -----------
     * # c_cc: For control characters.
     * ***Sets to BLOCKING READ ONE CHAR AT A TIME MODE.
     * -----------
     *
     * @apiNote This should be called right after {@link NativeTerminalDevice#openPort0(String, int)}.
     * 
     * @return errno(-1) for failure, errno(-2) for invalid port, errno(1) for success.
     */
    protected native int initTermios0();

    /**
     * Adjusts the baud rate aka. the speed of data transmission in bits/seconds for this bus.
     *
     * @param baudRate the baud rate POSIX native code, find more about baud rate codes at <./usr/include/x86_64-linux-gnu/bits/termios.h>.
     * @return errno(-1) for failure, errno(-2) for invalid port, errno(1) for success.
     */    
    protected native int setBaudRate0(int baudRate);

    /**
     * Closes the serial port of this terminal device releasing the resources.
     * @apiNote This call triggers {@link NativeTerminalDevice#serialPort#portOpened} to 0, 
     * the port descriptor {@link NativeTerminalDevice#serialPort#fd} to 0 and the port path {@link NativeTerminalDevice#serialPort#fd} to "".
     * 
     * @return errno(-1) for failure, errno(-2) for invalid port, errno(1) for success.
     */
    protected native int closePort0();
}