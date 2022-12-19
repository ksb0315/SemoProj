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
import com.example.semoproj.databinding.FragmentThreeBinding
import com.example.semoproj.databinding.ItemRecyclerviewSnapBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Thread.sleep
import java.net.URL
import kotlin.concurrent.thread


class ThreeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentThreeBinding.inflate(inflater, container, false)
        var name = mutableListOf<String>()
        var like = mutableListOf<String>()
        var dislike = mutableListOf<String>()
        val datas3 = mutableListOf<MutableList<MutableList<String>>>()

        for (row in daegu_s_info) {
            Log.d("semoApp", "row : $row")
        }
        val url = URL("http://192.168.35.81:8080/final/snapshot.jsp")
        val connection = url.openConnection()

        thread {
            var first = true
            BufferedReader(InputStreamReader(connection.getInputStream(), "euc-kr")).use { inp ->
                var line: String?
                while (inp.readLine().also { line = it } != null) {
                    if(first) {
                        val snapshotData = JSONArray(line)
                        for(i in 0 until snapshotData.length()){
                            val obj = snapshotData.getJSONObject(i)
                            name.add(obj.getString("name"))
                            like.add(obj.getString("like"))
                            dislike.add(obj.getString("dislike"))
                            val table = obj.getJSONArray("table")
                            val tableData = mutableListOf<MutableList<String>>()
                            for(j in 0 until table.length()){
                                val row = table.getJSONObject(j)
                                tableData.add(mutableListOf<String>(row.getString("rank"), row.getString("thingName")))
                            }
                            datas3.add(tableData)
                        }
                        first = false
                    }
                }
            }
        }.join()

        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewSnapOuter.layoutManager=layoutManager

        val adapter = MyAdapter3(name, like, dislike, datas3)
        binding.recyclerViewSnapOuter.adapter=adapter

        return binding.root
    }
}