# Info

Тестовое задание **Конвертер валют**. Подробное условие в файле files/**task.pdf**.  
Проект состоит из репозиториев:
- Backend (данный репозиторий)
- [Frontend](https://github.com/aleksey-nsk/currency_converter_frontend)
- [Deployment](https://github.com/aleksey-nsk/currency_converter_deployment)

# Backend

1. Бэкенд реализован в виде **Spring Boot REST API**.

2. Используется БД **PostgreSQL** в контейнере **Docker**. Настройки контейнера указываем в  
   файле docker/**docker-compose.yaml**.

3. Настройки приложения (порт, логирование, подключение к БД) прописываем в  
   файле src/main/resources/**application-dev.yaml**.

4. Для миграций используем **Liquibase**. В том числе добавляем **миграцию** создающую **индексы**.
   
5. Для тестирования используем **in-memory базу данных H2**. Настройки test-профиля прописываем  
   в файле src/test/resources/**application-test.yaml**. Далее над всеми тестовыми классами пишем
   аннотацию **@ActiveProfiles("test")** для активации тестового профиля.  
   Тесты (**интеграционные** и **unit**) создаём в директории **src/test/java**.

6. Реализована валидация данных с помощью **spring-boot-starter-validation**.

7. Создаём **пользовательские исключения**. Для обработки исключений используем **эдвайсы**. Глобально и
   централизованно обрабатываем исключения с помощью класса с аннотацией **@ControllerAdvice**. Внутри данного
   класса создаются методы с аннотацией **@ExceptionHandler**, с помощью которой определяется
   обрабатываемое исключение (либо список обрабатываемых исключений).

8. Документацию к API генерируем с помощью **Swagger**. Для этого подключаем зависимости  
   в pom-файле:  
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/01_1_swagger.png)  

   Далее создаём конфигурационный файл  
   src/main/java/com/example/demo/config/**SwaggerConfig.java**:  
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/01_2_config.png)  

   Для документирования используем аннотацию **@Tag** над классами контроллеров, и  
   аннотацию **@Operation** над методами:  
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/01_3_controller.png)  

   Для просмотра **API-документации** открыть адрес: http://localhost:8082/swagger-ui/index.html  
   Выглядит документация так:  
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/01_4_doc.png)  

9. Открыть приложение в браузере (например статистику по конвертациям)  
   можно по адресу: http://localhost:8082/api/v1/statistics    
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/02_1_browser.png)  

   Остальные точки проверяем с помощью **Postman**:  
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/02_2_postman.png)  

10. Настроим **CORS**:  
    ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/03_cors.png)  

# Создать образ

- Команды для создания образа пропишем в файле docker/build/**Dockerfile**

- Чтобы не билдить каждый раз в консоли руками, создаём в корне скрипт **build_and_push.sh**

- Далее надо через консоль залогиниться на **Docker Hub**

- Теперь запустим файл **build_and_push.sh**. Будет создан образ для бэкенда и загружен на **Docker Hub**
