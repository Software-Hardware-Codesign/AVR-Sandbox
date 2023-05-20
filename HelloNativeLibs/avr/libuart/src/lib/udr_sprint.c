#include<serial.h>

void uart::sprint(char* data) {
    int i = 0;
    while (i < strlen(data)) {
        uart_instance.cprint(&data[i++]);
    }
}