#include<serial.h>

void uart::stopProtocol() {
    uart_instance.state_instance.onProtocolStopped();
    REG_UCSRB = 0x00; 
}