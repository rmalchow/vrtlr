#!/bin/bash
set -e
set +x
echo "login to harbor ... "
docker login -u ${CI_EMAIL} -p ${CI_PASSWORD} harbor.rand0m.me
echo "build image ... "
docker build -t harbor.rand0m.me/public/${CI_PROJECT_NAME}:latest -f scripts/Dockerfile .
echo "push image ... "
docker push harbor.rand0m.me/public/${CI_PROJECT_NAME}:latest
