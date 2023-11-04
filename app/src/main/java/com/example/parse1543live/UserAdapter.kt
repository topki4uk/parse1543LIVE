package com.example.parse1543live

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import serealize.LessonInfoObject

class UserAdapter(private val lessonInfo: List<LessonInfoObject>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = lessonInfo[position]
        holder.textView.text = "${info.number} ${info.name} ${info.room}"

    }

    override fun getItemCount(): Int {
        return lessonInfo.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textInfoView)
    }
}