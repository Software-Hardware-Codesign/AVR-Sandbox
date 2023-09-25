#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
source variables.sh

# make a dir for java byte code
if [[ ! -d $javabuild_directory ]]; then
    mkdir $javabuild_directory
fi

##
# Copies the source files to a single dir to be compiled manually.
##
function copyJavaSources() {
    #copy code to buildDir to compile java files
    java_packages=($javasrc_directory'/*')
    cp -r $java_packages $javabuild_directory
}

##
# Generates C headers for the java native files and compiles java code inside the buildDir.
##
function generateHeaders() {
    local result=-1
    cd $javabuild_directory
    java_sources=`find -name "*.java"`
    # creates C headers file for java natives.
    $javac -cp '.:'$dependencies -h .  $java_sources -Xlint:unchecked --release '19' --enable-preview
    result=$?
    # generate a methods signature file to help in invocation api
    bytecode=`find -name "*.class"`
    sigs=`$JAVA__HOME'/javap' -s -p $bytecode`
    printf ' %s \n' $sigs > 'sig.signature'
    # remove the source code
    rm $java_sources
    return $result
}

##
# Moves all the generated header files from the build dir to the include dir of the natives.
##
function moveHeaders() {
    cd $javabuild_directory
    headers=`find -name "*.h"`
    # check if the headers exist then move them
    if [[ $headers ]]; then
        mv $headers $nativessrc_directory'/include/jni'
    fi
}

