#include<serial.h>

void uart::println(const int64_t data, const uint8_t base) {
    uart_instance.print(data, base);
    uart_instance.sprint(NEW_LINE_CARRIAGE_R);
}