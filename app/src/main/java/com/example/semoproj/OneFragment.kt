package com.example.semoproj

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.semoproj.databinding.FragmentOneBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.lang.Math.round
import java.net.URL
import kotlin.concurrent.thread


class OneFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentOneBinding.inflate(inflater, container, false)

        for (row in daegu_s_info) {
            Log.d("semoApp", "row : $row")
        }

        val datas = mutableListOf<MutableList<String>>()

        val url = URL("http://192.168.35.81:8080/final/thingRank.jsp")
        val connection = url.openConnection()

        thread {
            var first = true
            BufferedReader(InputStreamReader(connection.getInputStream(), "euc-kr")).use { inp ->
                var line: String?
                while (inp.readLine().also { line = it } != null) {
                    if(first) {
                        val thingRank = JSONArray(line)
                        for(i in 0 until thingRank.length()){
                            val row = thingRank.getJSONObject(i)
                            datas.add(mutableListOf<String>(row.getString("name"), (round(row.getDouble("score")*100)/100.0).toString()))
                        }
                        first = false
                    }
                }
            }
        }.join()

        val comparator : Comparator<MutableList<String>> = compareByDescending{it[1]}
        val temp : MutableList<MutableList<String>> = (datas.sortedWith(comparator)).toMutableList()

        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager=layoutManager

        val adapter=MyAdapter(temp)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.addItemDecoration(MyDecoration(activity as Context))

        return binding.root
    }

}