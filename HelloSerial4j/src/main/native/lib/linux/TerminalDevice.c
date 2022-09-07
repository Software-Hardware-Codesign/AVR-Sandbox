#include<TerminalDevice.h>

int* Terminal::TerminalDevice::openPort(const char* port) {
    this->portFileDescriptor = open(port, flags);
    if (this->portFileDescriptor > 0) {
        Logger::LOGS(SERIAL, "Serial Port Opened.");
    } else {
        Logger::LOGS(SERIAL, "Serial Port isn't available.");
    }
    return &(this->portFileDescriptor);
}

int Terminal::TerminalDevice::fetchSerialPorts() {
    Logger::LOGS(SERIAL, "Fetching serial devices.");

    DIR* dirp = opendir(DEVICES_DIR);
    
    /* sanity check the input */
    if (dirp == NULL) {
        return ERR_INVALID_DIR;
    }

    struct dirent* dp = (struct dirent*) calloc(1, sizeof(struct dp*));
    struct stat filestat;

    /* start reading available ports */
    while ((dp = readdir(dirp)) != NULL) {
        
        char* device = (char*) calloc(1, sizeof(char*));
        device = SerialUtils::concatIntoDevice(device, dp->d_name, DEVICES_DIR);
        
        /* delete the device buffer if it's not a serial port */
        if (!SerialUtils::isSerialPort(device, flags)) {
            BufferUtils::deleteBuffer(device);
            continue;
        }

        /* add the device to the serial ports major buffer and count up */
        (this->serialPorts).add(device);
    }

    closedir(dirp);
    BufferUtils::deleteBuffer(dp);

    if ((this->serialPorts).getItem(0) == NULL) {
        return ERR_NO_AVAILABLE_TTY_DEVICES;
    }

    return OPERATION_SUCCEEDED;
}

int Terminal::TerminalDevice::initTermios() {
    if (this->portFileDescriptor <= 0) {
        return ERR_INVALID_PORT;
    }

    /* initialize the teletype terminal port */
    tcgetattr(this->portFileDescriptor, &(this->tty));

    /* setup tty attributes */
    this->tty.c_cflag &= ~(CBAUDEX | CBAUD); /* clear BAUDs */
    this->tty.c_cflag |= (CREAD | CS8 | CLOCAL); /* set flags */
    this->tty.c_lflag &= ~(ICANON | ECHO | ECHOE | ECHOK | ECHOKE | ECHONL | ECHOPRT | ECHOCTL | ISIG | IEXTEN);
    this->tty.c_oflag &= ~(OPOST | ONLCR);
    this->tty.c_iflag = 0x00;

    /* define default read mode as blocking read on char at a time */
    this->tty.c_cc[VTIME] = BLOCKING_READ_ONE_CHAR[0];
    this->tty.c_cc[VMIN] = BLOCKING_READ_ONE_CHAR[1];

    /* apply attriutes flag bits */

    return tcsetattr(this->portFileDescriptor, TCSAFLUSH, &(this->tty));
}

int Terminal::TerminalDevice::setReadConfigurationMode(const cc_t* readConfig, const int VTIME_VALUE, const int VMIN_VALUE) {
    if (this->portFileDescriptor <= 0) {
        return ERR_INVALID_PORT;
    }

    this->tty.c_cc[VTIME] = readConfig[0] * VTIME_VALUE;
    this->tty.c_cc[VMIN] = readConfig[1] * VMIN_VALUE;

    return tcsetattr(this->portFileDescriptor, TCSAFLUSH, &(this->tty));
}

cc_t* Terminal::TerminalDevice::getReadConfigurationMode() {
    cc_t* readConfig = (cc_t*) calloc(2, sizeof(int));
    if (this->portFileDescriptor <= 0) {
        readConfig[0] = ERR_INVALID_PORT;
        readConfig[1] = ERR_INVALID_PORT;
        return readConfig;
    }
    readConfig[0] = this->tty.c_cc[VTIME];
    readConfig[1] = this->tty.c_cc[VMIN];
    return readConfig;
}

int Terminal::TerminalDevice::setBaudRate(int baudRate) {
    if (this->portFileDescriptor <= 0) {
        return ERR_INVALID_PORT;
    }
    cfsetspeed(&(this->tty), baudRate);
    return tcsetattr(this->portFileDescriptor, TCSAFLUSH, &(this->tty));
}

speed_t Terminal::TerminalDevice::getBaudRate() {
    if (this->portFileDescriptor <= 0) {
        return ERR_INVALID_PORT;
    }
    return cfgetospeed(&(this->tty));
}

ssize_t Terminal::TerminalDevice::writeData(const void* buffer, int length) {
    if (this->portFileDescriptor <= 0) {
        return ERR_INVALID_PORT;
    }
    return write(this->portFileDescriptor, buffer, length);
}

ssize_t Terminal::TerminalDevice::readData(void* buffer, int length) {
    if (this->portFileDescriptor <= 0) {
        return ERR_INVALID_PORT;
    }
    return read(this->portFileDescriptor, buffer, length);
}

int Terminal::TerminalDevice::closePort() {
    if (this->portFileDescriptor <= 0) {
        return ERR_INVALID_PORT;
    }
    return close(this->portFileDescriptor);
} 

int* Terminal::TerminalDevice::getPortFileDescriptor() {
    return &(this->portFileDescriptor);
}

int* Terminal::TerminalDevice::getErrno() {
    return &errno;
}