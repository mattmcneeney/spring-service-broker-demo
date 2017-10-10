# Spring Service Broker Demo

1. Go to [https://start.spring.io/](https://start.spring.io/])
1. Generate a Maven Project with Java and Spring Boot 2.0.0 (SNAPSHOT)
1. Add the `spring-cloud-cloudfoundry-service-broker` depdendency to `pom.xml`:
   ```
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-cloudfoundry-service-broker</artifactId>
        <version>1.0.0.RELEASE</version>
    </dependency>
   ```
1. Configure the Catalog data in `ServicebrokerApplication.java`:
   ```
   package com.servicebroker.servicebroker;

   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;

   import org.springframework.cloud.servicebroker.model.Catalog;
   import org.springframework.cloud.servicebroker.model.Plan;
   import org.springframework.cloud.servicebroker.model.ServiceDefinition;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import java.util.Collections;

   @SpringBootApplication
   public class ServicebrokerApplication {

   	public static void main(String[] args) {
   		SpringApplication.run(ServicebrokerApplication.class, args);
   	}

       @Bean
       public Catalog catalog() {
           return new Catalog(Collections.singletonList(
               new ServiceDefinition(
                     "demo-service-broker",
                     "demo",
                     "An example service broker implementation",
                     true,
                     false,
                     Collections.singletonList(
                           new Plan("simple",
                                 "default",
                                 "This is a default  plan.",
                                 null)),
                     null,
                     null,
                     null,
                     null)));
       }

   }
   ```
1. Create `ServiceInstance.java` implementing  `ServiceInstanceService`:
   ```
   package com.servicebroker.servicebroker;

   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.cloud.servicebroker.model.CreateServiceInstanceRequest;
   import org.springframework.cloud.servicebroker.model.CreateServiceInstanceResponse;
   import org.springframework.cloud.servicebroker.model.UpdateServiceInstanceRequest;
   import org.springframework.cloud.servicebroker.model.UpdateServiceInstanceResponse;
   import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceRequest;
   import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceResponse;
   import org.springframework.cloud.servicebroker.model.GetLastServiceOperationRequest;
   import org.springframework.cloud.servicebroker.model.GetLastServiceOperationResponse;
   import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
   import org.springframework.stereotype.Service;

   @Service
   public class ServiceInstance implements ServiceInstanceService {

       @Override
       public CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request) {
           return new CreateServiceInstanceResponse();
       }

       @Override
       public UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest request) {
           return new UpdateServiceInstanceResponse();
       }

       @Override
       public DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request) {
           return new DeleteServiceInstanceResponse();
       }

       @Override
       public GetLastServiceOperationResponse getLastOperation(GetLastServiceOperationRequest request) {
           return new GetLastServiceOperationResponse();
       }

   }
   ```

6. Create `ServiceInstanceBinding.java` implementing `ServiceInstanceBindingService`:
   ```
   package com.servicebroker.servicebroker;

   import java.util.Map;
   import java.util.HashMap;

   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingRequest;
   import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingResponse;
   import org.springframework.cloud.servicebroker.model.CreateServiceInstanceAppBindingResponse;
   import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceBindingRequest;
   import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
   import org.springframework.stereotype.Service;

   @Service
   public class ServiceInstanceBinding implements ServiceInstanceBindingService {

       @Override
       public CreateServiceInstanceBindingResponse createServiceInstanceBinding(CreateServiceInstanceBindingRequest request) {
           Map<String, Object> credentials = new HashMap<String, Object>();
           credentials.put("username", "admin");
           credentials.put("password", "password");
           return new CreateServiceInstanceAppBindingResponse().withCredentials(credentials);
       }

       @Override
       public void deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest request) {
           return;
       }

   }
   ```
1. Build the jar:
   ```
   mvn package
   ```
1. Deploy the broker:
   ```
   cf push broker -p target/servicebroker-0.0.1-SNAPSHOT.jar -m 1G
   ```
1. Get the broker password:
   ```
   cf logs --recent broker | grep "Using default security password"
   ```
1. Create the service broker:
   ```
   cf create-service-broker broker “user” <password> <url>
   ```
1. Enable service access:
   ```
   cf enable-service-access demo
   ```
1. Show the marketplace:
   ```
   cf marketplace
   ```
1. Create a service instance:
   ```
   cf create-service demo simple r1
   ```
1. Create a service key (i.e. a service instance binding):
   ```
   cf create-service-key r1 my-key
   ```
1. Show the service key:
   ```
   cf service-key r1 my-key    
   ```

