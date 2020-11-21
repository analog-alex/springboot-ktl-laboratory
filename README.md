# Spring Data

## Introduction

A quick cheat sheet for how to use **Spring Data JPA** and **Spring Data JDBC** to model relational data, store it and expose it
using **Spring Data Rest**.

Examples include **Criteria API** queries, JDBC queries and JQPL queries.

The use case is the relational database Postgres. The tables are created via **Flyway** when the application is launched.


## Model

_TODO_

## How to run

### Locally

**Pre-requisites**: Java 11+ installed; Docker

1. Launch the DB using `docker-compose up -d postgres`.
2. Build the application (ideally via **IntelliJ**). 
3. Boot up the application. All **Flyway** migrations are applied here.
4. Call `localhost:8080/api` to get the resource endpoints.
5. Enjoy!

### Docker

_SOON_

## Notes

If the `spring.profile` is set to `dev` the application will inject into the DB a few example rows on application
start up.  


by **Miguel Alexandre** @2020

  
