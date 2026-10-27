package com.nothingsecure.funs_extra

import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Context.CLIPBOARD_SERVICE
import android.content.DialogInterface
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.nothingsecure.R
import com.nothingsecure.recy_information.project_class
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.security.GeneralSecurityException
import java.security.Key
import java.security.KeyStore
import java.security.MessageDigest
import java.security.SecureRandom
import java.security.spec.AlgorithmParameterSpec
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import kotlin.coroutines.resume


fun deri_expressed (pref: SharedPreferences?, pass: String = pref!!.getString("k_u", "").toString(), deri: Boolean = pref!!.getBoolean("deri", false), salt: String = pref!!.getString("salt_def", "").toString(), iter: Int = pref!!.getInt("it_def", 600000), k_size: Int = pref!!.getInt("k_le", 256), algo: String = pref!!.getString("algo", "AES").toString()): Key {
    return if (deri) {
        SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(PBEKeySpec(pass.toCharArray(), Base64.getDecoder().decode(salt), iter, k_size)).encoded, algo)
    } else {
        KeyStore.getInstance("AndroidKeyStore").apply { load(null) }.getKey(pass, null)
    }
}

fun db_create (pref: SharedPreferences, data: String, regi: Boolean, info: String? = null, key: Key, ref: List<String> = listOf(db_p_meta, db_p_val), algo: String = pref.getString("algo", "AES").toString(), color: String = "#1b1b1d"): ContentValues {
    val c_meta = cip_no_cip(pref, Cipher.ENCRYPT_MODE, key, algo)


    return ContentValues().apply {
        put(ref[0],
            es_data(c_meta.doFinal((des_es_values(null, null, regi, null, false, info, color) as project_class.multi_data).data.toByteArray()), c_meta.iv)
        )
        put(ref[1],
            data
        )
    }

}

fun cip_no_cip (pref: SharedPreferences? = null, mode: Int, k: Key, algo: String = pref?.getString("algo", "AES").toString(), iv: String? = null): Cipher {
    data class iv_spec (val algo: String, val specs: AlgorithmParameterSpec?)
    val specs_algo = mapOf(
        "AES" to iv_spec("AES/GCM/NoPadding", if (iv != null) { GCMParameterSpec(128, Base64.getDecoder().decode(iv)) } else { iv }),
        "ChaCha20" to iv_spec("ChaCha20-Poly1305", if (iv != null) { IvParameterSpec(Base64.getDecoder().decode(iv)) } else { iv })
    )

    return Cipher.getInstance(specs_algo.getValue(algo).algo).apply {
        if (mode == Cipher.ENCRYPT_MODE) {
            init(mode, k)
        } else {
            init(mode, k, specs_algo.getValue(algo).specs)
        }
    }
}

suspend fun auth_time_out (context: FragmentActivity, onSuccesse: () -> Unit) {
    try {
        onSuccesse()
    } catch (e: GeneralSecurityException) {
        Log.e("auth_error", e.toString())

        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Unauthenticated key", Toast.LENGTH_SHORT).show()

            suspendCancellableCoroutine { corru ->
                biometric_auto(context, {
                    onSuccesse()
                    corru.resume(Unit)

                }, { corru.resume(Unit) })
            }
        }
    }
}

