package com.joonhee.excel

import org.dhatim.fastexcel.Workbook
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties

class ExcelManager {
    init {
        createExcel<WriteCellValue>(getDatas())
    }

    inline fun <reified T : Any> createExcel(t: List<T>) {
        val os = ByteArrayOutputStream()
        val wb = Workbook(os, "Test", "1.0")
        val ws = wb!!.newWorksheet("Sheet 1")


        t.withIndex().map {row->
            val c = T::class.declaredMemberProperties
            c.withIndex().forEach { (colIndex, property) ->
                ws.value(row.index, colIndex, property.get(row.value) as String)
                println("${row.index} $colIndex ${property.name} : ${property.get(row.value)}")

            }
        }
        wb.finish()
        os.writeTo(FileOutputStream(File("excel-write-sample3.xlsx")))
    }


    fun getDatas(): List<WriteCellValue> {
        val writeCellValues: MutableList<WriteCellValue> = mutableListOf()
        writeCellValues.add(WriteCellValue("Gil1", "Grissom1", "Addr1", "우리집1", ""))
        writeCellValues.add(WriteCellValue("Gil2", "Grissom2", "Addr2", "우리집2", ""))
        return writeCellValues
    }

    data class WriteCellValue(
        var a1_firstName: String,
        var a2_secondName: String,
        var a3_address: String,
        var a4_message1: String,
        var a5_message2: String,
    )
}

fun main() {
    ExcelManager()
}