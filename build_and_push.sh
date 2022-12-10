mvn clean package
cp --force target/demo-0.0.1-SNAPSHOT.jar prod_create/services/backend
docker build prod_create/services/backend -t alexz2/converter_back:1.0.0
docker push alexz2/converter_back:1.0.0

docker build prod_create/services/frontend -t alexz2/converter_front:1.0.0
docker push alexz2/converter_front:1.0.0
