package com.example.todoapp

import androidx.lifecycle.ViewModel

class DBViewModel(private var context: DBRepository) : ViewModel() {
    val dbRepository = DBRepository(context)

    fun createData(TiTle: String, Description: String, DateTime: String) {
        dbRepository.createData(TiTle, Description, DateTime)
    }

    fun deleteAll() {
        dbRepository.deleteAll()
    }

    fun updateData(TiTle: String, Description: String, DateTime: String) {
        dbRepository.updateData(TiTle, Description, DateTime)
    }

    fun dateTime(): String {
        return dbRepository.dateTime()
    }

    fun getDataList(): List<PersonalData> {
        return dbRepository.getPersonalData()
    }

    fun getPersonalData(): List<PersonalData> {
        return dbRepository.getPersonalData()

    }

    fun deleteData(TiTle: String, Description: String, DateTime: String) {
        dbRepository.deleteData(TiTle, Description, DateTime)
    }
}


