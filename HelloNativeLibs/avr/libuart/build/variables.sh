#!/bin/bash

#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#

# export all locales as C
export LC_ALL=C

canonical_link=`readlink -f ${0}`
build_dir=`dirname $canonical_link`

# cut the working directory from its end by a one '/' delimiter
project=`dirname $build_dir`

avr_examples_dir="${project%/*}"

hello_native_libs="${avr_examples_dir%/*}"
# cut the working directory from its end by a one '/' delimiter again
rootProject="${hello_native_libs%/*}"
# pass the value of the dire

clibName=('libuart')
# AVR-DUDE properties
CHIP='atmega328p'

# Common Variables contain colors
source ${rootProject}'/CommonVariables.sh'
# provide avr home
AVR_HOME=${rootProject}'/avr8-gnu-toolchain-linux_x86_64'
output=${project}'/output/'${clibName}
