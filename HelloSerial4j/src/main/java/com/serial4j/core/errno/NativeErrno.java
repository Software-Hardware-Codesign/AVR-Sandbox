package com.serial4j.core.errno;

public final class NativeErrno {
    
    private NativeErrno() {
    }

    protected static native int getBadFileDescriptorErrno();

    protected static native int getBrokenPipeErrno();

    protected static native int getFileAlreadyOpenedErrno();

    protected static native int getFileIsDirectoryErrno();

    protected static native int getFileTableOverflowErrno();

    protected static native int getFileTooLargeErrno();

    protected static native int getInputOutputErrno();

    protected static native int getInterruptedSystemCallErrno();

    protected static native int getInvalidArgumentErrno();

    protected static native int getInvalidPortErrno();

    protected static native int getNoAvailableTtyDevicesErrno();

    protected static native int getNoSpaceLeftErrno();

    protected static native int getNoSuchDeviceErrno();

    protected static native int getNoSuchFileErrno();

    protected static native int getNotTtyDeviceErrno();

    protected static native int getOperationFailedErrno();

    protected static native int getPermissionDeniedErrno();

    protected static native int getReadOnlyFileSystemErrno();

    protected static native int getTooManyOpenedFilesErrno();

    protected static native int getTryAgainErrno();
}