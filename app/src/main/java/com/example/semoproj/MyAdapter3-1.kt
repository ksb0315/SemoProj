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
import com.example.semoproj.databinding.FragmentThreeBinding
import com.example.semoproj.databinding.ItemRecyclerviewBinding
import com.example.semoproj.databinding.ItemRecyclerviewSnap2Binding
import com.example.semoproj.databinding.ItemRecyclerviewSnapBinding

class MyAdapter3_1(val datas: MutableList<MutableList<String>>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class MyViewHolder(val binding: ItemRecyclerviewSnap2Binding): RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int{
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = MyViewHolder(ItemRecyclerviewSnap2Binding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyViewHolder).binding
        binding.snapRankData.text = datas[position][0]
        binding.snapItemData.text = datas[position][1]
    }
}

class MyDecoration3_1(val context: Context): RecyclerView.ItemDecoration() {

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

