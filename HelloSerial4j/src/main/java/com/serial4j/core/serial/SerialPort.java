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

/**
 * Represents a Unix terminal device port properties wrapper.
 * 
 * @author pavl_g.
 */
public final class SerialPort {

   private String path;
   private int portOpened;
   private int fd;
   private int ioFlag;

   /**
    * Instantiates a new serial port for a terminal device with a port path.
    * The rest of the port data is assigned from the jni side after opening the device.
    * 
    * @param path the port path on the file system.
    */
   public SerialPort(final String path) {
        this.path = path;
   }

  /**
   * Gets the io flags defined by the {@link com.serial4j.core.serial.Permissions}.
   * The io flags setter is assigned from the jni side by {@link com.serial4j.core.serial.NativeTerminalDevice#openPort(String, int)}.
   * 
   * @return a number format representing the IO flag for the POSIX base file api.
   */
   public int getIOFlag() {
       return ioFlag;
   }
   
  /**
   * Gets the path to this port.
   * 
   * @return a String representing the path to this port.
   */
   public String getPath() {
       return path;
   }
   
  /**
   * Gets the virtual file descriptor defining this port.
   * The fd (file descriptor) setter is assigned from the jni side by {@link com.serial4j.core.serial.NativeTerminalDevice#openPort(String, int)}.
   * 
   * @return a number format representing the file descriptor for this port.
   */
   public int getFd() {
       return fd;
   }

  /**
   * Tests whether this port is still opened.
   * This flag setter is assigned from the jni side by {@link com.serial4j.core.serial.NativeTerminalDevice#openPort(String, int)} 
   * and {@link com.serial4j.core.serial.NativeTerminalDevice#closePort()}. 
   * 
   * @return true if the port is still alive, false if the port is closed.
   */
   public boolean isPortOpened() {
       return this.portOpened == 1;
   }
}