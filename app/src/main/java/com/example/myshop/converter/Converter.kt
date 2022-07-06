package com.example.myshop.converter

import androidx.room.TypeConverter

class Converter {
//    @TypeConverter
//    fun stringFromInfoList(materials: List<Material>): String {
//        var str = ""
//        for (material in materials) {
//            str += material.id.toString() + "," + material.materialName + "," + material.amount + "," + material.description + "-"
//        }
//        return str
//    }
//
//    @TypeConverter
//    fun stringToInfoList(itemsString: String): List<Material> {
//        var list = arrayListOf<Material>()
//        var strs = itemsString.split('-')
//        for (str in strs) {
//            if (str.isNullOrBlank())
//                break
//            var items = str.split(',')
//            list.add(Material(items[0].toInt(), items[1], items[2].toDouble(), items[3]))
//        }
//        return list
//    }
}