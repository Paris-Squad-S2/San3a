package com.paris_2.san3a.data.utils

fun List<*>?.toFloatList(): List<Float>? =
    this?.filterIsInstance<Number>()?.map { it.toFloat() }