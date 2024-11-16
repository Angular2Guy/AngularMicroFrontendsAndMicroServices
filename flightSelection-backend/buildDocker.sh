#!/bin/sh
docker build -t angular2guy/microfrontends-flightSelection:latest --no-cache .
docker run -p 8080:8080 angular2guy/microfrontends-flightSelection:latest