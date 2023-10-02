package com.joonhee.object_mapper.basic_custom_object_mapper

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer


fun main() {
    val mapper = ObjectMapper()
    val module = SimpleModule("CustomCarSerializer")
    module.addSerializer(Car::class.java, CustomCarSerializer())    // 결과로 나올 클래스와 시리얼 라이저를 등록
    mapper.registerModule(module)
    val car = Car("yellow", "renault")
    val carJson = mapper.writeValueAsString(car)
    println(carJson)
}


class Car(
    val color: String,
    val type: String
)


class CustomCarSerializer : StdSerializer<Car>(Car::class.java) {
    override fun serialize(car: Car, jsonGenerator: JsonGenerator, provider: SerializerProvider?) {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("car_brand", car.type);
        jsonGenerator.writeEndObject();
    }
}

