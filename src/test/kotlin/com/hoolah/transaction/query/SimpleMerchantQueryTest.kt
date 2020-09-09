package com.hoolah.transaction.query

import com.hoolah.transaction.model.Transaction
import com.hoolah.transaction.model.TransactionDatabase
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Percentage
import org.junit.Test
import java.math.BigDecimal
import java.time.LocalDateTime

private val acme1 = Transaction(arrayOf("1", "01/01/2018 13:00:00", "10.00", "Acme", "PAYMENT"))
private val acme2 = Transaction(arrayOf("2", "01/01/2018 14:00:00", "20.00", "Acme", "PAYMENT"))
private val acme3 = Transaction(arrayOf("3", "01/01/2018 15:00:00", "30.00", "Acme", "PAYMENT"))
private val acme4 = Transaction(arrayOf("4", "01/01/2018 22:00:00", "10.00", "Acme", "REVERSAL", "1"))

class SimpleMerchantQueryTest {


	@Test
	fun shouldMatchWithReversal() {
		shouldMatchWithReversalOutsideQueryRange("acme")
	}

	@Test
	fun shouldMatchWithReversalIgnoringCase() {
		shouldMatchWithReversalOutsideQueryRange("ACME")
	}


	@Test
	fun shouldMatchWithReversalIgnoringWhitespace() {
		shouldMatchWithReversalOutsideQueryRange("  Acme  ")
	}

	@Test
	fun shouldMatchWithReversalInsideQueryRange() {
		val acme1b = Transaction(arrayOf("1000", "01/01/2018 13:15:00", "10.00", "Acme", "REVERSAL", "1"))
		val db = createTestDatabase(acme1, acme1b, acme2, acme3)
		val start = LocalDateTime.of(2018, 1, 1, 12, 55, 0)
		val finish = start.plusHours(2L)
		val query = SimpleMerchantQuery(start, finish, "acme")
		db.query(query)
		val stats = SimpleTransactionStatistics()
		query.generateStatistics(stats)
		assertThat(stats.number).isEqualTo(1)
		assertThat(stats.average).isCloseTo(BigDecimal.valueOf(20.0), Percentage.withPercentage(0.01))
	}


	@Test
	fun shouldMatchWithNoReversals() {
		val db = createTestDatabase(acme1, acme2, acme3)
		val start = LocalDateTime.of(2018, 1, 1, 12, 55, 0)
		val finish = start.plusHours(2L)
		val query = SimpleMerchantQuery(start, finish, "acme")
		db.query(query)
		val stats = SimpleTransactionStatistics()
		query.generateStatistics(stats)
		assertThat(stats.number).isEqualTo(2)
		assertThat(stats.average).isCloseTo(BigDecimal.valueOf(15.0), Percentage.withPercentage(0.01))
	}

	@Test
	fun shouldNotMatchUnknownMerchant() {
		val db = createTestDatabase(acme1, acme2)

		val start = LocalDateTime.of(2018, 1, 1, 12, 55, 0)
		val finish = start.plusHours(1L)
		val query = SimpleMerchantQuery(start, finish, "unknown")
		db.query(query)
		val stats = SimpleTransactionStatistics()
		query.generateStatistics(stats)
		assertThat(stats.number).isEqualTo(0)
		assertThat(stats.average).isEqualTo(BigDecimal.ZERO)
	}


	@Test
	fun shouldNotMatchEmptyDatabase() {
		val db = createTestDatabase()

		val start = LocalDateTime.of(2018, 1, 1, 12, 55, 0)
		val finish = start.plusHours(1L)
		val query = SimpleMerchantQuery(start, finish, "doesNotMatter")
		db.query(query)
		val stats = SimpleTransactionStatistics()
		query.generateStatistics(stats)
		assertThat(stats.number).isEqualTo(0)
		assertThat(stats.average).isEqualTo(BigDecimal.ZERO)
	}


	@Test(expected = IllegalArgumentException::class)
	fun shouldNotAllowBadDateParameters() {
		val start = LocalDateTime.of(2018, 1, 1, 0, 0, 0)
		SimpleMerchantQuery(start, start, "doesNotMatter")
	}

	@Test(expected = IllegalArgumentException::class)
	fun shouldNotAllowDateParametersWithWrongOrder() {
		val start = LocalDateTime.of(2018, 1, 1, 0, 0, 0)
		val end = start.minusDays(1L)
		SimpleMerchantQuery(start, end, "doesNotMatter")
	}

	@Test(expected = IllegalArgumentException::class)
	fun shouldNotAllowEmptyMerchant() {
		val start = LocalDateTime.of(2018, 1, 1, 0, 0, 0)
		val end = start.plusDays(1)
		SimpleMerchantQuery(start, end, "")
	}

	/*
	   Perform basic matching test which returns non-zero statistics
	 */
	private fun shouldMatchWithReversalOutsideQueryRange(merchant: String) {
		val db = createTestDatabase(acme1, acme2, acme3, acme4)
		val start = LocalDateTime.of(2018, 1, 1, 12, 55, 0)
		val finish = start.plusHours(2L)
		val query = SimpleMerchantQuery(start, finish, merchant)
		db.query(query)
		val stats = SimpleTransactionStatistics()
		query.generateStatistics(stats)
		assertThat(stats.number).isEqualTo(1)
		assertThat(stats.average).isCloseTo(BigDecimal.valueOf(20.0), Percentage.withPercentage(0.01))
	}


	private fun createTestDatabase(vararg transactions: Transaction): TransactionDatabase {
		val db = TransactionDatabase()
		for (t in transactions)
			db.addTransaction(t)
		return db
	}
}
