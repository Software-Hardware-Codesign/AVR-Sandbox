#!/bin/bash

canonical_link=`readlink -f ${0}`
build_dir=`dirname $canonical_link`

source $build_dir"/"variables.sh

function compile() {
	# attrs : dir to compile & sharedLib name
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

function provokePermissions() {
	sudo chmod +xrwrwrw ${output}'.a'
}

function copyToExample() {
	cp ${output}'.a' ${avr_examples_dir}'/Hellouart/libs'
	cp ${output}'.map' ${avr_examples_dir}'/Hellouart/libs'
	cp -r ${project}'/src/include/.' ${avr_examples_dir}'/Hellouart/src/include/'
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

echo -e "${WHITE_C} --MajorTask@PermissionControl : Provoking permissions to the object file."

echo -e ${RESET_Cs}

provokePermissions

if [[ ! $? -eq 0 ]]; then
	echo -e "${RED_C} --MajorTask@PermissionsControl : Failed to provoke permissions."
	exit 500
else 
	echo -e "${GREEN_C} --MajorTask@PermissionsControl : Permissions provoked successfully."
fi
echo -e ${RESET_Cs}

copyToExample

if [[ ! $? -eq 0 ]]; then
	echo -e "${RED_C} --MajorTask@Copying : "
	exit 500
else 
	echo -e "${GREEN_C} --MajorTask@Copying : "
fi
echo -e ${RESET_Cs}

