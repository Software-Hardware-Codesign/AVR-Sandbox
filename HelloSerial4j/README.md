## The Serial4j Architectural HAL:

![image](https://user-images.githubusercontent.com/60224159/189999625-fd667e7c-b219-4aa8-a91f-c9809dcef225.png)

Serial4j is a terminal io library primarialy built on top of POSIX base file API `<fcntl>` for file control operations, `<unistd>` for Unix std R/W operations and `<dirent>` for directory entry dir operations.

## Quick Overview:

- Java Implementation of the binding library: 

| Entity/Class | Usages | 
|---------------|----------------|
| `com.serial4j.core.serial` | A java package that provides the base implementation code for the serial4j API to control a terminal device. |
| `TerminalDevice.java` | A java class that provides a final implementation code for the `NativeTerminalDevice.java`, the api opens, initializes and R/W from/to a terminal device. |
| `Permissions.java` | A java class that provides the permissions for the terminal io operations. |
| `BaudRate.java` | A java enum class that provides the constants for the available POSIX termios bit speed. |
| `SerialPort.java` | A java wrapper class that wraps a serial port with a name and a port descriptor. |
| `ReadConfiguration.java` | A java enum class that provides an implementation for the various read configurations defined by the POSIX `termios-c_cc.h`. |
| `NativeTerminalDevice.java` | A java class that provides a base implementation for the Serial4j base native code. |
|                           |                          |
| `com.serial4j.core.control` | A java package that provides the terminal flags for the terminal device including control, local, input and output flags. |
| `TerminalFlag.java` | A java abstract class that provides the base implementation for a terminal flag. | 
| `TerminalControlFlag.java` | A java class that provides an implementation for the POSIX terminal control mode flags, e.g: CREAD for receive enable, CS8 for charachter size 8-bits,...etc. |
| `TerminalLocalFlag.java` | A java class that provides an implementation for the POSIX terminal local mode flags, e.g: ECHO for enabling echoing charachters, ECHOCTL for enabling echoing control charachters visually on the terminal. |
| `TerminalInputFlag.java` | A java class that provides an implementation for the POSIX terminal input mode flags, e.g: IUTF8 for enabling utf-8 input, ICRNL for mapping (translating) CR into NL on input. | 
| `TerminalOutputFlag.java` | A java class that provides an implementation for the POSIX terminal output mode flags, e.g: OCRNL for mapping (translating) CR to NL on output. |
|                           |                          |
| `com.serial4j.core.throwable` | A java package that provides java exceptions as translations to C native error codes. |

- Java Binding library for the native API:

- Direct Implementation of the POSIX base file/directory and terminal IO APIs:


- Unix/POSIX Base APIs:

