package com.nothingsecure.funs_extra

import android.app.Activity
import android.app.Dialog
import android.content.SharedPreferences
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.nothingsecure.db
import com.nothingsecure.recy_information.project_class
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.security.GeneralSecurityException
import java.security.Key
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import kotlin.random.Random

suspend fun desen_v (context: Activity, key: Key, pref: SharedPreferences, v_all: String, db: db, oper_all: (JSONObject) -> Unit, final: () -> Unit, info: String, algo: String = pref.getString("algo", "AES").toString()) {

    val c_f_idioms = idioms_instance(pref, 5)
    val extra_idioms = idioms_instance(pref, 0)

    var p: ByteArray? = null

    try {
        val json = JSONObject(v_all)
        val c = cip_no_cip(pref, Cipher.DECRYPT_MODE, key, algo, json.getString("iv"))

        p = c.doFinal(Base64.getDecoder().decode(json.getString("data")))

        add_register(pref, db, c_f_idioms.select_idiom(0) + " $info")
        withContext(Dispatchers.Main) {
            oper_all(JSONObject(String(p)))
        }
    } catch (e: Exception) {
        Log.e(extra_idioms.select_idiom(7), e.toString())

        withContext(Dispatchers.Main) {
            Toast.makeText(context, extra_idioms.select_idiom(7), Toast.LENGTH_SHORT).show()
        }
    } finally {
        p.let { it?.fill(0) }
        final()
    }
}

suspend fun cip_p_v (context: Activity, pref: SharedPreferences, db: db, info_dialog: TextView, input_p: EditText, input_i: EditText, list_oper: (Int, String) -> Unit, finish_ac: () -> Unit, final: () -> Unit, db_op: Boolean = true, id_db: Int? = null) {

    val c_f_idioms = idioms_instance(pref, 5)
    val extra_idioms = idioms_instance(pref, 0)

    withContext(Dispatchers.Main) {
        text_load_change(pref, info_dialog, extra_idioms.select_idiom(8))
    }

    var k: Key? = null

    try {

        k = deri_expressed(pref)

        val c_data = cip_no_cip(pref, Cipher.ENCRYPT_MODE, k)

        val cip_data = es_data(c_data.doFinal((des_es_values(null, null, false, input_p.text.toString(), true) as project_class.multi_data).data.toByteArray()), c_data.iv)

        var id = 0
        if (pref.getBoolean("db_sus", true)) {
            withContext(Dispatchers.Main) {
                text_load_change(pref, info_dialog, extra_idioms.select_idiom(9))
            }

            val db_va = db_create(pref, cip_data, false, input_i.text.toString(), k)
            if (db_op) {
                id = db.insert(pref, "info_s", db_va).toInt()
            } else {
                db.update("info_s", db_va, id_db.toString())
            }
        }

        list_oper(id, cip_data)

        add_register(pref, db, if (db_op) { c_f_idioms.select_idiom(1) } else { c_f_idioms.select_idiom(2) })

        withContext(Dispatchers.Main) {
            finish_ac()
        }

    } catch (error: GeneralSecurityException) {
        Log.e("pass_e", error.toString())

        withContext(Dispatchers.Main) {
            input_p.text.clear()
            input_i.text.clear()
            Toast.makeText(context, extra_idioms.select_idiom(10), Toast.LENGTH_SHORT).show()
        }

    } finally {
        k.let { it?.encoded?.fill(0) }
        withContext(Dispatchers.Main) {
            final()
        }
    }
}

suspend fun des_p_l_v (context: FragmentActivity, pref: SharedPreferences, load_dialog: Dialog, input_load: TextView, db: db, values_modi: () -> Unit, list_oper: (Int, String, String) -> Unit, honey_pot: (project_class.pass) -> Unit, key: String = pref.getString("k_u", "").toString(), algo: String = pref.getString("algo", "AES").toString(), logs: Boolean = false) {

    val c_f_idioms = idioms_instance(pref, 5)
    val extra_idioms = idioms_instance(pref, 0)

    var k: Key? = null

    try {
        k = deri_expressed(pref, key, if (logs) { false } else { pref.getBoolean("deri", false) })

        if (!pref.getBoolean("honeypot_mod", false)) {

            withContext(Dispatchers.Main) {
                text_load_change(pref, input_load, extra_idioms.select_idiom(12))
            }
            val p_list = db.select(
                if (logs) {
                    "info_r"
                } else {
                    "info_s"
                }
            )

            withContext(Dispatchers.Main) {
                text_load_change(pref, input_load, extra_idioms.select_idiom(11))
            }

            for ((id, meta_data, values_data) in p_list) {
                val json = JSONObject(meta_data)

                auth_time_out(context) {
                    val c = cip_no_cip(pref, Cipher.DECRYPT_MODE, k, algo, json.getString("iv"))
                    list_oper(id, String(c.doFinal(Base64.getDecoder().decode(json.getString("data")))), values_data)
                }

            }

        } else {
            val alias_false = mutableListOf(
                "Microsoft",
                "Google",
                "My account",
                "Bank",
                "PayPal (the main account)",
                "Google (work)",
                "Bank - card account",
                "Amazon (family)",
                "Netflix - shared",
                "Spotify (student)",
                "Apple ID (iCloud)",
                "Crypto Wallet (ETH)",
                "Crypto Wallet (BTC)",
                "Umbrella.corp"
            )


            for (position in 0..SecureRandom().nextInt(alias_false.size - 3) + 2) {
                val alias = alias_false.shuffled()[SecureRandom().nextInt(alias_false.size - 1)]

                val c = cip_no_cip(pref, Cipher.ENCRYPT_MODE, k)

                honey_pot(
                    project_class.pass(
                        position,
                        alias,
                        es_data(c.doFinal((des_es_values(null, null, false, pass_generator(Random.nextInt(8, 20)), true) as project_class.multi_data).data.toByteArray()), c.iv)
                    )
                )

                alias_false.remove(alias)
            }
        }

        withContext(Dispatchers.Main) {
            text_load_change(pref, input_load, c_f_idioms.select_idiom(3))
        }

        if (!logs) {
            pref.edit().putBoolean("desen_pass", true).commit()
        }

        add_register(pref, db, c_f_idioms.select_idiom(4))

        withContext(Dispatchers.Main) {
            values_modi()
        }

    } catch (e: Exception) {

        Log.e(extra_idioms.select_idiom(7), e.toString())

        withContext(Dispatchers.Main) {
            Toast.makeText(context, extra_idioms.select_idiom(7), Toast.LENGTH_SHORT).show()
        }

    } finally {
        k.let { it?.encoded?.fill(0) }

        withContext(Dispatchers.Main) {
            load_dialog.dismiss()
        }
    }
}