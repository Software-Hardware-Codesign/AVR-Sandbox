#include<serial.h>

void uart::sprintln(char* data) {
    uart_instance.sprint(data);
    uart_instance.sprint(NEW_LINE_CARRIAGE_R);
}