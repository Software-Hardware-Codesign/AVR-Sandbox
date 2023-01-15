#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
#!/bin/sh

canonical_link=`readlink -f ${0}`
build_dir=`dirname $canonical_link`

# cut the working directory from its end by a one '/' delimiter
project="${build_dir%/*}"
# cut the working directory from its end by a one '/' delimiter again
rootProject="${project%/*}"

object_file=('hello-avr-types.elf')
# AVR-DUDE properties
BAUD_RATE='57600'
PORT='/dev/ttyUSB0'
CHIP='atmega328p'
CHIP_ALIAS='m328'
PROGRAMMER='arduino'
# Common Variables contain colors
source ${rootProject}'/common-variables.sh'
source ${rootProject}'/avr-home.sh'
output=${project}'/output/'${object_file}
