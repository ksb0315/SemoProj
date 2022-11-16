package com.example.semoproj

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.semoproj.databinding.FragmentOneBinding


class OneFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentOneBinding.inflate(inflater, container, false)

        val datas = mutableListOf<MutableList<String>>()
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
            "삼천포건어물",
            "고운맛식품",
            "공원보리밥",
            "이룸치과기공소",
            "주도락",
            "들바다푸드시스템",
            "명가식품",
            "리스본주점",
            "둥지보신탕",
            "미니카페",
            "대하축산",
            "코메루",
            "휴대폰ACC",
            "똘똘이동태전문식당",
            "커피명가",
            "김도영쌩얼미인",
            "카카오pcCULB",
            "이앤권헤어살롱",
            "탑스쿨",
            "스타벅스",
            "이음도예공방",
            "협신전기",
            "미팜스튜디오",
            "백운장여관",
            "복권나라",
            "상아마을",
            "신토불이",
            "서순자손수제비",
            "애플",
            "나이스식육",
            "광원수산.",
            "스랍음악학원",
            "로엠동성로",
            "정봉이닭발",
            "카페나니아",
            "나윤라이프",
            "MR우드닥터",
            "동경세탁",
            "원진수퍼",
            "루크안경"
        )
        for(i in 1..50){
            val temp = mutableListOf<String>()
            temp.add(i.toString())
            temp.add(name[i])
            temp.add((5-i*0.02f).toString())
            datas.add(temp)
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager=layoutManager
        val adapter=MyAdapter(datas)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.addItemDecoration(MyDecoration(activity as Context))

        return binding.root
    }

}