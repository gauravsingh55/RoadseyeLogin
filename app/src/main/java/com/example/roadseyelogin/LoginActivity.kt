import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.roadseyelogin.OtpActivity
import com.example.roadseyelogin.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Initialize the phone authentication callbacks
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1. Instant verification. In some cases, the phone number can be instantly
                //    verified without needing to send or enter a verification code.
                // 2. Auto-retrieval. On some devices, Google Play services can automatically
                //    detect the incoming verification SMS and perform verification without user action.

                // Sign in with the auto-retrieved phone credential.
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked if an invalid request for verification is made,
                // for instance, if the phone number format is not valid.
                Toast.makeText(this@LoginActivity, "Verification Failed", Toast.LENGTH_SHORT).show()
                binding.getOtpProgress.visibility = View.INVISIBLE
                binding.getOtp.visibility = View.VISIBLE
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number,
                // we need to save the verificationId and start the OtpActivity
                val intent = Intent(this@LoginActivity, OtpActivity::class.java)
                intent.putExtra("mobile", binding.inputMobileNumber.text.toString())
                intent.putExtra("verificationId", verificationId)
                startActivity(intent)
            }
        }

        binding.getOtp.setOnClickListener { view ->
            val number = binding.inputMobileNumber.text?.toString()?.trim() ?: ""
            if (number.isNotEmpty() && number.length == 10) {
                binding.getOtpProgress.visibility = View.VISIBLE
                binding.getOtp.visibility = View.INVISIBLE

                // Start the phone number verification process
                startPhoneNumberVerification(number)
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

    private fun startPhoneNumberVerification(phoneNumber: String) {
        // Start the phone number verification process
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,  // Timeout duration
            TimeUnit.SECONDS,
            this,
            callbacks
        )
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, navigate to the next activity if needed
                    val user = task.result?.user
                    // You can add your logic here to handle successful sign in
                } else {
                    // Sign in failed
                    Toast.makeText(
                        this@LoginActivity,
                        "Authentication Failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.getOtpProgress.visibility = View.INVISIBLE
                    binding.getOtp.visibility = View.VISIBLE
                }
            }
    }
}
