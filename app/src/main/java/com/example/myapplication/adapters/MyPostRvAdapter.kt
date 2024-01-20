package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Models.Post
import com.example.myapplication.databinding.MyPostRvDesignBinding
import com.squareup.picasso.Picasso

class MyPostRvAdapter (var context:Context, var postList:ArrayList<Post>) :RecyclerView.Adapter<MyPostRvAdapter.ViewHolder>(){

    inner class ViewHolder(var binding: MyPostRvDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding=MyPostRvDesignBinding.inflate(LayoutInflater.from(context),parent,false)
         return ViewHolder(binding)
     }

     override fun getItemCount(): Int {
        return postList.size
     }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = postList[position]
        val imageUrl = post.postUrl // Replace 'imageUrl' with the actual attribute in your 'Post' class
        Picasso.get().load(imageUrl).into(holder.binding.postImage)
    }

}






