package com.judekwashie.contactbook.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        val listType = object : TypeToken<ArrayList<String?>?>() {}.getType()
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}