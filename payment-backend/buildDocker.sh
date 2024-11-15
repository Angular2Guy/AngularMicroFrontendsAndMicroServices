#!/bin/sh
docker build -t angular2guy/microfrontends-payment:latest --build-arg JAR_FILE=payment.jar --no-cache .
docker run -p 8080:8080 angular2guy/microfrontends-payment:latest