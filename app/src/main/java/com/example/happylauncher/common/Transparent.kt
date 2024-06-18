package com.example.happylauncher.common

import com.example.happylauncher.R


enum class Transparent(val transparentStep: Int, val transparentValue: Float) {
    PERCENT100(R.color.black, 10f),
    PERCENT90(R.color.transparent90, 9f),
    PERCENT80(R.color.transparent80, 8f),
    PERCENT70(R.color.transparent70, 7f),
    PERCENT60(R.color.transparent60, 6f),
    PERCENT50(R.color.transparent50, 5f),
    PERCENT40(R.color.transparent40, 4f),
    PERCENT30(R.color.transparent30, 3f),
    PERCENT20(R.color.transparent20, 2f),
    PERCENT10(R.color.transparent10, 1f)
}