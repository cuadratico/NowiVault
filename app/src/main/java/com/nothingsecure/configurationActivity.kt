package com.nothingsecure

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.android.material.imageview.ShapeableImageView
import com.nothingsecure.configuration_tools.delay_time_conf
import com.nothingsecure.configuration_tools.dialog_color_icon
import com.nothingsecure.configuration_tools.edit_icon_conf
import com.nothingsecure.configuration_tools.switch_conf
import com.nothingsecure.funs_extra.biometric_auto
import com.nothingsecure.funs_extra.create_material_dialog
import com.nothingsecure.funs_extra.idioms_instance
import com.nothingsecure.funs_extra.version_name
import com.nothingsecure.recy_information.adapter_global
import com.nothingsecure.recy_information.project_class

class configurationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_configuration)

        val pref = EncryptedSharedPreferences.create(
            this,
            "ap",
                    MasterKey.Builder(this).apply {
                        setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    }.build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

        val r_idioms = idioms_instance(pref, 3)
        val extra_idioms = idioms_instance(pref, 0)

        val nowi_d = findViewById<ShapeableImageView>(R.id.nowi_d)
        val cat_d = findViewById<ShapeableImageView>(R.id.cat_d)
        val bug = findViewById<ShapeableImageView>(R.id.bug_r)
        val git_d = findViewById<ShapeableImageView>(R.id.git_d)

        val recy_conf = findViewById<RecyclerView>(R.id.recy_conf)

        val version_info = findViewById<TextView>(R.id.version_info)

        bug.setOnClickListener {
            create_material_dialog(this, r_idioms.select_idiom(0), r_idioms.select_idiom(1),
                extra_idioms.select_idiom(17), extra_idioms.select_idiom(14), {

                    startActivity(Intent(Intent.ACTION_VIEW, "https://docs.google.com/forms/d/e/1FAIpQLSfyW_uUiiXtZy6lkawKgNxcVQ17CC27cNS2-J1uDVjgn8LHLw/viewform".toUri()))

                }, {}
            )
        }

        nowi_d.setOnClickListener {
            create_material_dialog(this, r_idioms.select_idiom(2), r_idioms.select_idiom(3),
                extra_idioms.select_idiom(17), extra_idioms.select_idiom(14), {

                    startActivity(Intent(Intent.ACTION_VIEW, "https://liberapay.com/Nowi".toUri()))

                }, {}
            )
        }

        cat_d.setOnClickListener {
            create_material_dialog(this, r_idioms.select_idiom(4), r_idioms.select_idiom(5),
                extra_idioms.select_idiom(17), extra_idioms.select_idiom(14), {

                    startActivity(Intent(Intent.ACTION_VIEW, "https://santuariolara.org/p/donaciones".toUri()))

                }, {}
            )
        }

        git_d.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, "https://github.com/cuadratico/NowiVault".toUri()))
        }


        val config_list = listOf(
            project_class.conf_data(R.drawable.conf_modes, r_idioms.select_idiom(6), r_idioms.select_idiom(7), 0),
            project_class.conf_data(R.drawable.conf_color, r_idioms.select_idiom(8), r_idioms.select_idiom(9), 1),
            project_class.conf_data(R.drawable.conf_log, r_idioms.select_idiom(10), r_idioms.select_idiom(11), 2),
            project_class.conf_data(R.drawable.conf_time_out, r_idioms.select_idiom(12), r_idioms.select_idiom(13), 3),
            project_class.conf_data(R.drawable.conf_detection, r_idioms.select_idiom(14), r_idioms.select_idiom(15), 4),
            project_class.conf_data(R.drawable.conf_vibrate, r_idioms.select_idiom(16), r_idioms.select_idiom(17), 5),
            project_class.conf_data(R.drawable.ray, r_idioms.select_idiom(18), r_idioms.select_idiom(19), 6)
        )

        val color_list = listOf(
            project_class.multi_data("#FF000000"),
            project_class.multi_data("#4A0E17"),
            project_class.multi_data("#1A434E"),
            project_class.multi_data("#671B26"),
            project_class.multi_data("#0F294A"),
            project_class.multi_data("#3B1A2E"),
            project_class.multi_data("#2C3539"),
            project_class.multi_data("#58111A"),
            project_class.multi_data("#1b1b1d"),
            project_class.multi_data("#aa4040"),
            project_class.multi_data("#5898df")
        )

        recy_conf.adapter = adapter_global(config_list, 5, { data ->
            (data as project_class.conf_data)

            biometric_auto(this@configurationActivity, {

                when (data.id) {

                    0 -> {
                        edit_icon_conf(this, pref)
                    }

                    1 -> {
                        dialog_color_icon(this, color_list, 4, { data ->

                            pref.edit().putString("color_back", data.data).commit()

                        }, { data ->

                            (this.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).apply {
                                clearPrimaryClip()
                                setPrimaryClip(
                                    ClipData.newPlainText("Color", data.data)
                                )
                            }

                        })
                    }

                    2 -> {
                        switch_conf(this, pref, "log_very", data.info)
                    }

                    3 -> {
                        delay_time_conf(this, pref)
                    }

                    4 -> {
                        switch_conf(this, pref, "ace_force", data.info)
                    }

                    5 -> {
                        switch_conf(this, pref, "vibra", data.info)
                    }

                    else -> {
                        switch_conf(this, pref, "delay", data.info, true)
                    }

                }


            }, {})

        }, { data ->
            (data as project_class.conf_data)

            create_material_dialog(this, r_idioms.select_idiom(20), data.info_material, extra_idioms.select_idiom(0), "", {}, {})
        })
        recy_conf.layoutManager = LinearLayoutManager(this)


        version_info.text = version_name

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}