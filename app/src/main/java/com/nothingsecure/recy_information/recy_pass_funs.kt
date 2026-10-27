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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.nothingsecure.R
import com.nothingsecure.funs_extra.add_register
import com.nothingsecure.db
import com.nothingsecure.funs_extra.create_dialog
import com.nothingsecure.funs_extra.entropy
import com.nothingsecure.funs_extra.idioms_instance
import com.nothingsecure.funs_extra.pass_generator_dialog
import com.nothingsecure.funs_extra.vibra_conf
import com.nothingsecure.funs_extra.visibility
import com.nothingsecure.t_state
import com.nothingsecure.time_out_state


fun recy_edit_add (context: Activity, pref: SharedPreferences, edit: Boolean, p_desen: (EditText, EditText, TextView) -> Unit, edit_oper: (Dialog, EditText, EditText) -> Unit, dis: () -> Unit) {
    t_state = time_out_state.stop

    val edit_dialog = Dialog(context)
    val edit_view = create_dialog(context, R.layout.add_edit_dialog, edit_dialog)

    val edit_information = edit_view.findViewById<EditText>(R.id.information_pass)
    val edit_pass = edit_view.findViewById<EditText>(R.id.input_password)
    val edit_progress = edit_view.findViewById<LinearProgressIndicator>(R.id.progress)
    val pass_visibility = edit_view.findViewById<ConstraintLayout>(R.id.secure_visibility)
    val icon_visi = edit_view.findViewById<ShapeableImageView>(R.id.visibility_icon)
    val bottom = edit_view.findViewById<ShapeableImageView>(R.id.multi_bottom)
    val last_modi = edit_view.findViewById<TextView>(R.id.last_modi)
    val pass_generator = edit_view.findViewById<ConstraintLayout>(R.id.pass_generator)

    if (edit) {
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
        pass_generator_dialog(context, pref, null, edit_pass) {}
    }

    p_desen(edit_information, edit_pass, last_modi)

    bottom.setOnClickListener {
        if (edit_pass.text.trim().isNotEmpty() && edit_information.text.trim().isNotEmpty()) {
            edit_oper(edit_dialog, edit_pass, edit_information)
            vibra_conf(context, pref, longArrayOf(0, 50, 0, 50))
        }
    }

    edit_dialog.setOnDismissListener { _-> dis() }
}

fun recy_preview (context: Activity, pref: SharedPreferences, logs_view: ShapeableImageView, db: db, view_type: Boolean = false, p_desen: (TextView) -> Unit) {

    val funs_idioms = idioms_instance(pref, 4)

    val dialog_see = Dialog(context)
    val dialog_view = create_dialog(context, R.layout.see_password, dialog_see)

    val see_password = dialog_view.findViewById<TextView>(R.id.pass_visible)
    val copy = dialog_view.findViewById<ShapeableImageView>(R.id.copy)
    val info_recy = dialog_view.findViewById<RecyclerView>(R.id.recy_opor)

    val list_recy = listOf(project_class.opor_data(R.drawable.key), project_class.opor_data(R.drawable.key), project_class.opor_data(R.drawable.key), project_class.opor_data(R.drawable.key), project_class.opor_data(R.drawable.key))
    val adapter = adapter_global(list_recy.take(pref.getInt("copy_in", 5)), 7, {}, {})

    info_recy.adapter = adapter
    info_recy.layoutManager = LinearLayoutManager(context).apply {
        orientation = RecyclerView.HORIZONTAL
    }

    fun copy() {
        if (pref.getInt("copy_in", 5) <= 0) {
            copy.visibility = View.GONE
            info_recy.visibility = View.GONE
        } else {
            adapter.update(list_recy.take(pref.getInt("copy_in", 5)))
        }
    }

    if (view_type) {
        copy.visibility = View.GONE
        info_recy.visibility = View.GONE
    } else {
        copy()
    }

    copy.setOnClickListener {
        (context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).apply {
            clearPrimaryClip()
            setPrimaryClip(ClipData.newPlainText("pass", see_password.text.toString()))
        }
        pref.edit().putInt("copy_in", pref.getInt("copy_in", 5) - 1).commit()
        add_register(pref, db, funs_idioms.select_idiom(5))
        logs_view.visibility = View.VISIBLE
        copy()
    }

    p_desen(see_password)

}
