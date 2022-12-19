package com.example.semoproj


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.semoproj.databinding.ItemRecyclerview2Binding
import com.example.semoproj.databinding.ItemRecyclerviewBinding

class MyViewHolder2(val binding: ItemRecyclerview2Binding): RecyclerView.ViewHolder(binding.root)

class MyAdapter2(val datas: MutableList<MutableList<String>>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun getItemCount(): Int{
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = MyViewHolder2(ItemRecyclerview2Binding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyViewHolder2).binding
        binding.rankData.text= (position+1).toString()
        binding.UserIDData.text= datas[position][0]
        binding.likeData.text= datas[position][1]
    }
}

class MyDecoration2(val context: Context): RecyclerView.ItemDecoration() {

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

    private val paint = Paint()
    init{
        paint.color = Color.BLACK
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = 0f
        val right = parent.width

        for(i in 0 until parent.childCount){
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = (child.bottom + params.bottomMargin).toFloat()
            val bottom = top+3f

            c.drawRect(left, top, right.toFloat(), bottom, paint)
        }
    }
}