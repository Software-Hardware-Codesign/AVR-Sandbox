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
package com.serial4j.core.util.natives;

import java.io.FileOutputStream;
import java.io.InputStream;
import com.serial4j.core.util.process.Semaphore;

/**
 * Helper utility for loading native images.
 * 
 * @author pavl_g.
 */
public final class NativeImageLoader {
    
    /**
     * A state for a critical section.
     */
    public enum State {
		TOGGLED,
	}
    
    /**
     * Represents an image domain.
     */
    public enum NativeImage {
        LINUX_x86_x64("native/Linux/linux-x86-x64/libserial4j.so"),
        LINUX_x86_x86("native/Linux/linux-x86-x86/libserial4j.so"),
        MAC_x86_x64("native/OSX/mac-x86-x64/libserial4j.dylb"),
        WIN_x86_x64("native/Windows/win-x86-x64/libserial4j.dll");

        private final String image;

        NativeImage(final String image) {
            this.image = image;
        }

        public String getImage() {
            return image;
        }
    }

    /**
     * Represents an operating system domain.
     */
    private enum OS {
        NAME(System.getProperty("os.name")),
        ARCH(System.getProperty("os.arch"));
        
        public static final String Linux = "Linux";
        public static final String Windows = "Windows";
        public static final String Mac = "Mac";
        public static final String Android = "Android";

        private final String data;

        OS(final String data) {
            this.data = data;
        }
        public String getData() {
            return data;
        }
        
        /** WIP */

        public static boolean is_x86_x64(final String arch) {
            return arch.equals("amd64");
        }
        public static boolean is_x86_x86(final String arch) {
            return arch.equals("amd32");
        }
        public static boolean is_arrm32(final String arch) {
            return arch.equals("armhf");
        }
    }

    protected static final Semaphore.Mutex MUTEX = new Semaphore.Mutex();
	protected static final Semaphore SEMAPHORE = Semaphore.build(MUTEX);
    
    private static boolean isLoaded = false;
	
    private NativeImageLoader() {
    }

    public static void loadLibrary() {
        if (OS.NAME.getData().equals(OS.Linux)) {
            loadLinux();
        } else if (OS.NAME.getData().equals(OS.Windows)) {
            /** WIP */
        } else if (OS.NAME.getData().equals(OS.Mac)) {
            /** WIP */
        } else if (OS.NAME.getData().equals(OS.Android)) {
            loadAndroid();
        }
    }

    private static void loadLinux() {
        if (OS.is_x86_x64(OS.ARCH.getData())) {
            extractImage(NativeImage.LINUX_x86_x64);
        } 
    }

    private static void loadWindows() {
        /** WIP */
    }

    private static void loadMac() {
        /** WIP */
    }

    private static void loadAndroid() {
        System.loadLibrary("Serial4j");
    }

    /**
     * Extracts an appropriate system-based native image from sources.
     * 
     * @param image the image to extract to the user directory.
     */
    private static void extractImage(final NativeImage image) {
        
        // protect a critical section
        initMutexWithLockData();

        SEMAPHORE.lock(NativeImageLoader.class);

        if (isLoaded) {
            SEMAPHORE.unlock(NativeImageLoader.class);
            return;
        }

        // extract the shipped native files
        final String workingDirectory = System.getProperty("user.dir");
        try {
            final InputStream nativeImageIS = NativeImageLoader.class.getClassLoader().getResourceAsStream(image.getImage());
            final byte[] buffer = new byte[nativeImageIS.available()];
            final FileOutputStream fos = new FileOutputStream(workingDirectory + "/libserial4j.so");  
            int numberOfReadBytes = 0;
            while ((numberOfReadBytes = nativeImageIS.read(buffer)) != -1) {
                /* use the numberOfReadBytes as the buffer length to write valid data */
                fos.write(buffer, 0, numberOfReadBytes);
            }
            nativeImageIS.close();
            fos.close();
            System.load(workingDirectory + "/libserial4j.so");
        } catch(final Exception exception) {
            exception.printStackTrace();
        }
        
        isLoaded = true;

        SEMAPHORE.unlock(NativeImageLoader.class);

    }

    private static void initMutexWithLockData() {
        MUTEX.setLockData(NativeImageLoader.State.TOGGLED);
        MUTEX.setMonitorObject(NativeImageLoader.class);
    }
}