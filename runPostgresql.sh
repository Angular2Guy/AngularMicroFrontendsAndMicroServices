#!/bin/sh
docker pull postgres:latest
docker run --name hotel-selection-postgres -e POSTGRES_PASSWORD=sven1 -e POSTGRES_USER=sven1 -e POSTGRES_DB=hotel_selection -p 5432:5432 -d postgres
# deprecated psql -h localhost -U sven1 -f src/main/resources/db/initDB.sql movies
# docker start hotel-selection-postgres
# docker stop hotel-selection-postgres
# docker exec -it hotel-selection-postgres bash