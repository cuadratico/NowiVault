package com.nothingsecure.funs_extra

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.SharedPreferences
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.nothingsecure.R
import com.nothingsecure.recy_information.adapter_global
import com.nothingsecure.recy_information.holders_datas
import com.nothingsecure.recy_information.project_class
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.text.iterator

fun pass_generator (size: Int, ini_list: List<List<Char>> = listOf(
    mayusculas_l,
    minusculas_l,
    simbolos_l,
    numeros_l
)): String {

    val bool_list = mutableListOf<Boolean>()

    for (i in 0..ini_list.size - 1) {
        bool_list.add(false)
    }

    var final_list = ""

    for (position in ini_list) {
        final_list += position.joinToString("")
    }


    while (true) {
        var pass = ""
        for (i in 0..size - 1) {
            pass += final_list.toList().shuffled()[0]
        }

        for (value in pass) {

            var final = true
            for (position in 0..bool_list.size - 1) {
                if (ini_list[position].contains(value) && !bool_list[position]) {
                    bool_list[position] = true
                }
                final = final and bool_list[position]
            }
            if (final) {
                return pass
            }


        }
    }

}
fun pass_generator_dialog (context: Activity, pref: SharedPreferences, info_image: ShapeableImageView? = null, pass_input: EditText? = null) {
    val total = mutableListOf(minusculas_l)

    val gene_dilaog = BottomSheetDialog(context)
    val gen_view = create_dialog(context, R.layout.generator_dialog, gene_dilaog)


    val refresh = gen_view.findViewById<ConstraintLayout>(R.id.refresh)
    val icon_refresh = gen_view.findViewById<ShapeableImageView>(R.id.icon_refresh)
    val result_pass = gen_view.findViewById<TextView>(R.id.result_pass)
    val size = gen_view.findViewById<SeekBar>(R.id.size)
    val information_size = gen_view.findViewById<TextView>(R.id.information_size)
    val progress_calculator = gen_view.findViewById<LinearProgressIndicator>(R.id.progress)
    val capital_l = gen_view.findViewById<MaterialSwitch>(R.id.capital_l)
    val number = gen_view.findViewById<MaterialSwitch>(R.id.numbers)
    val simbol = gen_view.findViewById<MaterialSwitch>(R.id.simbol)
    val copy = gen_view.findViewById<ShapeableImageView>(R.id.copy)
    val mail_gen = gen_view.findViewById<ConstraintLayout>(R.id.gen_emails_intent)

    mail_gen.setOnClickListener {
        pref.edit().putBoolean("gene", false).commit()
        gene_dilaog.dismiss()
        info_image!!.setImageResource(R.drawable.mail_generator)
        email_generator(context, pref, info_image)
    }
    if (info_image == null) {
        mail_gen.visibility = View.INVISIBLE
    }
    fun gen (bar: SeekBar) {
        result_pass.text = pass_generator(bar.progress, total)
        entropy(result_pass.text.toString(), progress_calculator)
    }

    gen(size)

    size.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(p0: SeekBar?, size: Int, p2: Boolean) {
            information_size.text = size.toString()
        }

        override fun onStartTrackingTouch(bar: SeekBar?) {}

        override fun onStopTrackingTouch(bar: SeekBar) {
            gen(bar)
        }

    })

    capital_l.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(p0: CompoundButton, check: Boolean) {
            if (check) {
                total.add(mayusculas_l)
            }else {
                total.remove(mayusculas_l)
            }
            gen(size)
        }

    })
    number.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(p0: CompoundButton, check: Boolean) {
            if (check) {
                total.add(numeros_l)
            }else {
                total.remove(numeros_l)
            }
            gen(size)
        }
    })
    simbol.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(p0: CompoundButton, check: Boolean) {
            if (check) {
                total.add(simbolos_l)
            }else {
                total.remove(simbolos_l)
            }
            gen(size)
        }
    })

    refresh.setOnClickListener {
        val anim_rotate = AnimationUtils.loadAnimation(context, R.anim.rotate)

        anim_rotate.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationEnd(p0: Animation?) {
                refresh.isEnabled = true
            }

            override fun onAnimationRepeat(p0: Animation?) {}

            override fun onAnimationStart(p0: Animation?) {
                refresh.isEnabled = false
                gen(size)
            }

        })

        icon_refresh.startAnimation(anim_rotate)
    }

    copy.setOnClickListener {
        val manage = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        manage.setPrimaryClip(ClipData.newPlainText("pass", result_pass.text.toString()))

        if (pass_input != null) {
            pass_input.setText(result_pass.text.toString())
            pass_input.setSelection(pass_input.text.length)
            gene_dilaog.dismiss()
        }
    }
}

