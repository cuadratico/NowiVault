package com.nothingsecure.funs_extra

import com.nothingsecure.recy_information.project_class
import org.json.JSONObject
import java.time.LocalDateTime
import java.util.Base64

fun es_data (pass_all: ByteArray, iv: ByteArray): String {

    return JSONObject().apply {
        put("data", Base64.getEncoder().withoutPadding().encodeToString(pass_all))
        put("iv", Base64.getEncoder().withoutPadding().encodeToString(iv))
    }.toString()
}

fun now (): String {
    return LocalDateTime.now().toString().split("T").joinToString(" \n ").split(".")[0]
}

fun des_es_values (json: String? = null, id: Int? = null, regi: Boolean = false, p_r: String? = null, values: Boolean = false, info: String? = null, color: String = "#1b1b1d"): project_class {

    return if (json == null) {
        project_class.multi_data(
            if (regi) {
                if (values) {
                    JSONObject().apply {
                        put(val_l, p_r)
                    }.toString()
                } else {
                    JSONObject().apply {
                        put(time_r, now())
                        put(color_expre, color)
                    }.toString()
                }
            } else {
                if (values) {
                    JSONObject().apply {
                        put(time_r, now())
                        put(val_p, p_r)
                    }.toString()
                } else {
                    JSONObject().apply {
                        put(alias_p, info)
                    }.toString()
                }
            }
        )
    } else {
        val json_final = JSONObject(json)

        if (regi) {
            project_class.register(id!!, json_final.getString(time_r), json_final.getString(color_expre), p_r!!)
        } else {
            project_class.pass(id!!, json_final.getString(alias_p), p_r!!)

        }
    }

}