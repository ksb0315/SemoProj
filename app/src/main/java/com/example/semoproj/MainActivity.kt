package com.example.semoproj

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.semoproj.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.opencsv.CSVReader
import java.io.FileReader
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    companion object{
        var isLogin = false
        lateinit var id : String
        lateinit var password : String
    }

    companion object{
        var isLogin = false
        lateinit var id : String
        lateinit var password : String
    }

    lateinit var toggle: ActionBarDrawerToggle
    val tabName = arrayOf<String>("랭킹", "유저랭킹", "스냅샷", "로그인")

    class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        val fragments: List<Fragment>
        init {
            fragments= listOf(OneFragment(), TwoFragment(), ThreeFragment(), FourFragment())
        }
        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tabName = mutableListOf<String>("가게 랭킹", "유저 랭킹", "snapshot", "마이 페이지")
        setSupportActionBar(binding.toolbar)
        // 뷰 페이지에 어댑터 적용
        val adapter = MyFragmentPagerAdapter(this)
        binding.viewpager.adapter = adapter
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = "${tabName[position]}"
        }.attach()
        
        val assetManager = this.assets
        val fileName = "thing_50.csv"
        val dataReader = DataRead(assetManager, fileName)
        dataReader.dataRead()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}