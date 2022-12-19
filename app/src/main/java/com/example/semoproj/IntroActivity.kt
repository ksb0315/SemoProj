package com.example.semoproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.semoproj.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.opencsv.CSVReader
import java.io.FileReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList
import android.os.Handler
import com.example.semoproj.databinding.IntroLayoutBinding

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =IntroLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var handler = Handler()
        handler.postDelayed({var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 1000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}