package com.hoolah.transaction.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.math.BigDecimal
import java.time.LocalDateTime

class TransactionTest {

	@Test
	fun shouldLoadWithRelatedTransaction() {
		// given
		val data = arrayOf("TX1", "20/08/2018 13:14:11", "10.95", "Acme", "REVERSAL", "TX2")

		// when
		val tx = Transaction(data)

		// then
		assertThat(tx.id).isEqualTo("tx1")
		assertThat(tx.date).isEqualTo(LocalDateTime.of(2018, 8, 20, 13, 14, 11))
		assertThat(tx.amount).isEqualTo(BigDecimal.valueOf(10.95))
		assertThat(tx.merchant).isEqualTo("acme")
		assertThat(tx.type).isEqualTo(TransactionType.reversal)
		assertThat(tx.relatedTransactionId).isEqualTo("tx2")
	}

	@Test
	fun shouldLoadWithNoRelatedTransaction() {
		//given
		val data = arrayOf("tx1", "20/08/2018 13:14:11", "10.95", "Acme", "REVERSAL")

		// when
		val tx = Transaction(data)

		// then
		assertThat(tx.id).isEqualTo("tx1")
		assertThat(tx.date).isEqualTo(LocalDateTime.of(2018, 8, 20, 13, 14, 11))
		assertThat(tx.amount).isEqualTo(BigDecimal.valueOf(10.95))
		assertThat(tx.merchant).isEqualTo("acme")
		assertThat(tx.type).isEqualTo(TransactionType.reversal)
		assertThat(tx.relatedTransactionId).isNull()
	}
}
