#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#

echo "Compiling the project"
echo -e $RESET_Cs
echo "--------Script start--------"
source buildJava.sh
source buildNatives.sh

if [[ $enable_java_build == true ]]; then
    echo -e "$ORANGE_C---MajorTask@Build Java Sources : Java build started"
    copyJavaSources
    if [[ `generateHeaders` -eq 0 ]]; then
       moveHeaders
       echo -e "$GREEN_C Task@Build Compile Java : Succeeded"
    else
        echo -e "$RED_C Task@Build Compile Java : Failed"
    fi
    echo -e "$ORANGE_C---MajorTask@Build Java Sources : Java build finished"
fi

echo -e $RESET_Cs

if [[ $enable_natives_build == true ]]; then
    echo -e "$MAGNETA_C---MajorTask@Build Native Sources : Native build started"
    compile
    echo -e "$MAGNETA_C---MajorTask@Build Native Sources : Native build finished"
fi

echo -e $RESET_Cs
echo "--------Script end--------"
