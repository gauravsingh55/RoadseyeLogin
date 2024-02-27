package com.example.roadseyelogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.example.roadseyelogin.databinding.ActivityOtpBinding


class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mobileNo.text = String.format("+91-%s", intent.getStringExtra("mobile"))

        binding.getOtp.setOnClickListener {view ->
            val n1 = binding.inputotp1.text.toString().trim()
            val n2 = binding.inputotp1.text.toString().trim()
            val n3 = binding.inputotp1.text.toString().trim()
            val n4 = binding.inputotp1.text.toString().trim()
            val n5 = binding.inputotp1.text.toString().trim()
            val n6 = binding.inputotp1.text.toString().trim()


            val message = if (n1.isNotEmpty() && n2.isNotEmpty() && n3.isNotEmpty()
                && n4.isNotEmpty() && n5.isNotEmpty() && n6.isNotEmpty()) {
                // logic for verifying the numbers goes here
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