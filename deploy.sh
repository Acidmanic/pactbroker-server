
BASE=target/broker-server

mvn clean install && \
mkdir $BASE && \
cp target/application-standalone.jar $BASE && \
echo 'java -jar application-standalone.jar $@' > $BASE/start.sh && \
chmod +x $BASE/start.sh

