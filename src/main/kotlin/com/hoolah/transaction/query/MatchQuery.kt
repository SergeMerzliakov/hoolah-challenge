package com.hoolah.transaction.query

import com.hoolah.transaction.model.Transaction

/**
 * Queries understand how to match a transaction and
 * store the results internally.
 *
 * Statistics can be generated from the result set
 */
interface MatchQuery {

	fun match(tx: Transaction)

	fun generateStatistics(f: TransactionStatistics)

}

