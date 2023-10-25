package com.stas.picker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File



//class FilePickerAdapter(private val fileTypes: List<FileType>) : RecyclerView.Adapter<FilePickerAdapter.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.item_file, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val fileType = fileTypes[position]
//        holder.bind(fileType)
//    }
//
//    override fun getItemCount(): Int {
//        return fileTypes.size
//    }
//
//    inner class ViewHolder(private val binding: OLOLO) : RecyclerView.ViewHolder(binding) {
//        private val fileTypeIcon: ImageView = itemView.findViewById(R.id.fileTypeIcon)
//        private val fileTypeLabel: TextView = itemView.findViewById(R.id.fileTypeLabel)
//
//        fun bind(fileType: FileType) {
//            fileTypeIcon.setImageResource(fileType.iconResourceId)
//            fileTypeLabel.text = fileType.extension
//        }
//    }
//}
