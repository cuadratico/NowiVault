package com.nothingsecure.recy_information

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nothingsecure.R


sealed class project_class {
    data class register (val id: Int, var time: String, val color: String, val regi_all: String): project_class()
    data class pass (val id: Int, var alias: String, var pass_all: String): project_class()
    data class multi_data (val data: String): project_class()
    data class icon_info (val icon: Int, val info: String, val info_material: String = ""): project_class()
    data class conf_data (val icon: Int, val info: String, val info_material: String, val id: Int): project_class()
    data class opor_data (val icon: Int): project_class()
}

abstract class holder_global (view: View): RecyclerView.ViewHolder(view) {
    abstract fun init (data: project_class, click: (project_class) -> Unit, extra_click: (project_class) -> Unit)
}

class adapter_global (var list: List<project_class>, val holder_indi: Int, val click: (project_class) -> Unit, val extra_click: (project_class) -> Unit): RecyclerView.Adapter<holder_global>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holder_global {
        val l_i = LayoutInflater.from(parent.context)
        return when (holder_indi) {
            1 -> pass_holder(l_i.inflate(R.layout.recy_password, null))
            2 -> register_holder(l_i.inflate(R.layout.recy_history, null))
            3 -> modes_holder(l_i.inflate(R.layout.recy_modes, null))
            4 -> color_holder(l_i.inflate(R.layout.recy_color, null))
            5 -> conf_holder(l_i.inflate(R.layout.configuration_recy, null))
            6 -> email_holder(l_i.inflate(R.layout.recy_email, null))
            else -> opor_holder(l_i.inflate(R.layout.keys_opor_recy, null))
        }
    }

    override fun onBindViewHolder(holder: holder_global, position: Int) = holder.init(list[position], click, extra_click)


    override fun getItemCount(): Int = list.size

    fun update (new_list: List<project_class>) {
        val diff = DiffUtil.calculateDiff(diffUi_global(list, new_list))
        this.list = new_list
        diff.dispatchUpdatesTo(this)
    }

}

class diffUi_global(val old_list: List<project_class>, val new_list: List<project_class>): DiffUtil.Callback() {

    override fun getOldListSize(): Int = old_list.size

    override fun getNewListSize(): Int = new_list.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old_list[oldItemPosition] == new_list[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old_list[oldItemPosition] == new_list[newItemPosition]
    }

}
