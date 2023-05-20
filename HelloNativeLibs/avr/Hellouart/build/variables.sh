#!/bin/bash

#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
export LC_ALL=C
# define work directory

canonical_link=`readlink -f ${0}`
build_dir=`dirname $canonical_link`

project="${build_dir%/*}"

avr_examples_dir="${project%/*}"

hello_native_libs="${avr_examples_dir%/*}"
# cut the working directory from its end by a one '/' delimiter again
rootProject="${hello_native_libs%/*}"
# pass the value of the dire

clibName=('libHelloUART')
# AVR-DUDE properties
BAUD_RATE='57600'
PORT='/dev/ttyUSB0'
CHIP='atmega328p'
CHIP_ALIAS='m328'
PROGRAMMER='arduino'
# Common Variables contain colors
source ${rootProject}'/CommonVariables.sh'
AVR_HOME=${rootProject}'/avr8-gnu-toolchain-linux_x86_64'
output=${project}'/output/'${clibName}
