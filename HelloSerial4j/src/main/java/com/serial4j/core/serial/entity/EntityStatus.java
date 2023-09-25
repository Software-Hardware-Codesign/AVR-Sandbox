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
package com.serial4j.core.serial.entity;

/**
 * Represents the serial entity status listener.
 *
 * @param <T> generic type for the {@link SerialMonitorEntity} type, either {@link serial.entity.impl.SerialWriteEntity}
 *           or {@link serial.entity.impl.SerialReadEntity}.
 *
 * @author pavl_g.
 */
public interface EntityStatus<T extends SerialMonitorEntity> {

    /**
     * Fired when the serial entity is initialized on the first monitor update.
     *
     * @param serialMonitorEntity the serial entity object.
     */
    void onSerialEntityInitialized(final T serialMonitorEntity);

    /**
     * Fired when the serial entity is terminated on the last monitor update.
     *
     * @param serialMonitorEntity the serial entity object.
     */
    void onSerialEntityTerminated(final T serialMonitorEntity);

    /**
     * Fired on each serial monitor update.
     *
     * @param serialMonitorEntity the serial entity object.
     */
    void onUpdate(final T serialMonitorEntity);
}
