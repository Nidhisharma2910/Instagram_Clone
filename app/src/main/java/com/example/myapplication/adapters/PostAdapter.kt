package com.example.myapplication.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Models.Post
import com.example.myapplication.Models.User
import com.example.myapplication.R
import com.example.myapplication.databinding.PostRvBinding
import com.example.myapplication.utils.USER_NODE
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class PostAdapter(private val context: Context, private val postList: ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.MyHolder>() {

    inner class MyHolder(val binding: PostRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = PostRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            Firebase.firestore.collection(USER_NODE).document(postList[position].name).get()
                .addOnSuccessListener { documentSnapshot ->
                    val user = documentSnapshot.toObject<User>()
                    if (user != null) {
                        Glide.with(context).load(user.image).placeholder(R.drawable.profile).into(holder.binding.postImage)
                        holder.binding.name.text = user.name
                    } else {
                        // Handle the case where user is null (optional)
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle the failure to fetch user data if needed
                }
        } catch (e: Exception) {
            // Handle exceptions if needed
        }

        Glide.with(context).load(postList[position].postUrl).placeholder(R.drawable.loading).into(holder.binding.postImage)
        holder.binding.time.text = postList[position].time

        holder.binding.share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, postList[position].postUrl)
            context.startActivity(intent)
        }

        holder.binding.caption.text = postList[position].caption

        holder.binding.like.setOnClickListener {
            holder.binding.like.setImageResource(R.drawable.heartc)
        }
    }
}
