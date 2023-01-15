#include<SerialCounter.h>
#define DELAY 300

void SerialCounter::Counter::start(volatile uint8_t& PORT) {
    volatile uint8_t CLK = 0x00;
    while (HIGH) {
	_delay_ms(DELAY/2);
	PORT = HIGH << (++CLK % UINT8_MAX);
	_delay_ms(DELAY/2);
    }
}
