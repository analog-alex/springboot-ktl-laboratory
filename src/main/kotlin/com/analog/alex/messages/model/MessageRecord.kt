package com.analog.alex.messages.model

import java.io.Serializable
import java.time.Instant
import org.springframework.data.annotation.Id
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table

@Table(value = "message_record")
data class MessageRecord(
    @Id @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED) val id: String,
    @Column(value = "body") val body: String,
    @Column(value = "at") val at: Instant,
    @Column(value = "queue") val queue: String,
    @Column(value = "key") val key: String
) : Serializable
