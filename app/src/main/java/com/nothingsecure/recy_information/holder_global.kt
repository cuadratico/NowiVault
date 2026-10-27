package com.nothingsecure.recy_information

import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.toColorInt
import com.google.android.material.imageview.ShapeableImageView
import com.nothingsecure.R
import com.nothingsecure.configuration_tools.icons_list


class pass_holder(view: View): holder_global(view){

    val all_cl = view.findViewById<ConstraintLayout>(R.id.all)
    val alias = view.findViewById<TextView>(R.id.title)

    override fun init(data: project_class, click: (project_class) -> Unit, extra_click: (project_class) -> Unit) {
        (data as project_class.pass)
        alias.text = data.alias

        all_cl.setOnClickListener {
            click(data)
        }

        all_cl.setOnLongClickListener {
            extra_click(data)
            true
        }

    }
}

class register_holder (view: View): holder_global(view) {

    val all = view.findViewById<ConstraintLayout>(R.id.all)
    val time = view.findViewById<TextView>(R.id.time)
    val color_info = view.findViewById<View>(R.id.color_information)

    val delete = view.findViewById<ShapeableImageView>(R.id.delete)

    override fun init(data: project_class, click: (project_class) -> Unit, extra_click: (project_class) -> Unit) {
        (data as project_class.register)

        time.text = data.time
        color_info.backgroundTintList = ColorStateList.valueOf(data.color.toColorInt())

        all.setOnClickListener {
            click(data)
        }

        delete.setOnClickListener {
            extra_click(data)
        }

    }
}

class modes_holder (view: View): holder_global(view) {

    val back = view.findViewById<View>(R.id.back)
    val icon_mode = view.findViewById<ShapeableImageView>(R.id.icon)
    val text_mode = view.findViewById<TextView>(R.id.text_type)

    override fun init(data: project_class, click: (project_class) -> Unit, extra_click: (project_class) -> Unit) {
        (data as project_class.icon_info)

        icon_mode.setImageResource(data.icon)
        text_mode.text = data.info

        back.setOnClickListener {

            click(project_class.multi_data(icons_list.indexOf(data).toString()))

        }

    }
}

class color_holder (view: View): holder_global(view) {

    val back = view.findViewById<View>(R.id.back)
    val color_expre = view.findViewById<ConstraintLayout>(R.id.back_color_expressed)
    val text_color = view.findViewById<TextView>(R.id.color_code_output)

    override fun init(data: project_class, click: (project_class) -> Unit, extra_click: (project_class) -> Unit) {
        (data as project_class.multi_data)

        color_expre.backgroundTintList = ColorStateList.valueOf(data.data.toColorInt())
        text_color.text = data.data

        back.setOnClickListener {
            click(data)
        }

        back.setOnLongClickListener {
            extra_click(data)
            true
        }
    }
}

class conf_holder (view: View): holder_global(view) {

    val back = view.findViewById<ConstraintLayout>(R.id.back_confi)

    val icon = view.findViewById<ShapeableImageView>(R.id.icon_info)
    val name = view.findViewById<TextView>(R.id.text_info)
    val info = view.findViewById<ShapeableImageView>(R.id.info_but)

    override fun init(data: project_class, click: (project_class) -> Unit, extra_click: (project_class) -> Unit) {
        (data as project_class.conf_data)

        icon.setImageResource(data.icon)
        name.text = data.info

        back.setOnClickListener {
            click(data)
        }

        info.setOnClickListener {
            extra_click(data)
        }


    }

}

class email_holder (view: View): holder_global(view) {

    val all = view.findViewById<ConstraintLayout>(R.id.all)
    val email = view.findViewById<TextView>(R.id.mail)

    override fun init(data: project_class, click: (project_class) -> Unit, extra_click: (project_class) -> Unit) {
        (data as project_class.multi_data)

        email.text = data.data

        all.setOnClickListener {
            click(data)
        }

    }
}

class opor_holder (view: View): holder_global(view) {
    val icon = view.findViewById<ShapeableImageView>(R.id.icon)

    override fun init(data: project_class, click: (project_class) -> Unit, extra_click: (project_class) -> Unit) {
        (data as project_class.opor_data)

        icon.setImageResource(data.icon)
    }

}

