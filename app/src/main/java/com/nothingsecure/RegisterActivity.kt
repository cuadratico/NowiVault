package com.nothingsecure

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.provider.Settings
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.component1
import androidx.activity.result.component2
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputLayout
import com.nothingsecure.funs_extra.add_register
import com.nothingsecure.funs_extra.alias_k_s
import com.nothingsecure.funs_extra.biometric_auto
import com.nothingsecure.funs_extra.create_dialog
import com.nothingsecure.funs_extra.create_material_dialog
import com.nothingsecure.funs_extra.destroy_info
import com.nothingsecure.funs_extra.entropy
import com.nothingsecure.funs_extra.force
import com.nothingsecure.funs_extra.idioms_instance
import com.nothingsecure.funs_extra.key_conf_dialog
import com.nothingsecure.funs_extra.load
import com.nothingsecure.funs_extra.pass_generator_dialog
import com.nothingsecure.funs_extra.rege
import com.nothingsecure.funs_extra.text_load_change
import com.nothingsecure.funs_extra.very_expre
import com.nothingsecure.funs_extra.vibra_conf
import com.nothingsecure.funs_extra.visibility
import com.nothingsecure.funs_extra.x_regi
import com.nothingsecure.funs_extra.y_regi
import com.nothingsecure.funs_extra.z_regi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.KeyGenerator
import kotlin.random.Random
import kotlin.toString

class RegisterActivity : AppCompatActivity(), SensorEventListener {
    private var sensor_manager: SensorManager? = null
    private lateinit var pref: SharedPreferences
    private var destroy: Boolean = true
    private var pause: Boolean = false
    private var hash_to_calculate: ByteArray? = null
    private lateinit var hash_dialog: Dialog

