# Info

Task **Currency converter**. Detailed task requirements see in files/**task.pdf**.  
Project consists of repositories:  
- Backend (current repository)
- [Frontend](https://github.com/aleksey-nsk/currency_converter_frontend)
- [Deployment](https://github.com/aleksey-nsk/currency_converter_deployment)

# Backend

1. The backend is created as **Spring Boot REST API**.

2. Database **PostgreSQL** is used in **Docker** container. Container settings are specified in  
   the docker/**docker-compose.yaml**:  
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/00_1_docker_compose.png)  

3. Application settings (port, logging, database connection) are written in  
   the src/main/resources/**application-dev.yaml**:  
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/00_2_application.png)  

4. We use **Liquibase** for migrations. Also **migration** for **indexes** is added.
   
5. We use **in-memory database H2** for testing. Test-profile settings are specified in  
   the src/test/resources/**application-test.yaml**. Annotation **@ActiveProfiles("test")** is written  
   above all test classes in order to activate test profile.  
   Tests (**integration** and **unit**) are created in the directory **src/test/java**.
   
6. Data validation is implemented using **spring-boot-starter-validation**.

7. **Custom exceptions** are created. **Advises** are used to handle exceptions. In order to handle exceptions
   globally and centrally we use class annotated with **@ControllerAdvice**. Within this class we use methods with
   annotation **@ExceptionHandler**, which is used to define handled exception (or list of handled exceptions).
   
8. API documentation is generated using **Swagger**. To do this, add dependencies  
   in pom-file:  
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/01_1_swagger.png)  

   Next, create a configuration file  
   src/main/java/com/example/demo/config/**SwaggerConfig.java**:  
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/01_2_config.png)  

   For documentation, use the **@Tag** annotation on the controller classes, and  
   **@Operation** annotation on the methods:  
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/01_3_controller.png)  

   To view **API documentation** open the address: http://localhost:8082/swagger-ui/index.html  
   The documentation looks like this:  
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/01_4_doc.png)  

9. Open the application in a browser. For example, conversion statistics  
   available at: http://localhost:8082/api/v1/statistics      
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/02_1_browser.png)  
   
   The remaining **endpoints** are checked using **Postman**:    
   ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/02_2_postman.png)  

10. **CORS** is configured:  
    ![](https://github.com/aleksey-nsk/currency_converter_backend/blob/master/screenshots/03_cors.png)  

# Create image

- We will write the commands for image creating in the file docker/build/**Dockerfile**

- In order not to build every time in the console manually, we create the script **build_and_push.sh**

- Next, you need to log in to **Docker Hub** using console

- Finally run the **build_and_push.sh**. The backend image will be created and uploaded to **Docker Hub**
