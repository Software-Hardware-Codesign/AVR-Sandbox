#include<serial.h>

void uart::print(const int64_t data, const uint8_t base) {
    char* strBuffer = allocateStringBuffer();
    // convert input to string
    itoa(data, strBuffer, base);
    int i = 0;
    while (i < strlen(strBuffer)) {
        uart_instance.cprint(&strBuffer[i++]);
    }
    free(strBuffer);
}