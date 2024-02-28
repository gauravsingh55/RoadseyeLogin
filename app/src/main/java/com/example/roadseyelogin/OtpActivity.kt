package com.example.roadseyelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.roadseyelogin.databinding.ActivityOtpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    private lateinit var auth: FirebaseAuth
    lateinit var inputOTP1 : EditText
    lateinit var inputOTP2 : EditText
    lateinit var inputOTP3 : EditText
    lateinit var inputOTP4 : EditText
    lateinit var inputOTP5 : EditText
    lateinit var inputOTP6 : EditText

    private lateinit var OTP: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var phoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        inputOTP1 = binding.inputotp1
        inputOTP2 = binding.inputotp2
        inputOTP3 = binding.inputotp3
        inputOTP4 = binding.inputotp4
        inputOTP5 = binding.inputotp5
        inputOTP6 = binding.inputotp6

        addTextChangeListener()
        resendOTPTvVisibility()

        OTP = intent.getStringExtra("OTP").toString()
        resendToken = intent.getParcelableExtra("resendToken")!!
        phoneNumber = intent.getStringExtra("phoneNumber")!!
        binding.mobileNo.text = phoneNumber

        binding.resendotp.setOnClickListener {
            resendVerificationCode()
            resendOTPTvVisibility()
        }


        binding.submit.setOnClickListener { view ->
            val n1 = binding.inputotp1.text.toString().trim()
            val n2 = binding.inputotp2.text.toString().trim()
            val n3 = binding.inputotp3.text.toString().trim()
            val n4 = binding.inputotp4.text.toString().trim()
            val n5 = binding.inputotp5.text.toString().trim()
            val n6 = binding.inputotp6.text.toString().trim()
            val typedOTP = n1 + n2 + n3 + n4 + n5 + n6

            if (typedOTP.isNotEmpty()) {
                if (typedOTP.length == 6) {
                    val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                        OTP, typedOTP
                    )
                    binding.submitProgress.visibility = View.VISIBLE
                    binding.submit.visibility = View.INVISIBLE
                    signInWithPhoneAuthCredential(credential)
                } else {
                    Toast.makeText(this, "Please Enter Correct OTP", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun resendOTPTvVisibility() {
        inputOTP1.setText("")
        inputOTP2.setText("")
        inputOTP3.setText("")
        inputOTP4.setText("")
        inputOTP5.setText("")
        inputOTP6.setText("")
        binding.resendotp.visibility = View.INVISIBLE
        binding.resendotp.isEnabled = false

        Handler(Looper.myLooper()!!).postDelayed({
            binding.resendotp.visibility = View.VISIBLE
            binding.resendotp.isEnabled = true
        }, 60000)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            }
            binding.submitProgress.visibility = View.VISIBLE
            binding.submit.visibility = View.INVISIBLE
            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            // Save verification ID and resending token so we can use them later
            OTP = verificationId
            resendToken = token
        }
    }

    private fun resendVerificationCode() {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)
            .setForceResendingToken(resendToken)// OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    binding.submitProgress.visibility = View.VISIBLE
                    binding.submit.visibility = View.INVISIBLE
                    Toast.makeText(this, "Authenticate Successfully", Toast.LENGTH_SHORT).show()
                    sendToMain()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.d("TAG", "signInWithPhoneAuthCredential: ${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
                binding.submitProgress.visibility = View.VISIBLE
                binding.submit.visibility = View.INVISIBLE
            }
    }

    private fun sendToMain() {
        val intent = Intent(this@OtpActivity , HomeActivity::class.java)
        intent.putExtra("phoneNumber" , phoneNumber)
        startActivity(intent)
    }

    private fun addTextChangeListener() {
        inputOTP1.addTextChangedListener(EditTextWatcher(inputOTP1))
        inputOTP2.addTextChangedListener(EditTextWatcher(inputOTP2))
        inputOTP3.addTextChangedListener(EditTextWatcher(inputOTP3))
        inputOTP4.addTextChangedListener(EditTextWatcher(inputOTP4))
        inputOTP5.addTextChangedListener(EditTextWatcher(inputOTP5))
        inputOTP6.addTextChangedListener(EditTextWatcher(inputOTP6))
    }

    inner class EditTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {

            val text = p0.toString()
            when (view.id) {
                R.id.inputotp1 -> if (text.length == 1) inputOTP2.requestFocus()
                R.id.inputotp2 -> if (text.length == 1) inputOTP3.requestFocus()
                else if (text.isEmpty()) inputOTP1.requestFocus()

                R.id.inputotp3 -> if (text.length == 1) inputOTP4.requestFocus()
                else if (text.isEmpty()) inputOTP2.requestFocus()

                R.id.inputotp4 -> if (text.length == 1) inputOTP5.requestFocus()
                else if (text.isEmpty()) inputOTP3.requestFocus()

                R.id.inputotp5 -> if (text.length == 1) inputOTP6.requestFocus()
                else if (text.isEmpty()) inputOTP4.requestFocus()

                R.id.inputotp6 -> if (text.isEmpty()) inputOTP5.requestFocus()

            }
        }

    }

}