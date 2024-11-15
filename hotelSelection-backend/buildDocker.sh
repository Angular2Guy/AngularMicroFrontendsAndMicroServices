#!/bin/sh
docker build -t angular2guy/microfrontends-hotelSelection:latest --build-arg JAR_FILE=hotelSelection.jar --no-cache .
docker run -p 8088:8088 angular2guy/microfrontends-hotelSelection:latest