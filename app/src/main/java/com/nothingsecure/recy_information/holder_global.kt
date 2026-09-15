package com.nothingsecure.recy_information

import android.content.res.ColorStateList
import android.graphics.drawable.shapes.Shape
import android.icu.text.IDNA
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.toColorInt
import com.google.android.material.imageview.ShapeableImageView
import com.nothingsecure.R
import com.nothingsecure.configuration_tools.icons_list


// continuar con la realizacion de los holders (poco a poco, en base al momento que los necesite)
class pass_holder(view: View): holder_global(view){

    val all_cl = view.findViewById<ConstraintLayout>(R.id.all)
    val alias = view.findViewById<TextView>(R.id.title)
    val edit_b = view.findViewById<ShapeableImageView>(R.id.edit)
    val delete_b = view.findViewById<ShapeableImageView>(R.id.delete)

    override fun init(data: project_class, lambda: holders_datas?) {
        (lambda as holders_datas.pass_holder_data)
        (data as project_class.pass)


        alias.text = data.information

        all_cl.setOnLongClickListener {
            lambda.all_click(data)
            true
        }
        edit_b.setOnClickListener {
            lambda.edit_pass(data)
        }
        delete_b.setOnClickListener {
            lambda.delete_pass(data)
        }

    }
}

class register_holder (view: View): holder_global(view) {

    val time = view.findViewById<TextView>(R.id.time)
    val info = view.findViewById<TextView>(R.id.information_history)
    val color_info = view.findViewById<View>(R.id.color_information)

    val delete = view.findViewById<ShapeableImageView>(R.id.delete)

    override fun init(data: project_class, lambda: holders_datas?) {
        (data as project_class.register)
        (lambda as holders_datas.register_holer_data)

        time.text = data.time
        info.text = data.information
        color_info.backgroundTintList = ColorStateList.valueOf(data.color.toColorInt())

        delete.setOnClickListener {
            lambda.delete_call_back(data)
        }
    }
}

class modes_holder (view: View): holder_global(view) {

    val back = view.findViewById<View>(R.id.back)
    val icon_mode = view.findViewById<ShapeableImageView>(R.id.icon)
    val text_mode = view.findViewById<TextView>(R.id.text_type)

    override fun init(data: project_class, lambda: holders_datas?) {
        (data as project_class.icon_info)
        (lambda as holders_datas.multi_holder_data)

        icon_mode.setImageResource(data.icon)
        text_mode.text = data.info

        back.setOnClickListener {

            lambda.click(project_class.multi_data(icons_list.indexOf(data).toString()))

        }

    }
}

class color_holder (view: View): holder_global(view) {

    val back = view.findViewById<View>(R.id.back)
    val color_expre = view.findViewById<ConstraintLayout>(R.id.back_color_expressed)
    val text_color = view.findViewById<TextView>(R.id.color_code_output)

    override fun init(data: project_class, lambda: holders_datas?) {
        (data as project_class.multi_data)
        (lambda as holders_datas.multi_holder_data)

        color_expre.backgroundTintList = ColorStateList.valueOf(data.data.toColorInt())
        text_color.text = data.data

        back.setOnClickListener {
            lambda.click(data)
        }

        back.setOnLongClickListener {
            lambda.long_click(data)
            true
        }
    }
}

class conf_holder (view: View): holder_global(view) {

    val back = view.findViewById<ConstraintLayout>(R.id.back_confi)

    val icon = view.findViewById<ShapeableImageView>(R.id.icon_info)
    val name = view.findViewById<TextView>(R.id.text_info)
    val info = view.findViewById<ShapeableImageView>(R.id.info_but)

    override fun init(data: project_class, lambda: holders_datas?) {
        (data as project_class.conf_data)
        (lambda as holders_datas.multi_holder_data)

        icon.setImageResource(data.icon)
        name.text = data.info



        info.setOnClickListener {
            lambda.long_click(data)
        }

        back.setOnClickListener {
            lambda.click(data)
        }
    }

}

class email_holder (view: View): holder_global(view) {

    val all = view.findViewById<ConstraintLayout>(R.id.all)
    val email = view.findViewById<TextView>(R.id.mail)

    override fun init(data: project_class, lambda: holders_datas?) {
        (data as project_class.multi_data)

        email.text = data.data

        all.setOnClickListener {
            (lambda as holders_datas.multi_holder_data).click(data)
        }

    }
}

