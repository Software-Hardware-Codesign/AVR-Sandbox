#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
#!/bin/sh

canonical_link=`readlink -f ${0}`
build_dir=`dirname $canonical_link`

source "${build_dir}/variables.sh"

function prepare() {
    sudo apt-get install screen 
}

function adjustBaudRate() {
    stty -F $1 $2
}

function holdStill() {
    for ((i = 0; i < 4; i++))
    do 
        sleep 2
        printf "....."
    done
    printf "\n"
}

function readPort() {
    screen -R "READ-PORT" -U "$1" "$BAUD_RATE"
}

printf "Preparing the [screen] util\n"

prepare

adjustBaudRate "$PORT" "$BAUD_RATE"

printf "Started reading PORT [$PORT] to terminate hold [CTRL+A+D]\n"

holdStill

readPort "$PORT"
