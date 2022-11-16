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


class TwoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTwoBinding.inflate(inflater, container, false)

        val datas = mutableListOf<MutableList<String>>()

        for(i in 1..50){
            val temp = mutableListOf<String>()
            temp.add(i.toString())
            temp.add("User$i")
            temp.add((874-i*5).toString())
            datas.add(temp)
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager=layoutManager
        val adapter=MyAdapter2(datas)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.addItemDecoration(MyDecoration2(activity as Context))

        return binding.root    }


}