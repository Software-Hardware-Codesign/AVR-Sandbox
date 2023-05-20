#include<serial.h>

/**
 * @brief Triggered when the bit TXC is settled and the data transmission has been completed.
 */
ISR (__vector_USART_TX) {
    uart_instance.state_instance.onDataTransmitCompleted((const uint8_t) REG_UDR);
    uart_instance.stopTransmitterISR();
}

void uart::startTransmitterISR() {
    REG_UCSRB |= (1 << BIT_TXCIE);
    /* start the interrupt service handlers */
    sei();
}

void uart::stopTransmitterISR() {
    // Finds the 1s complement of all bits in RXCIE0 ---> eg: 0b11111101
    REG_UCSRB &= ~(1 << BIT_TXCIE); 
}