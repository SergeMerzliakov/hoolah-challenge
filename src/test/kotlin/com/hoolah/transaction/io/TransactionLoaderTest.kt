package com.hoolah.transaction.io

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.io.File


class TransactionLoaderTest {


	@Test
	fun shouldLoadFromFileAndHandleReversals() {
		// given
		val testFile = File(javaClass.getResource("/transactions1.csv").file)

		// when
		val loader = TransactionLoader()

		// then
		val db = loader.load(testFile)

		assertThat(db.transactions).hasSize(4)
		assertThat(db.transactions.map { it.id }).contains("wlmfrdgd", "lfvcteym", "suovoisp", "jyapkzfz")
	}


	@Test
	fun shouldLoadFromEmptyFile() {
		// given
		val testFile = File(javaClass.getResource("/empty.csv").file)

		// when
		val loader = TransactionLoader()

		// then
		val db = loader.load(testFile)

		assertThat(db.transactions).isEmpty()
	}


	@Test
	fun shouldLoadFromEmptyFileNoHeader() {
		// given
		val testFile = File(javaClass.getResource("/emptyNoHeader.csv").file)

		// when
		val loader = TransactionLoader()

		// then
		val db = loader.load(testFile)

		assertThat(db.transactions).isEmpty()
	}


	@Test(expected = TransactionLoadException::class)
	fun shouldFailLoadIfTransactionFileNotFound() {
		val loader = TransactionLoader()
		loader.load(File("/not/found"))
	}
}
