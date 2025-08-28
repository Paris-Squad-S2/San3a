package com.paris_2.san3a.presentation.utill

fun Float.roundFloat():Float{
    return ((this * 10).toInt() / 10.0).toFloat()
}

fun Float.format(): String {
    return if (this % 1.0f == 0.0f) {
        this.toInt().toString()
    } else {
        this.toString()
    }
}