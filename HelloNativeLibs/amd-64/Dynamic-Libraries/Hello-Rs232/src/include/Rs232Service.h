#ifndef _RS232_SERVICE
#define _RS232_SERVICE

#include<linux/TerminalDevice.h>
#include<stdio.h>
#include<string.h>
#include<pthread.h>
#include<assert.h>

#define PORT ((const char*) "/dev/ttyUSB0")
#define HANDSHAKING_SIGNAL ((const char*) "_HANDSHAKER0 2022")
#define HANDSHAKES_LEN ((size_t) sizeof(HANDSHAKING_SIGNAL) / sizeof(HANDSHAKING_SIGNAL[0]))

static pthread_mutex_t handshake_mutex = PTHREAD_MUTEX_INITIALIZER;
static pthread_cond_t handshake_cond = PTHREAD_COND_INITIALIZER;

static pthread_t handshakeTx_service;
static pthread_t handshakeRx_service;

static int fd = 0;

int* startRs232Service();

void initRs232Service(int* fd);

void* stopRs232Service(int*);

void* handshakeDeviceDriver(void*);

void* logHandshakingSignals(void*);

void releaseRs232Service();

#endif