package id.ak.findonwhatsapp.utils

fun String.formatAsPhoneNumber() = buildString {
    append("+62 ")
    append(this@formatAsPhoneNumber.take(3))
    append("-")

    val rest = this@formatAsPhoneNumber.drop(3).chunked(4)
    append(rest.joinToString(separator = "-"))
}