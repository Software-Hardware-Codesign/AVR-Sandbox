#include<serial.h>

void uart::startProtocol(const uint16_t BAUD_RATE_VAL) {
    REG_UCSRB = (1 << BIT_TXEN) | (1 << BIT_RXEN); /* enable the transmit and receiver buffers and their interrupt services */
    REG_UCSRC = (1 << BIT_USBS) | (3 << BIT_UCSZ0); // enables the UCSZ0, UCSZ1 and URSEL
    REG_UBRR = BAUD_RATE_VAL; // 0x10 (16) for BR = 57600 // 0x33 (51) for 9600
    uart_instance.state_instance.onProtocolStarted();
    uart_instance.startReceiverISR();
}