#!/bin/bash

canonical_link=`readlink -f ${0}`
build_dir=`dirname $canonical_link`

source $build_dir"/"variables.sh

function addLibsTold() {
	sudo ${AVR_HOME}'/bin/avr-ld' --library-path "${project}/libs" "${project}/libs/libuart"
}

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

	# get the elf32bit file from the object linked data -- the elf is the whole application
	# ${AVR_HOME}'/bin/avr-g++' -mmcu=${CHIP} -O2 ${output}'.o' -L"${project}/libs/" -l'uart' -o ${output}'.elf'

	return $?
}

function convertToHex() {
  	 ${AVR_HOME}'/bin/avr-objcopy' -I 'elf32-avr' -O 'ihex' ${output}'.elf' ${output}'.hex'
	 return $?
}

echo -e "${WHITE_C} --MajorTask@Compile : Compiling the project"

echo -e ${RESET_Cs}

if [[ ! `compile` -eq 0 ]]; then
	echo -e "${RED_C} --MajorTask@Compile : Failed compiling sources, exits with errno500."
	exit 500
else 
	echo -e "${GREEN_C} --MajorTask@Compile : Compilation succeeded."
fi
echo -e ${RESET_Cs}

echo -e "${WHITE_C} --MajorTask@Hexing : Creating Hex file"
if [[ ! `convertToHex` -eq 0 ]]; then
	echo -e "${RED_C} --MajorTask@Hexing : Failed to create hex file, exits with errno600."
	exit 600
else 
	echo -e "${GREEN_C} --MajorTask@Hexing : Hex file created successfully."
fi
echo -e ${RESET_Cs}
