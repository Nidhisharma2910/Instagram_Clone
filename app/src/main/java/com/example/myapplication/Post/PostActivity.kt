package com.example.myapplication.Post

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.HomeActivity
import com.example.myapplication.Models.Post
import com.example.myapplication.Models.User
import com.example.myapplication.databinding.ActivityPostBinding
import com.example.myapplication.utils.POST
import com.example.myapplication.utils.POST_FOLDER
import com.example.myapplication.utils.USER_NODE
import com.example.myapplication.utils.uploadImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding
    private var imageUrl: String? = null

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(it, POST_FOLDER) { url ->
                binding.selectImage.setImageURI(uri)
                imageUrl = url
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
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        binding.postButton.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser?.uid ?: "")
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val user = documentSnapshot.toObject<User>()
                    if (user != null) {
                        val post = Post(
                            postUrl = imageUrl.orEmpty(),
                            caption = binding.caption.editText?.text.toString(),
                            name = user.image.orEmpty(),
                            time = System.currentTimeMillis().toString()
                        )

                        Firebase.firestore.collection(POST).document().set(post)
                            .addOnSuccessListener {
                                Firebase.firestore.collection(Firebase.auth.currentUser?.uid ?: "").document()
                                    .set(post)
                                    .addOnSuccessListener {
                                        startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                                        finish()
                                    }
                            }
                    }
                }
        }
    }
}
