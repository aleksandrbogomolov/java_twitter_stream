#!/bin/bash
echo "--- Stop all container ---"
docker stop $(docker ps -a -q)
echo "--- Remove all old images ---"
docker rmi -f $(docker images | grep "<none>" | awk "{print \$3}")
echo "--- Build Produser image ---"
cd produser && mvn clean install -P con
echo "--- Run Produser image ---"
docker run -d -p 8888:8888 produser:0.1
echo "--- Build Web image ---"
cd ../web/ && mvn clean install -P con
echo "--- Run Web image ---"
docker run -d -p 8080:8080 web:0.1
echo "--- All containers work ---"