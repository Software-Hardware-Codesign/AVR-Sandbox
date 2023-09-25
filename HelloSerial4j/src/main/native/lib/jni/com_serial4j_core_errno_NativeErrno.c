#include<jni/com_serial4j_core_errno_NativeErrno.h>
#include<ErrnoUtils.h>

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getBadFileDescriptorErrno
  (JNIEnv* env, jclass clazz) {
    return EBADFD;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getBrokenPipeErrno
  (JNIEnv* env, jclass clazz) {
    return EPIPE;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getFileAlreadyOpenedErrno
  (JNIEnv* env, jclass clazz) {
    return EEXIST;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getFileIsDirectoryErrno
  (JNIEnv* env, jclass clazz) {
    return EISDIR;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getFileTableOverflowErrno
  (JNIEnv* env, jclass clazz) {
    return ENFILE;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getFileTooLargeErrno
  (JNIEnv* env, jclass clazz) {
    return EFBIG;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getInputOutputErrno
  (JNIEnv* env, jclass clazz) {
    return EIO;
} 

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getInterruptedSystemCallErrno
  (JNIEnv* env, jclass clazz) {
    return EINTR;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getInvalidArgumentErrno
  (JNIEnv* env, jclass clazz) {
    return EINVAL;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getInvalidPortErrno
  (JNIEnv* env, jclass clazz) {
    return ERR_INVALID_PORT;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getNoAvailableTtyDevicesErrno
  (JNIEnv* env, jclass clazz) {
    return ERR_NO_AVAILABLE_TTY_DEVICES;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getNoSpaceLeftErrno
  (JNIEnv* env, jclass clazz) {
    return ENOSPC;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getNoSuchDeviceErrno
  (JNIEnv* env, jclass clazz) {
    return ENXIO;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getNoSuchFileErrno
  (JNIEnv* env, jclass clazz) {
    return ENOENT;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getNotTtyDeviceErrno
  (JNIEnv* env, jclass clazz) {
    return ERR_NO_AVAILABLE_TTY_DEVICES;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getOperationFailedErrno
  (JNIEnv* env, jclass clazz) {
    return ERR_OPERATION_FAILED;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getPermissionDeniedErrno
  (JNIEnv* env, jclass clazz) {
    return EACCES;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getReadOnlyFileSystemErrno
  (JNIEnv* env, jclass clazz) {
    return EROFS;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getTooManyOpenedFilesErrno
  (JNIEnv* env, jclass clazz) {
    return EMFILE;
}

JNIEXPORT jint JNICALL Java_com_serial4j_core_errno_NativeErrno_getTryAgainErrno
  (JNIEnv* env, jclass clazz) {
    return EAGAIN;
}
