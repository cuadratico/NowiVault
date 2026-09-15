package com.nothingsecure

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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
import com.nothingsecure.recy_information.adapter_global
import com.nothingsecure.recy_information.conf_holder
import com.nothingsecure.recy_information.holders_datas
import com.nothingsecure.recy_information.project_class

class configurationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_configuration)

        val pref = EncryptedSharedPreferences.create(this, "ap",
            MasterKey.Builder(this).apply {
                setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            }.build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val nowi_d = findViewById<ShapeableImageView>(R.id.nowi_d)
        val cat_d = findViewById<ShapeableImageView>(R.id.cat_d)
        val bug = findViewById<ShapeableImageView>(R.id.bug_r)

        val recy_conf = findViewById<RecyclerView>(R.id.recy_conf)

        val version_info = findViewById<TextView>(R.id.version_info)

        bug.setOnClickListener {
            create_material_dialog(this, "Do you want to leave a comment about NowiVault?", "All feedback regarding improvements to NowiVault is actively addressed by \"cuadratico\" and compiled for continuous improvement",
                "I'd love to", "Better not", {

                    startActivity(Intent(Intent.ACTION_VIEW, "https://docs.google.com/forms/d/e/1FAIpQLSfyW_uUiiXtZy6lkawKgNxcVQ17CC27cNS2-J1uDVjgn8LHLw/viewform".toUri()))

                }, {}
            )
        }

        nowi_d.setOnClickListener {
            create_material_dialog(this, "Do you want to donate to the Nowi project?", "At Nowi, we develop Android apps focused on local defensive security.\n" +
                    "By donating, you are supporting the project's continuity.\n" +
                    "\n" +
                    "Thanks for everything! \uD83D\uDE01",
                "I'd love to", "Better not", {

                    startActivity(Intent(Intent.ACTION_VIEW, "https://liberapay.com/Nowi".toUri()))

                }, {}
            )
        }

        cat_d.setOnClickListener {
            create_material_dialog(this, "Do you want to help kittens?", "At Nowi, we believe kittens are essential to life, so why not help as many as possible? \uD83D\uDE3A",
                "I'd love to", "Better not", {

                    startActivity(Intent(Intent.ACTION_VIEW, "https://santuariolara.org/p/donaciones".toUri()))

                }, {}
            )
        }


        val config_list = listOf(
            project_class.conf_data(R.drawable.conf_modes, "settings button", "Here, you can configure the functionality of your \"settings button\" and its icon separately.", 0),
            project_class.conf_data(R.drawable.conf_color, "Menu color", "Here you can configure the background color of the top menu.", 1),
            project_class.conf_data(R.drawable.conf_log, "login type", "Here, you can enable or disable the login button so that the login process is triggered based on the length of your password.", 2),
            project_class.conf_data(R.drawable.conf_time_out, "Time-Out", "Here you can configure the NowiVault timeout, with the ability to extend or shorten your session duration.", 3),
            project_class.conf_data(R.drawable.conf_detection, "accelerometer detection", "Here you can configure whether or not to enable accelerometer verification; this feature causes NowiVault to close if sudden movement is detected.", 4),
            project_class.conf_data(R.drawable.conf_vibrate, "Vibration", "Here you can configure whether to enable or disable vibration feedback in NowiVault.", 5)
        )

        val color_list = listOf(
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

        // continuar con la implementacion de las configuraciones
        recy_conf.adapter = adapter_global(config_list, 5, holders_datas.multi_holder_data({ data ->
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

                    else -> {
                        switch_conf(this, pref, "vibra", data.info)
                    }

                }


            }, {})

        }, { data ->
            (data as project_class.conf_data)

            create_material_dialog(this, "What does this configuration do?", data.info_material, "Ok", "", {}, {})
        }))
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