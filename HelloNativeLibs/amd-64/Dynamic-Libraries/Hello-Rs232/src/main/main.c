#include<Rs232Service.h>

static inline int* prepareRs232Service() {

	int* service_descriptor = startRs232Service();
	initRs232Service(service_descriptor);

	return service_descriptor;
}

static inline void dispatchRs232Services() {

	const int* handshake_descriptor = prepareRs232Service();
	
	pthread_create(&handshakeRx_service, NULL, &logHandshakingSignals, (void*) handshake_descriptor);
    usleep(2000000); /* sleep 2 seconds until the read service starts */
	pthread_create(&handshakeTx_service, NULL, &handshakeDeviceDriver, (void*) handshake_descriptor);

	pthread_join(handshakeRx_service, NULL); /* wait for the Rx thread to terminate */
}

int main(int argc, char **argv) {
	printf("%s\n", " --- Started Rs232 driver example --- ");
	
	dispatchRs232Services();

	return 0;
}
