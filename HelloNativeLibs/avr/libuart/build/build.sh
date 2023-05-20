#!/bin/bash

canonical_link=`readlink -f ${0}`
build_dir=`dirname $canonical_link`

echo -e "--Building the libuart into a static object file---"
echo "Ccoffee Script starts"
echo "---------------------------------------------"
source $build_dir"/"compile.sh
echo "---------------------------------------------"
echo "Ccoffee Script finishes"
