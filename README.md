# Info

1. Тестовое задание **Конвертер валют**. Подробное условие задачи в файле files/**task.pdf**.

2. Бэкенд реализован в виде **Spring Boot REST API**.

3. В dev-профиле используется БД **PostgreSQL** в контейнере **Docker**. Настройки контейнера указываем
в файле **docker-compose.yaml**. Настройки подключения к БД прописываем  
в файле src/main/resources/**application-dev.yaml**. Для миграций используем **Liquibase**.

4. Для тестирования используем **in-memory базу данных H2**. Настройки test-профиля прописываем  
в файле src/test/resources/**application-test.yaml**. Тесты (**интеграционные** и **unit**) создаём  
в директории **src/test/java**.

5. Документацию к API генерируем с помощью **Swagger**. Для просмотра документации открыть адрес:
   - в dev-профиле: http://localhost:8082/swagger-ui/index.html
   - в проде: http://localhost:8083/swagger-ui/index.html

6. Реализована валидация данных с помощью **spring-boot-starter-validation**.

7. Фронтенд реализован в виде **SPA**: имеется единственный HTML-файл с вёрсткой, и JS-скрипт
для динамической подгрузки данных с бэкенда. Использован **AngularJS**. Запущенное приложение  
в dev-профиле доступно по адресу http://localhost:8082/:  
![](https://github.com/aleksey-nsk/currency_converter/blob/master/screenshots/01_app_run.png)  

# Развернуть приложение в проде

1. Развернём приложение в проде. Будем использовать **Docker-контейнеры**. Поднимем 3 контейнера:

       - Бэкенд (Spring Boot REST API на встроенном Tomcat-сервере)
       - Базу данных PostgreSQL
       - AngularJS-фронтенд на сервере Nginx

2. Создаём файл src/main/resources/**application-prod.yaml** для настройки prod-профиля.
Также в файле src/main/resources/**application.yaml** указываем в качестве активного профиль prod.

3. Далее создаём конфигурационный файл src/main/java/com/example/demo/config/**CorsConfig.java** для настройки **CORS**.

4. В корне проекта создать папку **prod_create** с такой структурой:  
![](https://github.com/aleksey-nsk/currency_converter/blob/master/screenshots/02_prod_create.png)  

5. Файлы для фронтенда скопировать в директорию prod_create/services/frontend/**html**, и при этом
поменять `contextPath` в js-файле.

6. Создать в корне проекта файл **build_and_push.sh** с содержимым:  
![](https://github.com/aleksey-nsk/currency_converter/blob/master/screenshots/03_build_and_push.png)  

7. Запустим файл **build_and_push.sh**. Будут созданы образы для бэкенда и фронтенда,  
и загружены на **Docker Hub**:  
![](https://github.com/aleksey-nsk/currency_converter/blob/master/screenshots/04_create_images.png)  
![](https://github.com/aleksey-nsk/currency_converter/blob/master/screenshots/05_docker_hub.png)  

# Как запустить приложение

1. Файл для запуска **prod_ready/docker-compose.yaml** выглядит так:  
![](https://github.com/aleksey-nsk/currency_converter/blob/master/screenshots/06_file_for_run.png)  

2. Возьмите машину, на которой установлены **Docker** и утилита **docker-compose**. Скопируйте на эту машину
файл **prod_ready/docker-compose.yaml**. Далее откройте в терминале папку с этим файлом и выполните
команду `docker-compose up --build`. После этого скачаются нужные образы, создадутся контейнеры, и затем
приложение будет запущено:  
![](https://github.com/aleksey-nsk/currency_converter/blob/master/screenshots/07_images_and_containers.png)  

3. Приложение доступно в браузере по адресу:
   - http://localhost:8080/

4. Документация к API доступна по адресу:
   - http://localhost:8083/swagger-ui/index.html

5. Чтобы остановить приложение, нажмите в консоли `Ctrl + C`. Контейнеры будут остановлены:  
![](https://github.com/aleksey-nsk/currency_converter/blob/master/screenshots/08_stop_app.png)  

6. Чтобы удалить контейнеры, выполните команду `docker-compose down`. В результате контейнеры будут удалены:  
![](https://github.com/aleksey-nsk/currency_converter/blob/master/screenshots/09_docker_compose_down.png)  
![](https://github.com/aleksey-nsk/currency_converter/blob/master/screenshots/10_watch_containers.png)  

7. Если нужно удалить и **том (volume) с данными**, тогда выполните  
команду `docker-compose down --volume`, в итоге:  
![](https://github.com/aleksey-nsk/currency_converter/blob/master/screenshots/11_volume_deleted.png)  
