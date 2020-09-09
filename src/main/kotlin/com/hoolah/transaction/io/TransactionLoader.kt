package com.hoolah.transaction.io

import com.hoolah.transaction.model.Transaction
import com.hoolah.transaction.model.TransactionDatabase
import com.opencsv.CSVReader
import java.io.File
import java.io.FileReader

/**
 * Loads Transactions from a CSV file
 */
class TransactionLoader {

	/**
	 * load single file with transactions
	 * @throws TransactionLoadException if file not file or there is an error with loading the data itself
	 */
	fun load(transactionFile: File): TransactionDatabase {
		if (!transactionFile.exists())
			throw TransactionLoadException("transaction file [${transactionFile.absolutePath} ] not found. Load terminated.")

		var csvReader: CSVReader? = null
		try {
			csvReader = CSVReader(FileReader(transactionFile))
			csvReader.skip(1) //skip header

			val transactions = TransactionDatabase()

			var line: Array<String>? = csvReader.readNext()
			while (line != null) {
				val tx = Transaction(line)
				transactions.addTransaction(tx)
				line = csvReader.readNext()
			}
			return transactions
		}
		catch (e: Exception) {
			throw TransactionLoadException("Error loading transactions from file [${transactionFile.absolutePath}]", e)
		}
		finally {
			csvReader?.close()
		}
	}
}
