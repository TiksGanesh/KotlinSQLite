package com.example.ganeshtikone.kotlinsqlite.adapter

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ganeshtikone.kotlinsqlite.R
import com.example.ganeshtikone.kotlinsqlite.model.UserModel


/**
 * Created by ganeshtikone on 13/11/17.
 * User Adapter Class
 */
class UserAdapter(mContext: Context) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val context: Context = mContext
    private var userList: MutableList<UserModel> = mutableListOf()


    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UserViewHolder {
        val itemView: View = LayoutInflater
                .from(context)
                .inflate(R.layout.adapter_user,
                        parent,
                        false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder?, position: Int) {
        val user: UserModel = userList[position]
        holder?.textViewUserMobile?.text = user.mobile
        holder?.textViewUserName?.text = user.name
    }

    fun updateData(data: MutableList<UserModel>) {
        userList = data
        notifyDataSetChanged()
    }



    /**
     * ViewHolder class for RecyclerView
     */
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewUserName: AppCompatTextView = itemView.findViewById(R.id.textViewUserName)
        val textViewUserMobile: AppCompatTextView = itemView.findViewById(R.id.textViewUserMobile)
    }
}