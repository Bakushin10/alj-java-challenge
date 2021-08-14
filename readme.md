### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.



### Instructions

- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests
  - Change syntax
  - Protect controller end points
  - Add caching logic for database calls
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.

#### Restrictions
- use java 8


#### What we will look for
- Readability of your code
- Documentation
- Comments in your code 
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### Your experience in Java

Please let us know more about your Java experience in a few sentences. For example:

- I have 3 years experience in Java and I started to use Spring Boot from last year
- I'm a beginner and just recently learned Spring Boot
- I know Spring Boot very well and have been using it for many years


to do
create table by logging in to the running postgres container. 


CREATE TABLE Employee(
	id BIGINT,
	EMPLOYEE_NAME VARCHAR( 50 ),
	EMPLOYEE_SALARY integer,
	DEPARTMENT VARCHAR( 50 ),
);


## Get started with this app

### prerequisite

- Docker is installed on your system.
- working experience with Docker and docker-compose.
- working experience with container and postgres.


1. **spin up postgres container and initial setting**

      1. change `POSTGRES_MASTER_DATA` in `.env`. this will be used in valume for your data.

      2. spin up postgres container. open up a terminal and type below command. `docker-compose up postgres` 

      3. open up another terminal and type below to log into a running postgres container.
      `docker exec -it axa-postgres bash`

      4. log in to postgres db `psql postgres -U postgres`
      5. type below command to create Employee table. `CREATE TABLE Employee( id BIGINT, EMPLOYEE_NAME VARCHAR( 50 ), EMPLOYEE_SALARY integer, DEPARTMENT VARCHAR( 50 ));`

      6. populate db with some data if you like


        insert into Employee (id, EMPLOYEE_NAME, EMPLOYEE_SALARY, DEPARTMENT) values (1, 'Valene Hasker', '9299948', 'Legal');
        insert into Employee (id, EMPLOYEE_NAME, EMPLOYEE_SALARY, DEPARTMENT) values (2, 'Hersch Haycock', '11319624', 'Human Resources');
        insert into Employee (id, EMPLOYEE_NAME, EMPLOYEE_SALARY, DEPARTMENT) values (3, 'Florinda St Hill', '16135903', 'Support');
        insert into Employee (id, EMPLOYEE_NAME, EMPLOYEE_SALARY, DEPARTMENT) values (4, 'Rolf Tett', '18414949', 'Engineering');



  you might populat more data in `Employee.sql`
  simply type `select * from Employee;` to check you have create the table. if you see the result, you are done setting up your db. please check out [my other repo](https://github.com/Bakushin10/postgres-setup-docker) for more info about postgres and command.

2. **running the applciation**
    1. once you are done with setting up your postgres DB, you have 2 options to start the application. 
        1. Option 1 : you can simply start Java application from your favorite IDE just as you would normally would. **please note that your postgres container is up and running otherwise the application will fail to start.**
        2. Option 2 [recommended]: you can simply type `docker-compose up` to start Java app as container along with postgres. this will create java image out ot `Dockerfile` provided for this repo and takes care of managing postgres DB and Java services (possibly more)

  