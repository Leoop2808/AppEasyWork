package com.proy.easywork.data.datasource.helpers

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONObject

object GsonHelper {
    private val gsonBuilder: GsonBuilder = GsonBuilder()
    private val gson: Gson = gsonBuilder.create()

    fun Any.generateJSONObject(): JSONObject? {
        var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject(gson.toJson(this))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return jsonObject
    }

    fun <T> String.convertJsonToClass(cls: Class<T>): T {
        return gson.fromJson(this, cls)
    }
}