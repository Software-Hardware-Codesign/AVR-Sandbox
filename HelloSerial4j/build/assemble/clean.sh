#**
#* Ccoffee Build tool, manual build, alpha-v1.
#* Custom Includsions for GTKmm cpp wrapper
#* dependencies '-I"/usr/include/glibmm-2.9.1/glib" -I"/usr/include/sigc++-2.0/sigc++" -I"/usr/include/giomm-2.4" -I"/usr/include/gtkmm-4.2.0/gtk"'
#*
#* @author pavl_g.
#*#

source variables.sh

function clean() {
    if [[ -d $project_root'/output/'$jar_folder ]]; then
        rm -rf ${project_root}'/output/'$jar_folder
    fi
    return $?
} 
