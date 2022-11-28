package com.example.todoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.todoapp.databinding.LayoutCustomViewBinding

class MyRecyclerViewAdapter(
    private val Student: List<PersonalData>,
    param: OnItemClickListener
) : Adapter<MyRecyclerViewAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(layoutCustomViewBinding: LayoutCustomViewBinding) :
        RecyclerView.ViewHolder(layoutCustomViewBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var layoutCustomViewBinding: LayoutCustomViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_custom_view,
            parent,
            false
        )
        return CustomViewHolder(layoutCustomViewBinding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val personal = Student[position]
        holder.layoutCustomViewBinding.tvtitle.text = personal.TiTle
        holder.layoutCustomViewBinding.tvdescription.text = personal.Description
        holder.itemView.setOnClickListener {
            val onItemClickListener = null
            onItemClickListener.onItemClick(personal, position)
        }


    }

    override fun getItemCount(): Int {

        return Student.size
    }
}