package com.example.ganeshtikone.kotlinsqlite.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.ganeshtikone.kotlinsqlite.model.UserModel

/**
 * Created by ganeshtikone on 13/11/17.
 * DatabaseManager class to manage application database
 */
class DatabaseManager(mContext: Context) : SQLiteOpenHelper(mContext, DB_NAME, null, DB_VERSION) {

    companion object {
        val DB_NAME = "KotlinSQLite"
        val DB_VERSION = 1
    }


    override fun onCreate(database: SQLiteDatabase?) {
        database!!.execSQL(DatabaseContract.User.createTableQuery())
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        database!!.execSQL(DatabaseContract.User.dropTableQuery())
        database.execSQL(DatabaseContract.User.createTableQuery())
    }

    /**
     * Insert user record
     * @param value
     *          Content Value Object
     * @return Long
     *          last inserted row id if success
     *          else -1
     */
    fun insertUser(value: ContentValues): Long {

        val insertedID = readableDatabase.insert(DatabaseContract.User.TABLE_NAME,
                null,
                value)

        readableDatabase.close()
        return insertedID
    }

    /**
     * Delete user record using user id
     * @param userId
     *          _id created after user record insert
     *
     * @return Boolean
     *          true if deleted user record success
     *          else false
     */
    fun deleteUser(userId: Long): Boolean {
        val affectedRows = writableDatabase.delete(
                DatabaseContract.User.TABLE_NAME,
                " _id = ? ",
                Array<String>(1, { userId.toString() })
        )
        writableDatabase.close()
        return affectedRows > 0
    }

    /**
     * Delete user record by passing mobile number
     * @param mobile
     *          User mobile number
     * @return Boolean
     *          true if deleted user record success
     *          else false
     */
    fun deleteUser(mobile: String): Boolean {
        val affectedRows = writableDatabase.delete(
                DatabaseContract.User.TABLE_NAME,
                " ${DatabaseContract.User.COL_MOBILE} = ? ",
                Array<String>(1, { mobile })
        )
        writableDatabase.close()
        return affectedRows > 0
    }

    /**
     * Get all user records from database
     * @return MutableList<UserModel>
     */
    fun getAllUser(): MutableList<UserModel> {

        val cursor: Cursor = readableDatabase.query(
                DatabaseContract.User.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        )

        val userList: MutableList<UserModel> = mutableListOf()

        if (cursor.moveToFirst()) {
            do {

                val name = cursor.getString(cursor.getColumnIndex(DatabaseContract.User.COL_NAME))
                val mobile = cursor.getString(cursor.getColumnIndex(DatabaseContract.User.COL_MOBILE))
                userList.add(UserModel(name, mobile, date = null))

            } while (cursor.moveToNext())
        }

        readableDatabase.close()
        cursor.close()

        return userList
    }

}