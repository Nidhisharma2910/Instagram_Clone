package com.example.myapplication.Post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.Models.Post
import com.example.myapplication.databinding.ActivityPostBinding
import com.example.myapplication.utils.POST
import com.example.myapplication.utils.POST_FOLDER
import com.example.myapplication.utils.USER_PROFILE_FOLDER
import com.example.myapplication.utils.uploadImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding
    var imageUrl: String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {

            uploadImage(uri, POST_FOLDER) { url ->
                if (it != null) {
                    binding.selectImage.setImageURI(uri)
                    imageUrl=url
                }

            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.postButton.setOnClickListener {
            var post: Post = Post(imageUrl!!,binding.caption.editText?.text.toString())

            Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post).addOnSuccessListener{
                    finish()
                }

            }
        }
    }

}
