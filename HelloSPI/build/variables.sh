#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#

# define work directory
# 1) print the current working directory to a string value
pwd=`pwd`
# cut the working directory from its end by a one '/' delimiter
project="${pwd%/*}"
# cut the working directory from its end by a one '/' delimiter again
rootProject="${project%/*}"
# pass the value of the dire

clibName=('libHelloSpi')
# AVR-DUDE properties
BAUD_RATE='9600'
PORT='/dev/ttyUSB0'
CHIP='atmega32'
CHIP_ALIAS='m32'
PROGRAMMER='usbasp'
# Common Variables contain colors
source ${rootProject}'/CommonVariables.sh'
source ${rootProject}'/AVR__HOME.sh'
output=${project}'/output/'${clibName}
