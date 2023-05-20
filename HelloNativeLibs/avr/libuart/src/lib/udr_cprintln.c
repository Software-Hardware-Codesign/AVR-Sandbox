#include<serial.h>

void uart::cprintln(char* data) {
    uart_instance.cprint(data);
    uart_instance.sprint(NEW_LINE_CARRIAGE_R);
}