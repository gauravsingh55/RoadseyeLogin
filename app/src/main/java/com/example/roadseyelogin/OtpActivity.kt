package com.example.roadseyelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.roadseyelogin.databinding.ActivityOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider


class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val backendotp = intent.getStringExtra("verificationId").toString()
        val mobileNo = String.format("+91-%s", intent.getStringExtra("mobile"))
        binding.mobileNo.text = mobileNo


        binding.submit.setOnClickListener {view ->
            val n1 = binding.inputotp1.text.toString().trim()
            val n2 = binding.inputotp2.text.toString().trim()
            val n3 = binding.inputotp3.text.toString().trim()
            val n4 = binding.inputotp4.text.toString().trim()
            val n5 = binding.inputotp5.text.toString().trim()
            val n6 = binding.inputotp6.text.toString().trim()


            val message = if (n1.isNotEmpty() && n2.isNotEmpty() && n3.isNotEmpty()
                && n4.isNotEmpty() && n5.isNotEmpty() && n6.isNotEmpty()) {

                val otp = n1+n2+n3+n4+n5+n6
                if(backendotp != null){

                    binding.submitProgress.visibility = View.VISIBLE
                    binding.submit.visibility = View.INVISIBLE

                    val phoneAuthCredential : PhoneAuthCredential = PhoneAuthProvider.getCredential(backendotp,otp)
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                        .addOnCompleteListener {task ->

                            binding.submitProgress.visibility = View.GONE
                            binding.submit.visibility = View.VISIBLE

                            if (task.isSuccessful) {
                                // Authentication successful, you can proceed to the next activity or any other logic
                                val intent = Intent(this@OtpActivity, HomeActivity::class.java)
                                intent.putExtra("mobile", mobileNo)
                                startActivity(intent)
                                Toast.makeText(this@OtpActivity, "Authentication successful", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@OtpActivity, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }

                        }

                }else{
                    Toast.makeText(view.context, "check you internet", Toast.LENGTH_SHORT).show()
                }
                "OTP verified"
            } else {
                "Please enter all numbers"
            }
            Toast.makeText(view.context, message, Toast.LENGTH_SHORT).show()
        }

        numberOtpMove()
    }


    private fun numberOtpMove() {

        binding.inputotp1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    binding.inputotp2.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.inputotp2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    binding.inputotp3.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        binding.inputotp3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    binding.inputotp4.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        binding.inputotp4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    binding.inputotp5.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        binding.inputotp5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    binding.inputotp6.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


    }

}