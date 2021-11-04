package br.com.devspoint.bitcoindatahistory


import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfig
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BitcoinDatahistoryApplication

fun main(args: Array<String>) {

	MongodStarter
		.getDefaultInstance()
		.prepare(
			MongodConfig.builder()
			.version(Version.Main.PRODUCTION)
			.net(Net(Network.getFreeServerPort(), Network.localhostIsIPv6()))
			.build()
		)
		.start()

	runApplication<BitcoinDatahistoryApplication>(*args)
}