#include<linux/TerminalDevice.h>

struct termios* TerminalDevice::getTermiosFromFd(int* fd) {
    if (fd == NULL) {
        return NULL;
    }
    struct termios* tty = (struct termios*) calloc(1, sizeof(struct termios));
    /* update the termios struct pointer with the data from the port descriptor */
    tcgetattr(*fd, tty);
    return tty;
}

int TerminalDevice::openPort(const char* port, int flag) {
    return open(port, flag);
}

int TerminalDevice::fetchSerialPorts(struct DynamicBuffer* serialPorts) {

    DIR* dirp = opendir(DEVICES_DIR);
    
    /* sanity check the input */
    if (dirp == NULL) {
        return ERR_INVALID_DIR;
    }

    struct dirent* dp = (struct dirent*) calloc(1, sizeof(struct dirent));

    /* start at the beginning of the buffer to override last data */
    serialPorts->resetDataPointer();

    /* start reading available ports */
    while ((dp = readdir(dirp)) != NULL) {
        
        char* device = (char*) calloc(1, sizeof(char));
        device = SerialUtils::concatIntoDevice(device, dp->d_name, DEVICES_DIR);
        
        /* delete the device buffer if it's not a serial port */
        if (!SerialUtils::isSerialPort(device, DEFAULT_FLAGS)) {
            BufferUtils::deleteBuffer(device);
            continue;
        }

        /* add the device to the serial ports major buffer and count up */
        serialPorts->add(device);
    } 

    /* release resources */
    closedir(dirp);
    BufferUtils::deleteBuffer(dp);

    /* throws error indicating the availability issues */
    if (serialPorts->getItem(0) == NULL) {
        return ERR_NO_AVAILABLE_TTY_DEVICES;
    }
    return OPERATION_SUCCEEDED;
}

int TerminalDevice::initTermios(int* fd) {
    if (*fd <= 0) {
        return ERR_INVALID_PORT;
    }
    
    struct termios* tty = TerminalDevice::getTermiosFromFd(fd);

    /* setup tty attributes */
    tty->c_cflag &= ~(CBAUDEX | CBAUD); /* clear BAUDs */
    tty->c_cflag |= (CREAD | CS8 | CLOCAL); /* set flags */
    tty->c_lflag &= ~(ICANON | ECHO | ECHOE | ECHOK | ECHOKE | ECHONL | ECHOPRT | ECHOCTL | ISIG | IEXTEN);
    tty->c_oflag &= ~(OPOST | ONLCR);
    tty->c_iflag = 0x00;

    /* define default read mode as blocking read on char at a time */
    tty->c_cc[VTIME] = BLOCKING_READ_ONE_CHAR[0];
    tty->c_cc[VMIN] = BLOCKING_READ_ONE_CHAR[1];

    /* apply attriutes flag bits */
    int state = tcsetattr(*fd, TCSAFLUSH, tty);
    BufferUtils::deleteBuffer(tty);

    return state;
}

int TerminalDevice::setTerminalControlFlag(TerminalFlag flag, int* fd) {
    if (*fd <= 0) {
        return ERR_INVALID_PORT;
    }
    
    struct termios* tty = TerminalDevice::getTermiosFromFd(fd);

    tty->c_cflag = flag;
    /* sets the new terminal settings to the file descriptor with flushing any output */
    int state = tcsetattr(*fd, TCSAFLUSH, tty);
    BufferUtils::deleteBuffer(tty);

    return state;
}

int TerminalDevice::setTerminalLocalFlag(TerminalFlag flag, int* fd) {
    if (*fd <= 0) {
        return ERR_INVALID_PORT;
    }

    struct termios* tty = TerminalDevice::getTermiosFromFd(fd);

    tty->c_lflag = flag;
    int state = tcsetattr(*fd, TCSAFLUSH, tty);
    BufferUtils::deleteBuffer(tty);

    return state;
}

int TerminalDevice::setTerminalInputFlag(TerminalFlag flag, int* fd) {
    if (*fd <= 0) {
        return ERR_INVALID_PORT;
    }
    struct termios* tty = TerminalDevice::getTermiosFromFd(fd);

    tty->c_iflag = flag;
    int state = tcsetattr(*fd, TCSAFLUSH, tty);
    BufferUtils::deleteBuffer(tty);

    return state;
}

