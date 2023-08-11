#!/bin/sh
PROJECT_NAME=zipgo-backend

echo '> production 서버를 실행합니다'
echo '> 현재 구동중인 애플리케이션 PID 확인'
CURRENT_PID=$(sudo netstat -lntp | grep 8080 | awk '{print $7}' | cut -d'/' -f1)

echo "현재 구동중인 애플리케이션 PID: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -15 $CURRENT_PID"
    sudo kill -15 $CURRENT_PID
    sleep 5
    if ps -p $CURRENT_PID > /dev/null; then
        echo "> 애플리케이션을 다시 종료합니다."
        sudo kill -9 $CURRENT_PID
    else
        echo "> 프로세스 아이디 $CURRENT_PID 가 성공적으로 종료되었습니다."
    fi
fi

sudo chmod +x ./zipgo-backend-0.0.1-SNAPSHOT.jar
sudo nohup java -jar \
-Dspring.profiles.active=prod \
-Dspring.config.import=env.properties \
./zipgo-backend-0.0.1-SNAPSHOT.jar > ./application.log 2>&1 &