fun email_generator (context: Activity, pref: SharedPreferences, info_image: ShapeableImageView) {

    val dialog_email = BottomSheetDialog(context)
    val email_view = create_dialog(context, R.layout.email_generator_dialog, dialog_email)

    val pass_intent = email_view.findViewById<ConstraintLayout>(R.id.gen_pass_intent)

    val recy_emails = email_view.findViewById<RecyclerView>(R.id.recy_mails)

    val input_name = email_view.findViewById<EditText>(R.id.input_name)
    val input_date = email_view.findViewById<EditText>(R.id.input_date)
    val input_domain = email_view.findViewById<EditText>(R.id.input_domain)

    val but_generator = email_view.findViewById<ShapeableImageView>(R.id.generator)


    val mail_adapter = adapter_global(listOf(
        project_class.multi_data("alex-1998@hotmail.com"),
        project_class.multi_data("alex1998@hotmail.com"),
        project_class.multi_data("1998alex@hotmail.com"),
        project_class.multi_data("alex.1998@hotmail.com"),
        project_class.multi_data("1998.alex@hotmail.com"),
        project_class.multi_data("alex/1998@hotmail.com"),
        project_class.multi_data("1998/alex@hotmail.com"),
        project_class.multi_data("alex19981998@hotmail.com"),
        project_class.multi_data("19981998alex@hotmail.com"),
        project_class.multi_data("1998.alex@hotmail.com")),
        6, holders_datas.multi_holder_data({ data ->
            (data as project_class.multi_data)

            (context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
                ClipData.newPlainText("mail", data.data)
            )

        }, {})
    )
    recy_emails.adapter = mail_adapter
    recy_emails.layoutManager = LinearLayoutManager(context)

    pass_intent.setOnClickListener {
        pref.edit().putBoolean("gene", true).commit()
        dialog_email.dismiss()
        info_image.setImageResource(R.drawable.generator)
        pass_generator_dialog(context, pref, info_image)
    }



    but_generator.setOnClickListener {
        if (Regex("@.+\\..+").matches(input_domain.text.toString()) && input_name.text.isNotEmpty() && input_date.text.isNotEmpty()) {

            val (load_dialog, info_dialog) = load("Generating the emails", context)

            val name = input_name.text.toString()
            val date = input_date.text.toString()
            val domain = input_domain.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                val emails_list = listOf(
                    project_class.multi_data("$name-$date$domain"),
                    project_class.multi_data("$name$date$domain"),
                    project_class.multi_data("$date$name$domain"),
                    project_class.multi_data("$name.$date$domain"),
                    project_class.multi_data("$date.$name$domain"),
                    project_class.multi_data("$name/$date$domain"),
                    project_class.multi_data("$date/$name$domain"),
                    project_class.multi_data("$name${date}${date}$domain"),
                    project_class.multi_data("$date${date}$name$domain"),
                    project_class.multi_data("$date.$name$domain")
                )

                withContext(Dispatchers.Main) {
                    mail_adapter.update(emails_list)
                    load_dialog.dismiss()
                }
            }
        }else {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}