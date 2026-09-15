package com.nothingsecure.funs_extra

import androidx.core.graphics.toColorInt
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlin.math.log2

val mayusculas_l = ('A'..'Z').joinToString("").toList()
val numeros_l = (0..9).joinToString ("").toList()
val minusculas_l = ('a'..'z').joinToString("").toList()
val simbolos_l = listOf("@#$|?¿¡!").joinToString("").toList()
fun entropy(pass: String, porgress: LinearProgressIndicator){

    var simbolos = 0
    var minusculas = 0
    var mayusculas = 0
    var numeros = 0

    for (valor in pass) {

        if (minusculas_l.contains(valor)) {
            if (minusculas != 26 ) {minusculas += 26}
        }else if (mayusculas_l.contains(valor)) {
            if (mayusculas != 26) {mayusculas += 26}
        }else if (numeros_l.contains(valor)) {
            if (numeros != 9) {numeros += 9}
        }else {
            simbolos ++
        }
    }

    val final =  pass.length * log2((simbolos + mayusculas + minusculas + numeros).toDouble())

    porgress.progress = final.toInt()
    if (final in 1.0..40.0) {
        porgress.setIndicatorColor("#aa4040".toColorInt())
    }else if (final in 40.0..60.0) {
        porgress.setIndicatorColor("#c9a23e".toColorInt())
    }else if (final > 60.0){
        porgress.setIndicatorColor("#40aa47".toColorInt())
    }else {
        porgress.setIndicatorColor("#e3e3e3".toColorInt())
    }
}