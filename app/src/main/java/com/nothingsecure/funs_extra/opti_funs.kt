package com.nothingsecure.funs_extra

import android.app.Activity
import android.app.Dialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.nothingsecure.R
import com.nothingsecure.t_state
import com.nothingsecure.time_out_state
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.crypto.Cipher
import kotlin.time.Duration.Companion.milliseconds

fun biometric_auto (context: FragmentActivity, succeded: () -> Unit, error: () -> Unit, title: String = "G2") {

    BiometricPrompt(context, ContextCompat.getMainExecutor(context), object: BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            succeded()
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            error()
        }
    }).authenticate(
        BiometricPrompt.PromptInfo.Builder().apply {
            setTitle(title)
            setAllowedAuthenticators(BiometricManager.Authenticators.DEVICE_CREDENTIAL or BiometricManager.Authenticators.BIOMETRIC_STRONG)
            setConfirmationRequired(true)
        }.build()
    )

}

fun load (context: Activity, pref: SharedPreferences): Pair<Dialog, TextView> {
    t_state = time_out_state.stop

    val load_dialog = Dialog(context)
    val load_view = create_dialog(context, R.layout.load, load_dialog)
    load_dialog.setCancelable(false)

    val load_information = load_view.findViewById<TextView>(R.id.load_information)
    val info_text = load_view.findViewById<TextView>(R.id.info)

    info_text.text = idioms_instance(pref, 0).select_idiom(18)

    return Pair(load_dialog, load_information)
}
suspend fun text_load_change (pref: SharedPreferences, load_input: TextView, info: String) {
    delay(10.milliseconds)
    withContext(Dispatchers.Main) {
        load_input.text = "$info..."
    }

    if (pref.getBoolean("delay", true)) {
        delay(300.milliseconds)
    }
}

fun create_dialog (context: Activity, view: Int, type: Dialog): View {

    val view = LayoutInflater.from(context).inflate(view, null)

    type.setContentView(view)
    type.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    type.window?.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
    type.show()

    return view
}

fun create_material_dialog (context: Activity, title: String, message: String, t_p_button: String, t_n_button: String, p_button: () -> Unit, n_button: () -> Unit): AlertDialog {

    return MaterialAlertDialogBuilder(context).apply {
        setTitle(title)
        setMessage(message)

        setPositiveButton(t_p_button) {_, _ ->
            p_button()
        }

        setNegativeButton(t_n_button) {_, _ ->
            n_button()
        }
    }.show()
}

fun multi_opti_dialog (context: Activity, l_icon: Int, r_icon: Int, left: (Dialog) -> Unit, right: (Dialog) -> Unit) {

    val back_dialog = Dialog(context)
    val view_back_dialog = create_dialog(context, R.layout.archive_oprtions, back_dialog)

    val left_b = view_back_dialog.findViewById<ConstraintLayout>(R.id.left)
    val left_icon = view_back_dialog.findViewById<ShapeableImageView>(R.id.left_icon)

    val right_b = view_back_dialog.findViewById<ConstraintLayout>(R.id.right)
    val right_icon = view_back_dialog.findViewById<ShapeableImageView>(R.id.right_icon)

    left_icon.setImageResource(l_icon)
    right_icon.setImageResource(r_icon)

    left_b.setOnClickListener {
        left(back_dialog)
    }

    right_b.setOnClickListener {
        right(back_dialog)
    }
}