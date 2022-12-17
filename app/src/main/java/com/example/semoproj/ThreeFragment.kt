package com.example.semoproj

import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.semoproj.databinding.FragmentThreeBinding
import com.example.semoproj.databinding.ItemRecyclerviewSnapBinding
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
        val datas3 = mutableListOf<MutableList<MutableList<String>>>()

        val url = URL("http://192.168.35.4:8080/final/test.jsp")
        val connection = url.openConnection()
        var inTable = false
        var inRow = false
        var tdCount = 0
        var table = mutableListOf<MutableList<String>>()
        var row = mutableListOf<String>()
        var name = ""
        thread {
            BufferedReader(InputStreamReader(connection.getInputStream(), "euc-kr")).use { inp ->
                var line: String?
                while (inp.readLine().also { line = it } != null) {
                    if (line != null) {
                        if(line!!.indexOf("h2") != -1){
                            var start = 0
                            var end = 0
                            for(i in 0 until line!!.length){
                                if(line!![i] == '>'){
                                    start = i
                                    break
                                }
                            }
                            for(i in start until line!!.length){
                                if(line!![i] == '<'){
                                    end = i
                                    break
                                }
                            }
                            row.add(line!!.substring(start+1, end))
                            table.add(row)
                            row = mutableListOf<String>()
                        }
                        else if (line!!.indexOf("/table") != -1) {
                            inTable = false
                            datas3.add(table)
                            table = mutableListOf<MutableList<String>>()
                        } else if (line!!.indexOf("table") != -1) {
                            inTable = true
                        } else if (inTable) {
                            if (line!!.indexOf("/tr") != -1) {
                                tdCount = 0
                                inRow = false
                                table.add(row)
                                row = mutableListOf<String>()
                            } else if (line!!.indexOf("tr") != -1) {
                                inRow = true
                            } else if (inRow) {
                                if (line!!.indexOf("td") == -1) {
                                    tdCount++
                                    row.add(line!!)
                                }
                            }
                        }
                    }
                }
            }
        }

        sleep(5000)
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewSnapOuter.layoutManager=layoutManager

        val adapter = MyAdapter3(datas3)
        binding.recyclerViewSnapOuter.adapter=adapter

        return binding.root
    }
}