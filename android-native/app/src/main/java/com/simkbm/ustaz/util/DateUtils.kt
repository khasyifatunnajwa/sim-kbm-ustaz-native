package com.simkbm.ustaz.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private val hariList = listOf("Ahad", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu")

    fun today(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun now(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }

    fun formatTanggal(dateStr: String): String {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = sdf.parse(dateStr) ?: return dateStr
            val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID"))
            outputFormat.format(date)
        } catch (e: Exception) {
            dateStr
        }
    }

    fun formatTanggalLengkap(dateStr: String): String {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = sdf.parse(dateStr) ?: return dateStr
            val outputFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale("id", "ID"))
            outputFormat.format(date)
        } catch (e: Exception) {
            dateStr
        }
    }

    fun namaHari(index: Int): String {
        return if (index in hariList.indices) hariList[index] else ""
    }

    fun hariIni(): String {
        val cal = Calendar.getInstance()
        return namaHari(cal.get(Calendar.DAY_OF_WEEK) - 1)
    }

    fun getCurrentTimestamp(): Long {
        return System.currentTimeMillis()
    }

    fun generateUUID(): String {
        return UUID.randomUUID().toString()
    }
}
