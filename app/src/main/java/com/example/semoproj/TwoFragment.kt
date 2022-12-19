package com.example.semoproj

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.semoproj.databinding.FragmentOneBinding
import com.example.semoproj.databinding.FragmentTwoBinding
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import kotlin.concurrent.thread


class TwoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTwoBinding.inflate(inflater, container, false)
        val datas = mutableListOf<MutableList<String>>()

        val url = URL("http://192.168.35.81:8080/final/userRank.jsp")
        val connection = url.openConnection()

        thread {
            var first = true
            BufferedReader(InputStreamReader(connection.getInputStream(), "euc-kr")).use { inp ->
                var line: String?
                while (inp.readLine().also { line = it } != null) {
                    if(first) {
                        val userRank = JSONArray(line)
                        for(i in 0 until userRank.length()){
                            val row = userRank.getJSONObject(i)
                            datas.add(mutableListOf<String>(row.getString("name"), row.getString("like")))
                        }
                        first = false
                    }
                }
            }
        }.join()

        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager=layoutManager

        val adapter=MyAdapter2(datas)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.addItemDecoration(MyDecoration2(activity as Context))

        return binding.root

    }
}