#!/bin/bash

cd $(dirname $0)
export home=$(pwd)
echo "LDAP home directory is ${home}"
echo "stopping stack ... "
docker-compose -p shrtnr pull
docker-compose -p shrtnr down
docker-compose -p shrtnr rm
echo "launching containers ... "
docker-compose -p shrtnr up -d 