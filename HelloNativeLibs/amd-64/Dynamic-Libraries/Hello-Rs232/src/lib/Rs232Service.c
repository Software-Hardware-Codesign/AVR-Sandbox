#include<Rs232Service.h>
#include<DynamicBuffer.h>

static inline void terminatePrinterFormat(int* numberOfPrintedChars, size_t targetLength) {
    if (*numberOfPrintedChars == targetLength) {
        printf("\r\n");
        *numberOfPrintedChars = 0;
    } 
}

int* startRs232Service() {
    /** create a service block to enable the retry again criteria */
    service: {
        fd = TerminalDevice::openPort(PORT, DEFAULT_FLAGS);
        
        if (fd <= 0) {
            TerminalDevice::closePort(&fd);
        } else {
            printf("%s\n", " --- Rs232 driver service opened --- ");
        }

        // try again and block until a request is accepted -- services retry criteria.
        if (fd <= 0) {
            goto service;
        }
    }
	
	return &fd;
}

void initRs232Service(int* fd) {
	int state = TerminalDevice::initTermios(fd);
    int baud_state = TerminalDevice::setBaudRate(B57600, fd);
	if ((state | baud_state) == -2) {
        perror(" --- Invalid Port --- \n");
	} else if (state < 0) {
        perror(" --- Failed to initialize the service --- \n");
    } else {
        printf("%s\n", " --- Initialized the Rs232 driver service --- ");
    }
}

void* handshakeDeviceDriver(void* data) {
    int* service_descriptor = (int*) data;
    
    printf("%s\n", " --- Started Rs232 handshake service --- ");

    handshake_service:{
        assert(pthread_mutex_lock(&handshake_mutex) == 0);
        assert(pthread_cond_wait(&handshake_cond, &handshake_mutex) == 0);
        /* critical section starts */

        int numberOfWrittenBytes = TerminalDevice::writeData(HANDSHAKING_SIGNAL, strlen(HANDSHAKING_SIGNAL), service_descriptor);
        if (numberOfWrittenBytes == -2) {
            perror(" --- Invalid Port --- \n");
        } else if (numberOfWrittenBytes < 0) {
            perror(" --- Failed to handshake the service --- \n");
        } 
        
        /* critical section ends */
        assert(pthread_mutex_unlock(&handshake_mutex) == 0);
        
        usleep(100000); /* send data each 100,000 micros = 0.1 seconds */

        goto handshake_service;
    }


    pthread_exit(NULL);
}

void* logHandshakingSignals(void* data) {
    
    int* service_descriptor = (int*) data;

    char* vacant = (char*) calloc(1, sizeof(char));

    int numberOfPrintedChars = 0;

    printf("%s\n", " --- Started Rs232 log service --- ");

    /* wait for the start of the reading thread */
    printf("%s\n\r", " --- Read Rs232 driver service handshakes --- ");

    read_service:{
        assert(pthread_mutex_lock(&handshake_mutex) == 0);
        /* critical section starts */

        int numberOfReadBytes = TerminalDevice::readData((void*) vacant, 1, service_descriptor);

        if (*vacant == 0x20) {
            /* critical section ends */
            assert(pthread_cond_signal(&handshake_cond) == 0);
            assert(pthread_mutex_unlock(&handshake_mutex) == 0);  

            goto read_service;
        }

        if (numberOfReadBytes > 0) {
            printf("%c", *vacant);
            *vacant = 0;
            numberOfPrintedChars += 1;
        } else if (numberOfReadBytes == -2) {
            perror(" --- Invalid Port --- \n");
            return NULL;
        }
        
        terminatePrinterFormat(&numberOfPrintedChars, strlen(HANDSHAKING_SIGNAL));

        /* critical section ends */
        assert(pthread_cond_signal(&handshake_cond) == 0);
        assert(pthread_mutex_unlock(&handshake_mutex) == 0);  

        goto read_service;
    }
    
    pthread_exit(NULL);
}

void* stopRs232Service(int* service_descriptor) {

    assert(pthread_mutex_lock(&handshake_mutex) == 0);

    /* run the main command and retrieve the state */
    int state = TerminalDevice::closePort(service_descriptor);
    /* log the error/print according to the state or behave differently or use a retry criteria as defined above */
    if (state == -2) {
        perror(" --- Invalid Port --- \n");
    } else if (state < 0) {
        perror(" --- Failed to stop the service --- \n");
    } else {
        printf("%s\n", " --- Stopped Rs232 driver service successfully --- ");
    }

    assert(pthread_mutex_unlock(&handshake_mutex) == 0);

    releaseRs232Service();

    pthread_exit(NULL);
}

void releaseRs232Service() {
    assert(pthread_mutex_destroy(&handshake_mutex) == 0);
    assert(pthread_cond_destroy(&handshake_cond) == 0);
}