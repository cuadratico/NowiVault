package com.nothingsecure.configuration_tools

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatSpinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.toColorInt
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.materialswitch.MaterialSwitch
import com.nothingsecure.R
import com.nothingsecure.db
import com.nothingsecure.funs_extra.add_register
import com.nothingsecure.funs_extra.create_dialog
import com.nothingsecure.funs_extra.create_material_dialog
import com.nothingsecure.recy_information.adapter_global
import com.nothingsecure.recy_information.holders_datas
import com.nothingsecure.recy_information.project_class
import java.security.KeyStore

val icons_list = listOf(
    project_class.icon_info(R.drawable.honeypot, "HoneyPot"),
    project_class.icon_info(R.drawable.close_all, "CloseAll"),
    project_class.icon_info(R.drawable.delete_all, "DeleteAll"),
    project_class.icon_info(R.drawable.padlock, "EncryptData")
)
val description_list = listOf(
    "The \"HoneyPot\" mode allows you to create a secure execution environment that mimics NowiVault without exposing your data. It is an excellent way to mitigate an attacker by making them believe they have successfully accessed your information. ¡¡To exit this mode, you will need to press the volume down button twice!!",
    "The \"CloseAll\" mode allows you to close NowiVault safely, ensuring that all values \u200B\u200B slated for local deletion are indeed removed.",
    "The \"DeleteAll\" mode allows you to remove all of the app's local data; it is essentially a panic button. This deletion includes your cryptographic key (in any mode), your database, any settings, and all logs.",
    "The \"EncryptData\" mode allows you to encrypt your passwords if you have already decrypted them."
)

fun edit_icon_conf (context: Activity, pref: SharedPreferences) {

    val db = db(context)

    val modes_expre = listOf("HoneyPot", "CloseAll", "DeleteAll", "EncryptData")

    var init = false

    val dialog_edit_icon = Dialog(context)
    val view_edit_icon = create_dialog(context, R.layout.config_in_edit_icon, dialog_edit_icon)

    val icons_all = view_edit_icon.findViewById<ConstraintLayout>(R.id.icons_all)
    val edit_icon = view_edit_icon.findViewById<ShapeableImageView>(R.id.edit_icon)
    val edit_button = view_edit_icon.findViewById<ShapeableImageView>(R.id.edit_icons_button)
    val fun_spinner = view_edit_icon.findViewById<AppCompatSpinner>(R.id.fun_spinner)
    val check_dialog = view_edit_icon.findViewById<AppCompatCheckBox>(R.id.dialog_show_check)
    fun_spinner.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, modes_expre)
    check_dialog.isChecked = pref.getBoolean("info_modes", true)

    icons_all.backgroundTintList = ColorStateList.valueOf(pref.getString("color_back", "#FF000000")!!.toColorInt())
    edit_icon.setImageResource(icons_list[pref.getInt("modes_icon", 0)].icon)
    fun_spinner.setSelection(pref.getInt("modes_sel", 0))

    edit_button.setOnClickListener {
        dialog_color_icon(context, icons_list, 3, { data ->
            pref.edit().putInt("modes_icon", data.data.toInt()).commit()
            edit_icon.setImageResource(icons_list[pref.getInt("modes_icon", 0)].icon)
        }, {})
    }

    fun_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            if (init) {
                create_material_dialog(context, "The \"${modes_expre[position]}\" mode", description_list[position], "Ok", "", {}, {})
                pref.edit().putInt("modes_sel", position).commit()
                add_register(pref, db, "The button mode has been changed to \"${modes_expre[position]}\" ")
            } else {
                init = true
            }
        }
        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }

    check_dialog.setOnCheckedChangeListener( object: CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(p0: CompoundButton, check: Boolean) {
            pref.edit().putBoolean("info_modes", check).commit()
        }
    })

}

fun dialog_color_icon (context: Activity, list: List<project_class>, type_holder: Int, click: (project_class.multi_data) -> Unit, long_click: (project_class.multi_data) -> Unit) {
    val dialog_conf = Dialog(context)
    val dialog_view = create_dialog(context, R.layout.dialog_config_expressed, dialog_conf)

    val recy = dialog_view.findViewById<RecyclerView>(R.id.multi_recy)

    recy.adapter = adapter_global(list, type_holder, holders_datas.multi_holder_data({ data ->
        (data as project_class.multi_data)

        click(data)
        dialog_conf.dismiss()

    }, { data ->
        (data as project_class.multi_data)

        long_click(data)
    }))
    recy.layoutManager = LinearLayoutManager(context)
}


fun delay_time_conf (context: FragmentActivity, pref: SharedPreferences) {
    val time_list = listOf("5 secons", "10 secons", "20 secons", "30 secons", "60 secons")

    MaterialAlertDialogBuilder(context).apply {
        setTitle("Your current timeout is ${pref.getInt("time_out", 30000) / 1000} seconds")
        setAdapter(
            ArrayAdapter(context, android.R.layout.simple_list_item_1, time_list),
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    pref.edit().putInt("time_out", time_list[which].split(" ")[0].toInt() * 1000).commit()
                }
            })
    }.show()
}

fun switch_conf (context: Activity, pref: SharedPreferences, alias: String, info_switch: String) {

    val switch_dialog = Dialog(context)
    val switch_view = create_dialog(context, R.layout.config_in_switch_global, switch_dialog)

    val info = switch_view.findViewById<TextView>(R.id.info_switch)
    val switch = switch_view.findViewById<MaterialSwitch>(R.id.switch_but)

    info.text = info_switch
    switch.isChecked = pref.getBoolean(alias, false)

    switch.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(p0: CompoundButton, b: Boolean) {
            pref.edit().putBoolean(alias, b).commit()
            if (alias == "ace_force") {
                create_material_dialog(context, "", "NowiVault needs to restart to activate this configuration", "reset", "", {}, {})
            }
        }
    })

}

fun delete_all_fun (contex: Context, pref: SharedPreferences, db: db) {
    db.delete("info_s")
    db.delete("info_r")
    contex.cacheDir.deleteRecursively()
    contex.externalCacheDir?.deleteRecursively()

    val ks = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
    ks.deleteEntry(pref.getString("key_u", ""))
    ks.deleteEntry(pref.getString("key_l", ""))

    pref.edit().clear().apply()
}

