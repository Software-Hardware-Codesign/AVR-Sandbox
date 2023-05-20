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
clibName=('HelloRs232.elf')

# native toolchains
gcc='g++-10'

# code sources
nativessrc_directory=$project_root'/src'

# native shared/dynamic libs
linux_libs_root_dir=$project_root'/libs/shared/native/Linux/linux-x86-x64/'
output_dir=$project_root'/output'

source $avr_sandbox_root'/CommonVariables.sh'
