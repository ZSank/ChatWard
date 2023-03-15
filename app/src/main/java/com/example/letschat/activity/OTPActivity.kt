package com.example.letschat.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.letschat.R
import com.example.letschat.databinding.ActivityOtpactivityBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

private const val TAG = "auth"
class OTPActivity : AppCompatActivity() {
	private lateinit var binding: ActivityOtpactivityBinding
	private lateinit var auth: FirebaseAuth
	private lateinit var verificationId: String
	private lateinit var dialog: AlertDialog

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = DataBindingUtil.setContentView(this, R.layout.activity_otpactivity)

//		verificationId = "120"
		auth = FirebaseAuth.getInstance()

		val phoneNumber = "+91 " + intent.getStringExtra("number")


		val builder = AlertDialog.Builder(this)

		builder.setMessage("OTP send to $phoneNumber \n Please Wait...")
		builder.setTitle("loading")
		builder.setCancelable(false)

		dialog = builder.create()
		dialog.show()



		binding.textView.text = phoneNumber.toString()

		val options = PhoneAuthOptions.newBuilder(auth)
//			.setPhoneNumber("+91 8380821996")
			.setPhoneNumber(phoneNumber)
			.setTimeout(60L, TimeUnit.SECONDS)
			.setActivity(this)
			.setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
				override fun onVerificationCompleted(p0: PhoneAuthCredential) {
					Log.d(TAG, "onVerificationCompleted: ")
				}

				override fun onVerificationFailed(p0: FirebaseException) {
					dialog.dismiss()
					Toast.makeText(this@OTPActivity, "Please try again", Toast.LENGTH_SHORT).show()
					Log.d(TAG, "onVerificationFailed: ")
				}

				override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
					super.onCodeSent(p0, p1)
					dialog.dismiss()
					verificationId = p0
					Log.d(TAG, "onCodeSent: codeSent")
				}
			}).build()

		PhoneAuthProvider.verifyPhoneNumber(options)

		binding.button.setOnClickListener {
			if (binding.OTP.text!!.isEmpty()) {
				Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show()
			} else {
				dialog.show()

				val credential =
					PhoneAuthProvider.getCredential(verificationId, binding.OTP.text!!.toString())

				auth.signInWithCredential(credential)
					.addOnCompleteListener {
						if (it.isSuccessful) {
							dialog.dismiss()
							startActivity(Intent(this, ProfileActivity::class.java))
							finish()
						} else {
							dialog.dismiss()
							Toast.makeText(this, "Error ${it.exception}", Toast.LENGTH_SHORT).show()
						}
					}
			}
		}
	}
}













