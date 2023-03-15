package com.example.letschat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.letschat.activity.NumberActivity
import com.example.letschat.adapter.ViewPagerAdapter
import com.example.letschat.databinding.ActivityMainBinding
import com.example.letschat.ui.CallFragment
import com.example.letschat.ui.ChatFragment
import com.example.letschat.ui.StatusFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding
	private lateinit var auth: FirebaseAuth
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding= DataBindingUtil.setContentView(this, R.layout.activity_main)

		val fragmentArrayList = ArrayList<Fragment>()

		fragmentArrayList.add(ChatFragment())
		fragmentArrayList.add(StatusFragment())
		fragmentArrayList.add(CallFragment())


		auth = FirebaseAuth.getInstance()

		if (auth.currentUser == null){
			startActivity(Intent(this, NumberActivity::class.java))
			finish()
		}

		val adapter = ViewPagerAdapter(this, supportFragmentManager,fragmentArrayList)

		binding.viewPager.adapter = adapter

		binding.tabs.setupWithViewPager(binding.viewPager)


	}
}























