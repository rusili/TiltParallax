package com.rusili.lib.common

internal fun Float.shorten() =
    "%.4f".format(this.toDouble())

internal fun Long.abbreviateTime() =
    this.toString().takeLast(5)
