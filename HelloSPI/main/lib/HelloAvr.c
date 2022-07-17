#include <avr/io.h>
//standard AVR header
#define MOSI 5
#define SCK 7
int main (void)
{
DDRB = (1<<MOSI)|(1<<SCK);
//MOSI and SCK are output
DDRA = 0xFF;
//Port A is output
SPCR = (1<<SPE)|(1<<MSTR)|(1<<SPR0);//enable SPI as master
while(1){
//do for ever
SPDR = 'G';
//start transmission
while(!(SPSR & (1<<SPIF)));
//wait transfer finish
PORTA = SPDR;
//move received data to
}
//Port A
return 0;
}
