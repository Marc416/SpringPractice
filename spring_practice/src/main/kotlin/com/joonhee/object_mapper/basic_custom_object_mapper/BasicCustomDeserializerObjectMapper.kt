package com.joonhee.object_mapper.basic_custom_object_mapper

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.ObjectCodec
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer


fun main() {
    // Serialize
    val serializerMapper = ObjectMapper()
    val serializerModule = SimpleModule("CustomCarSerializer2")
    serializerModule.addSerializer(Car2::class.java, CustomCarSerializer2())    // 결과로 나올 클래스와 시리얼 라이저를 등록
    serializerMapper.registerModule(serializerModule)
    val car = Car2("yellow", "renault")
    val carJson = serializerMapper.writeValueAsString(car)
    println(carJson)

    // --------------------------------------------------

    // Deserialize
    val json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }"
    val deserializerMapper = ObjectMapper()
    val deserializerModule = SimpleModule("CustomCarDeserializer2")
    deserializerModule.addDeserializer(Car2::class.java, CustomCarDeserializer2())
    deserializerMapper.registerModule(deserializerModule)
    val carObject = deserializerMapper.readValue(json, Car2::class.java)
    println(carObject)
}


class Car2(
    val color: String,
    val type: String? = null,
)




class CustomCarSerializer2 : StdSerializer<Car2>(Car2::class.java) {
    override fun serialize(car: Car2, jsonGenerator: JsonGenerator, provider: SerializerProvider?) {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("car_brand", car.type);
        jsonGenerator.writeEndObject();
    }
}

class CustomCarDeserializer2 : StdDeserializer<Car2>(Car2::class.java) {

    override fun deserialize(parser: JsonParser, ctxt: DeserializationContext?): Car2 {
        val codec: ObjectCodec = parser.codec
        val node = codec.readTree<JsonNode>(parser)
        val colorNode = node["color"]
        val color = colorNode.asText()
        val car = Car2(color=color)
        return car
    }
}

