package com.nothingsecure.funs_extra

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nothingsecure.R
import com.nothingsecure.t_state
import com.nothingsecure.time_out_state
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.time.Duration

fun biometric_auto (context: FragmentActivity, succeded: () -> Unit, error: () -> Unit, title: String = "Authenticate yourself") {

    BiometricPrompt(context, ContextCompat.getMainExecutor(context), object: BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            succeded()
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            Toast.makeText(context, "Authentication error", Toast.LENGTH_SHORT).show()
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

fun load (info: String, context: Activity): Pair<Dialog, TextView> {
    val load_dialog = Dialog(context)
    val load_view = create_dialog(context, R.layout.load, load_dialog)
    load_dialog.setCancelable(false)

    val load_information = load_view.findViewById<TextView>(R.id.load_information)
    load_information.text = "$info..."

    return Pair(load_dialog, load_information)
}
suspend fun text_load_change (load_input: TextView, info: String) {
    delay(300)
    load_input.text = "$info..."
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