fun key_conf_dialog (context: Activity, pref: SharedPreferences, deri: Boolean = false, force: Boolean = false, onDismiss: (TextView, TextView, EditText, TextView) -> Unit) {

    val k_idioms = idioms_instance(pref, 6)
    val extra_idioms = idioms_instance(pref, 0)

    val dialog = Dialog(context)
    val view = create_dialog(context, R.layout.cripto_key_conf, dialog)

    var algo_list = listOf("AES", "ChaCha20")

    val info = view.findViewById<TextView>(R.id.info)

    info.text = k_idioms.select_idiom(6)

    val algo_expre = view.findViewById<TextView>(R.id.algori)
    val algo_edit = view.findViewById<ShapeableImageView>(R.id.algori_edit)

    algo_expre.text = algo_list[0]

    val size_expre = view.findViewById<TextView>(R.id.size_expre)
    val edit_size = view.findViewById<ShapeableImageView>(R.id.edit_size)

    val size_list = listOf("128", "256")

    size_expre.setText(size_list[1])

    algo_edit.setOnClickListener {
        algo_list = algo_list.reversed()
        algo_expre.text = algo_list[0]

        if (algo_expre.text.toString() == "AES") {
            edit_size.visibility = View.VISIBLE
        } else {
            edit_size.visibility = View.INVISIBLE
            size_expre.text = "256"
        }
    }

    edit_size.setOnClickListener {
        MaterialAlertDialogBuilder(context).apply {
            setTitle(k_idioms.select_idiom(0) + " ${size_expre.text} bits")
            setAdapter(ArrayAdapter(context, android.R.layout.simple_list_item_1, size_list), object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    size_expre.text = size_list[which]
                }
            })
        }.show()
    }

    val iter_back = view.findViewById<ConstraintLayout>(R.id.iter_gloabl)
    val input_iter = view.findViewById<EditText>(R.id.input_iter)
    val iter_info = view.findViewById<ShapeableImageView>(R.id.iter_info)
    val error_iter = view.findViewById<ShapeableImageView>(R.id.error_iter)
    error_iter.visibility = View.INVISIBLE

    input_iter.setText("600000")
    input_iter.addTextChangedListener { text ->
        if (text.toString().length >= 6) {
            error_iter.visibility = View.INVISIBLE
            if (text.toString().toInt() < 600000) {
                input_iter.setText("600000")
            }
            input_iter.setSelection(text.toString().length)
        } else {
            error_iter.visibility = View.VISIBLE
        }
    }

    iter_info.setOnClickListener {
        create_material_dialog(context, k_idioms.select_idiom(1), k_idioms.select_idiom(2), "", "", {}, {})
    }

    val salt_expre = view.findViewById<TextView>(R.id.salt_expre)
    val copy_salt = view.findViewById<ShapeableImageView>(R.id.copy_salt)
    val re_salt = view.findViewById<ShapeableImageView>(R.id.re_salt)
    salt_expre.visibility = View.GONE
    copy_salt.visibility = View.GONE
    re_salt.visibility = View.GONE

    copy_salt.setOnClickListener {
        (context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(ClipData.newPlainText("item_data", salt_expre.text.toString()))
    }

    re_salt.setOnClickListener {
        salt_expre.text = Base64.getEncoder().withoutPadding().encodeToString(SecureRandom().generateSeed(16))
    }

    if (!deri) {
        iter_back.visibility = View.GONE
        iter_info.visibility = View.GONE
        algo_edit.visibility = View.GONE
    } else {
        if (!force) {
            create_material_dialog(
                context,
                k_idioms.select_idiom(3),
                k_idioms.select_idiom(4),
                k_idioms.select_idiom(5),
                extra_idioms.select_idiom(14),
                {
                    pref.edit().putBoolean("d_v_h", true).commit()

                    salt_expre.visibility = View.VISIBLE
                    copy_salt.visibility = View.VISIBLE
                    re_salt.visibility = View.VISIBLE
                    salt_expre.text = Base64.getEncoder().withoutPadding()
                        .encodeToString(SecureRandom().generateSeed(16))
                },
                {}
            )
        }
    }


    dialog.setOnDismissListener(object: DialogInterface.OnDismissListener {
        override fun onDismiss(p0: DialogInterface?){
            onDismiss(algo_expre, size_expre, input_iter, salt_expre)
        }

    })

}

fun very_hash (hash_1: String, value: String, salt: String): Boolean {
    if (MessageDigest.isEqual(Base64.getDecoder().decode(hash_1), MessageDigest.getInstance("SHA256").digest(value.toByteArray() + Base64.getDecoder().decode(salt)))) {
        return true
    } else {
        return false
    }
}

fun alias_k_s (pref: SharedPreferences, data: String) {
    pref.edit().putString("k_u", Base64.getEncoder().withoutPadding().encodeToString(MessageDigest.getInstance("SHA256").digest(data.toByteArray() + Base64.getDecoder().decode(pref.getString("s_k_c", ""))))).commit()
}

fun rege (pref: SharedPreferences, salt: String, hash: String, value: String) {
    pref.edit().putString(salt, Base64.getEncoder().withoutPadding().encodeToString(SecureRandom().generateSeed(16))).commit()
    pref.edit().putString(hash, Base64.getEncoder().withoutPadding().encodeToString(MessageDigest.getInstance("SHA-256").digest(value.toByteArray() + Base64.getDecoder().decode(pref.getString(salt, ""))))).commit()
}