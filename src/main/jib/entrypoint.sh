#!/bin/sh

## add jq so we can parse AWS service metadata
#RUN apk update \
# && apk add jq \
# && rm -rf /var/cache/apk/*
#EXPOSE 9094 5701/udp

echo "The application will start in ${JHIPSTER_SLEEP}s..." && sleep ${JHIPSTER_SLEEP}
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "avid.video.games.AvidApp"  "$@"

