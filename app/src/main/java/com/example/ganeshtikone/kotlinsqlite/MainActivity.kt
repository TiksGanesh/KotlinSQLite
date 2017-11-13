package com.example.ganeshtikone.kotlinsqlite

import android.content.ContentValues
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.LinearLayoutManager
import com.example.ganeshtikone.kotlinsqlite.adapter.UserAdapter
import com.example.ganeshtikone.kotlinsqlite.database.DatabaseContract
import com.example.ganeshtikone.kotlinsqlite.database.DatabaseManager
import com.example.ganeshtikone.kotlinsqlite.model.UserModel
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter

    companion object {
        var isActivityVisible: Boolean = false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUserRecyclerView()
        setOnClickListener()
    }

    private fun setOnClickListener() {

        floatingActionButtonAdd.setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {

        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.alert_dialog_add_user,null)
        builder.setView(dialogView)
        builder.setTitle("Add User")

        val buttonCancel:AppCompatButton = dialogView.findViewById(R.id.ButtonCancel)
        val buttonAdd = dialogView.findViewById<AppCompatButton>(R.id.ButtonAddUser)
        val editTextUserName = dialogView.findViewById<AppCompatEditText>(R.id.alertEditTextUserName)
        val editTextUserMobile = dialogView.findViewById<AppCompatEditText>(R.id.alertEditTextUserMobile)


        val dialog = builder.create()
        dialog.show()


        buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        buttonAdd.setOnClickListener {

            val name = editTextUserName.text.toString().trim()
            val mobile = editTextUserMobile.text.toString().trim()
            addUser(name,mobile)
            dialog.dismiss()

        }


    }

    private fun addUser(name: String, mobile: String) {
        if (name.isNotEmpty() && mobile.isNotEmpty()){

            val manager = DatabaseManager(this)
            val values = ContentValues()
            val currentTime = "${System.currentTimeMillis()}"


            values.put(DatabaseContract.User.COL_NAME,name)
            values.put(DatabaseContract.User.COL_MOBILE,mobile)
            values.put(DatabaseContract.User.COL_CREATED_DATE,currentTime)

            manager.insertUser(values)

            LoadUserTask(this).execute()

        }
    }

    private fun setupUserRecyclerView() {

        adapter = UserAdapter(this)

        recyclerViewUser.setHasFixedSize(true)
        recyclerViewUser.layoutManager = LinearLayoutManager(this)
        recyclerViewUser.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        isActivityVisible = true
        LoadUserTask(this).execute()
    }

    override fun onStop() {
        super.onStop()
        isActivityVisible = false
    }


    inner class LoadUserTask(mContext: Context) : AsyncTask<Void, Void, MutableList<UserModel>>() {

        private val context: Context = mContext

        override fun doInBackground(vararg p0: Void?): MutableList<UserModel> {
            val dbManager = DatabaseManager(context)
            return dbManager.getAllUser()
        }

        override fun onPostExecute(result: MutableList<UserModel>?) {
            super.onPostExecute(result)
            if (isActivityVisible) {
                adapter.updateData(result!!)
            }
        }
    }
}
