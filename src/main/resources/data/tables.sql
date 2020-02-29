-- KEYSPACE Messages
CREATE KEYSPACE messages
WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

USE messages;

-- TABLE Message Record
CREATE TABLE message_record (
   id text PRIMARY KEY,
   body text,
   at timestamp,
   queue text,
   key text
);
