package com.hoolah.transaction.io

/**
 * Thrown when attempting to load transactions from data source (files)
 */
class TransactionLoadException : Exception {
	constructor(message: String?) : super(message)
	constructor(message: String?, cause: Throwable?) : super(message, cause)
}
