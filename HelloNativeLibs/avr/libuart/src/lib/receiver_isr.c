#include<serial.h>

/**
 * @brief Triggered when the RXC0 is settled and the receiving has been completed.
 */
ISR (__vector_USART_RX) {
    uart_instance.state_instance.onDataReceiveCompleted((const uint8_t) REG_UDR);
}

void uart::startReceiverISR() {
    REG_UCSRB |= (1 << BIT_RXCIE);
    /* start the interrupt service handlers */
    sei();
}

void uart::stopReceiverISR() {
    // Finds the 1s complement of all bits in RXCIE0 ---> eg: 0b11111101
    REG_UCSRB &= ~(1 << BIT_RXCIE); 
}