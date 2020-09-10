package com.hoolah.runner

import com.hoolah.transaction.io.TransactionLoader
import com.hoolah.transaction.model.cleanDate
import com.hoolah.transaction.model.cleanString
import com.hoolah.transaction.query.SimpleMerchantQuery
import com.hoolah.transaction.query.SimpleTransactionStatistics
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.File

private val log: Logger = LogManager.getLogger(RunApplication::class.java)


class RunApplication {

	/**
	 * Entry Point
	 */
	companion object {

		@JvmStatic
		fun main(args: Array<String>) {
			val app = RunApplication()
			app.run(args)
		}
	}

	fun run(args: Array<String>) {
		if (args.size != 4) {
			println("Invalid arguments. Arguments are:")
			println("\t 1) Merchant name. Case is not important")
			println("\t 2) from date in format: DD/MM/YYYY hh:mm:ss")
			println("\t 3) to date in format: DD/MM/YYYY hh:mm:ss")
			println("\t 4) file path of csv file")
			return
		}

		log.info("Transaction processing started")

		try {
			val merchant = cleanString(args[0])
			val fromDate = cleanDate(args[1])
			val toDate = cleanDate(args[2])
			val file = File(args[3])

			val loader = TransactionLoader()
			val db = loader.load(file)

			val query = SimpleMerchantQuery(fromDate, toDate, merchant)
			db.query(query)

			val stats = SimpleTransactionStatistics()
			query.generateStatistics(stats)

			println("\nStatistics:")
			println("\tNumber of Transactions: ${stats.number}")
			println("\tAverage Transaction Value: ${stats.average}")
			println()

			log.info("Transaction processing completed successfully")
		}
		catch (e: Exception) {
			log.error("Transaction processing failed", e)
		}
	}
}

