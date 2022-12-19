package com.example.semoproj

import android.content.res.AssetManager
import com.opencsv.CSVReader
import java.io.InputStreamReader

var daegu_s_info : MutableList<ArrayList<String>> = arrayListOf()

class DataRead(val assetManager: AssetManager, val fileName:String){

    fun dataRead() {
        val inputStream = assetManager.open(fileName)
        val reader = CSVReader(InputStreamReader(inputStream))
        var cont = ""

        val header = reader?.readNext()
        val allContent = reader?.readAll()
        if (allContent != null) {
            for (content in allContent) {
                cont = content.toList().toString()
                var arr = cont.split(", ")
                daegu_s_info.add(arr as ArrayList<String>)
            }
        }
    }
}