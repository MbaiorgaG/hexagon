package com.hexagonkt.helpers

import java.time.*
import java.util.Date

/**
 * Formats a date as a formatted integer with this format: `YYYYMMDDHHmmss`.
 */
fun LocalDateTime.toNumber (): Long =
    (this.toLocalDate().toNumber() * 1_000_000_000L) + this.toLocalTime().toNumber()

fun LocalDate.toNumber (): Int =
    (this.year       * 10_000) +
    (this.monthValue * 100) +
    this.dayOfMonth

fun LocalTime.toNumber (): Int =
    (this.hour   * 10_000_000) +
    (this.minute * 100_000) +
    (this.second * 1_000) +
    (this.nano / 1_000_000) // Nanos to millis

fun LocalDateTime.withZone (zoneId: ZoneId = Jvm.timeZone.toZoneId()): ZonedDateTime =
    ZonedDateTime.of(this, zoneId)

/**
 * Parses a date from a formatted integer with this format: `YYYYMMDDHHmmss`.
 */
fun Long.toLocalDateTime (): LocalDateTime = (this / 1_000_000_000).toInt()
    .toLocalDate()
    .atTime((this % 1_000_000_000).toInt().toLocalTime())

fun Int.toLocalDate (): LocalDate = LocalDate.of(
    this / 10_000,
    (this % 10_000) / 100,
    this % 100
)

fun Int.toLocalTime (): LocalTime = LocalTime.of(
    (this / 10_000_000),
    ((this % 10_000_000) / 100_000),
    ((this % 100_000) / 1_000),
    ((this % 1_000) * 1_000_000) // Millis to nanos
)

fun ZonedDateTime.toDate (): Date = Date.from(this.toInstant())

fun LocalDateTime.toDate (): Date = this.atZone(Jvm.timeZone.toZoneId()).toDate()

fun LocalDate.toDate (): Date = this.atStartOfDay(Jvm.timeZone.toZoneId()).toDate()

fun Date.toLocalDateTime (): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(this.time), ZoneId.systemDefault())

fun Date.toLocalDate (): LocalDate = this.toLocalDateTime().toLocalDate()
