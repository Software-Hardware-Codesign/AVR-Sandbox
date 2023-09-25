/**
 * @file com_serial4j_core_serial_NativeTerminalDevice.c
 * @author pavl_g.
 * @brief Instantiates a native interface of Serial4j API for the java programming language.
 * @version 0.1
 * @date 2022-09-06
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
#include<jni/com_serial4j_core_serial_NativeTerminalDevice.h>
#include<errno.h>
#include<TerminalDevice.h>
#include<stdlib.h>
#include<JniUtils.h>

struct DynamicBuffer serialPorts;
cc_t* config = (cc_t*) calloc(2, sizeof(cc_t));

const cc_t* getReadConfiguration(jobject* object);

const cc_t* getReadConfiguration(jobject* object) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(object);
    const cc_t* readConfigBuffer = TerminalDevice::getReadConfigurationMode(&fd);
    return readConfigBuffer;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_setupJniEnvironment0
  (JNIEnv* env, jclass clazz) {
    return JniUtils::setupJavaEnvironment(env, JNI_VERSION_1_8);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_setTerminalControlFlag
  (JNIEnv* env, jobject object, jlong flag) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(&object);
    return TerminalDevice::setTerminalControlFlag((jlong) flag, &fd);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_setTerminalLocalFlag
  (JNIEnv* env, jobject object, jlong flag) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(&object);
    return TerminalDevice::setTerminalLocalFlag((jlong) flag, &fd);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_setTerminalInputFlag
  (JNIEnv* env, jobject object, jlong flag) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(&object);
    return TerminalDevice::setTerminalInputFlag((jlong) flag, &fd);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_setTerminalOutputFlag
  (JNIEnv* env, jobject object, jlong flag) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(&object);
    return TerminalDevice::setTerminalOutputFlag((jlong) flag, &fd);
}

JNIEXPORT jlong JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_getTerminalControlFlag
  (JNIEnv* env, jobject object) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(&object);
    return (jlong) TerminalDevice::getTerminalControlFlag(&fd);
}

JNIEXPORT jlong JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_getTerminalLocalFlag
  (JNIEnv* env, jobject object) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(&object);
    return (jlong) TerminalDevice::getTerminalLocalFlag(&fd);
} 

JNIEXPORT jlong JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_getTerminalInputFlag
  (JNIEnv* env, jobject object) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(&object);
    return (jlong) TerminalDevice::getTerminalInputFlag(&fd);
}

JNIEXPORT jlong JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_getTerminalOutputFlag
  (JNIEnv* env, jobject object) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(&object);
    return (jlong) TerminalDevice::getTerminalOutputFlag(&fd);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_setReadConfigurationMode0
  (JNIEnv* env, jobject object, jint timeoutValue, jint minimumBytes) {
    
    jint fd = JniUtils::getPortDescriptorFromSerialPort(&object);

    return TerminalDevice::setReadConfigurationMode(timeoutValue, minimumBytes, &fd);
}

JNIEXPORT jintArray JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_getReadConfigurationMode0
  (JNIEnv* env, jobject object) {
    const cc_t* readConfig = getReadConfiguration(&object);
    int* buffer = (int*) calloc(2, sizeof(int));
    buffer[0] = readConfig[0];
    buffer[1] = readConfig[1];
    return JniUtils::getIntArrayFromBuffer(buffer, READ_CONFIG_SIZE);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_fetchSerialPorts0
  (JNIEnv* env, jobject object) {
     int state = TerminalDevice::fetchSerialPorts(&serialPorts);
     
     int length = *serialPorts.getItemsCount();
     jobjectArray stringArray = JniUtils::createNewArrayFromBuffer(java_lang_String, length);
     JniUtils::setObjectField(&object, "serialPorts", "[Ljava/lang/String;", stringArray);

     jobject* objectBuffer = JniUtils::getObjectBufferFromString((const char**) serialPorts.getBuffer(), length);
     JniUtils::setArrayElements(stringArray, length, objectBuffer);
     BufferUtils::deleteBuffer(objectBuffer);

     return state;  
}

JNIEXPORT jlong JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_writeData0
  (JNIEnv* env, jobject object, jint buffer) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(&object);
    return TerminalDevice::writeData(&buffer, 1, &fd);
}

JNIEXPORT jlong JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_readData0
  (JNIEnv* env, jobject object) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(&object);
    int length = getReadConfiguration(&object)[1] <= 0 ? getReadConfiguration(&object)[1] : 1;
    
    jchar* buffer = (jchar*) calloc(length, sizeof(jchar));
    int state = TerminalDevice::readData((void*) buffer, length, &fd);
    
    jcharArray arrayBuffer = JniUtils::getCharArrayFromBuffer(buffer, length);
    JniUtils::setObjectField(&object, "readData", "[C", arrayBuffer);

    BufferUtils::deleteBuffer(buffer);
    return state;
}

JNIEXPORT jlong JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_writeBuffer0
  (JNIEnv* env, jobject object, jstring string, jint length) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(&object);
    const char* buffer = JniUtils::getBufferFromString(string);
    int state = TerminalDevice::writeData(buffer, length, &fd);
    BufferUtils::deleteBuffer((void*) buffer);
    return state;
}

JNIEXPORT jlong JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_readBuffer0
  (JNIEnv* env, jobject object) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(&object);
    /* construct a termios and get the control charachter flag from fd */
    struct termios* tty = TerminalDevice::getTermiosFromFd(&fd);

    int length = tty->c_cc[VMIN];
  
    const char* strBuffer = (const char*) calloc(length, sizeof(const char));

    long numberOfReadChars = TerminalDevice::readData((void*) strBuffer, length, &fd);

    /* get the java string buffer and setup its data with the buffer */
    JniUtils::setObjectField(&object, "readBuffer", "Ljava/lang/String;",  (*JniUtils::getJniEnv())->NewStringUTF(strBuffer));
    
    BufferUtils::deleteBuffer(tty);
    BufferUtils::deleteBuffer((void*) strBuffer);
    

    return numberOfReadChars;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_setBaudRate0
  (JNIEnv* env, jobject object, jint baudRate) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(&object);
    return TerminalDevice::setBaudRate(baudRate, &fd);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_getBaudRate0
  (JNIEnv* env, jobject object) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(&object);
    return TerminalDevice::getBaudRate(&fd);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_initTermios0
  (JNIEnv* env, jobject object) {
    jint fd = JniUtils::getPortDescriptorFromSerialPort(&object);
    return TerminalDevice::initTermios(&fd);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_openPort0
  (JNIEnv* env, jobject object, jstring strBuffer, jint flag) {
   
    int fd = TerminalDevice::openPort((*JniUtils::getJniEnv())->GetStringUTFChars(strBuffer, 0), flag);
    jobject serialPortObject = JniUtils::getSerialPortFromTerminalDevice(&object);
    JniUtils::setIntField(&serialPortObject, "portOpened", "I", 1);
    JniUtils::setIntField(&serialPortObject, "fd", "I", fd);
    JniUtils::setIntField(&serialPortObject, "ioFlag", "I", flag);

    return fd;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_closePort0
  (JNIEnv* env, jobject object) {
    
    jobject serialPortObject = JniUtils::getSerialPortFromTerminalDevice(&object);
    
    jint fd = JniUtils::getPortDescriptorFromSerialPort(&object);
    JniUtils::setIntField(&serialPortObject, "portOpened", "I", 0);
    JniUtils::setIntField(&serialPortObject, "fd", "I", 0);
    JniUtils::setObjectField(&serialPortObject, "path", "Ljava/lang/String;", (*JniUtils::getJniEnv())->NewStringUTF(""));

    return TerminalDevice::closePort(&fd);
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_serial_NativeTerminalDevice_getErrno0
  (JNIEnv* env, jobject object) {
    return errno;
}