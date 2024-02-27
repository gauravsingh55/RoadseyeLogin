package com.example.roadseyelogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roadseyelogin.databinding.ActivityHomeBinding
import com.example.roadseyelogin.databinding.ActivityOtpBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mobileNo = String.format("+91-%s", intent.getStringExtra("mobile"))
        binding.greet.text = mobileNo
    }
}