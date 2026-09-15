package com.nothingsecure

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


data class data_extract_list (val id: Int, val data: String, val iv: String)

class db (context: Context): SQLiteOpenHelper(context, "information.db", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE info_r (id INTEGER PRIMARY KEY AUTOINCREMENT, global_time TEXT, iv TEXT)")
        db?.execSQL("CREATE TABLE info_s (id INTEGER PRIMARY KEY AUTOINCREMENT, global_pass TEXT, iv TEXT)")
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun insert (pref: SharedPreferences, table_name: String, values: ContentValues): Long {
        val db = this.writableDatabase
        pref.edit().putBoolean(table_name + "_full", true).commit()

        return db.insert(table_name, null, values)
    }

    fun update (table_name: String, values: ContentValues, id: String) {
        val db = this.writableDatabase

        db.update(table_name, values, "id = ?", arrayOf(id))
    }

    fun delete (table_name: String, argument: String? = null, values: Array<String>? = null) {
        val db = this.writableDatabase

        db.delete(table_name, argument, values)
    }

    fun select (table_name: String): List<data_extract_list> {

        val db = this.readableDatabase
        val query = db.query(table_name, null, null, null, null, null, null)

        var extract_list = listOf<data_extract_list>()

        fun add() {
            extract_list = extract_list.plus(
                data_extract_list(
                    query.getInt(0),
                    query.getString(1),
                    query.getString(2)
                )
            )
        }

        if (query.moveToFirst()) {
            add()
            while (query.moveToNext()) {
                add()
            }
        }
        return extract_list
    }

}