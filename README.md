# springboot-ktl-laboratory

## Graph QL

### Introduction

In this experiment, we create a **Spring Boot**® application that boots up, connects to a MongoDB® instance and serves a 
persistent resource using GraphQL.

### Set Up

Run `docker-compose up mongo`

### Resource Schema

```graphql
type Costumer {
	id: ID!
	name: String!
	age: Int
	country: String
}
```

### Test

The GraphQL functionality is exposed on `http://localhost:8080/apis/graphql`

An example *query*:

**POST**: `http://localhost:8080/apis/graphql`  
**BODY**:
```graphql
{
    findAllCostumers {
        id
        name
        age
        country
    }
}
```