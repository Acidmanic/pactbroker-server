#-----------------------------------------------------------------------------------
#   This script downloads latest pact doc application from github releases
#-----------------------------------------------------------------------------------
#-----------------------------------------------------------------------------------
#                                   Arguments
#-----------------------------------------------------------------------------------
CA_VERSION=$(curl -H "Accept: application/vnd.github.v3+json" https://api.github.com/repos/Acidmanic/pactbroker-server/releases/latest | grep '"tag_name":' | grep -Po 'v[0-9]\.[0-9]\.[0-9]')
RELEASE_DIR=https://github.com/Acidmanic/pactbroker-server/releases/download/$CA_VERSION
ZIP_FILE=cicd-assistant.zip
DOWNLOAD_URL=$RELEASE_DIR/$ZIP_FILE
#-----------------------------------------------------------------------------------
#-----------------------------------------------------------------------------------
#                                  Summary
#-----------------------------------------------------------------------------------
    echo "        Version: " $CA_VERSION
    echo "         Runner: " $DOWNLOAD_URL
#-----------------------------------------------------------------------------------
systemctl stop cicd-assistant.service
rm -rf $ZIP_FILE
wget $DOWNLOAD_URL   && \
unzip -o $ZIP_FILE -d . && \
./install.sh	     && \
echo "Installed and started successfully"

