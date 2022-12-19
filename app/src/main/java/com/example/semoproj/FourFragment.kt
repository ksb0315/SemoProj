package com.example.semoproj

import android.content.Context
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.semoproj.databinding.FragmentFourBinding
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.lang.Thread.sleep
import java.net.URL
import kotlin.concurrent.thread


class FourFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFourBinding.inflate(inflater, container, false)
        val datas4 = mutableListOf<MutableList<MutableList<String>>>()

        binding.btnLogin.setOnClickListener {
            MainActivity.id = binding.id.text.toString()
            MainActivity.password = binding.password.text.toString()

            val url = URL("http://192.168.35.4:8080/final/login.jsp")
            val postData = "id=" + MainActivity.id + "&password=" + MainActivity.password

            val conn = url.openConnection()
            conn.doOutput = true
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            conn.setRequestProperty("Content-Length", postData.length.toString())

            var first = true
            thread {
                DataOutputStream(conn.getOutputStream()).use { it.writeBytes(postData) }
                BufferedReader(InputStreamReader(conn.getInputStream(), "euc-kr")).use { bf ->
                    var line: String?
                    while (bf.readLine().also { line = it } != null) {
                        if(first) {
                            val test = JSONObject(line)
                            println(test.getString("login"))
                            first = false
                        }
                        if(line!!.indexOf("성공") != -1){
                            MainActivity.isLogin = true
                        }
                    }
                }
            }
        }

        binding.signUp.setOnClickListener{
            val intent = Intent(getActivity(),SignUpActivity::class.java)
            startActivity(intent)
        }

        /*
        sleep(5000)
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewSnapOuter.layoutManager=layoutManager

        val adapter = MyAdapter3(datas3)
        binding.recyclerViewSnapOuter.adapter=adapter
         */

        return binding.root
    }
}