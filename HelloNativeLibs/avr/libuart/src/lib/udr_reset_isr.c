#include<serial.h>

/**
 * @brief Triggered when the bit UDRE0 is one (the data register buffer is empty and ready to send data).
 */
ISR (USART_UDRE_vect) {
    REG_UDR = *(uart_instance.transmitData);
    uart_instance.state_instance.onDataBufferCleared((const uint8_t*) uart_instance.transmitData);
    uart_instance.stopDataRegisterEmptyBufferISR();
}

void uart::startDataRegisterEmptyBufferISR() {
    REG_UCSRB |= (1 << BIT_UDRIE);
    /* start the interrupt service handlers */
    sei();
}

void uart::stopDataRegisterEmptyBufferISR() {
    // Finds the 1s complement of all bits in UDRIE0 ---> eg: 0b11111101
    REG_UCSRB &= ~(1 << BIT_UDRIE); 
}