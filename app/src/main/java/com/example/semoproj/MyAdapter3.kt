package com.example.semoproj

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.semoproj.databinding.ItemRecyclerviewBinding
import com.example.semoproj.databinding.ItemRecyclerviewSnapBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class MyAdapter3(val datas: MutableList<MutableList<String>>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class MyViewHolder(val binding: ItemRecyclerviewSnapBinding): RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int{
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = MyViewHolder(ItemRecyclerviewSnapBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyViewHolder).binding
        binding.whoRankSnap.text = "User${position+1}의 Ranking Chart"

        val datasArr = mutableListOf<MutableList<String>>()
        for(i in 1..30){
            val temp = mutableListOf<String>()
            temp.add(i.toString())
            temp.add("name ${i}")
            datasArr.add(temp)
        }

        val layoutManager = LinearLayoutManager(binding.RecyclerViewSnap.context)
        binding.RecyclerViewSnap.layoutManager = layoutManager

        val adapter = MyAdapter3_1(datasArr)
        binding.RecyclerViewSnap.adapter=adapter

        //binding.RecyclerViewSnap.addItemDecoration(MyDecoration3(activity as Context))

        //binding.RecyclerViewSnap.addItemDecoration(MyDecoration3_1(binding.RecyclerViewSnap.context))
        //binding.RecyclerViewSnap.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }
}

class MyDecoration3(val context: Context): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val index = parent.getChildAdapterPosition(view) + 1

        ViewCompat.setElevation(view, 20.0f)

    }
}

