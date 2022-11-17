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


class ThreeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentThreeBinding.inflate(inflater, container, false)
        val datas3 = mutableListOf<MutableList<String>>()

        val name : List<String> = listOf("옛날포장마차",
            "으뜸전산컴퓨터",
            "박가냉면",
            "종합꽃",
            "장충동왕족발",
            "NEO",
            "한길부동산리서치",
            "대성서점문구지도",
            "김경숙쌤의티앤코수학학원",
            "한남광고",
            "노루페인트",
            "건영공인중개사",
            "달성천막공업사",
            "굽네치킨",
            "삼성상회",
            "주원일렉콤",
            "아가방앤컴퍼니수성동아NC점",
            "씨에스베스트",
            "슈에뜨",
            "그린플라워꽃",
            "닭S포차",
            "비테속셈학원",
            "태양코팅산업",
            "통일천막공업사",
            "미림식당",
            "풍미식당",
            "신정분식식당",
            "무림객잔",
            "마리",
            "신헤어라인3미용실",
        )

        for(i in 1..30){
            val temp = mutableListOf<String>()
            temp.add(i.toString())
            temp.add(name[i-1])
            datas3.add(temp)
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewSnapOuter.layoutManager=layoutManager

        val adapter = MyAdapter3(datas3)
        binding.recyclerViewSnapOuter.adapter=adapter

        binding.recyclerViewSnapOuter.addItemDecoration(MyDecoration3(activity as Context))
        binding.recyclerViewSnapOuter.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        return binding.root
    }
}