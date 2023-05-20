/**
 * @file hello_uart.c.
 * @author pavl_g.
 * @brief Shows a basic implementation of the [libuart] library based on the command-state pattern.
 * @version 0.1
 * @date 2022-10-08
 * 
 * @copyright Copyright (c) 2022
 * 
 */
#include<avr/io.h>
#include<string.h>

#include<serial.h>

const char* message = "Data Transmission completed successfully !";

/**
 * Called when the data buffer is cleared and is available to carry a new data on the UDR.
 *
 * Use the uart::setTransmitterData(uint_8*) from the udr_plain_io.c.o objectcode
 */
void uart::state::onDataBufferCleared(const uint8_t* transmitData) {
	uart_instance.sprintln((char*) "Data Transmitted !");
}

/**
 * An implementation (function definition) for the function prototype.
 * 
 * Listens for the protocol start command and fires accordingly.
 */
void uart::state::onProtocolStarted() {
	// uart_instance.sprintln((char*) "Protocol Started Successfully !");
}

/**
 * An implementation (function definition) for the function prototype.
 * 
 * Listens for the protocol stop command and fires accordingly.
 */
void uart::state::onProtocolStopped() {
	uart_instance.sprintln((char*) "Protocol Stopped !");
}

/**
 * An implementation (function definition) for the function prototype.
 *
 * Listens for the receive vector interrupt service subroutine ISR(RX) as it 
 * fires the listener when a data is received on the UDR register.
 *
 * @param data a data to be received through the interrupt service from the data register.
 */
void uart::state::onDataReceiveCompleted(const uint8_t data) {
	uart_instance.cprint((char*) &data);
}

/**
 * An implementation (function definition) for the function prototype.
 *
 * Listens for the transmitter vector interrupt service subroutine ISR(TX) as it 
 * fires the listener when a data is transmitted on the UDR register.
 *
 * @param data a data to be transmitted through the interrupt service from the data register.
 */
void uart::state::onDataTransmitCompleted(const uint8_t data) {
	// uart_instance.sprintln((char*) message);
}

/**
 * The main application entry point.
 * 
 * @param void parameters to pass from the compiler.
 */
int main(void) {
	
	uart_instance.startProtocol(BAUD_RATE_57600);

	while (true); // wait forever

 return 0;
}
