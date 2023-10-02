package com.joonhee.object_mapper.nested_object_mapper

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.ObjectCodec
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule

/**
 * 리턴 타입이 다른 경우 CustomDeserializer 를 만들기
 */

fun main() {
    // Deserialize

    // Bean 에 등록될 시리얼 라이저
    val deserializerMapper = ObjectMapper()
    val deserializerModule = SimpleModule("CustomCarNestedDeserializer")
    deserializerModule.addDeserializer(Car::class.java, CustomCarNestedDeserializer())
    deserializerMapper.registerModule(deserializerModule)
    // --------------------------------------------------


    // type: BMW  인경우
    val bmwJson = "{ " +
        "\"color\" : \"Black\", " +
        "\"type\" : \"BMW\", " +
        "\"trunk\" : { " +
        "\"size\" : 100, " +
        "\"type\" : \"big\" " +
        "} " +
        " }"
    val carObject = deserializerMapper.readValue(bmwJson, Car::class.java)
    println((carObject.trunk as SpecialTrunk).size)

    val avanteJson = "{ " +
        "\"color\" : \"Black\", " +
        "\"type\" : \"AVANTE\", " +
        "\"trunk\" : { " +
        "\"color\" : 1, " +
        "\"option\" : \"option\" " +
        "} " +
        " }"
    val carObject2 = deserializerMapper.readValue(avanteJson, Car::class.java)
    println((carObject2.trunk as NormalTrunk).option)

}


class Car(
    val color: String,
    val type: String? = null,
    val trunk: Trunk
)


interface Trunk
class SpecialTrunk(
    val size: Int,
    val type: String
) : Trunk

class NormalTrunk(
    val color: Int,
    val option: String
) : Trunk

class CustomCarNestedDeserializer : StdDeserializer<Car>(Car::class.java) {

    override fun deserialize(parser: JsonParser, ctxt: DeserializationContext?): Car {
        val codec: ObjectCodec = parser.codec
        val node = codec.readTree<JsonNode>(parser)

        // Create Car Object
        val color = node["color"].asText()
        val type = node["type"].asText()

        return when (type) {
            "BMW" -> Car(
                color = color,
                type = type,
                trunk = SpecialTrunk(
                    size = 100,
                    type = "big"
                )
            )

            "AVANTE" -> Car(
                color = color,
                type = type,
                trunk = NormalTrunk(
                    color = 1,
                    option = "option"
                )
            )

            else ->
                throw IllegalArgumentException("type is not matched")

        }
    }
}

