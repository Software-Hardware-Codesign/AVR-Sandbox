/**
 * @file Serial.h
 * @author pavl_g.
 * @brief Represents the serial port devices control and operation for POSIX systems.
 * @note This is the base [HAL] (Hardware abstraction layer) for the Serial4j api.
 * @version 0.1
 * @date 2022-08-24
 *
 * @copyright
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
#ifndef SERIAL
#define SERIAL "Serial4j-API"

#include<termios.h>
#include<stdio.h>
#include<stdlib.h>

#include<unistd.h>
#include<string.h>
#include<fcntl.h>
#include<errno.h>
#include<dirent.h>

#include<DynamicBuffer.h>
#include<SerialUtils.h>
#include<ErrnoUtils.h>

#define READ_CONFIG_SIZE (2)
#define DEVICES_DIR ((const char*) "/dev/")

/** The default flags for the base file api */
#define DEFAULT_FLAGS (O_RDWR | O_NONBLOCK | O_NOCTTY)

typedef unsigned short int TerminalFlag;

namespace TerminalDevice {

    /** Param@0 = VTIME, Param@1 = VMIN */
    const cc_t POLLING_READ[READ_CONFIG_SIZE] = {0, 0};
    const cc_t BLOCKING_READ_ONE_CHAR[READ_CONFIG_SIZE] = {0, 1};
    const cc_t READ_WITH_TIMEOUT[READ_CONFIG_SIZE] = {1, 0};
    const cc_t READ_WITH_INTERBYTE_TIMEOUT[READ_CONFIG_SIZE] = {1, 1};
    
    /**
     * Retrieves the termios of this tty device described by the file descriptor (fd).
     * 
     * @param fd the virtual file descriptor for this tty device.
     * @return a memory reference to the termios defining this tty device terminal attributes.
     */
    struct termios* getTermiosFromFd(int* fd);

    /**
     * @brief Fetches serial port devices on "/dev/" into [serialPorts] buffer.
     * @note Uses <dirent.h>, <SerialUtils.h>, <BufferUtils.h>, <DynamicBuffer.h> and <ErrnoUtils.h>.
     *
     * @return int (-3) if the directory ["/dev"] is invalid, (-4) if there are no tty
     * devices available at the ["/dev"] directory, (1) if operation succeeded.
     */
    int fetchSerialPorts(struct DynamicBuffer* serialPorts);

    /**
     * @brief Opens a serial port device with a name.
     * @note Uses <fcntl.h> Unix file base api and <ErrnoUtils.h>.
     *
     * @param port the path for the serial port device.
     * @return int* a memory reference for the port file descriptor.
     */
    int openPort(const char* port, int flag);

    /**
     * @brief Initializes the default terminal for this device with the following default charachteristics:
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
     * @return int (-1) for failure, (-2) for invalid port or (1) for success.
     */
    int initTermios(int* fd);

    /**
     * @brief Sets the Terminal Control Flag [c_cflag] for the [termios] variable.
     *
     * @param flag bits to set, concatenate the flags using bitwise OR [|].
     * @return int (-1) for failure, (-2) for invalid port or (1) for success.
     */
    int setTerminalControlFlag(TerminalFlag flag, int* fd);

    /**
     * @brief Sets the Terminal Local Flag [c_lflag] for the [termios] variable.
     *
     * @param flag bits to set, concatenate the flags using bitwise OR [|].
     * @return int (-1) for failure, (-2) for invalid port or (1) for success.
     */
    int setTerminalLocalFlag(TerminalFlag flag, int* fd);

    /**
     * @brief Sets the Terminal Output Flag [c_oflag] for the [termios] variable.
     *
     * @param flag bits to set, concatenate the flags using bitwise OR [|].
     * @return int (-1) for failure, (-2) for invalid port or (1) for success.
     */
    int setTerminalOutputFlag(TerminalFlag flag, int* fd);

    /**
     * @brief Sets the Terminal Input Flag [c_iflag] for the [termios] variable.
     *
     * @param flags bits to set, concatenate the flags using bitwise OR [|].
     * @return int (-1) for failure, (-2) for invalid port or (1) for success.
     */
    int setTerminalInputFlag(TerminalFlag flag, int* fd);

    /**
     * @brief Gets the Terminal Control Flag defined by the termios attributes for this serial device.
     * 
     * @return TerminalFlag the terminal control flag in [unsigned short int].
     */
    TerminalFlag getTerminalControlFlag(int* fd);

    /**
     * @brief Gets the Terminal Local Flag defined by the termios attributes for this serial device.
     * 
     * @return TerminalFlag the terminal local flag in [unsigned short int].
     */
    TerminalFlag getTerminalLocalFlag(int* fd);

    /**
     * @brief Gets the Terminal Input Flag defined by the termios attributes for this serial device.
     * 
     * @return TerminalFlag the terminal input flag in [unsigned short int].
     */
    TerminalFlag getTerminalInputFlag(int* fd);

    /**
     * @brief Gets the Terminal Output Flag defined by the termios attributes for this serial device.
     * 
     * @return TerminalFlag the terminal output flag in [unsigned short int].
     */
    TerminalFlag getTerminalOutputFlag(int* fd);

    /**
     * @brief Sets the Read Configuration Mode using a ReadConfiguration with a
     * VMIN_VALUE for lesser bytes to read and VTIME_VALUE for the elapsed time to
     * set if the ReadConfiguration mode provides a timeout.
     *
     * @param VTIME_VALUE the value of the read timeout elapsed time, the timer starts
     * with this value after read() is called.
     * @param VMIN_VALUE the value of the minimum number of bytes to read.
     * @return int (ERR_INVALID_PORT = -2) if port isn't available, (0) otherwise.
     */
    int setReadConfigurationMode(const int VTIME_VALUE, const int VMIN_VALUE, int* fd);

    /**
     * @brief Get the Read Configuration Mode in a new pointer.
     *
     * @return int* a memory reference to the new read configuration instance holding the VTIME and VMIN.
     */
    cc_t* getReadConfigurationMode(int* fd);

    /**
     * @brief Sets the Baud Rate object for the terminal io.
     *
     * @param baudRate the baud rate (bits/seconds).
     * @return int (1) for success, (-1) for failure, (-2) for invalid port.
     */
    int setBaudRate(int baudRate, int* fd);

    /**
     * @brief Gets the Baud Rate object.
     *
     * @return speed_t baud rate in integers.
     */
    speed_t getBaudRate(int* fd);

    /**
     * @brief Writes a data to the serial port device from a buffer.
     *
     * @param buffer a buffer to write to the file.
     * @param length the number of charachters to write from the buffer.
     * @return ssize_t the number of bytes written to the serial device, (-1) for failure, (-2) for invalid port.
     */
    ssize_t writeData(const void* buffer, int length, int* fd);

    /**
     * @brief Reads data from the serial port device and saves it to a buffer.
     *
     * @param buffer a buffer to read from the file to it.
     * @param length the number of the charachters to read by this buffer.
     * @return ssize_t the number of bytes read from the terminal, (-1) for failure, (-2) for invalid port.
     */
    ssize_t readData(void* buffer, int length, int* fd);

    /**
     * @brief Closes the serial port device.
     *
     * @return int (1) for success, (-1) for failure, (-2) for invalid port.
     */
    int closePort(int* fd);

}


#endif
