
BASE=target/cicd-assistant

rm -rf $BASE

mvn clean install && \
mkdir $BASE && \
cp target/*.jar $BASE/application.jar && \
mkdir $BASE/dependency && \
cp -R target/dependency/* $BASE/dependency/ && \
echo 'DIR=$(dirname $BASH_SOURCE)' > $BASE/start.sh && \
echo 'DIR=$(realpath $DIR)' >> $BASE/start.sh && \
echo 'JAR=$DIR/application.jar' >> $BASE/start.sh && \
echo 'java -jar "$JAR" $@ &' >> $BASE/start.sh && \
chmod +x $BASE/start.sh && \
echo 'touch .kill' > $BASE/stop.sh && \
chmod +x $BASE/stop.sh && \
echo 'DIR=$(dirname $BASH_SOURCE)' > $BASE/token.sh && \
echo 'DIR=$(realpath $DIR)' >> $BASE/token.sh && \
echo 'JAR=$DIR/application.jar' >> $BASE/token.sh && \
echo 'java -jar "$JAR" token' >> $BASE/token.sh && \
chmod +x $BASE/token.sh
