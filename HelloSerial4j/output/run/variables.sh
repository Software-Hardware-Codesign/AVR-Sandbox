#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#

# 1) print the current working directory to a string value
run_dir=`pwd`
# cut the working directory from its end by a one '/' delimiter
output_dir="${run_dir%/*}"
# cut the working directory from its end by a one '/' delimiter again
project_root="${output_dir%/*}"

# include the JAVAHOME
source $project_root'/JAVAHOME.sh'
source $project_root'/CommonVariables.sh'
source $project_root'/build/assemble/variables.sh'

java=$JAVA__HOME'/java'
