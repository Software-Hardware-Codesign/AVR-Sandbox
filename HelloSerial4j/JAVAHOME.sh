#**
#* Ccoffee Build tool, manual build, alpha-v1.
#* @author pavl_g.
#*#
# DONOT DELETE OR RENAME
## EDIT YOUR CUSTOM JAVAHOME FROM HERE, Ccoffee uses this custom java home to create new aliases for your script !
compile_dir=`pwd`
build_dir="${compile_dir%/*}"
project_dir="${build_dir%/*}"
root_dir="${project_dir%/*}"
JAVA__HOME=$root_dir'/jdk-19/bin'
export JAVA_HOME="${JAVA__HOME%/*}"

