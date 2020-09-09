package com.hoolah.transaction.model

import java.math.BigDecimal
import java.time.LocalDateTime

enum class TransactionType {
	payment,
	reversal
}

/**
 * For all financial transactions
 * relatedTransactionId will contain the ID of the transaction it is reversing if type is reversal
 */
class Transaction(data: Array<String>) {

	val id: String = cleanString(data[0])
	val date: LocalDateTime = cleanDate(data[1])
	val amount: BigDecimal = BigDecimal(data[2].trim())
	val merchant: String = cleanString(data[3])
	val type: TransactionType = TransactionType.valueOf(cleanString(data[4]))
	var relatedTransactionId: String? = null

	init {
		// assumes data is well formed but may have spaces
		if (data.size > 5)
			relatedTransactionId = cleanString(data[5])
	}
}



