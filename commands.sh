#!/bin/bash

java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$PORT -jar app.jar

#rc-service tor start &&

#curl ifconfig.me && tor -f /app/torrc.default && java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$PORT -jar app.jar