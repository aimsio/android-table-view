package com.aimsio

import android.content.Context
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader

data class Person(
    @SerializedName("id")
    val id: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("avatar")
    val avatar: String
)

enum class PersonColumns(name: String) {

    ID("ID"),
    FIRST_NAME("First Name"),
    LAST_NAME("Last Name"),
    EMAIL("Email"),
    GENDER("Gender"),
    ADDRESS("Address"),
    CITY("City"),
    COUNTRY("Country"),
    AVATAR("Avatar")
}

class DataGenerator {

    companion object {

        fun columns(): List<String> {
            return PersonColumns.values().map { it.name }
        }

        fun rows(context: Context): List<Person> {
            return Gson().fromJson(
                getMockDataJson(context),
                object : TypeToken<List<Person>>() {}.type
            )
        }

        private fun getMockDataJson(context: Context): String {
            val inputStream = context.assets.open("mockdata.json")
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuffer = StringBuffer()
            var line = bufferedReader.readLine()
            while (line != null) {
                stringBuffer.append(line)
                line = bufferedReader.readLine()
            }

            return stringBuffer.toString()
        }
    }
}