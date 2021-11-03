package br.com.devspoint.bitcoindatahistory


import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfig
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.net.URL


@SpringBootApplication
class BitcoinDatahistoryApplication

fun main(args: Array<String>) {
	val starter = MongodStarter.getDefaultInstance()

	val port = Network.getFreeServerPort()
	val mongodConfig: MongodConfig = MongodConfig.builder()
		.version(Version.Main.PRODUCTION)
		.net(Net(port, Network.localhostIsIPv6()))
		.build()

	var mongodExecutable = starter.prepare(mongodConfig)
	val mongod = mongodExecutable.start()
	runApplication<BitcoinDatahistoryApplication>(*args)
}