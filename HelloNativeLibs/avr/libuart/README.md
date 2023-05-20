# libuart

libuart: is a C avr library designed to interface and control the UART protocol with an implementation biased design towards file streams and file io (startProtocol, sendData and readData).

The library has one header `serial.h` and 12 source `.C` files, each file has one function or 2 highly connected functions at most so far.

This library is completely based on the HelloUART example written previously on the AVR-Sandbox project with removal of some ridiculous object-oriented patterns (the singleton pointer and the struct) since the developer already knows that the protocol is a singleton in its hardware implementation (you cannot have 2 different pointers pointing at the same hardware and behaving differently).

The design of this library is based on a tweaked version of the Concrete Command-State pattern.

The concrete command-state pattern of the `serial.h`: 

1) It's a concrete pattern, i.e: no abstraction layer.

2) Encapsulate the boolean operations for the `initialization` and `termination` of the universal async/sync receiver/transmitter protocol by triggering status control registers.

3) Firing listeners as a response to a command to the interrupt uint of the avr.