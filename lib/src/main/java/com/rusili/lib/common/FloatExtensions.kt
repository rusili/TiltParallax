package com.rusili.lib.common

internal fun Float.isZero() =
    this == 0f

internal fun Float.isNotZero() =
    !this.isZero()
