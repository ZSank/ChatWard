package com.example.letschat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.letschat.R
import com.example.letschat.adapter.ChatAdapter
import com.example.letschat.databinding.FragmentChatBinding
import com.example.letschat.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ChatFragment : Fragment() {
	private lateinit var binding: FragmentChatBinding
	private var database: FirebaseDatabase? = null
	lateinit var userList: ArrayList<UserModel>
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)

		database = FirebaseDatabase.getInstance()
		userList = ArrayList()

		database!!.reference.child("users")
			.addValueEventListener(object : ValueEventListener {
				override fun onDataChange(snapshot: DataSnapshot) {
					userList.clear()

					for (snapshot1 in snapshot.children) {
						val user = snapshot1.getValue(UserModel::class.java)
						if (user!!.uid != FirebaseAuth.getInstance().uid) {
							userList.add(user)
						}
					}

					binding.userListRecyclerView.adapter = ChatAdapter(requireContext(), userList)
				}

				override fun onCancelled(error: DatabaseError) {
					TODO("Not yet implemented")
				}
			})


		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)


	}
}