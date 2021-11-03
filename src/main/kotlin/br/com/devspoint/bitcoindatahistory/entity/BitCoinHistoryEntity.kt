package br.com.devspoint.bitcoindatahistory.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document("history")
data class BitCoinHistoryEntity (

    @Id
    val id: String?,
    val order: Int,
    val openingValue: BigDecimal,
    val date: String

)