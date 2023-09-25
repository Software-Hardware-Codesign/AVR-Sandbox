#**
#* Ccoffee Build tool, manual build, alpha-v1.
#* Custom Includsions for GTKmm cpp wrapper
#* dependencies '-I"/usr/include/glibmm-2.9.1/glib" -I"/usr/include/sigc++-2.0/sigc++" -I"/usr/include/giomm-2.4" -I"/usr/include/gtkmm-4.2.0/gtk"'
#*
#* @author pavl_g.
#*# 

# Sanity check the build directory
source variables.sh

if [[ ! -d $javabuild_directory ]]; then 
    chmod +x $project_root'/build/compile/compile.sh'
    cd $project_root'/build/compile'
    ./'compile.sh'
fi

echo "Assemble JAR"
echo -e $RESET_Cs
echo "--------Script start--------"
cd $project_root'/build/assemble'
source script.sh
source clean.sh

clean=`clean`
if (( clean > 0 )); then 
    echo -e "$RED_C Task@Clean : Failed"
else 
    echo -e "$CYAN_C Task@Clean : Completed"
fi

echo -e $RESET_Cs

make_output_dir=`makeOutputDir`
if ((  make_output_dir > 0 ));  then
    echo -e "$RED_C Task@MakeOutputDirectory : Failed"
else
    echo -e "$WHITE_C Task@MakeOutputDirectory : Completed"
fi

echo -e $RESET_Cs

create_manifest=`createManifest`
if (( create_manifest > 0 )); then
    echo -e "$RED_C Task@CreateJarManifest : Failed"
else
    echo -e "$ORANGE_C Task@CreateJarManifest : Completed"
fi

echo -e $RESET_Cs

generate_docs=`generateDocs`
if (( generate_docs > 0 )); then
    echo -e "$RED_C Task@GenerateJavaDocs : Failed"
else
    echo -e "$ORANGE_C Task@GenerateJavaDocs : Completed"
fi

echo -e $RESET_Cs

add_docs=`addJavaDocs`
if (( add_docs > 0 )); then 
    echo -e "$RED_C Task@AddJavaDocs : Failed"
else
    echo -e "$MAGNETA_C Task@AddJavaDocs : Completed"
fi

echo -e $RESET_Cs

add_natives=`addDesktopNativeDependencies`
if (( add_natives > 0 )); then 
    echo -e "$RED_C Task@AddDesktopNativeDependencies : Failed"
else
    echo -e "$MAGNETA_C Task@AddDesktopNativeDependencies : Completed"
fi

echo -e $RESET_Cs

add_android_natives=`addAndroidNativeDependencies`
if (( add_android_natives > 0 )); then 
    echo -e "$RED_C Task@AddNativeDependencies : Failed"
else
    echo -e "$MAGNETA_C Task@AddNativeDependencies : Completed"
fi

echo -e $RESET_Cs

add_assets=`addAssets`
if (( add_assets > 0 )); then 
    echo -e "$RED_C Task@AddAssets : Failed -- AssetsNotFound"
else
    echo -e "$ORANGE_C Task@AddAssets : Completed"
fi

echo -e $RESET_Cs

create_jar=`createJar`
if (( create_jar > 0 )); then 
    echo -e "$RED_C Task@CreateJar : Failed"
else
    echo -e "$ORANGE_C Task@CreateJar : Completed"
fi

echo -e $RESET_Cs

create_docsjar=`createDocsJar`
if (( create_jar > 0 )); then 
    echo -e "$RED_C Task@CreateDocsJar : Failed"
else
    echo -e "$ORANGE_C Task@CreateDocsJar : Completed"
fi

echo -e $RESET_Cs

create_output=`createOutput`
if (( create_jar > 0 )); then 
    echo -e "$RED_C Task@CreateOutput : Failed"
else
    echo -e "$WHITE_C Task@CreateOutput : Completed"
fi

echo -e $RESET_Cs

remove_manifest=`removeManifest`
if (( create_jar > 0 )); then 
    echo -e "$RED_C Task@RemoveManifest : Failed"
else
    echo -e "$WHITE_C Task@RemoveManifest : Completed"
fi

echo -e $RESET_Cs

echo "--------Script end--------"
