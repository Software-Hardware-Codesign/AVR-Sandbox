source variables.sh

function generateDocs() {
    $javadoc -d docs -sourcepath $project_root'/src/main/java/' -subpackages $root_package -d $project_root'/'$java_docs_folder
}
