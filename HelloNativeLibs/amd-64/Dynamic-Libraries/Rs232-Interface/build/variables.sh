#!/bin/bash

#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#

# print the canonical file name from its symbolic link
canonical_link=`readlink -f ${0}`
# get the directory name of this canonical name
build_dir=`dirname $canonical_link`

# work directories
project_root="${build_dir%/*}"

dynamic_libs_dir="${project_root%/*}"

amd_examples_dir="${dynamic_libs_dir%/*}"

hello_native_libs="${amd_examples_dir%/*}"

# cut the working directory from its end by a one '/' delimiter again
avr_sandbox_root="${hello_native_libs%/*}"

# constant independent
clibName=('librs232.so')

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
enable_natives_build=true
enable_android_build=false

# code sources
nativessrc_directory=$project_root'/src'

# native shared/dynamic libs
shared_root_dir=$project_root'/shared'
android_natives_dir=$project_root'/shared/lib'
linux_natives_dir=$project_root'/shared/native/Linux'

source $avr_sandbox_root'/NDKPATH.sh'
source $avr_sandbox_root'/CommonVariables.sh'
