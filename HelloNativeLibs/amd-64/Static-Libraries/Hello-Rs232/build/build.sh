#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#

canonical_link=`readlink -f ${0}`
build_dir=`dirname $canonical_link`

echo "Compiling the project"
echo -e $RESET_Cs
echo "--------Script start--------"

source $build_dir'/compile.sh'

echo -e $RESET_Cs

compile

echo -e $RESET_Cs
echo "--------Script end--------"