    private lateinit var r_a_idioms: idioms_instance
    private lateinit var extra_idioms: idioms_instance

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.register_main)

        val lan_change = findViewById<ConstraintLayout>(R.id.id_change)

        val bio_global = findViewById<ConstraintLayout>(R.id.bio_global)
        val info_bio = findViewById<ShapeableImageView>(R.id.info_bio)
        val back_global = findViewById<ConstraintLayout>(R.id.back_global)
        val info_close = findViewById<ShapeableImageView>(R.id.info_close)
        val information = findViewById<TextView>(R.id.information_register)

        val opor = findViewById<TextView>(R.id.opor)

        val input_pass = findViewById<EditText>(R.id.input_password)
        val input_salt = findViewById<EditText>(R.id.input_salt)
        val input_iter = findViewById<EditText>(R.id.input_iter)
        val progress = findViewById<LinearProgressIndicator>(R.id.progress_pass)

        val back_salt = findViewById<TextInputLayout>(R.id.input_salt_background)
        val back_iter = findViewById<TextInputLayout>(R.id.input_iter_background)

        val create = findViewById<ShapeableImageView>(R.id.create_password)

        val pass_gen = findViewById<ConstraintLayout>(R.id.pas_gen)
        val pris_secure_back = findViewById<ConstraintLayout>(R.id.secure_visibility)
        val pris_secure = findViewById<ShapeableImageView>(R.id.visibility_icon)

        val back_derived = findViewById<ConstraintLayout>(R.id.back_derive)
        val derived_check = findViewById<CheckBox>(R.id.derived_check)
        val info_derived = findViewById<ShapeableImageView>(R.id.info_derived)
        val very_apk = findViewById<ConstraintLayout>(R.id.very_apk)

        val bio_error = findViewById<ShapeableImageView>(R.id.bio_error)


        pref = EncryptedSharedPreferences.create(
            this,
            "ap",
            MasterKey.Builder(this).apply {
                setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            }.build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        r_a_idioms = idioms_instance(pref, 1)
        extra_idioms = idioms_instance(pref, 0)


        if (!pref.getBoolean("d_v_h", false)) {
            back_salt.visibility = View.GONE
            back_iter.visibility = View.GONE
        }
        derived_check.text = r_a_idioms.select_idiom(23)

        if (BiometricManager.from(this).canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL) != BiometricManager.BIOMETRIC_SUCCESS) {
            back_global.visibility = View.GONE
            very_apk.visibility = View.GONE
        } else {
            bio_global.visibility = View.GONE
        }

        if (BiometricManager.from(this).canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS) {
            bio_error.visibility = View.GONE
        }

        info_bio.setOnClickListener {
            create_material_dialog(this,
                r_a_idioms.select_idiom(0), r_a_idioms.select_idiom(1), r_a_idioms.select_idiom(2), extra_idioms.select_idiom(3),
                {
                    startActivity(Intent(Settings.ACTION_SETTINGS))
            }, {})
        }

        val animation = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.scale)

        fun block_mode() {
            val dialog = Dialog(this)
            val view = create_dialog(this, R.layout.block, dialog)
            dialog.setCancelable(false)

            val time = view.findViewById<TextView>(R.id.time)

            lifecycleScope.launch (Dispatchers.IO){
                for (tim in (60 * pref.getInt("multi", 1)).downTo(0)) {
                    withContext(Dispatchers.Main) {
                        time.text = tim.toString()
                    }
                    delay(1000)
                }

                pref.edit().putBoolean("block", false).commit()
                pref.edit().putInt("opor", 9).commit()
                pref.edit().putInt("multi", pref.getInt("multi", 1) + 1).commit()
                withContext(Dispatchers.Main) {
                    dialog.dismiss()
                    opor.text = "*".repeat(pref.getInt("opor", 9))
                }
            }
        }

        if (pref.getBoolean("block", false)) {
            block_mode()
        }

        if (pref.getBoolean("start", false)) {
            back_derived.visibility = View.GONE
            pass_gen.visibility = View.GONE

            if (!pref.getBoolean("log_very", false)) {
                create.visibility = View.GONE
            }
            opor.text = "*".repeat(pref.getInt("opor", 9))
        } else {
            opor.visibility = View.GONE
            back_derived.visibility = View.VISIBLE
        }


        if (pref.getBoolean("close", false)) {
            pref.edit().putBoolean("close", false).commit()
            info_close.setImageResource(R.drawable.fall)
            information.text = r_a_idioms.select_idiom(5)
        }

        info_close.setOnClickListener {
            create_material_dialog(
                this,
                "",
                if (pref.getBoolean("close", false)) {
                    r_a_idioms.select_idiom(6)
                } else {
                    r_a_idioms.select_idiom(7)
                },
                extra_idioms.select_idiom(3),
                "",
                {},
                {}
            )
        }

        val lan_list = listOf("en", "es", "ru", "ch", "jp")
        lan_change.setOnClickListener {
            MaterialAlertDialogBuilder(this).apply {
                setTitle("Choose a language")
                setAdapter(ArrayAdapter(this@RegisterActivity, android.R.layout.simple_list_item_1, lan_list), object: DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, posi: Int) {
                        pref.edit().putString("idio", lan_list[posi]).commit()

                        create_material_dialog(this@RegisterActivity, "", "The app needs to be restarted to change the language", "reset", "", {
                            finishAffinity()
                        }, {})
                    }
                })
            }.show()
        }


        fun empty_all () {
            input_pass.text.clear()
            if (pref.getBoolean("d_v_h", false)) {
                input_iter.text.clear()
                input_salt.text.clear()
            }
        }

        val db = db(this)

        fun fin () {
            destroy = false
            add_register(pref, db, r_a_idioms.select_idiom(8), "#40aa47")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        fun very_pass () {
            animation.setAnimationListener(object: Animation.AnimationListener {
                override fun onAnimationEnd(animation: Animation?) {
                    create.visibility = View.INVISIBLE
                    vibra_conf(this@RegisterActivity, pref, longArrayOf(50, 0, 50, 50))
                    fin()
                }

                override fun onAnimationRepeat(animation: Animation?) {}

                override fun onAnimationStart(animation: Animation?) {
                    create.isEnabled = false
                }

            })

             biometric_auto(this,  {
                 val iter_total = (if (pref.getBoolean("d_v_h", false)) { input_iter.text.toString() } else { 0 }).toString().toByteArray()

                 very_expre(this, db, pref, opor, (input_pass.text.toString() + input_salt.text.toString() + String(iter_total) ), "t_h", "t_s", {

                     rege(pref, "t_s", "t_h", (input_pass.text.toString() + input_salt.text.toString() + String(iter_total) ))
                     iter_total.fill(0)

                     if (pref.getBoolean("d_v_h", false)) {

                         pref.edit().putString("salt_def", input_salt.text.toString()).commit()
                         pref.edit().putInt("it_def", input_iter.text.toString().toInt()).commit()

                     }

                     alias_k_s(pref, input_pass.text.toString())

                     if (pref.getBoolean("log_very", false)) {
                         create.startAnimation(animation)
                     } else {
                         fin()
                     }

                 },{})

                empty_all()

            }, {
                empty_all()
            })
        }



        input_pass.addTextChangedListener {dato ->
            entropy(dato.toString(), progress)
            if (pref.getBoolean("start", false) && !pref.getBoolean("log_very", false) && dato?.length == pref.getInt("size", 0)) {
                very_pass()
            }
        }


        create.setOnClickListener {
            if (input_pass.text!!.isEmpty()) {
                Toast.makeText(this, r_a_idioms.select_idiom(13), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pref.getBoolean("log_very", false)) {
                if (pref.getBoolean("d_v_h", false) && (input_salt.text.isEmpty() || input_iter.text.isEmpty())) {
                    return@setOnClickListener
                }
                very_pass()
            } else {

                if (input_pass.text!!.length < 8) {
                    Toast.makeText(this, r_a_idioms.select_idiom(12), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                key_conf_dialog(this, pref, derived_check.isChecked) { algo_expre, size_expre, input_iter, salt_expre ->

                    biometric_auto(this, {

                        val (load, load_info) = load(this, pref)

                        animation.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationEnd(ani: Animation?) {
                                create.visibility = View.INVISIBLE
                                load.dismiss()
                                fin()
                            }

                            override fun onAnimationRepeat(p0: Animation?) {}

                            override fun onAnimationStart(p0: Animation?) {
                                create.isEnabled = false
                            }

                        })

                        val bool_check = derived_check.isChecked

                        lifecycleScope.launch(Dispatchers.IO) {
                            text_load_change(pref, load_info, r_a_idioms.select_idiom(11))

                            pref.edit().putString("s_k_c", Base64.getEncoder().withoutPadding().encodeToString(SecureRandom().generateSeed(16))).commit()
                            alias_k_s(pref, withContext(Dispatchers.Main) { input_pass.text.toString() })
                            pref.edit().putInt("size", input_pass.text.length).commit()

                            if (!bool_check) {
                                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore").apply {
                                    init(
                                        KeyGenParameterSpec.Builder(pref.getString("k_u", "").toString(), KeyProperties.PURPOSE_DECRYPT or KeyProperties.PURPOSE_ENCRYPT).apply {
                                            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                                            setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                                            setKeySize(size_expre.text.toString().toInt())
                                            setUserAuthenticationRequired(true)
                                            setUserAuthenticationValidityDurationSeconds(5)

                                            if (packageManager.hasSystemFeature(PackageManager.FEATURE_STRONGBOX_KEYSTORE)) {
                                                setIsStrongBoxBacked(true)
                                            }

                                        }.build()
                                    )
                                }.generateKey()

                            } else {

                                pref.edit().putBoolean("deri", true).commit()

                                withContext(Dispatchers.Main) {
                                    if (input_iter.text.toString().toInt() < 600000) {
                                        input_iter.setText("600000")
                                    }
                                }

                                if (pref.getBoolean("d_v_h", false)) {
                                    pref.edit().putString("salt_def", withContext(Dispatchers.Main) { salt_expre.text.toString() }).commit()
                                } else {
                                    pref.edit().putString("salt_def", Base64.getEncoder().withoutPadding().encodeToString(SecureRandom().generateSeed(16))).commit()
                                }

                                withContext(Dispatchers.Main) {
                                    pref.edit().putInt("k_le", size_expre.text.toString().toInt()).commit()
                                    pref.edit().putInt("it_def", input_iter.text.toString().toInt()).commit()
                                    pref.edit().putString("algo", algo_expre.text.toString()).commit()
                                }
                            }

                            rege(pref, "t_s", "t_h", withContext(Dispatchers.Main) { (input_pass.text.toString() + pref.getString("salt_def", "") + pref.getInt("it_def", 0).toString()) })


                            withContext(Dispatchers.Main) {
                                input_pass.text.clear()
                                text_load_change(pref, load_info, r_a_idioms.select_idiom(11))
                            }

                            pref.edit().putString("key_l", String(Random.nextBytes(8))).commit()

                            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore").apply {
                                init(KeyGenParameterSpec.Builder(pref.getString("key_l", "").toString(), KeyProperties.PURPOSE_DECRYPT or KeyProperties.PURPOSE_ENCRYPT).apply {
                                        setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                                        setBlockModes(KeyProperties.BLOCK_MODE_GCM)

                                        if (packageManager.hasSystemFeature(PackageManager.FEATURE_STRONGBOX_KEYSTORE)) {
                                            setIsStrongBoxBacked(true)
                                        }
                                    }.build()
                                )

                            }.generateKey()

                            pref.edit().putBoolean("start", true).commit()
                            pref.edit().putBoolean("log_very", true).commit()
                            vibra_conf(this@RegisterActivity, pref, longArrayOf(0, 100, 0, 100))

                            withContext(Dispatchers.Main) {
                                create.startAnimation(animation)
                            }
                        }

                    }, {
                        empty_all()
                        pref.edit().clear().commit()
                    })

                }
            }

        }

        very_apk.setOnClickListener {
            hash_dialog = Dialog(this)
            val view_hash = create_dialog(this, R.layout.very_apk, hash_dialog)

            val input_hash = view_hash.findViewById<EditText>(R.id.input_hash)
            val file_hahs = view_hash.findViewById<ShapeableImageView>(R.id.file_hash)
            val info_very = view_hash.findViewById<TextView>(R.id.info_very)

            info_very.text = r_a_idioms.select_idiom(24)

            file_hahs.setOnClickListener {
                if (input_hash.text.isEmpty() && !Regex(".*[0-9a-z]{64}.*").matches(input_hash.text.toString())) {
                    Toast.makeText(this, r_a_idioms.select_idiom(15), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                pause = true
                hash_to_calculate = input_hash.text.toString().toByteArray()

                apk_very_file.launch(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "application/vnd.android.package-archive"
                })

            }

            hash_dialog.setOnDismissListener(object: DialogInterface.OnDismissListener {
                override fun onDismiss(p0: DialogInterface?) {
                    hash_to_calculate.let {  it?.fill(0) }
                }
            })

            create_material_dialog(this, r_a_idioms.select_idiom(15), r_a_idioms.select_idiom(16), "", "", {}, {})

        }

        derived_check.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton, check: Boolean) {
                if (check) {
                    create_material_dialog(this@RegisterActivity, r_a_idioms.select_idiom(17), r_a_idioms.select_idiom(18), extra_idioms.select_idiom(0), "", {}, {})
                }
            }

        })

        info_derived.setOnClickListener {
            create_material_dialog(this, r_a_idioms.select_idiom(17), r_a_idioms.select_idiom(18), extra_idioms.select_idiom(0), "", {}, {})
        }


        pass_gen.setOnClickListener {
            pass_generator_dialog(this, pref, null, input_pass) {}
        }

        pris_secure_back.setOnClickListener {
            pref.edit().putBoolean("prims", !pref.getBoolean("prims", false)).commit()

            visibility(pref, pris_secure, input_pass)

            if (pref.getBoolean("d_v_h", false)) {
                visibility(pref, pris_secure, input_salt)
                visibility(pref, pris_secure, input_iter)
            }
        }


        if (pref.getBoolean("ace_force", false)) {
            sensor_manager = getSystemService(SENSOR_SERVICE) as SensorManager
            sensor_manager?.registerListener(this, sensor_manager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        }

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

    override fun onDestroy() {
        super.onDestroy()
        sensor_manager?.unregisterListener(this)

        if (!pref.getBoolean("start", false)) {
            pref.edit().clear().commit()
        } else {
            if (destroy) {
                destroy_info(pref)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (!pause) {
            finish()
        }
        pause = true
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {

            force(this, pref, event.values[0], event.values[1], event.values[2])

            x_regi = event.values[0]
            y_regi = event.values[1]
            z_regi = event.values[2]
        }
    }

    private var apk_very_file = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { (code, data) ->

        if (code == RESULT_CANCELED) {
            Toast.makeText(this, extra_idioms.select_idiom(3), Toast.LENGTH_SHORT).show()
            return@registerForActivityResult
        }

        val query = contentResolver.query(data?.data!!, null, null, null, null)
        if (!query!!.moveToFirst()) {
            Toast.makeText(this, extra_idioms.select_idiom(4), Toast.LENGTH_SHORT).show()
            return@registerForActivityResult
        }

        hash_dialog.dismiss()

        val body = contentResolver.openInputStream(data.data!!).use { file ->
            file?.readBytes()
        }
        val (load_dialog, load_text) = load(this, pref)

        lifecycleScope.launch(Dispatchers.IO) {
            text_load_change(pref, load_text, r_a_idioms.select_idiom(19))

            var info_match = r_a_idioms.select_idiom(20)
            var icon_match = R.drawable.match

            if (!MessageDigest.isEqual(MessageDigest.getInstance("SHA256").digest(body), String(hash_to_calculate!!).hexToByteArray())) {
                info_match = r_a_idioms.select_idiom(21)
                icon_match = R.drawable.no_match
            }

            withContext(Dispatchers.Main) {
                load_dialog.dismiss()

                val info_dialog = Dialog(this@RegisterActivity)
                val view_dialog =
                    create_dialog(this@RegisterActivity, R.layout.alert_dialog, info_dialog)

                val text_info = view_dialog.findViewById<TextView>(R.id.alert_text)
                val view_info = view_dialog.findViewById<ShapeableImageView>(R.id.alert_view)

                text_info.text = info_match
                view_info.setImageResource(icon_match)
            }
        }

    }
}