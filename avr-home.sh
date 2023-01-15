#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
#!/bin/sh

# store the value of the working directory
tmp=`pwd`
# get back to the root project to find the toolchain
cd ../..
project_root=`pwd`
avr_home=$project_root'/avr8-gnu-toolchain-linux_x86_64'
# get back to the working directory
cd $tmp
