#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#

jar_folder='serial4j'
version_number='0.3A'
jar=${jar_folder}'.jar'

java_docs_folder=$jar_folder'-'$version_number'-javadoc'
java_docs_jar=$java_docs_folder'.jar'

# Manifest attributes
manifest='Manifest-Version: 1.0'
mainclass='Main-Class: com.serial4j.example.Launcher'
classpath='Class-Path: dependencies'
api_name='Name: '$jar_folder
version='Version: '$version_number

assemble_dir=`pwd`
# cut the working directory from its end by a one '/' delimiter
build_dir="${assemble_dir%/*}"
# cut the working directory from its end by a one '/' delimiter again
project_root="${build_dir%/*}"
root_package='com.serial4j'
docs_dir=$project_root'/'$java_docs_folder

source ${project_root}'/JAVAHOME.sh'
source ${build_dir}'/compile/variables.sh'
source ${project_root}'/CommonVariables.sh'

java_jar=${JAVA__HOME}'/jar'
javadoc=${JAVA__HOME}'/javadoc'
jar_tmp=$javabuild_directory"/$jar_folder"
docs_tmp=$jar_tmp"/$java_docs_folder"

manifest_file=$javabuild_directory'/'$jar_folder'/Manifest.mf'

