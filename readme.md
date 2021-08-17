
## Get started with this app

  

### prerequisite

- Docker is installed on your system.
- working experience with Docker and docker-compose.
- working experience with container and postgres.

1.  **spin up postgres container and initial setting**


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
    insert into Employee (id, EMPLOYEE_NAME, EMPLOYEE_SALARY, DEPARTMENT) values (4, 'Rolf Tett', '18414949', 'Engineering');`

  
  
  

you might populat more data in `Employee.sql`

simply type `select * from Employee;` to check you have create the table. if you see the result, you are done setting up your db. please check out [my other repo](https://github.com/Bakushin10/postgres-setup-docker) for more info about postgres and command.
  

2.  **running the applciation**

3. once you are done with setting up your postgres DB, you have 2 options to start the application.

4. Option 1 : you can simply start Java application from your favorite IDE just as you would normally would. **please note that your postgres container is up and running otherwise the application will fail to start.**

5. Option 2 [recommended]: you can simply type `docker-compose up` to start Java app as container along with postgres. this will create java image out ot `Dockerfile` provided for this repo and takes care of managing postgres DB and Java services (possibly more)


# Test

### Unit Test
simply run `gradle test` to execute the unit tests.

### Component Test
run `gradle componentTestDocker` to execute component test. since I do now have other services to test against this service, I simply test the health check. please read more for the commnet [here](https://github.com/Bakushin10/alj-java-challenge/blob/dev-shin/src/componentTest/java/AxaJavaAPIComponentTest.java).


## My experience in Java
I have 1-2 years of expericnes in Java and spring boot. 