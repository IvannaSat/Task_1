package com.ispolska.myapplication.utils.extensions

fun String.capitalizeFirstChar() = this.replaceFirstChar { it.uppercaseChar() }