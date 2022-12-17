권순범
최성혁
유병주
황성재

-----------------------------------------------------------------------------------------
2022년 11월 17일 목요일 (12:00 PM 기준)
- Tab3의 중첩 RecyclerView에서, 외부 recyclerview 와 내부 recyclerview 연결이 제대로 되지 않은 것 같음

< MyAdapter3.kt 중 일부 >

<code>
      override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyViewHolder).binding
        binding.whoRankSnap.text = "User${position+1}의 Ranking Chart"

        binding.RecyclerViewSnap.layoutManager = LinearLayoutManager(binding.RecyclerViewSnap.context)
        val adapter31 = MyAdapter3_1(datas)
        binding.RecyclerViewSnap.adapter = adapter31

        binding.RecyclerViewSnap.addItemDecoration(MyDecoration3_1(binding.RecyclerViewSnap.context as Context))
        binding.RecyclerViewSnap.addItemDecoration(DividerItemDecoration(binding.RecyclerViewSnap.context, LinearLayoutManager.VERTICAL))
    }
</code>

- MyAdapter3는 외부 recylcerview 를 담당하는 어댑터
- 외부 recyclerview는 "RecyclerViewSnap" 이라는 내부 recyclerview 를 가지고 있음
- 유사한 코드를 참고해서, LinearLayoutManager(Context) 내 context를 하위 recyclerview의 context로 변경해주긴 했으나, 맞게 한 것인지 의문
- 여전히, 내부 RecyclerViewSnap은 작동안함..;;;
