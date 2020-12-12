# Spring Security

## Introduction

A quick cheat sheet for how to use **Spring Security**. Of course, this is only a small demonstrations of a somewhat restricted scenario of authentications against a web service. 

The scenario here is where a user can log itself with a `username` and `password` pair and obtain a `JWT` with which to authenticate itself in subsequent requests.

 ## How to run

### Locally

**Pre-requisites**: Java 11+ installed; Docker

1. Launch the DB via `docker-compose up -d mongo`.
2. Build the application (ideally via **IntelliJ**).
3. Enjoy!

 by **Miguel Alexandre** @2020