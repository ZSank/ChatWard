package com.example.letschat.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.letschat.MainActivity
import com.example.letschat.R
import com.example.letschat.databinding.ActivityNumberBinding
import com.google.firebase.auth.FirebaseAuth

class NumberActivity : AppCompatActivity() {
	private lateinit var binding: ActivityNumberBinding
	private lateinit var auth: FirebaseAuth
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_number)

		auth = FirebaseAuth.getInstance()

		if (auth.currentUser != null) {
			startActivity(Intent(this, MainActivity::class.java))
			finish()
		}

		binding.button.setOnClickListener {
			if (binding.phoneNumber.text!!.isEmpty()) {
				Toast.makeText(this, "Please enter your Number!!", Toast.LENGTH_SHORT).show()
			} else {
				val intent = Intent(this, OTPActivity::class.java)
				intent.putExtra("number", binding.phoneNumber.text!!.toString())
				startActivity(intent)
				finish() //TODO: Extra code
			}
		}
	}
}