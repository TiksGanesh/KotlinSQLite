package com.example.ganeshtikone.kotlinsqlite.database

import android.provider.BaseColumns
import android.provider.BaseColumns._ID

/**
 * Created by ganeshtikone on 13/11/17.
 * Database Schema Class - Contract
 */
class DatabaseContract private constructor() {

    class User private constructor() : BaseColumns {

        companion object {

            val TABLE_NAME = "user"

            val COL_NAME = "user_name"
            val COL_MOBILE = "user_mobile"
            val COL_CREATED_DATE = "created_date"


            fun createTableQuery(): String {
                return " CREATE TABLE ${TABLE_NAME} ( " +
                        "${_ID} INTEGER PRIMARY KEY AUTOINCREMENT ," +
                        "${COL_NAME} TEXT NOT NULL ," +
                        "${COL_MOBILE} TEXT NOT NULL ," +
                        "${COL_CREATED_DATE} TEXT NOT NULL " +
                        " ); "
            }

            fun dropTableQuery(): String {
                return " DROP TABLE ${TABLE_NAME} IF EXIST "
            }
        }


    }
}