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
package com.serial4j.example.monitor;

import com.serial4j.core.serial.BaudRate;
import com.serial4j.core.serial.entity.EntityStatus;
import com.serial4j.core.serial.entity.impl.WritableCapsule;
import com.serial4j.core.serial.entity.impl.SerialWriteEntity;
import com.serial4j.core.serial.monitor.SerialDataListener;
import com.serial4j.core.serial.monitor.SerialMonitor;

/**
 * Represents an example for UART using jSerialComm.
 *
 * @author pavl_g.
 */
public class HelloSerialMonitor implements SerialDataListener, EntityStatus<SerialWriteEntity>, Runnable {

    private static boolean isDataSent;

    @Override
    public void run() {
        System.out.println(Thread.currentThread());
        System.out.println("---------------Welcome to Serial4j Monitor Testcase---------------");
        try {
            final SerialMonitor serialMonitor = new SerialMonitor("Monitor A");
            serialMonitor.startDataMonitoring("/dev/ttyUSB0", BaudRate.B57600, null);
            serialMonitor.setWriteEntityStatus(this);
            serialMonitor.addSerialDataListener(this);

            // /* write data to UART with return-carriage/newline */
            // delay(2000);
            // writeInUARTCapsule(serialMonitor, "0\n\r");

            /* terminate after 20 seconds */
            delay(20000);
            serialMonitor.setTerminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeInUARTCapsule(final SerialMonitor serialMonitor, final String data) {
        final WritableCapsule writableCapsule = new WritableCapsule();
        writableCapsule.write(data);
        serialMonitor.getSerialWriteEntity().addWritableCapsule(writableCapsule);
    }

    private void delay(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSerialEntityInitialized(SerialWriteEntity serialMonitorEntity) {

    }

    @Override
    public void onSerialEntityTerminated(SerialWriteEntity serialMonitorEntity) {
        System.err.println("Serial4j: Terminated");
    }

    @Override
    public void onUpdate(SerialWriteEntity serialMonitorEntity) {
        if (isDataSent) {
            return;
        }
        /* send data after 5 seconds */
        delay(5000);
        /* send data in a UART capsule on the serial write entity thread */
        writeInUARTCapsule(serialMonitorEntity.getSerialMonitor(), "0\n");
        isDataSent = true;
    }

    @Override
    public void onDataReceived(int data) {
        
    }

    @Override
    public void onDataTransmitted(int data) {

    }

    @Override
    public void onDataReceived(String data) {
        System.out.println(Thread.currentThread());
        System.out.println(data);
    }
}
