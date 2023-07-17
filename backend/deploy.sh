#!/bin/sh
PROJECT_NAME=zipgo-backend

echo '> 현재 구동중인 애플리케이션 PID 확인'
CURRENT_PID=$(sudo netstat -lntp | grep 8080 | awk '{print $7}' | cut -d'/' -f1)

echo "현재 구동중인 애플리케이션 PID: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -9 $CURRENT_PID"
    sudo kill -9 $CURRENT_PID
    sleep 5
fi

sudo chmod +x zipgo-backend-0.0.1-SNAPSHOT.jar
sudo nohup java -jar \
-Dspring.profiles.active=prod \
-Dspring.config.import=env.properties \
zipgo-backend-0.0.1-SNAPSHOT.jar > ~/application.log 2>&1 &
