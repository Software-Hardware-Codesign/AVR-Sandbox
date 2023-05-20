#include<serial.h>

void uart::setTransmitDataRegister(const uint8_t* transmitData) {
    *(uart_instance.transmitData) = *transmitData;
    uart_instance.startDataRegisterEmptyBufferISR();
    uart_instance.startTransmitterISR();
}

uint8_t uart::readASCII() {
    while (!(REG_UCSRA & (1 << BIT_RXC)));
    return REG_UDR;
}