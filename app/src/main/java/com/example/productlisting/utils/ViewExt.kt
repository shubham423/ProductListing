package com.example.productlisting.utils

import android.view.View

fun View.visible() {
    if (visibility != View.VISIBLE) {
        this.visibility = View.VISIBLE
    }
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    if (visibility != View.GONE)
        this.visibility = View.GONE
}