/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2022, Scrappers Team, The Arithmos Project.
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
package com.serial4j.core.util.process;

import java.util.logging.Logger;

/**
 * A thread synchronizer utility using mutex data states.
 *
 * @see Semaphore.Mutex
 * @author pavl_g.
 */
public final class Semaphore {
    private final Mutex mutex;
    private static final Logger logger = Logger.getLogger(Semaphore.class.getName());

    public static final class Mutex {
        private Object lockData;
        private Object monitorObject;
        private Object unlockData;

        public <O, LD, ULD> Mutex(final O monitorObject, final LD lockData, final ULD unlockData) {
            this.monitorObject = monitorObject;
            this.lockData = lockData;
            this.unlockData = unlockData;
        }

        public Mutex() {
            this(new Object(), new Object(), new Object());
        }

        public void setLockData(Object lockData) {
            this.lockData = lockData;
        }

        public void setMonitorObject(Object monitorObject) {
            this.monitorObject = monitorObject;
        }

        protected void setUnlockData(Object unlockData) {
            this.unlockData = unlockData;
        }

        public Object getLockData() {
            return lockData;
        }

        public Object getMonitorObject() {
            return monitorObject;
        }

        public Object getUnlockData() {
            return unlockData;
        }

        /**
         * Creates a 'no job' job to trick the java loop.
         * (a java loop must have something inside to act upon).
         *
         * @return a null job.
         */
        protected final Void NO_JOB() {
            return null;
        }
    }

    private Semaphore(Mutex mutex) {
        this.mutex = mutex;
    }

    public static Semaphore build(Mutex mutex) {
        logger.fine("Semaphore initialized with monitor " + mutex.getMonitorObject() + " and data object " + mutex.getLockData());
        return new Semaphore(mutex);
    }

    public <MO> void lock(final MO monitorObject) {
        // order for a lock
        this.mutex.setMonitorObject(monitorObject);
        // format the unlock data for a new lock dispatcher
        this.mutex.setUnlockData(new Object());
        logger.fine("Lock obtained with monitor " + monitorObject);
    }

    public void waitForUnlock() {
        while (mutex.getLockData() != mutex.getUnlockData()) {
            final Void NO_JOB = mutex.NO_JOB();
        }
    }

    public <O> void unlock(final O locker) {
        if (locker != mutex.getMonitorObject()) {
            throw new IllegalMonitorStateException("The current thread doesn't own this object !");
        }
        // release the lock
        mutex.setUnlockData(mutex.getLockData());
        logger.fine(locker + " Unlocked");
    }

    public Mutex getMutex() {
        return mutex;
    }
}
