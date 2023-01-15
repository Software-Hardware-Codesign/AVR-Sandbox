
#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
#!/bin/sh

# setup variables
avrgcc_compressed='avr-gcc.tar.gz'

jdk_compressed='jdk.tar.gz'

# setup functions to download and extract toolchains/compilers

##
# Prompt the download of the toolchains.
##
function confirmDownload() {

   printf "%s" "Additional data apply charges may be applied, if you want to proceed type [y] or [Y], and [n] or [N] to terminate ? "

   read command < /dev/stdin

   if [[ "$command" = "n" || "$command" = "N" ]]; then
        exit 640
   # ignore other ASCIIs other than 'n' and "N"
   elif [[ "$command" != "y" && "$command" != "Y" ]]; then
        echo -e 'Command not found'
        # Recall the prompt
        confirmDownload
   fi
}

##
# Sets up avr-dude from the apt package manager.
# @return [0] for success, [positive-number] for failure indicating errno, [1] for operation not permitted [EPERM].
##
function setupAvrDude() {
    sudo apt-get install avrdude 
    return $?
}

##
# Sets up the client url utility.
# @return [0] for success, [positive-number] for failure indicating errno, [1] for operation not permitted [EPERM].
##
function setupCURL() {
    sudo apt-get install curl
    return $?
}

##
# Downloads the avr gcc toolchains from Microchip domains.
# @return [0] for success, [positive-number] for failure indicating errno, [1] for operation not permitted [EPERM].
##
function downloadAvrToolChain() {
    curl https://ww1.microchip.com/downloads/aemDocuments/documents/DEV/ProductDocuments/SoftwareTools/avr8-gnu-toolchain-3.7.0.1796-linux.any.x86_64.tar.gz --output $avrgcc_compressed
    return $?
}

##
# Downloads jdk-19 from oracle domains.
# @return [0] for success, [positive-number] for failure indicating errno, [1] for operation not permitted [EPERM].
##
function downloadJdk() {
    curl https://download.oracle.com/java/19/archive/jdk-19_linux-x64_bin.tar.gz --output $jdk_compressed
    return $?
}

##
# Provokes R/W/E permissions to the specified files in the given folder.
# @return [0] for success, [positive-number] for failure indicating errno, [1] for operation not permitted [EPERM].
##
function provokeReadWriteExecutePermissions() {
    local directory=$1
    # rwx = read-write-execute for the owner, -R for recursive search
    chmod +rwx -R $directory
    return $?
}

##
# Extracts a compressed `.tar.gz` file to this directory.
# @return [0] for success, [positive-number] for failure indicating errno, [1] for operation not permitted [EPERM].
##
function extractCompressedFile() {
    local compressedFile=$1
    tar --overwrite -xf $compressedFile
    return $?
}

##
# Deletes the avr gcc archive if exists.
# @return [0] for success, [positive-number] for failure indicating errno, [1] for operation not permitted [EPERM].
##
function deleteAvrToolchainsArchive() {
    if [[ ! -e "$avrgcc_compressed" ]]; then
        return 1 
    fi
    rm $avrgcc_compressed 
    return $?
}

##
# Deletes the jdk archive if exists.
# @return [0] for success, [positive-number] for failure indicating errno, [1] for operation not permitted [EPERM].
##
function deleteJdkArchive() {
    if [[ ! -e "$jdk_compressed" ]]; then
        return 1    
    fi
    rm $jdk_compressed
    return $?
}
