package com.hoolah.transaction.query

import com.hoolah.transaction.model.Transaction
import java.math.BigDecimal

/**
 * Return average transaction value and transaction count
 */
class SimpleTransactionStatistics : TransactionStatistics {
	var number: Int = 0
	var average: BigDecimal = BigDecimal.ZERO

	/**
	 * We store all dollar amounts as big decimals, even though it's more painful for summary calculations
	 */
	override fun calculate(results: List<Transaction>) {
		if (results.isEmpty())
			return

		val total = results.map { it.amount.toDouble() }.reduce { sum, amount -> sum + amount }

		number = results.size
		average = BigDecimal.valueOf(total / results.size)
	}
}