int TerminalDevice::setTerminalOutputFlag(TerminalFlag flag, int* fd) {
    if (*fd <= 0) {
        return ERR_INVALID_PORT;
    }
    struct termios* tty = TerminalDevice::getTermiosFromFd(fd);

    tty->c_oflag = flag;
    int state = tcsetattr(*fd, TCSAFLUSH, tty);
    BufferUtils::deleteBuffer(tty);

    return state;
}

TerminalFlag TerminalDevice::getTerminalControlFlag(int* fd) {
    if (*fd <= 0) {
        return ERR_INVALID_PORT;
    }
    struct termios* tty = TerminalDevice::getTermiosFromFd(fd);

    return tty->c_cflag;
}

TerminalFlag TerminalDevice::getTerminalLocalFlag(int* fd) {
    if (*fd <= 0) {
        return ERR_INVALID_PORT;
    }
    struct termios* tty = TerminalDevice::getTermiosFromFd(fd);

    return tty->c_lflag;
}

TerminalFlag TerminalDevice::getTerminalInputFlag(int* fd) {
    if (*fd <= 0) {
        return ERR_INVALID_PORT;
    }
    struct termios* tty = TerminalDevice::getTermiosFromFd(fd);

    return tty->c_iflag;
}

TerminalFlag TerminalDevice::getTerminalOutputFlag(int* fd) {
    if (*fd <= 0) {
        return ERR_INVALID_PORT;
    }
    struct termios* tty = TerminalDevice::getTermiosFromFd(fd);

    return tty->c_oflag;
}

int TerminalDevice::setReadConfigurationMode(const int VTIME_VALUE, const int VMIN_VALUE, int* fd) {
    if (*fd <= 0) {
        return ERR_INVALID_PORT;
    }
    struct termios* tty = TerminalDevice::getTermiosFromFd(fd);

    tty->c_cc[VTIME] = VTIME_VALUE;
    tty->c_cc[VMIN] = VMIN_VALUE;
    int state = tcsetattr(*fd, TCSAFLUSH, tty);
    BufferUtils::deleteBuffer(tty);

    return state;
}

cc_t* TerminalDevice::getReadConfigurationMode(int* fd) {
    cc_t* readConfig = (cc_t*) calloc(2, sizeof(unsigned char));
    if (*fd <= 0) {
        readConfig[0] = ERR_INVALID_PORT;
        readConfig[1] = ERR_INVALID_PORT;
        return readConfig;
    }
    struct termios* tty = TerminalDevice::getTermiosFromFd(fd);

    readConfig[0] = tty->c_cc[VTIME];
    readConfig[1] = tty->c_cc[VMIN];
    BufferUtils::deleteBuffer(tty);

    return readConfig;
}

int TerminalDevice::setBaudRate(int baudRate, int* fd) {
    if (*fd <= 0) {
        return ERR_INVALID_PORT;
    }
    struct termios* tty = TerminalDevice::getTermiosFromFd(fd);

    /* update the baud rate of the termios */
    cfsetspeed(tty, baudRate);
    int state = tcsetattr(*fd, TCSAFLUSH, tty);
    BufferUtils::deleteBuffer(tty);

    return state;
}

speed_t TerminalDevice::getBaudRate(int* fd) {
    if (*fd <= 0) {
        return ERR_INVALID_PORT;
    }
    struct termios* tty = TerminalDevice::getTermiosFromFd(fd);

    int speed = cfgetospeed(tty);
    BufferUtils::deleteBuffer(tty);

    return speed;
}

ssize_t TerminalDevice::writeData(const void* buffer, int length, int* fd) {
    if (*fd <= 0) {
        return ERR_INVALID_PORT;
    }
    return write(*fd, buffer, length);
}

ssize_t TerminalDevice::readData(void* buffer, int length, int* fd) {
    if (*fd <= 0) {
        return ERR_INVALID_PORT;
    }
    return read(*fd, buffer, length);
}

int TerminalDevice::closePort(int* fd) {
    if (*fd <= 0) {
        return ERR_INVALID_PORT;
    }
    return close(*fd);
} 
