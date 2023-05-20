#include<linux/TerminalDevice.h>

static inline int* startRs232Service() {
	printf("%s\n", " --- Started Rs232 driver service --- ");

	static int fd = TerminalDevice::openPort("/dev/ttyUSB0", DEFAULT_FLAGS);
	if (fd <= 0) {
		perror("Error driver not available, trying again......");
	}

	// try again and block until a request is accepted
	while (fd <= 0 ) {
		startRs232Service();
	}
	
	return &fd;
}

static inline void initRs232Service(int* fd) {
	int state = TerminalDevice::initTermios(fd);
	if (state > 0) {
		perror("Cannot initialize port");
	}
}

int main(int argc, char **argv) {
	printf("%s\n", " --- Started Rs232 driver example --- ");

	int* service_descriptor = startRs232Service();

	initRs232Service(service_descriptor);

	return 0;
}
