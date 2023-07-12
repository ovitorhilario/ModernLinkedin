package com.vitorhilarioapps.modernlinkedin.utils.extensions

fun Int.toInteractionValue(): String {
    val value = this
    return buildString {
        if (value > 1000) {
            append(String.format("%.1f", value.toDouble() / 1000.0))
            append("k")
        } else {
            append(value)
        }
    }
}