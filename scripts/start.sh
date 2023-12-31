PROJECT_ROOT="/home/ubuntu/app"
JAR_FILE="$PROJECT_ROOT/spring-webapp.jar"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"
ELASTIC_SEARCH_ROOT="$PROJECT_ROOT/src/main/resources"
TIME_NOW=$(date +%c)

# build 파일 복사
echo "$TIME_NOW > $JAR_FILE 파일 복사" >> $DEPLOY_LOG
cp $PROJECT_ROOT/build/libs/*.jar $JAR_FILE

# ELASTIC_SEARCH 실행
# cd $ELASTIC_SEARCH_ROOT # resources 폴더 위치로 이동
# docker-compose -f $ELASTIC_SEARCH_ROOT/docker-compose.yml down
docker-compose -f $ELASTIC_SEARCH_ROOT/docker-compose.yml up --build -d  # Docker Compose로 애플리케이션 실행 및 빌드

# sleep
echo "sleep 30 second"
sleep 30

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG
nohup java -jar $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG
