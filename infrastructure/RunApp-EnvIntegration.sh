#!/bin/bash
set -xe
docker-compose --project-name CRM-module-itg -f /home/docker/crm_module/Stark-JAVA-itg/infrastructure/environnement-integration.yml down -v
docker-compose --project-name CRM-module-itg -f /home/docker/crm_module/Stark-JAVA-itg/infrastructure/environnement-integration.yml up --build -d

rm -rf ~/crm_module/*
echo 'script executed with success !'