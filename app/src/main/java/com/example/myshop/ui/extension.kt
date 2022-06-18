package com.example.myshop.ui

fun handleRequestCode(code:Int):String{
    return when(code){
        201->"موفقیت آمیز بود."
        404->"نتیجه یافت نشد."
        else->"سرور مشکل دارد."
    }
}