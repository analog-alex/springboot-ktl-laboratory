# Spring Boot Laboratory

![Dexter](https://i.ebayimg.com/images/g/ntAAAOSw0rdcAEgr/s-l300.jpg)

## Introduction
A collection of micro **Spring Boot** (v2.0.0+) projects, in the **Kotlin** programming language, that exemplify common usages of the framework. Each project counts a an experiment in the science of **Spring Boot**.

The goal of each of the experiments is to serve as a kind of cheat sheet of how to do some common integrations with other tools and infrastructure, as well as a learning opportunity for good practices and what not 🤓️. 

It separates the different experiments with _git_ branches.

##  Usages
### Spring Data

| Branch        | Link          |
| ------------- |:-------------:| 
| `spring-data` | [Go to branch](https://github.com/analog-alex/springboot-ktl-laboratory/tree/spring-data)     |

Leveraging **Postgres** database and **Flyway** migrations this micro project uses *Spring Data*, both JPA and JDBC, to model entities, create complex queries, alter several tables in a transaction and expose them via Rest (with *Data Rest*).

### GraphQL
| Branch        | Link          |
| ------------- |:-------------:| 
| `graph-ql` | [Go to branch](https://github.com/analog-alex/springboot-ktl-laboratory/tree/graph-ql)     |

A very simple example of how to access data via GraphQL. Data is stores in MongoDB and the user (via, perhaps, Postman 😀️) can construct arbitrary queries on that data.

### Message Brokers
| Branch        | Link          |
| ------------- |:-------------:| 
| `message-brokers` | [Go to branch](https://github.com/analog-alex/springboot-ktl-laboratory/tree/message-brokers)     |

A very simple demonstration of `RabbitMQ` and `Kafka` integration with **Spring Boot**.