package com.example.again

import kotlin.math.pow

fun main() {
    // Чтение параметров, которые заданы через консоль
    val params = mutableMapOf<String, String?>()
    repeat(6) {
        val (key, value) = readLine()!!.split(" ")
        params[key] = value
    }
    // Разделение названий переменных и их значений
    val x_coord = params["x_coord"]?.toFloat()
    val y_coord = params["y_coord"]?.toFloat()
    val age = params["age"]?.toInt()
    val os_version = params["os_version"]?.toInt()
    val time = params["time"]?.toLong()
    val gender = params["gender"]
    val expiry_date = params["expiry_date"]?.toLong()
    val radius = params["radius"]?.toInt()
    val text = params["text"]
    val type = params["type"]

    // Читает количество пушей
    val pushCount = readLine()!!.toInt()


    // Создание объектов, которые в последующем нужно будет отфильтровать
    // и присвоение им нужных значений
    val locationPush = LocationPush(
        x_coord = x_coord,
        y_coord = y_coord,
        expiry_date = expiry_date,
        radius = radius,
        text = text,
        type = type
    )

    val ageSpecificPush = AgeSpecificPush(
        age = age,
        expiry_date = expiry_date,
        text = text,
        type = type
    )

    val techPush = TechPush(
        os_version = os_version,
        text = text,
        type = type
    )

    val locationAgePush = LocationAgePush(
        x_coord = x_coord,
        y_coord = y_coord,
        age = age,
        radius = radius,
        text = text,
        type = type
    )

    val genderAgePush = GenderAgePush(
        gender = gender,
        age = age,
        text = text,
        type = type
    )

    val genderPush = GenderPush(
        gender = gender,
        text = text,
        type = type
    )
}



data class LocationPush(
    val x_coord: Float,
    val y_coord: Float,
    val radius: Int,
    val expiry_date: Long,
    val text: String,
    val type: String
)

data class AgeSpecificPush(
    val age: Int,
    val expiry_date: Long,
    val text: String,
    val type: String
)

data class TechPush(
    val os_version: Int,
    val text: String,
    val type: String
)

data class LocationAgePush(
    val x_coord: Float,
    val y_coord: Float,
    val radius: Int,
    val age: Int,
    val text: String,
    val type: String
)

data class GenderAgePush(
    val gender: String,
    val age: Int,
    val text: String,
    val type: String
)

data class GenderPush(
    val gender: String,
    val text: String,
    val type: String
)

fun DiractionFilter(push: Any, x: Float, y: Float): Boolean {
    return when (push) {
        is LocationPush -> {
            val distance = kotlin.math.sqrt((push.x_coord - x).pow(2) + (push.y_coord - y).pow(2))
            distance < push.radius
        }

        is LocationAgePush -> {
            val distance = kotlin.math.sqrt((push.x_coord - x).pow(2) + (push.y_coord - y).pow(2))
            distance < push.radius
        }

        else -> false
    }
}

fun AgeFilter(pushAge: Any, age: Int): Boolean {
    return when (pushAge) {
        is AgeSpecificPush -> {
            pushAge.age < age
        }

        is LocationAgePush -> {
            pushAge.age < age
        }

        is GenderAgePush -> {
            pushAge.age < age
        }

        else -> false
    }
}

fun OSFilet(pushTech: TechPush, os_version: Int): Boolean {
    return pushTech.os_version <= os_version
}

fun LifeTimeFilter(pushTime: Any, time: Long): Boolean {
    return when (pushTime) {
        is LocationPush -> {
            time <= pushTime.expiry_date
        }

        is AgeSpecificPush -> {
            time <= pushTime.expiry_date
        }

        else -> false
    }
}

fun GenderFilter(pushGender: Any, gender: String): Boolean {
    return when (pushGender) {
        is GenderAgePush -> {
            pushGender.gender == gender
        }

        is GenderPush -> {
            pushGender.gender == gender
        }

        else -> false
    }
}
