#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
#!/bin/sh

# export all locales as "en_US.UTF-8" for the gcc compiler 
export LC_ALL="en_US.UTF-8"

canonical_link=`readlink -f ${0}`
build_dir=`dirname $canonical_link`

source "${build_dir}/variables.sh"

function compile() {
	# attrs : dir to compile & sharedLib name
	native_sources=`find ${project}'/main' -name '*.c' -o -name '*.cxx' -o -name '*.cpp' -o -name '*.h' -o -name '*.c++'`

	sudo ${avr_home}'/bin/avr-g++' \
	-mmcu=${CHIP} ${native_sources} \
	-O2 				           \
	-I${avr_home}'/avr/include'    \
	-I${project}'/main/include'    \
	-o ${output}

	return $?
}

function convertToHex() {
  	 ${avr_home}'/bin/avr-objcopy' -O ihex ${output} ${output}'.hex'
	 return $?
}

echo -e "${WHITE_C} --MajorTask@Compile : Compiling the project"

echo -e ${RESET_Cs}

compile

if [[ ! $? -eq 0 ]]; then
	echo -e "${RED_C} --MajorTask@Compile : Failed compiling sources, exits with errno500."
	exit 500
else 
	echo -e "${GREEN_C} --MajorTask@Compile : Compilation succeeded."
fi
echo -e ${RESET_Cs}

echo -e "${WHITE_C} --MajorTask@Hexing : Creating Hex file"

convertToHex

if [[ ! $? -eq 0 ]]; then
	echo -e "${RED_C} --MajorTask@Hexing : Failed to create hex file, exits with errno600."
	exit 600
else 
	echo -e "${GREEN_C} --MajorTask@Hexing : Hex file created successfully."
fi

echo -e ${RESET_Cs}
