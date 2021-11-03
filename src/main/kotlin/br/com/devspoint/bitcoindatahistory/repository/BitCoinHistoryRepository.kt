package br.com.devspoint.bitcoindatahistory.repository

import br.com.devspoint.bitcoindatahistory.entity.BitCoinHistoryEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BitCoinHistoryRepository: ReactiveMongoRepository<BitCoinHistoryEntity, String>