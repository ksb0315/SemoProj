package com.example.semoproj

import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.semoproj.databinding.FragmentFourBinding
import org.json.JSONArray
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
        val datas = mutableListOf<MutableList<String>>()
        val sendDatas = mutableListOf<MutableList<String>>()
        val extraList= mutableListOf<String>("...", "...")

        binding.btnLogin.setOnClickListener {
            MainActivity.id = binding.id.text.toString()
            MainActivity.password = binding.password.text.toString()

            val url = URL("http://192.168.35.81:8080/final/login.jsp")
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
                            if(test.getString("login").equals("성공")){
                                MainActivity.isLogin = true
                            }
                            first = false
                        }
                    }
                }
            }.join()

            if(MainActivity.isLogin){
                binding.llMainLayout.visibility = View.GONE
                binding.myPageLayout.visibility = View.VISIBLE

                val url = URL("http://192.168.35.81:8080/final/myPage.jsp")
                val postData = "id=" + MainActivity.id

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
                                val userData = JSONObject(line)

                                val info = userData.getJSONObject("info")
                                binding.name.text = info.getString("name")
                                binding.region.text = info.getString("region")
                                binding.phoneNumber.text = info.getString("phone_number")

                                val snapshot = userData.getJSONObject("snapshot")
                                val likeScore = snapshot.getJSONObject("likeScore")
                                binding.like.text = "좋아요 : ${likeScore.getString("like")}, 싫어요 : ${likeScore.getString("dislike")}"
                                val table = snapshot.getJSONArray("rankList")
                                for(i in 0 until table.length()){
                                    val row = table.getJSONObject(i)
                                    datas.add(mutableListOf<String>(row.getString("rank"), row.getString("thing_name")))
                                }

                                first = false
                            }
                        }
                    }
                }.join()

                val layoutManager = LinearLayoutManager(activity)
                binding.recyclerViewSnapOuter.layoutManager=layoutManager

                if(datas.size > 5){
                    binding.btnSnap.visibility = View.VISIBLE
                    for(i in 0 until 5){
                        sendDatas.add(datas[i])
                    }
                    sendDatas.add(extraList)
                }
                else{
                    binding.btnSnap.visibility = View.GONE
                    for(i in 0 until datas.size){
                        sendDatas.add(datas[i])
                    }
                }
                val adapter = MyAdapter4(sendDatas)
                binding.recyclerViewSnapOuter.adapter=adapter

                if(datas.size > 5) {
                    var viewAllData = false
                    binding.btnSnap.setOnClickListener {
                        if(viewAllData){
                            while(sendDatas.size > 5){
                                sendDatas.removeLast()
                            }
                            sendDatas.add(extraList)
                            (binding.recyclerViewSnapOuter.adapter as MyAdapter4).notifyDataSetChanged()
                            viewAllData = false
                            binding.btnSnap.text = "펼치기"
                        }
                        else{
                            sendDatas.removeLast()
                            for(i in 5 until datas.size){
                                sendDatas.add(datas[i])
                            }
                            (binding.recyclerViewSnapOuter.adapter as MyAdapter4).notifyDataSetChanged()
                            viewAllData = true
                            binding.btnSnap.text = "접기"
                        }
                    }
                }
            }
        }

        return binding.root
    }
}