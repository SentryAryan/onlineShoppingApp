#!/bin/bash
./mvnw clean package -DskipTests
docker build -t online-shopping-app . 