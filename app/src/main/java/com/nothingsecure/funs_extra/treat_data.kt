package com.nothingsecure.funs_extra

import android.content.ContentValues
import com.nothingsecure.recy_information.project_class
import org.json.JSONObject
import java.time.LocalDateTime

fun db_create (json: String, iv: String, v_name: String = "global_pass"): ContentValues {

    return ContentValues().apply {
        put(v_name, json)
        put("iv", iv)
    }
}

fun des_es_pass (pass_all: project_class, extra: Boolean = true): project_class {

    if (extra) {
        val all = JSONObject((pass_all as project_class.multi_data).data)
        return project_class.pass_extra(all.getString("pas"), all.getString("iv"))
    } else {
        val all = pass_all as project_class.pass_extra
        return project_class.multi_data(JSONObject().apply {
            put("pas", all.pas)
            put("iv", all.iv)
        }.toString())
    }
}

fun now (): String {
    return LocalDateTime.now().toString().split("T").joinToString(" - ")
}

fun des_es_values (json: String?, id: Int?, info: String? = null, p_r: String? = null, regi: Boolean = false, color: String = "#1b1b1d"): project_class {

    if (json != null) {
        val json_extra = JSONObject(json)

        if (!regi) {
            return project_class.pass(id!!, json_extra.getString("pass_all"), json_extra.getString("info"), json_extra.getString("time"))
        } else {
            return project_class.register(id!!, json_extra.getString("time"), json_extra.getString("regi"), json_extra.getString("color"))
        }
    } else {
        return project_class.multi_data(
            if (!regi) {
                JSONObject().apply {
                    put("info", info!!)
                    put("time", now())
                    put("pass_all", p_r)
                }.toString()
            } else {
                JSONObject().apply {
                    put("time", now())
                    put("regi", p_r)
                    put("color", color)
                }.toString()
            }
        )
    }
}