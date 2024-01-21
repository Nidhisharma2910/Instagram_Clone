package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Models.Reel
import com.example.myapplication.R
import com.example.myapplication.databinding.ReelDgBinding
import com.squareup.picasso.Picasso

class ReelAdapter (var context: Context, var reelList: ArrayList<Reel>) : RecyclerView.Adapter<ReelAdapter.viewHolder>() {
    inner class viewHolder(var binding: ReelDgBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var binding = ReelDgBinding.inflate(LayoutInflater.from(context), parent, false)
        return viewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
     Picasso.get().load(reelList.get(position).profileLink).placeholder(R.drawable.profile).into(holder.binding.profileImage)
        holder.binding.caption.setText(reelList.get(position).caption)
        holder.binding.videoView.setVideoPath(reelList.get(position).reelUrl)
        holder.binding.videoView.setOnPreparedListener{
            holder.binding.progressBar.visibility=View.GONE
          holder.binding.videoView.start()
        }
    }
}
