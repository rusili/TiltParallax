package com.rusili.tiltparallax.common

internal fun Float.isZero() =
    this == 0f

internal fun Float.isNotZero() =
    !this.isZero()
