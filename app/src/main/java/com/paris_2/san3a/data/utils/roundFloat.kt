package com.paris_2.san3a.data.utils

fun Float.roundFloat():Float{
    return ((this * 10).toInt() / 10.0).toFloat()
}