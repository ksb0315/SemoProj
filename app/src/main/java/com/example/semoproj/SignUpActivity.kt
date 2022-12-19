package com.example.semoproj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.semoproj.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUp.setOnClickListener{


            setResult(RESULT_OK, intent)
            finish()
        }
    }
}