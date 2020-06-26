package com.analog.alex.messages.respository

import com.analog.alex.messages.model.Costumer
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CostumerRepository : MongoRepository<Costumer, String>