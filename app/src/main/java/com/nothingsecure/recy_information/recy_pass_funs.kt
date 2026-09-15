package com.nothingsecure.recy_information

import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.SharedPreferences
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.nothingsecure.R
import com.nothingsecure.funs_extra.add_register
import com.nothingsecure.db
import com.nothingsecure.funs_extra.create_dialog
import com.nothingsecure.funs_extra.entropy
import com.nothingsecure.funs_extra.pass_generator_dialog
import com.nothingsecure.funs_extra.vibra_conf
import com.nothingsecure.funs_extra.visibility


fun recy_edit_add (context: Activity, pref: SharedPreferences, edit: Boolean, p_desen: (Dialog, EditText, EditText, TextView) -> Unit, edit_oper: (Dialog, EditText, EditText) -> Unit) {

    val edit_dialog = Dialog(context)
    val edit_view = create_dialog(context, R.layout.add_edit_dialog, edit_dialog)

    val information_extra = edit_view.findViewById<TextView>(R.id.information)
    val edit_information = edit_view.findViewById<EditText>(R.id.information_pass)
    val edit_pass = edit_view.findViewById<EditText>(R.id.input_password)
    val edit_progress = edit_view.findViewById<LinearProgressIndicator>(R.id.progress)
    val pass_visibility = edit_view.findViewById<ConstraintLayout>(R.id.secure_visibility)
    val icon_visi = edit_view.findViewById<ShapeableImageView>(R.id.visibility_icon)
    val bottom = edit_view.findViewById<ShapeableImageView>(R.id.multi_bottom)
    val last_modi = edit_view.findViewById<TextView>(R.id.last_modi)
    val pass_generator = edit_view.findViewById<ConstraintLayout>(R.id.pass_generator)

    if (edit) {
        information_extra.text = "Edit your password"
        bottom.setImageResource(R.drawable.edit_pass)
    }

    edit_pass.isSelected = false
    edit_pass.isLongClickable = false

    entropy(edit_pass.text.toString(), edit_progress)
    edit_pass.addTextChangedListener { data ->
        if (data!!.isEmpty()) {
            entropy(data.toString(), edit_progress)
        }
    }

    visibility(pref, icon_visi, edit_pass)
    pass_visibility.setOnClickListener {
        pref.edit().putBoolean("prims", !pref.getBoolean("prims", false)).commit()
        visibility(pref, icon_visi, edit_pass)
    }

    pass_generator.setOnClickListener {
        pass_generator_dialog(context, pref, null, edit_pass)
    }

    p_desen(edit_dialog, edit_information, edit_pass, last_modi)

    bottom.setOnClickListener {
        if (edit_pass.text.trim().isNotEmpty() && edit_information.text.trim().isNotEmpty()) {
            edit_oper(edit_dialog, edit_pass, edit_information)
            vibra_conf(context, pref, longArrayOf(0, 50, 0, 50))
        }
    }
}

fun recy_preview (context: Activity, pref: SharedPreferences, logs_view: ShapeableImageView, db: db, p_desen: (TextView, Dialog) -> Unit) {

    val dialog_see = Dialog(context)
    val dialog_view = create_dialog(context, R.layout.see_password, dialog_see)

    val see_password = dialog_view.findViewById<TextView>(R.id.pass_visible)
    val copy = dialog_view.findViewById<ShapeableImageView>(R.id.copy)
    val info_copy = dialog_view.findViewById<TextView>(R.id.info_copy)

    fun copy() {
        if (pref.getInt("copy_in", 0) >= 5) {
            copy.visibility = View.GONE
            info_copy.text = "You cannot copy passwords"
        }else {
            info_copy.text = "You can copy ${5 - pref.getInt("copy_in", 0)} more passwords"
        }
    }
    copy()

    copy.setOnClickListener {
        (context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).apply {
            clearPrimaryClip()
            setPrimaryClip(ClipData.newPlainText("pass", see_password.text.toString()))
        }
        pref.edit().putInt("copy_in", pref.getInt("copy_in", 0) + 1).commit()
        add_register(pref, db, "A password has been copied")
        logs_view.visibility = View.VISIBLE
        copy()
    }

    p_desen(see_password, dialog_see)

}
