# Native Libraries development using GCC

<a name="TOPICS"></a>

## Contents of this tutorial: 
1) Static V.S. Dynamic Native libraries.
2) Simple project compilation using gcc for intel/arm processors V.S. avr-gcc.


## 1) Static V.S. Dynamic Native libraries:

| `Objective` | `Static Native libraries` | `Dynamic Native libraries` | 
|-------------|---------------------------|----------------------------|
| Definition | Are object files that includes all the sources for the target compiled project including the external included sources | Are object files that includes the project sources only and symbolic runtime links for the other dynamic libraries |
| Other names | Compile-time libraries | Shared or Runtime libraries |
| Time of linking | At the compile time, the static object files are linked to the project and compiled with the project sources to an executable file | At the runtime, the loader loads the runtime library |

--------------------------------------------------------
## 2) Simple project compilation using intel gcc V.S. avr-gcc: 

### Quick Overview:

| `Compiling for intel/arm processors` | `Compiling for avr MCUs` | 
|--------------------------------------|--------------------------|
| For intel/arm processors, it's far direct to compile object files and the obejct files are compiled into shared or dynamic libs by default unless specified `-static` compiler option explicitly. | Object files are compiled into static object files by default and the compiler don't support dynamic compilation | 

1) Compiling the project into a dynamic/shared (or runtime) library for intel processors: 

- The linker gnu (GNU ld) program makes sure to load the dynamic library on application runtime and link the library to the application code automatically, however you can do this also manually and here comes the difference in operation between shared and dynamic libs.

- Here is how to compile the project to a dynamic library:
```bash
function linux_x86_x64() {
    local native_sources=$1
    if [[ ! -d $linux_natives_dir'/linux-x86-x64' ]]; then
        mkdir -p $linux_natives_dir'/linux-x86-x64'
    fi
    $gcc -fPIC -v $native_sources -shared -o $linux_natives_dir'/linux-x86-x64/'${clibName} \
        -I${JAVA__HOME%/*}'/include' \
        -I${JAVA__HOME%/*}'/include/linux' \
        -I${nativessrc_directory}'/include/linux/' \
        -I${nativessrc_directory}'/include/' \

    return $?
}
```

- Here is how to link the library to another project and add the library path to the gnu ld:
```bash
function linux_x86_x64() {
    local native_sources=$1
    if [[ ! -d $output_dir'/linux-x86-x64' ]]; then
        mkdir -p $output_dir'/linux-x86-x64'
    fi
    $gcc -fpie $native_sources -o $output_dir'/linux-x86-x64/'${clibName} \
        -I${JAVA__HOME%/*}'/include' \
        -I${JAVA__HOME%/*}'/include/linux' \
        -I${nativessrc_directory}'/include/linux/' \
        -I${nativessrc_directory}'/include' \
        -L$linux_libs_root_dir \
        -Wl,-rpath,$linux_libs_root_dir \
        -l'rs232'

    return $?
}
```

- For shared libraries, the linker library can be included in the code using `dlfcn.h` and dynamic libraries can be loaded in code using the absolute file path and you won't need to link the library at the project compile-time. 

2) Compiling the project into a static (or compile-time) library for intel processors:

--- WIP ---

3) Compiling the project into a dynamic/shared (or runtime) library for avr MCUs: 

```
There is no way to use the dynamic linked libraries on a microcontroller, 
since there is no a runtime environment to act upon (the linker program needs a linux machine), 
so shared libraries on avr-gcc isn't supported.
```

4) Compiling the project into a static library for avr MCUs: 

- Compiling of source files into object files and then packaging the object files into a library archive using the `ar` archive gnu binutil tool:
```bash
function compile() {
	
	cd ${project}'/src/lib/'
	nativeSources=(`ls *.c *.cpp *.c++ *.cxx`)

	for ((i=0; i<${#nativeSources[@]}; i++)); do	
		${AVR_HOME}'/bin/avr-g++' \
		-c -O -x c++ \
		-mmcu=${CHIP} "${project}/src/lib/${nativeSources[i]}" \
		-I${AVR_HOME}'/avr/include'    \
		-I${project}'/src/include'    \
		-o "${project}/output/${nativeSources[i]}.o"
	done

	objectcode=`find ${project}'/output/' -name '*.o'`

	${AVR_HOME}'/bin/avr-ar' rcs ${output}'.a' $objectcode

	${AVR_HOME}'/bin/avr-nm' ${output}'.a' > ${output}'.map'
	
}
```
- Linking the static library to another project: 
```bash
function compile() {
	# addLibsTold
	# attrs : dir to compile & sharedLib name
	nativeSources=`find ${project}'/src/lib' -name '*.c' -o -name '*.cxx' -o -name '*.cpp' -o -name '*.c++'`

	# compile to ``.elf`` full program (Executable and Linkable format)
	${AVR_HOME}'/bin/avr-g++' -O2 -x c++ \
	-mmcu=${CHIP} ${nativeSources} \
	-I${AVR_HOME}'/avr/include'    \
	-I${project}'/src/include'  \
	-L"${project}/libs/" -l'uart' \
	-o ${output}'.elf' 

	return $?
}
```
- Object files are selectively added to the final code if a call has been made to their source functions in the main source code (to resolve undefined references), otherwise they aren't added to the final elf, even if you have included their function prototypes.

5) Analysis of the compiler command and command options: 

- `gcc`: GNU C Collections Toolchains (or GNU C Compiler), is a GNU binutil designed to compile and link C code into static object files and shared object files, object-code is a machine specific generated code, this machine-code is interpreted into hex code when runnning on the CPU, you need to do the interpretation manually on some tiny devices like (MCUs) before uploading using the `object-copy` gnu binutils as follows:
```bash
function convertToHex() {
  	 ${AVR_HOME}'/bin/avr-objcopy' -I 'elf32-avr' -O 'ihex' ${output}'.elf' ${output}'.hex'
	 return $?
}
```
- `g++`: GNU C++ Compiler Toolchains, is the same as gcc with added functionalities to compile and link C++ code into object files.

- `-mmcu=[CHIP]`: Defines the chip (micro-controller unit) used to compile the code, this ensures that the io package is the right package for the target hardware at the compile-time.

- `-I[DIR]`: Adds a directory to the include path.

- `-L[LIB-DIR]`: Adds a library directory to the gnu linker at compile-time.

- `-Wl,-rpath=[DYNAMIC-LIB-DIR]`: Adds a library path (dynamic library) to the runtime path of the linker.

- `-o[OUTPUT]`: Specifies the output object-code.

- `-c`: Commands the gnu compiler to compile the source only to static object files and skip the linking step.

- `-x[LANGUAGE]`: Explicitly specifies the language to use.
