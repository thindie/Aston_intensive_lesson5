package com.example.thindie.aston_intensive_lesson5.data.someData

import kotlinx.coroutines.flow.Flow

interface SomeDataHolder {
    fun getSomeData(): Flow<List<Triple<String, String, String>>>
    fun updateSomeData(
        oldData: Triple<String, String, String>,
        newData: Triple<String, String, String>
    )
}