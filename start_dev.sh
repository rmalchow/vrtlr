#!/bin/bash


#ln -s ./vrtlr-app/config/application.yml . || true
docker run -ti -v $(pwd):/app --entrypoint /bin/bash harbor.cloudpowered.services/mcon-group-public/centos8-base:latest -c "cd /app; mvn -s ./settings.dev.xml  -T 16 clean package"
docker run -ti -v $(pwd):/app -p 8080:8080 --entrypoint /bin/bash harbor.cloudpowered.services/mcon-group-public/centos8-base:latest -c "cd /app; bash scripts/dev.sh"