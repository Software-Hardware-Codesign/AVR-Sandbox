source variables.sh
export LC_ALL=C
function compile() {
	# attrs : dir to compile & sharedLib name
        nativeSources=`find ${project}'/main' -name '*.c' -o -name '*.cxx' -o -name '*.cpp' -o -name '*.h' -o -name '*.c++'`

	sudo ${AVR_HOME}'/bin/avr-g++' \
	-mmcu=${CHIP} ${nativeSources} \
	-I${AVR_HOME}'/avr/include'    \
	-I${project}'/main/include'    \
	-o ${output}
	return $?
}

function convertToHex() {
  	 ${AVR_HOME}'/bin/avr-objcopy' -O ihex ${output} ${output}'.hex'
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
