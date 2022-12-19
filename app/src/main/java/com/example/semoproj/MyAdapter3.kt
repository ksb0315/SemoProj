package com.example.semoproj

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.size
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.semoproj.databinding.ItemRecyclerviewSnapBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class MyAdapter3(val names: MutableList<String>, val like: MutableList<String>, val dislike: MutableList<String>, val datas: MutableList<MutableList<MutableList<String>>>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class MyViewHolder(val binding: ItemRecyclerviewSnapBinding): RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int{
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = MyViewHolder(ItemRecyclerviewSnapBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyViewHolder).binding
        binding.whoRankSnap.text = "${names[position]}의 Ranking Chart"
        binding.nLikes.text = like[position]
        binding.nDislikes.text = dislike[position]

        var newArr = mutableListOf<MutableList<String>>()
        val extraList = mutableListOf<String>("...", "...")
        if(datas[position].size > 5){
            for(i in 0 until 5){
                newArr.add(datas[position][i])
            }
            binding.dataButton.visibility = View.VISIBLE
            newArr.add(extraList)
        }
        else{
            for(i in 0 until datas[position].size){
                newArr.add(datas[position][i])
            }
            Log.d("test", "${position} visibility gone")
            binding.dataButton.visibility = View.GONE
        }
        Log.d("test", "${position} ${binding.btnLayout.height}")

        val layoutManager = LinearLayoutManager(binding.RecyclerViewSnap.context)
        binding.RecyclerViewSnap.layoutManager = layoutManager

        val adapter = MyAdapter3_1(newArr)
        binding.RecyclerViewSnap.adapter=adapter

        if(datas.size > 5){
            var viewAllData : Boolean = false
            binding.dataButton.setOnClickListener{
                if(viewAllData){
                    while(newArr.size > 5){
                        newArr.removeLast()
                    }
                    newArr.add(extraList)
                    (binding.RecyclerViewSnap.adapter as MyAdapter3_1).notifyDataSetChanged()
                    viewAllData = false
                    binding.dataButton.text = "펼치기"
                }
                else{
                    newArr.removeLast()
                    for(i in 5 until datas[position].size){
                        newArr.add(datas[position][i])
                    }
                    (binding.RecyclerViewSnap.adapter as MyAdapter3_1).notifyDataSetChanged()
                    viewAllData = true
                    binding.dataButton.text = "접기"
                }
            }
        }

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
