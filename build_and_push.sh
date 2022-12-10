echo "********* CREATE JAR *********"
mvn clean package

echo "********** COPY JAR **********"
cp --force target/demo-0.0.1-SNAPSHOT.jar docker

echo "******** BUILD IMAGE *********"
docker build -t alexz2/converter_back:1.0.0 docker -f docker/Dockerfile

echo "***** PUSH TO DOCKER HUB *****"
docker push alexz2/converter_back:1.0.0
