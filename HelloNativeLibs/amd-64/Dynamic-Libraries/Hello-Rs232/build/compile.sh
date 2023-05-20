#!/bin/bash

#**
#* Ccoffee Build tool, manual build, alpha-v1.
#* 
#* @author pavl_g.
#*#

canonical_link=`readlink -f ${0}`
build_dir=`dirname $canonical_link`

source $build_dir'/variables.sh'

##
# Compile and build native sources.
#
# @echo Script Succeeded if all the commands have passed successfully, exit with error code otherwise.
##
function compile() {
    native_sources=`find $nativessrc_directory'/main' $nativessrc_directory'/lib' -name '*.c' -o -name '*.cxx' -o -name '*.cpp' -o -name '*.c++'-o -name '*.ino'`
    # tests if the sources exist, then give the current user full permissions on them and compile them
    chmod +x $native_sources
    # append -lwiringPi for raspberry wiringPi includes
    # ${JAVA__HOME%/*} : % returns back to the root base directory of the java home, / is the separator delimiter of the directory string
    # compile and build a shared lib for linux systems
    if [[ `linux_x86_x64 "${native_sources}"` -eq 0 ]]; then
        echo -e "$GREEN_C Task@Build Linux-x86-x64 : Succeeded"
    else
        echo -e "$RED_C Task@Build Linux-x86-x64 : Failed"
        echo -e "$RED_C Exiting Script with error 150"
        exit 150
    fi
    echo -e "$GREEN_C---MajorTask@Build Native Sources : Succeeded---"
} 

##
# Build for desktop linux systems
#
# @param nativeSources sources to be compiled for linux desktop.
# @return 0 if command passes, non zero number otherwise with exit code 150 (search the code on repo's wiki).
##
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