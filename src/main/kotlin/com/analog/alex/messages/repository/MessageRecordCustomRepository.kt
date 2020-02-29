package com.analog.alex.messages.repository

import com.analog.alex.messages.model.MessageRecord
import com.datastax.driver.core.querybuilder.QueryBuilder
import org.springframework.data.cassandra.core.CassandraTemplate
import org.springframework.stereotype.Repository

interface MessageRecordCustomRepository {
    fun findByBodyNullable(body: String?): Collection<MessageRecord>
}

@Repository
class MessageRecordCustomRepositoryImpl(
    val cassandraTemplate: CassandraTemplate
) : MessageRecordCustomRepository {

    override fun findByBodyNullable(body: String?): Collection<MessageRecord> {
        val query = QueryBuilder.select().from("message_record")
        body?.let {
            query.where(QueryBuilder.eq("body", it))
        }
        return cassandraTemplate.select(query, MessageRecord::class.java)
    }
}
