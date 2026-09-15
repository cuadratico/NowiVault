package com.nothingsecure.funs_extra

import android.app.Activity
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
import java.security.GeneralSecurityException
import java.security.Key
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher

suspend fun desen_p (context: Activity, pref: SharedPreferences, p_all: String, db: db, oper_all: (ByteArray) -> Unit, final: () -> Unit) {
    val (pass, iv) = (des_es_pass(project_class.multi_data(p_all)) as project_class.pass_extra)

    var p: ByteArray? = null

    try {
        val c = cip_no_cip(pref, Cipher.DECRYPT_MODE, deri_expressed(pref), pref.getString("algo", "AES").toString(), iv)

        p = c.doFinal(Base64.getDecoder().decode(pass))

        add_register(pref, db, "A password has been decrypted")
        withContext(Dispatchers.Main) {
            oper_all(p)
        }
    } catch (e: Exception) {
        Log.e("Error decrypting the password", e.toString())

        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Error decrypting the password", Toast.LENGTH_SHORT).show()
        }
    } finally {
        p.let { it?.fill(0) }
        final()
    }
}

suspend fun cip_p_v (context: Activity, pref: SharedPreferences, db: db, info_dialog: TextView, input_p: EditText, input_i: EditText, list_oper: (Int, String) -> Unit, finish_ac: () -> Unit, final: () -> Unit, db_op: Boolean = true, id_db: Int? = null) {

    withContext(Dispatchers.Main) {
        text_load_change(info_dialog, "Encrypting the data")
    }

    var k: Key? = null

    try {

        k = deri_expressed(pref)

        val c_pass = cip_no_cip(pref, Cipher.ENCRYPT_MODE, k)

        val pass_all = (des_es_pass(project_class.pass_extra(Base64.getEncoder().withoutPadding().encodeToString(c_pass.doFinal(input_p.text.toString().toByteArray())), Base64.getEncoder().withoutPadding().encodeToString(c_pass.iv)), false) as project_class.multi_data).data

        var id = 0
        if (pref.getBoolean("db_sus", true)) {
            withContext(Dispatchers.Main) {
                text_load_change(info_dialog, "Adding to the database")
            }

            val c_global = cip_no_cip(pref, Cipher.ENCRYPT_MODE, k)

            val json = Base64.getEncoder().withoutPadding().encodeToString(c_global.doFinal((des_es_values(null, null, input_i.text.toString(), pass_all) as project_class.multi_data).data.toByteArray()))

            val db_va = db_create(json, Base64.getEncoder().withoutPadding().encodeToString(c_global.iv))
            if (db_op) {
                id = db.insert(pref, "info_s", db_va).toInt()
            } else {
                db.update("info_s", db_va, id_db.toString())
            }
        }

        list_oper(id, pass_all)

        add_register(pref, db, if (db_op) { "A password has been added" } else { "A password has been edited" })

        withContext(Dispatchers.Main) {
            finish_ac()
        }

    } catch (error: GeneralSecurityException) {
        Log.e("pass_e", error.toString())

        withContext(Dispatchers.Main) {
            input_p.text.clear()
            input_i.text.clear()
            Toast.makeText(context, "Error encrypting data", Toast.LENGTH_SHORT).show()
        }

    } finally {
        k.let { it?.encoded?.fill(0) }
        withContext(Dispatchers.Main) {
            final()
        }
    }
}

suspend fun des_p_l_v (context: FragmentActivity, pref: SharedPreferences, input_load: TextView, db: db, values_modi: () -> Unit, list_oper: (String, Int) -> Unit, honey_pot: (Int, String, String) -> Unit, key: String = pref.getString("key_u", "").toString(), algo: String = pref.getString("algo", "AES").toString(), logs: Boolean = false) {

    var k: Key? = null

    try {
        k = deri_expressed(pref, key, if (logs) { false } else { pref.getBoolean("deri", false) })

        if (!pref.getBoolean("honeypot_mod", false)) {

            withContext(Dispatchers.Main) {
                text_load_change(input_load, "Extracting data from the database")
            }
            val p_list = db.select(
                if (logs) {
                    "info_r"
                } else {
                    "info_s"
                }
            )

            fun des_va(id: Int, js: String, iv: String) {
                val c = cip_no_cip(pref, Cipher.DECRYPT_MODE, k, algo, iv)
                if (logs) {
                    list_oper(String(c.doFinal(Base64.getDecoder().decode(js))), id)
                } else {
                    list_oper(String(c.doFinal(Base64.getDecoder().decode(js))), id)
                }
            }

            withContext(Dispatchers.Main) {
                text_load_change(input_load, "Decrypting the data")
            }

            for ((id, data, iv) in p_list) {
                try {
                    des_va(id, data, iv)
                } catch (error: GeneralSecurityException) {
                    Log.e("Key timeout exceeded", error.toString())
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Time-out exceeded", Toast.LENGTH_SHORT).show()

                        auth_time_out(context, {
                            des_va(id, data, iv)
                        }, {})
                    }
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
                    position, alias, (des_es_pass(
                        project_class.pass_extra(
                            Base64.getEncoder().withoutPadding().encodeToString(
                                c.doFinal(
                                    pass_generator(SecureRandom().nextInt(6) + 6).toByteArray()
                                )
                            ),
                            Base64.getEncoder().withoutPadding().encodeToString(c.iv)
                        ), false
                    ) as project_class.multi_data).data
                )

                alias_false.remove(alias)
            }
        }

        withContext(Dispatchers.Main) {
            text_load_change(input_load, "Finishing processing the data")
        }

        if (!logs) {
            pref.edit().putBoolean("desen_pass", true).commit()
        }

        add_register(
            pref, db, "The ${
                if (logs) {
                    "logs"
                } else {
                    "metadata"
                }
            } have been decrypted"
        )

        withContext(Dispatchers.Main) {
            values_modi()
        }

    } catch (e: Exception) {

        Log.e("Error decrypting the values", e.toString())

        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Error decrypting the values", Toast.LENGTH_SHORT).show()
        }

    } finally {
        k.let { it?.encoded?.fill(0) }
    }
}