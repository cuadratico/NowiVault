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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.security.GeneralSecurityException
import java.security.Key
import java.security.MessageDigest
import java.util.Base64
import javax.crypto.Cipher

suspend fun export (context: FragmentActivity, load_input: TextView, pref: SharedPreferences, pass: String, salt: String, iter: Int, algo: String, k_size: Int, arch_name: String, db: db, export_list: List<project_class.pass>) {

    withContext(Dispatchers.IO) {
        var us_key: Key? = null

        try {
            withContext(Dispatchers.Main)  {
                text_load_change(load_input, "Obtaining your key")
            }
            us_key = deri_expressed(pref)

            val json_array = JSONArray()

            withContext(Dispatchers.Main)  {
                text_load_change(load_input, "Decrypting your passwords")
            }
            for ((id, pass_all, information) in export_list) {

                val (pass, iv) = des_es_pass(project_class.multi_data(pass_all)) as project_class.pass_extra

                var pass_all: String? = null

                auth_time_out(context, {
                    val us_c = cip_no_cip(pref, Cipher.DECRYPT_MODE, us_key, pref.getString("algo", "AES").toString(), iv)
                    pass_all = String(us_c.doFinal(Base64.getDecoder().decode(pass)))

                }, {
                    us_key.let { it.encoded?.fill(0) }
                    cancel()
                })

                json_array.put(JSONObject().apply {
                    put("info", information)
                    put("pass", pass_all)
                })

            }
            us_key.let { it.encoded?.fill(0) }


            withContext(Dispatchers.Main)  {
                text_load_change(load_input, "Deriving the key from the file")
            }
            val c = cip_no_cip(null, Cipher.ENCRYPT_MODE, deri_expressed(null, pass, true, salt, iter, k_size, algo), algo)

            withContext(Dispatchers.Main)  {
                text_load_change(load_input, "Encrypting and building the file")
            }
            val archive_json = JSONObject().apply {
                put("salt", salt)
                put("iter", iter)
                put("algo", algo)
                put("k_size", k_size)
                put("very_key", Base64.getEncoder().withoutPadding().encodeToString(MessageDigest.getInstance("SHA256").digest(pass.toByteArray() + Base64.getDecoder().decode(salt))))
                put("data_list", Base64.getEncoder().withoutPadding().encodeToString(c.doFinal(json_array.toString().toByteArray())))
                put("data_iv", Base64.getEncoder().withoutPadding().encodeToString(c.iv))
            }.toString()

            withContext(Dispatchers.Main) {
                text_load_change(load_input, "Exporting the file")
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

            add_register(pref, db, "Passwords have been exported")

            withContext(Dispatchers.Main) {
                text_load_change(load_input, "Exported file")
            }

        } catch (e: Exception) {
            Log.e("Export error", e.toString())

            withContext(Dispatchers.Main) {
                text_load_change(load_input, "Error exporting the file")
                Toast.makeText(context, "Export error", Toast.LENGTH_SHORT).show()
            }

        } finally {
            us_key.let { it?.encoded?.fill(0) }
        }
    }
}


enum class import_type { preview, replace_db, add_db }
suspend inline fun oper_values (pref: SharedPreferences, key: Key, json: JSONObject?, pass: String, list: List<project_class.pass>?, nk: Boolean, load_input: TextView, data_do: (project_class.pass) -> Unit) {

    if (list == null) {
        withContext(Dispatchers.Main) {
            text_load_change(load_input, "Deriving the key")
        }

        if (nk) {

            val deri_key = deri_expressed(
                null,
                pass,
                true,
                json!!.getString("salt"),
                json.getInt("iter"),
                256,
                "AES"
            )

            withContext(Dispatchers.Main) {
                text_load_change(load_input, "importing the file")
            }
            val array_json = JSONArray(json.getString("pass_list"))

            for (posi in 0..array_json.length() - 1) {

                val j_object = array_json.getJSONObject(posi)
                val c_im = cip_no_cip(null, Cipher.DECRYPT_MODE, deri_key, "AES", j_object.getString("iv"))

                data_do(project_class.pass(posi, String(c_im.doFinal(Base64.getDecoder().decode(j_object.getString("pass")))), j_object.getString("info"), now()))

            }


        } else {

            val c_im = cip_no_cip(
                null,
                Cipher.DECRYPT_MODE,
                deri_expressed(
                    null,
                    pass,
                    true,
                    json!!.getString("salt"),
                    json.getInt("iter"),
                    json.getInt("k_size"),
                    json.getString("algo")
                ),
                json.getString("algo"),
                json.getString("data_iv")
            )

            withContext(Dispatchers.Main) {
                text_load_change(load_input, "importing the file")
            }
            val array_info_dec = JSONArray(String(c_im.doFinal(Base64.getDecoder().decode(json.getString("data_list")))))

            for (posi in 0..array_info_dec.length() - 1) {

                val json_data = array_info_dec.getJSONObject(posi)

                data_do(project_class.pass(posi, json_data.getString("pass"), json_data.getString("info"), now()))

            }
        }

    } else {
        withContext(Dispatchers.Main) {
            text_load_change(load_input, "importing the list")
        }
        for ((id, pass_all, info, time) in list) {

            val (pas, iv) = (des_es_pass(project_class.multi_data(pass_all)) as project_class.pass_extra)
            val c_pass = cip_no_cip(pref, Cipher.DECRYPT_MODE, key, pref.getString("algo", "AES").toString(), iv)

            data_do(project_class.pass(id, String(c_pass.doFinal(Base64.getDecoder().decode(pas))), info, time))

        }
    }
}

suspend fun import (context: FragmentActivity, job: CoroutineScope, pref: SharedPreferences, pass: String, list: List<project_class.pass>?, json: JSONObject?, type: import_type, load_dialog: Dialog, load_input: TextView, db: db, nk: Boolean): List<project_class.pass> {

    withContext(Dispatchers.Main) {
        text_load_change(load_input, "Obtaining your key")
    }
    val us_key = deri_expressed(pref)
    var data_list = listOf<project_class.pass>()

    try {

        if (type == import_type.preview) {

            oper_values(pref, us_key, json, pass, list, nk, load_input) { data ->

                auth_time_out(context, {

                    val c = cip_no_cip(pref, Cipher.ENCRYPT_MODE, us_key)

                    data_list = data_list.plus(
                        data.copy(
                            pass_all = (des_es_pass(
                                project_class.pass_extra(
                                    Base64.getEncoder().withoutPadding()
                                        .encodeToString(c.doFinal(data.pass_all.toByteArray())),
                                    Base64.getEncoder().withoutPadding().encodeToString(c.iv)
                                ),
                                false
                            ) as project_class.multi_data).data
                        )
                    )

                }, {
                    job.cancel()
                })

            }

            pref.edit().putBoolean("db_sus", false).commit()

        } else {

            if (type == import_type.replace_db) {
                db.delete("info_s")
            }

            oper_values (pref, us_key, json, pass, list, nk, load_input){ data ->

                auth_time_out(context, {

                    val c_pass = cip_no_cip(pref, Cipher.ENCRYPT_MODE, us_key)

                    val es_pass = (des_es_pass(
                        project_class.pass_extra(
                            Base64.getEncoder().withoutPadding()
                                .encodeToString(c_pass.doFinal(data.pass_all.toByteArray())),
                            Base64.getEncoder().withoutPadding().encodeToString(c_pass.iv)
                        ), false
                    ) as project_class.multi_data).data

                    val es_values = (des_es_values(
                        null,
                        data.id,
                        data.information,
                        es_pass
                    ) as project_class.multi_data).data.toByteArray()

                    val c_global = cip_no_cip(pref, Cipher.ENCRYPT_MODE, us_key)

                    db.insert(pref, "info_s",
                        db_create(
                            Base64.getEncoder().withoutPadding().encodeToString(c_global.doFinal(es_values)),
                            Base64.getEncoder().withoutPadding().encodeToString(c_global.iv)
                        )
                    )

                }, {
                    job.cancel()
                })

            }
        }

        vibra_conf(context, pref, longArrayOf(0, 200, 0, 200))

        add_register(pref, db, "A password file has been imported")

        withContext(Dispatchers.Main) {
            text_load_change(load_input, "imported passwords")
        }

        return data_list
    } catch (e: Exception) {
        Log.e("import error", e.toString())

        withContext(Dispatchers.Main) {
            text_load_change(load_input, "import error")
            Toast.makeText(context, "import error", Toast.LENGTH_SHORT).show()
        }

        data_list = listOf()
        return data_list
    } finally {
        us_key.let { it.encoded?.fill(0) }
        data_list = listOf()

        withContext(Dispatchers.Main) {
            load_dialog.dismiss()
        }
    }
}

fun auth_json (json_f: JSONObject, pass: String, nk: Boolean): Boolean {

    if (nk) {

        val pro = json_f.getJSONArray("pro").getJSONObject(0)

        try {
            val c = cip_no_cip(null, Cipher.DECRYPT_MODE,
                deri_expressed(
                    null,
                    pass,
                    true,
                    json_f.getString("salt"),
                    json_f.getInt("iter"),
                    256,
                    "AES"
                ),
                "AES",
                pro.getString("iv")
            )

            c.doFinal(Base64.getDecoder().decode(pro.getString("value")))

            return true
        } catch (e: GeneralSecurityException) {
            return false
        }

    } else {
        if (MessageDigest.isEqual(Base64.getDecoder().decode(json_f.getString("very_key")), MessageDigest.getInstance("SHA256").digest(pass.toByteArray() + Base64.getDecoder().decode(json_f.getString("salt"))))) {
            return true
        } else {
            return false
        }
    }

}