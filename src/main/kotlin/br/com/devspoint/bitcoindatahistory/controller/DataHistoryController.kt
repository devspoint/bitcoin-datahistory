package br.com.devspoint.bitcoindatahistory.controller

import br.com.devspoint.bitcoindatahistory.BitcoinDatahistoryApplication
import br.com.devspoint.bitcoindatahistory.entity.BitCoinHistoryEntity
import br.com.devspoint.bitcoindatahistory.repository.BitCoinHistoryRepository
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.time.Duration

@RestController
@RequestMapping("/bitcoin/history")
class DataHistoryController(
    private val repository: BitCoinHistoryRepository
) {

    companion object {
        var hasRun = false
    }

    @GetMapping(produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getHistory(): Flux<BitCoinHistoryEntity> {
        if (!hasRun) {
            hasRun = true
            return seed().flatMapMany { findAllHistory() }
        }
        return findAllHistory()
    }

    private fun findAllHistory() = repository
        .findAll(Sort.by("order").ascending())
        .delayElements(Duration.ofMillis(100L))

    private fun seed(): Mono<BitCoinHistoryEntity> {
        val bitCoinHistoryEntities = mutableListOf<BitCoinHistoryEntity>()
        val file = File(BitcoinDatahistoryApplication::class.java.getResource("/coin_Bitcoin.csv").file)
        BufferedReader(FileReader(file)).use { br ->
            var line = br.readLine()
            while (line != null) {
                val values: Array<String> = line.split(",").toTypedArray()
                bitCoinHistoryEntities.add(
                    BitCoinHistoryEntity(
                        id = null,
                        order = values[0].toInt(),
                        date = values[3],
                        openingValue = values[6].toBigDecimal()
                    )
                )
                line = br.readLine()
            }
        }
        return repository.saveAll(bitCoinHistoryEntities).doOnComplete {
            println("All entities were saved")
        }.last()
    }
}