package com.example.myapplication.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Models.Post
import com.example.myapplication.Models.Reel
import com.example.myapplication.R
import com.example.myapplication.adapters.PostAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.utils.POST
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    var postList=ArrayList<Post>()
    private lateinit var adapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter= PostAdapter(requireContext(),postList)
        binding.postRv.layoutManager=LinearLayoutManager(requireContext())
        binding.postRv.adapter=adapter
        setHasOptionsMenu(true)

        // Assuming you have a toolbar in your layout file with id materialToolbar2
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar2)

        // Initialize your postList and adapter here if needed

        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            var tempList=ArrayList<Post>()
            postList.clear()
            for(i in it.documents){
                var post:Post=i.toObject<Post>()!!
                tempList.add(post)
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        // Add any companion object methods if needed
    }
}
