package com.hoolah.transaction.query

import com.hoolah.transaction.model.Transaction

/**
 * Interface for all statistics calculated from a list of transactions
 */
interface TransactionStatistics {
	fun calculate(results: List<Transaction>)
}


