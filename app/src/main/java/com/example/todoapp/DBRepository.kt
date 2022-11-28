package com.example.todoapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class DBRepository(private var context: MainActivity) {

    private val DB_Name: String = "TODO_Name"
    private val TABLE_NAME: String = "tbName"
    private val TODO_VERSION: Int = 1

    private val SR_NO: String = "SRNO"
    private val TITLE: String = "TiTle"
    private val DESCRIPTION: String = "Description"
    private val DATE_TIME: String = "DateTime"
    var query: String =
        buildString {
            append("CREATE TABLE ")
            append(TABLE_NAME)
            append(" (")
            append(SR_NO)
            append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
            append(TITLE)
            append(" TEXT,")
            append(DESCRIPTION)
            append(" TEXT, ")
            append(DATE_TIME)
            append(" TEXT)")
        }

    private var myTodoHelper = MyTodoHelper(context)
    private var sqLiteDB: SQLiteDatabase = myTodoHelper.writableDatabase
    fun createData(TiTle: String, Description: String, DateTime: String) {
        val contentValues = ContentValues()
        contentValues.put(TITLE, TiTle)
        contentValues.put(DESCRIPTION, Description)
        contentValues.put(DATE_TIME, DateTime)
        var id: Long = sqLiteDB.insert(TABLE_NAME, null, contentValues)
        if (id > 0) {
            Toast.makeText(context, "Record Successfully inserted", Toast.LENGTH_SHORT)
        } else {
            Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT)
        }

    }

    fun getPersonalData(): List<PersonalData> {
        var personalData: MutableList<PersonalData> = ArrayList()
        val collist = arrayOf(TITLE, DESCRIPTION, DATE_TIME)
        val cursor: Cursor = sqLiteDB.query(TABLE_NAME, collist, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val TiTle: String = cursor.getString(0)
                val Description: String = cursor.getString(1)
                val DateTime: String = cursor.getString(2)
                var yourdata = PersonalData(TiTle, Description, DateTime)
                personalData.add(yourdata)

            } while (cursor.moveToNext())
        }
//        else{
//            Toast.makeText(, "No Data Found", Toast.LENGTH_SHORT).show()
//        }
        return personalData
    }

    fun updateData(TiTle: String, Description: String, DateTime: String) {
        val contentValues = ContentValues()
        contentValues.put(TITLE, TiTle)
        contentValues.put(DESCRIPTION, Description)
        contentValues.put(DATE_TIME, DateTime)
        var id: Int = sqLiteDB.update(TABLE_NAME, contentValues, TITLE + "=" + TiTle, null)
        if (id > 0) {
            Toast.makeText(context, "Record updated..", Toast.LENGTH_SHORT)
        } else {
            Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT)
        }
        var id2: Int = sqLiteDB.update(TABLE_NAME, contentValues, "$DESCRIPTION=$Description", null)
        if (id2 > 0) {
            Toast.makeText(context, "Record updated..", Toast.LENGTH_SHORT)
        } else {
            Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT)
        }
    }

    fun deleteData(TiTle: String, Description: String, DateTime: String) {
        val contentValues = ContentValues()
        contentValues.put(TITLE, TiTle)
        contentValues.put(DESCRIPTION, Description)
        contentValues.put(DATE_TIME, DateTime)
        var id: Int = sqLiteDB.delete(TABLE_NAME, TITLE + "=" + TiTle, null)
        if (id > 0) {
            Toast.makeText(context, "deleted", Toast.LENGTH_SHORT)
        } else {
            Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT)
        }
        var id2: Int = sqLiteDB.update(TABLE_NAME, contentValues, "$DESCRIPTION=$Description", null)
        if (id2 > 0) {
            Toast.makeText(context, "deleted..", Toast.LENGTH_SHORT)
        } else {
            Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT)
        }
    }

    fun deleteAll() {
        var total = sqLiteDB.delete(TABLE_NAME, null, null)
        if (total > 0) {
            Toast.makeText(context, "record deleted...", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "something went wrong...", Toast.LENGTH_SHORT).show()
        }
    }

    fun dateTime(): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh-mm-ss-aa", Locale.getDefault())
        val date = Date()
        return simpleDateFormat.format(date)
    }


    inner class MyTodoHelper(private val context: Context) :
        SQLiteOpenHelper(context, DB_Name, null, 1) {
        override fun onCreate(sQliteDatabase: SQLiteDatabase?) {
            sQliteDatabase?.execSQL(query)

        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            TODO("Not yet implemented")
        }
    }
}

