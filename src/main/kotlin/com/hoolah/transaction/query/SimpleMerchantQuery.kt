package com.hoolah.transaction.query

import com.hoolah.transaction.model.Transaction
import com.hoolah.transaction.model.TransactionType
import com.hoolah.transaction.model.cleanString
import java.time.LocalDateTime


/**
 * reverses done here
 */
class SimpleMerchantQuery(private val fromDate: LocalDateTime, private val toDate: LocalDateTime, private val merchant: String) : MatchQuery {

	init {
		if (toDate.isBefore(fromDate) || toDate.isEqual(fromDate))
			throw IllegalArgumentException("To Date must be before From Date")
		if (merchant.trim().isEmpty())
			throw IllegalArgumentException("merchant name cannot be empty")
	}

	val results = mutableListOf<Transaction>()

	override fun match(tx: Transaction) {
		if (tx.date.isAfter(fromDate) && tx.date.isBefore(toDate) && tx.merchant == cleanString(merchant) && tx.type != TransactionType.reversal)
			results.add(tx)
	}

	override fun generateStatistics(f: TransactionStatistics) {
		f.calculate(results)
	}
}
