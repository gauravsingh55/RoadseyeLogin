package com.example.roadseyelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.roadseyelogin.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getOtp.setOnClickListener{ view ->
                val number =  binding.inputMobileNumber.text?.toString()?.trim() ?: ""
                if (number.isNotEmpty() && number.length == 10) {
                    val intent = Intent(view.context, OtpActivity::class.java)
                    intent.putExtra("mobile", number)
                    startActivity(intent)
                } else {
                    val message = if (number.isEmpty()) {
                        "Enter Mobile number"
                    } else {
                        "Please enter correct number"
                    }
                    Toast.makeText(view.context, message, Toast.LENGTH_SHORT).show()
                }


        }
    }
}