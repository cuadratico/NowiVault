package com.nothingsecure.funs_extra

import android.app.Dialog
import android.content.ContentValues
import android.content.SharedPreferences
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.nothingsecure.db
import com.nothingsecure.recy_information.project_class
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.security.GeneralSecurityException
import java.security.Key
import java.security.MessageDigest
import java.util.Base64
import javax.crypto.Cipher

suspend fun export (context: FragmentActivity, load_export: Dialog, load_input: TextView, pref: SharedPreferences, pass: String, salt: String, iter: Int, algo: String, k_size: Int, arch_name: String, db: db, export_list: List<project_class.pass>) {

    val e_i_idioms = idioms_instance(pref, 7)
    val extra_idioms = idioms_instance(pref, 0)

    withContext(Dispatchers.IO) {
        var us_key: Key? = null
        var file_key: Key? = null

        try {
            withContext(Dispatchers.Main)  {
                text_load_change(pref, load_input, extra_idioms.select_idiom(15))
            }
            us_key = deri_expressed(pref)
            file_key = deri_expressed(null, pass, true, salt, iter, k_size, algo)

            val json_array = JSONArray()

            withContext(Dispatchers.Main)  {
                text_load_change(pref, load_input, extra_idioms.select_idiom(11))
            }
            for ((id, alias, pass_all) in export_list) {

                var p: ByteArray? = null

                auth_time_out(context) {
                    val json_p = JSONObject(pass_all)

                    val us_c = cip_no_cip(pref, Cipher.DECRYPT_MODE, us_key, pref.getString("algo", "AES").toString(), json_p.getString("iv"))

                    p = JSONObject(String(us_c.doFinal(Base64.getDecoder().decode(json_p.getString("data"))))).getString(val_p).toByteArray()

                }
                val fi_c = cip_no_cip(null, Cipher.ENCRYPT_MODE, file_key, algo)

                json_array.put(JSONObject().apply {
                    put("alias", alias)
                    put("data_global", es_data(fi_c.doFinal(p), fi_c.iv))
                })

                p?.fill(0)

            }
            us_key.let { it.encoded?.fill(0) }


            withContext(Dispatchers.Main)  {
                text_load_change(pref, load_input, e_i_idioms.select_idiom(0))
            }
            val c_global = cip_no_cip(null, Cipher.ENCRYPT_MODE, file_key, algo)

            withContext(Dispatchers.Main)  {
                text_load_change(pref, load_input, e_i_idioms.select_idiom(1))
            }
            val archive_json = JSONObject().apply {
                put("salt", salt)
                put("iter", iter)
                put("algo", algo)
                put("k_size", k_size)
                put("arch_ver", 2)
                put("very_key", Base64.getEncoder().withoutPadding().encodeToString(MessageDigest.getInstance("SHA256").digest(pass.toByteArray() + Base64.getDecoder().decode(salt))))
                put("data_list", Base64.getEncoder().withoutPadding().encodeToString(c_global.doFinal(json_array.toString().toByteArray())))
                put("data_iv", Base64.getEncoder().withoutPadding().encodeToString(c_global.iv))
            }.toString()

            withContext(Dispatchers.Main) {
                text_load_change(pref, load_input, e_i_idioms.select_idiom(2))
            }
            val content_archive = ContentValues().apply {
                put(MediaStore.Files.FileColumns.DISPLAY_NAME, "$arch_name.ns")
                put(MediaStore.Files.FileColumns.MIME_TYPE, "application/ns")
                put(MediaStore.Files.FileColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val uri = context.contentResolver.insert(
                MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                content_archive
            )

            context.contentResolver.openOutputStream(uri!!)!!.bufferedWriter().use {
                it.write(archive_json)
            }

            vibra_conf(context, pref, longArrayOf(0, 200, 0, 200))

            add_register(pref, db, e_i_idioms.select_idiom(3))

            withContext(Dispatchers.Main) {
                text_load_change(pref, load_input, e_i_idioms.select_idiom(4))
            }

        } catch (e: Exception) {
            Log.e(e_i_idioms.select_idiom(5), e.toString())
            text_load_change(pref, load_input, "Exception")

            withContext(Dispatchers.Main) {
                Toast.makeText(context, e_i_idioms.select_idiom(5), Toast.LENGTH_SHORT).show()
                load_export.dismiss()
            }

        } finally {
            us_key.let { it?.encoded?.fill(0) }
            file_key.let { it?.encoded?.fill(0) }
        }
    }
}


enum class import_type { preview, replace_db, add_db }
suspend inline fun oper_values (pref: SharedPreferences, json: JSONObject?, pass: String, list: List<project_class.pass>?, alias_list: List<String>, load_input: TextView, nv_version: Int, e_i_idioms: idioms_instance, data_do: (project_class.pass) -> Unit) {

    if (list == null) {

        var file_k: Key? = null

        try {

            file_k = deri_expressed(
                null,
                pass,
                true,
                json!!.getString("salt"),
                json.getInt("iter"),
                json.getInt("k_size"),
                json.getString("algo")
            )

            withContext(Dispatchers.Main) {
                text_load_change(pref, load_input, e_i_idioms.select_idiom(6))
            }
            val c_im = cip_no_cip(
                null,
                Cipher.DECRYPT_MODE,
                file_k,
                json.getString("algo"),
                json.getString("data_iv")
            )
            val array_info_dec = JSONArray(String(c_im.doFinal(Base64.getDecoder().decode(json.getString("data_list")))))

            for (posi in 0..array_info_dec.length() - 1) {

                val json_data = array_info_dec.getJSONObject(posi)
                var data_data = json_data.getString(alias_list[1])

                if (nv_version == 2) {
                    val json_pass = JSONObject(data_data)

                    val c_des = cip_no_cip(null, Cipher.DECRYPT_MODE, file_k, json.getString("algo"), json_pass.getString("iv"))
                    data_data = String(c_des.doFinal(Base64.getDecoder().decode(json_pass.getString("data"))))
                }

                data_do(
                    project_class.pass(
                        posi,
                        json_data.getString(alias_list[0]),
                        data_data
                    )
                )

            }

        } catch (e: GeneralSecurityException) {
            Log.e(e_i_idioms.select_idiom(7), e.toString())
        } finally {
            file_k.let { it?.encoded?.fill(0) }
        }


    } else {
        withContext(Dispatchers.Main) {
            text_load_change(pref, load_input, e_i_idioms.select_idiom(8))
        }
        for (data in list) {

            data_do(data)

        }
    }
}

suspend fun import (context: FragmentActivity, pref: SharedPreferences, pass: String, list: List<project_class.pass>?, json: JSONObject?, type: import_type, load_dialog: Dialog, load_input: TextView, db: db, nv_version: Int): List<project_class.pass> {

    val e_i_idimoms = idioms_instance(pref, 7)
    val extra_idioms = idioms_instance(pref, 0)

    withContext(Dispatchers.Main) {
        text_load_change(pref, load_input, extra_idioms.select_idiom(15))
    }
    var us_key: Key? = null
    var data_list = listOf<project_class.pass>()

    var alias_list = listOf("alias", "data_global")

    if (nv_version == 1) {
        alias_list = listOf("info", "pass")
    }

    try {
        us_key = deri_expressed(pref)

        if (type == import_type.preview) {

            oper_values(pref, json, pass, list, alias_list, load_input, nv_version, e_i_idimoms) { data ->

                auth_time_out(context) {

                    val c_pass = cip_no_cip(pref, Cipher.ENCRYPT_MODE, us_key)

                    data_list = data_list.plus(data.copy(
                        pass_all = es_data(c_pass.doFinal((des_es_values(null, null, false, data.pass_all, true) as project_class.multi_data).data.toByteArray()), c_pass.iv)
                    ))

                }

            }

            pref.edit().putBoolean("db_sus", false).commit()

        } else {

            if (type == import_type.replace_db) {
                db.delete("info_s")
            }

            oper_values (pref, json, pass, list, alias_list, load_input, nv_version, e_i_idimoms){ data ->

                auth_time_out(context) {

                    var pass_data = data.pass_all

                    if (list == null) {
                        val c_global = cip_no_cip(pref, Cipher.ENCRYPT_MODE, us_key)
                        pass_data = es_data(c_global.doFinal((des_es_values(null, null, false, pass_data, true) as project_class.multi_data).data.toByteArray()), c_global.iv)
                    }

                    db.insert(
                        pref,
                        "info_s",
                        db_create(pref, pass_data, false, data.alias, us_key)
                    )

                }

            }
        }

        vibra_conf(context, pref, longArrayOf(0, 200, 0, 200))

        add_register(pref, db, e_i_idimoms.select_idiom(9))

        withContext(Dispatchers.Main) {
            text_load_change(pref, load_input, e_i_idimoms.select_idiom(10))
        }

        return data_list
    } catch (e: Exception) {
        Log.e(e_i_idimoms.select_idiom(11), e.toString())

        withContext(Dispatchers.Main) {
            text_load_change(pref, load_input, e_i_idimoms.select_idiom(11))
            Toast.makeText(context, e_i_idimoms.select_idiom(11), Toast.LENGTH_SHORT).show()
        }

        data_list = listOf()
        return data_list
    } finally {
        us_key.let { it?.encoded?.fill(0) }
        data_list = listOf()

        withContext(Dispatchers.Main) {
            load_dialog.dismiss()
        }
    }
}
