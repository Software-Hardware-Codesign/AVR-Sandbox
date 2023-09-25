#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#

source variables.sh

function run() {
    cd $project_root'/output/'$jar_folder
    $java --enable-preview -jar 'serial4j.jar'
}
