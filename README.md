## PALABRA

# Introduction
The project introduces:
1. Spring Cloud Config server that is deployed as Docker container and can manage a services configuration information using a file classpath repository.
2. Eureka server running as a Spring-Cloud based service. This service will allow multiple service instances to register with it. Clients that need to call a service will use Eureka to lookup the physical location of the target service.
3. API Gateway. All of our microservices can be routed through the gateway and have pre, response and post policies enforced on the calls.
4. Microservice that will manage the integration.
5. Postgres SQL database used to hold the data.

## Initial Configuration
Clone and run from your command line:

```bash
# Clone this repository
$ git clone https://github.com/mariogegaj/impatech-assignment

# Go into the parent project, by changing to the directory
$ cd impatech-parent

# To build the project as a docker image, open a command-line window and execute the following command:
$ mvn clean package dockerfile:build

# Now we are going to use docker-compose to start the actual image
$ docker-compose -f docker/docker-compose.yml up
```