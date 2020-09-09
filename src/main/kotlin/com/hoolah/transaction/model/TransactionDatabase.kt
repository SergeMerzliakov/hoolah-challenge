package com.hoolah.transaction.model

import com.hoolah.transaction.query.MatchQuery

/**
 * Ensures that only valid transactions are stored and
 * reversals processed on addition
 *
 * Stores transactions as a simple list and not optimized for very large numbers
 * of transactions
 */
class TransactionDatabase {
	var transactions = mutableListOf<Transaction>()

	/**
	 * perform all reversals as well
	 */
	fun addTransaction(t: Transaction) {
		if (t.type != TransactionType.reversal)
			transactions.add(t)
		else {
			transactions.removeIf { it.id == t.relatedTransactionId }
		}
	}

	fun query(q: MatchQuery) {
		transactions.forEach { q.match(it) }
	}
}
