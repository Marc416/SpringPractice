package com.joonhee.excel

import org.dhatim.fastexcel.Workbook
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.function.Consumer

class ExcelCreate {
    @Throws(IOException::class)
    fun writeWorkbook(consumer: Consumer<Workbook?>) {
        val os = ByteArrayOutputStream()
        val wb = Workbook(os, "Test", "1.0")
        consumer.accept(wb)
        wb.finish()
        os.writeTo(FileOutputStream(File("excel-write-sample.xlsx")))
        //return os.toByteArray()

    }

    @Test
    @DisplayName("정상적인 엑셀 생성")
    fun parallelStreams() {

        writeWorkbook { wb ->
            val ws = wb!!.newWorksheet("Sheet 1")
            getDatas().withIndex().map {
                ws.value(it.index, 0, it.value.firstName)
                ws.value(it.index, 1, it.value.secondName)
                ws.value(it.index, 2, it.value.address)
                ws.value(it.index, 3, it.value.message)
            }

        }
    }

    @Test
    fun `내가해보기`(){
        val os = ByteArrayOutputStream()
        val wb = Workbook(os, "Test", "1.0")
        val ws = wb!!.newWorksheet("Sheet 1")
        getDatas().withIndex().map {
            ws.value(it.index, 0, it.value.firstName)
            ws.value(it.index, 1, it.value.secondName)
            ws.value(it.index, 2, it.value.address)
            ws.value(it.index, 3, it.value.message)
        }
        wb.finish()
        os.writeTo(FileOutputStream(File("excel-write-sample.xlsx")))

    }

    fun getDatas() : List<WriteCellValue> {
        val writeCellValues: MutableList<WriteCellValue> = mutableListOf()
        writeCellValues.add(WriteCellValue("Gil1", "Grissom1", "Addr1", "우리집1"))
        writeCellValues.add(WriteCellValue("Gil2", "Grissom2", "Addr2", "우리집2"))
        return writeCellValues
    }

    data class WriteCellValue(
        var firstName: String,
        var secondName: String,
        var address : String,
        var message : String
    )
}

