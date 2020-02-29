package com.analog.alex.messages.repository

import com.analog.alex.messages.error.Forbidden
import com.analog.alex.messages.model.MessageRecord
import java.util.*
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.data.rest.core.annotation.RestResource
import org.springframework.stereotype.Repository

@Repository
@RepositoryRestResource(collectionResourceRel = "message-records", path = "message-records")
interface MessageRecordRepository : CassandraRepository<MessageRecord, String>,
    MessageRecordCustomRepository {

    @Cacheable("message_record")
    override fun findAll(): MutableList<MessageRecord>

    @Cacheable("message_record")
    override fun findById(id: String): Optional<MessageRecord>

    @CacheEvict("message_record", allEntries = true)
    override fun <S : MessageRecord?> save(entity: S): S

    @RestResource(exported = false)
    override fun delete(entity: MessageRecord) {
        throw Forbidden("Entries are not allowed to be deleted")
    }

    @RestResource(exported = false)
    override fun deleteById(id: String) {
        throw Forbidden("Entries are not allowed to be deleted")
    }
}
