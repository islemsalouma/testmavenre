#!/bin/bash
set -xe
docker-compose --project-name CRM-module-itg -f /home/docker/testcrm/Stark-JAVA-itg/infrastructure/environnement-integration.yml down -v
docker-compose --project-name CRM-module-itg -f /home/docker/testcrm/Stark-JAVA-itg/infrastructure/environnement-integration.yml up --build -d

rm -rf ~/testcrm/*
echo 'script executed with success !'