package com.hoolah.transaction.query

import com.hoolah.transaction.model.Transaction
import com.hoolah.transaction.model.TransactionDatabase
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Percentage
import org.junit.Test
import java.math.BigDecimal

class SimpleTransactionStatisticsTest {


	@Test
	fun emptyResult() {
		//given
		val st = SimpleTransactionStatistics()

		// then
		assertThat(st.number).isZero
		assertThat(st.average.intValueExact()).isZero
	}

	@Test
	fun shouldCalculate() {
		//given
		val stats = SimpleTransactionStatistics()
		val db = TransactionDatabase()

		// when
		db.addTransaction(Transaction(arrayOf("1", "01/01/2018 13:15:00", "10.00", "Acme", "PAYMENT")))
		db.addTransaction(Transaction(arrayOf("1", "01/01/2018 13:15:00", "20.00", "Acme", "PAYMENT")))
		stats.calculate(db.transactions)

		// then
		assertThat(stats.number).isEqualTo(2)
		assertThat(stats.average).isCloseTo(BigDecimal.valueOf(15.0), Percentage.withPercentage(0.01))
	}
}
