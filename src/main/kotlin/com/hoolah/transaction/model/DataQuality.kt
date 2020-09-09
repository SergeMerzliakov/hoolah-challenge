package com.hoolah.transaction.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Utility functions for cleaning data
 */

private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")


/**
 * Trim and convert to lowercase
 */
fun cleanString(s: String) = s.trim().toLowerCase()

/*
  Converts string to date from format "dd/MM/yyyy HH:mm:ss"
  No timezone support
 */
fun cleanDate(s: String): LocalDateTime {
	return LocalDateTime.parse(s.trim(), dateFormatter)
}
