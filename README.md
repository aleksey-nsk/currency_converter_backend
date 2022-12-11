# Info

Тестовое задание **Конвертер валют**. Подробное условие в файле files/**task.pdf**.  
Проект состоит из репозиториев:
- Backend (данный репозиторий)
- Frontend
- Deployment

# Backend

1. Бэкенд реализован в виде **Spring Boot REST API**.

2. Используется БД **PostgreSQL** в контейнере **Docker**. Настройки контейнера указываем в  
   файле docker/**docker-compose.yaml**.

3. Настройки приложения (порт, логирование, подключение к БД) прописываем в  
   файле src/main/resources/**application-dev.yaml**.

4. Для миграций используем **Liquibase**.

5. Для тестирования используем **in-memory базу данных H2**. Настройки test-профиля прописываем в
   файле src/test/resources/**application-test.yaml**. Тесты (**интеграционные** и **unit**) создаём в
   директории **src/test/java**.

6. Реализована валидация данных с помощью **spring-boot-starter-validation**.

7. Документацию к API генерируем с помощью **Swagger**. Для этого подключаем зависимости  
   в pom-файле:  
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/01_1_swagger.png)  

   Далее создаём конфигурационный файл  
   src/main/java/com/example/demo/config/**SwaggerConfig.java**:  
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/01_2_config.png)  

   Для документирования используем аннотацию **@Tag** над классами контроллеров, и  
   аннотацию **@Operation** над методами:  
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/01_3_controller.png)  

   Для просмотра документации открыть адрес: http://localhost:8082/swagger-ui/index.html  
   Выглядит документация так:  
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/01_4_doc.png)  

8. Открыть приложение в браузере (например статистику по конвертациям)  
   можно по адресу: http://localhost:8082/api/v1/statistics    
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/02_1_browser.png)  

   Остальные точки проверяем с помощью **Postman**:  
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/02_2_postman.png)  

# Создать образ

- Команды для создания образа пропишем в файле docker/**Dockerfile**

- Чтобы не билдить каждый раз в консоли руками, создаём в корне скрипт **build_and_push.sh**

- Далее надо через консоль залогиниться на **Docker Hub**

- Теперь запустим файл **build_and_push.sh**. Будет создан образ для бэкенда и загружен на **Docker Hub**
