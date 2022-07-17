source variables.sh

function upload() {
	sudo avrdude -c ${PROGRAMMER} -b${BAUD_RATE} -P${PORT} -p${CHIP_ALIAS} -F -B8 -U flash:w:${output}'.hex'
	return $?
}

echo -e "${WHITE_C} --MajorTask@UploadingCode : Uploading Hex file"
if [[ ! `upload` -eq 0 ]]; then
	echo -e "${RED_C} --MajorTask@UploadingCode : Failed to upload hex file, exits with errno700."
	exit 700
else 
	echo -e "${GREEN_C} --MajorTask@UploadingCode : Task finished."
fi
echo -e ${RESET_Cs}
