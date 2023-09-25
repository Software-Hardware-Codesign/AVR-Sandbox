#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#

# constant independent
clibName=('libserial4j.so')

# native toolchains
gcc='g++-10'

# android tool-chain constants
min_android_sdk=21
arm64="aarch64-linux-android"
arm64_lib="arm64-v8a"
arm32="armv7a-linux-androideabi"
arm32_lib="armeabi-v7a"
intel32="i686-linux-android"
intel32_lib="x86"
intel64="x86_64-linux-android"
intel64_lib="x86_64"
android_natives_jar="android-natives-${min_android_sdk}.jar"

# set some build guards
enable_java_build=true
enable_natives_build=true
enable_android_build=false

# work directories
compile_dir=`pwd`
build_dir="${compile_dir%/*}"
project_root="${build_dir%/*}"

# resources and dependencies
java_resources=$project_root'/src/resources'
dependencies=$java_resources'/dependencies'
assets=$java_resources'/assets'


# code sources
javasrc_directory=$project_root'/src/main/java'
nativessrc_directory=$project_root'/src/main/native'

# build directories
javabuild_directory=$project_root'/build/.buildJava'

# native shared/dynamic libs
shared_root_dir=$project_root'/shared'
android_natives_dir=$project_root'/shared/lib'
linux_natives_dir=$project_root'/shared/native/Linux'

source $project_root'/JAVAHOME.sh'
javac=$JAVA__HOME'/javac'

source $project_root'/NDKPATH.sh'
source $project_root'/CommonVariables.sh'
