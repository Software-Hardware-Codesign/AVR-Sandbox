#!/bin/bash

canonical_link=`readlink -f ${0}`
build_dir=`dirname $canonical_link`

echo "Ccoffee Script starts"
echo "---------------------------------------------"
source $build_dir"/"compile.sh
source $build_dir"/"upload.sh
echo "---------------------------------------------"
echo "Ccoffee Script finishes"
