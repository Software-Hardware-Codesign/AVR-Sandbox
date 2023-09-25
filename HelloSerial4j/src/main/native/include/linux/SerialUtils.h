/**
 * @file SerialUtils.util
 * @author pavl_g.
 * @brief Represents utilities for the [Serial.h] library.
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
#ifndef SERIAL_UTILS
#define SERIAL_UTILS

#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<fcntl.h>
#include<unistd.h>

namespace SerialUtils {

        /**
         * @brief Converts a [file] into a [device] and outputs the 
         * result into a [buffer].
         * 
         * @param buffer a buffer to fill in the operation.
         * @param file the file to convert into a device.
         * @return char* a buffer of {"/dev/<file>"} formula.
         */
        static inline char* concatIntoDevice(char* buffer, const char* file, const char* DEVICES_DIR) {
            strcat(buffer, DEVICES_DIR);
            strcat(buffer, file);
            return buffer;
        }

        /**
         * @brief Tests whether the PATH specified is a real serial port.
         * 
         * @param path the path to specify if its a serial port.
         * @return int 1 if FD is a valid descriptor, 0 otherwise.
         */
        static inline int isSerialPort(char* path, const int FLAG) {
            int fdp = open(path, FLAG);
            int state = isatty(fdp);
            close(fdp);
            return state;
        }  
} 

#endif