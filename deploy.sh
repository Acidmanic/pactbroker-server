
DIST_DIR=target

BASE=$DIST_DIR/cicd-assistant

rm -rf $BASE

mvn clean install && \
mkdir $BASE && \
cp target/*.jar $BASE/application.jar && \
mkdir $BASE/dependency && \
cp -R target/dependency/* $BASE/dependency/ && \
mkdir $BASE/wiki-styles && \
cp -R target/wiki-styles/* $BASE/wiki-styles/ && \
mkdir $BASE/assets && \
cp -R target/assets $BASE/ && \
echo 'DIR=$(dirname $BASH_SOURCE)' > $BASE/start.sh && \
echo 'DIR=$(realpath $DIR)' >> $BASE/start.sh && \
echo 'JAR=$DIR/application.jar' >> $BASE/start.sh && \
echo 'java -jar "$JAR" $@ &' >> $BASE/start.sh && \
chmod +x $BASE/start.sh && \

echo 'DIR=$(dirname $BASH_SOURCE)' > $BASE/install.sh && \
echo 'DIR=$(realpath $DIR)' >> $BASE/install.sh && \
echo 'JAR=$DIR/application.jar' >> $BASE/install.sh && \
echo 'java -jar "$JAR" installService' >> $BASE/install.sh && \
chmod +x $BASE/install.sh && \

echo 'DIR=$(dirname $BASH_SOURCE)' > $BASE/stop.sh && \
echo 'DIR=$(realpath $DIR)' >> $BASE/stop.sh && \
echo 'touch "$DIR/.kill"' >> $BASE/stop.sh && \
chmod +x $BASE/stop.sh && \
echo 'DIR=$(dirname $BASH_SOURCE)' > $BASE/token.sh && \
echo 'DIR=$(realpath $DIR)' >> $BASE/token.sh && \
echo 'JAR=$DIR/application.jar' >> $BASE/token.sh && \
echo 'java -jar "$JAR" token' >> $BASE/token.sh && \
chmod +x $BASE/token.sh
rm -rf $DIST_DIR/cicd-assistant.zip && (cd $DIST_DIR/cicd-assistant &&  zip -r ../cicd-assistant.zip **/* *)

