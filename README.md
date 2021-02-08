# test-tracker #
This repo contains microservices witch should be separated to independent repositories in future

## Main structure ##
Microservices list
* core(core api service)
* discovery (service discovery)
* report (service for report flow)
* gateway (service for API Gateway routing)

Utils modules
* database(environment module, using for storing ddl schema, migrations)
* util(utils module, using for storing some main flow for another modules, in this case for configurable working with file store providers like S3 or machine store. In future can be enhancement using another utils )

Mock module
* Info (mock service for user details service)

## Why microservices? ##
* Based on count of possible tasks that was written in requirements, was made a decision to use a microservice architecture with orchestration mechanism, for horizontal scalability
* System using docker private networking for hiding all services except gateway(gateway locating in private network but using open port for communication with world), authorization and authentication will be added to gateway layer in future 
 
## Environment list ##
core and report modules:

```
* TRACKER_DRIVER_CLASS_NAME - database driver 
* TRACKER_DB_URL - database url 
* TRACKER_DB_USER - database user 
* TRACKER_DB_PASSWORD - database password 
* TRACKER_DB_MAXIMUM_POOL_SIZE - database max pool size(using in hikari pool)
 
```

Util::filesystem module:
```
* FILE_SYSTEM_PROVIDER - selecting file store provider: S3 for S3 provider,  HOST for host machine store
* S3_BUCKET - bucket name
* S3_REGION - region name
* S3_ACCESS_KEY - acsess key
* S3_SECRET_KEY - secret key
* ROOT_DIR - root dir for HOST FILE_SYSTEM_PROVIDER

```

## Tech stack ##
* Java 11
* Maven
* Spring boot
* Spring cloud
* Postgress
* Docker
* Docker-compose



