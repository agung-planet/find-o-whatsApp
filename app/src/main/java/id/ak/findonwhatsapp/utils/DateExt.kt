package id.ak.findonwhatsapp.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.Period
import java.time.ZoneId

fun Long.toDateString() = buildString {
    val now = LocalDateTime.now()
    val converted =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(this@toDateString), ZoneId.systemDefault())
    val isToday = Period.between(now.toLocalDate(), converted.toLocalDate()).days == 0
    val isYesterday =
        Period.between(now.minusDays(1).toLocalDate(), converted.toLocalDate()).days == 0
    val isThisYear = Period.between(now.toLocalDate(), converted.toLocalDate()).years == 0

    when {
        isToday -> {
            append("Hari ini, ")
            append("${converted.hour}:${converted.minute}")
        }

        isYesterday -> {
            append("Kemarin, ")
            append("${converted.hour}:${converted.minute}")
        }

        isThisYear -> {
            append("${converted.dayOfMonth} ${converted.month}, ")
            append("${converted.hour}:${converted.minute}")
        }

        else -> {
            append("${converted.dayOfMonth} ${converted.month} ${converted.year}, ")
            append("${converted.hour}:${converted.minute}")
        }
    }
}