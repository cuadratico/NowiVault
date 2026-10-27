package com.nothingsecure.funs_extra

import android.app.Activity
import android.app.Dialog
import android.content.Context.VIBRATOR_SERVICE
import android.content.SharedPreferences
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.toColorInt
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentActivity
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.nothingsecure.R
import com.nothingsecure.db
import com.nothingsecure.recy_information.project_class
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.crypto.Cipher
import kotlin.math.abs


fun add_register (pref: SharedPreferences, db: db, note: String, color: String = "#1b1b1d") {
    val c_val = cip_no_cip(pref, Cipher.ENCRYPT_MODE, deri_expressed(pref, pref.getString("key_l", "").toString(), false), "AES")

    db.insert(
        pref,
        "info_r",
        db_create(pref, es_data(c_val.doFinal((des_es_values(null, null, true, note, true) as project_class.multi_data).data.toByteArray()), c_val.iv), true, null, deri_expressed(pref, pref.getString("key_l", "").toString(), false), listOf(db_l_meta, db_l_val), "AES", color)
    )

    pref.edit().putBoolean("info_r_full", true).commit()

}


var x_regi = 0f
var y_regi = 0f
var z_regi = 0f

fun force (context: Activity, pref: SharedPreferences, x: Float, y: Float, z: Float) {

    if (abs(x_regi - x) > 12 || abs(y_regi - y) > 12 || abs(z_regi - z) > 12) {

        pref.edit().putBoolean("close", true).commit()

        val dialog = Dialog(context)
        val view_force = create_dialog(context, R.layout.alert_dialog, dialog)

        val alert_text = view_force.findViewById<TextView>(R.id.alert_text)
        val alert_view = view_force.findViewById<ShapeableImageView>(R.id.alert_view)

        alert_text.text = idioms_instance(pref, 8).select_idiom(0)
        alert_view.setImageResource(R.drawable.fall)

        CoroutineScope(Dispatchers.IO).launch {
            delay(300)

            withContext(Dispatchers.Main) {
                dialog.dismiss()
                context.finishAffinity()
            }
        }


    }

}

fun destroy_info (pref: SharedPreferences) {
    pref.edit().remove("k_u").commit()
    pref.edit().remove("destroy").commit()
    pref.edit().remove("copy_in").commit()

    if (pref.getBoolean("d_v_h", false)) {
        pref.edit().remove("salt_def").commit()
        pref.edit().remove("it_def").commit()
    }
}


fun visibility (pref: SharedPreferences, icon: ShapeableImageView, input: EditText) {
    if (pref.getBoolean("prims", false)) {
        icon.setImageResource(R.drawable.prism_rell)
        input.setTextColor("#27272A".toColorInt())
    }else {
        icon.setImageResource(R.drawable.prism_no_rell)
        input.setTextColor("#e3e3e3".toColorInt())
    }
}

fun vibra_conf (context: Activity, pref: SharedPreferences, list: LongArray) {
    if (pref.getBoolean("vibra", false)) {
        (context.getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(VibrationEffect.createWaveform(list, -1))
    }
}

inline fun very_expre (context: Activity, db: db, pref: SharedPreferences, opor: TextView, value: String, a_hash: String, a_salt: String, very: () -> Unit, error: () -> Unit) {

    if (very_hash(pref.getString(a_hash, "").toString(), value, pref.getString(a_salt, "").toString())) {
        very()
    } else {
        pref.edit().putInt("opor", pref.getInt("opor", 9) - 1).commit()

        if (pref.getInt("opor", 9) == 0) {
            opor.text = ""
            pref.edit().putBoolean("block", true).commit()
            add_register(pref, db, idioms_instance(pref, 1).select_idiom(9), "#aa4040")
            context.finishAffinity()
        } else {
            opor.text = "*".repeat(pref.getInt("opor", 0))
        }

        Toast.makeText(context, idioms_instance(pref, 0).select_idiom(6), Toast.LENGTH_SHORT).show()

        error()
    }

}

fun very_grade (context: FragmentActivity, pref: SharedPreferences, db: db, very: () -> Unit) {

    pref.edit().remove("k_u").commit()

    val very_dialog = Dialog(context).apply {
        setCancelable(false)
    }
    val very_view = create_dialog(context, R.layout.dialog_very_grade_1, very_dialog)

    val info = very_view.findViewById<TextView>(R.id.info)
    val opor = very_view.findViewById<TextView>(R.id.opor)

    val very_input_pass = very_view.findViewById<EditText>(R.id.input_pass)
    val very_progress = very_view.findViewById<LinearProgressIndicator>(R.id.progress)

    val very_visible_button = very_view.findViewById<ConstraintLayout>(R.id.password_visibility)
    val very_icon_visible = very_view.findViewById<ShapeableImageView>(R.id.visibility_icon)

    val very_button = very_view.findViewById<ConstraintLayout>(R.id.unlock_buttom)

    info.text = idioms_instance(pref, 0).select_idiom(20)
    opor.text = "*".repeat(pref.getInt("opor", 9))

    very_input_pass.addTextChangedListener { dato ->
        entropy(dato.toString(), very_progress)
    }

    visibility(pref, very_icon_visible, very_input_pass)
    very_visible_button.setOnClickListener {
        pref.edit().putBoolean("prims", !pref.getBoolean("prims", false)).commit()
        visibility(pref, very_icon_visible, very_input_pass)
    }


    very_button.setOnClickListener {
        if (very_input_pass.text.isEmpty()) {
            Toast.makeText(context, idioms_instance(pref, 1).select_idiom(13), Toast.LENGTH_SHORT).show()
            return@setOnClickListener
        }

        biometric_auto(context, {

            very_expre(context, db, pref, opor, (very_input_pass.text.toString() + pref.getString("salt_def", "") + pref.getInt("it_def", 0).toString()), "t_h", "t_s", {
                alias_k_s(pref, very_input_pass.text.toString())
                very_dialog.dismiss()
                very()

            }, {
                very_input_pass.text.clear()
            })

        }, {
            very_input_pass.text.clear()
        })

    }

}