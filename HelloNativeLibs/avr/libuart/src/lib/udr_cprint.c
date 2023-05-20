#include<serial.h>

void uart::cprint(char* data) {
    while (!(REG_UCSRA & (1 << BIT_UDRE)));
    REG_UDR = *data;
    uart_instance.startTransmitterISR();
}