package com.example.productlisting.utils

fun Double.roundUpToTwoDecimalPlaces(): Double {
    return kotlin.math.ceil(this * 100) / 100
}