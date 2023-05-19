package com.example.thindie.aston_intensive_lesson5.data.someData


import androidx.compose.runtime.mutableStateListOf
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
internal class SomeDataImpl @Inject constructor() : SomeDataHolder {

    private val _someData: MutableList<Triple<String, String, String>> = mutableStateListOf(
        Triple("Boris", "TheShave", "+44 444 222 11 2"),
        Triple("Ivey", "Andre2000", "+44 123 555 22 1"),
        Triple("Jack", "Green", "+7 911 4442 112"),

        )

    override fun getSomeData(): Flow<List<Triple<String, String, String>>> {
        return flow {
            emit(_someData)
        }
    }

    override fun updateSomeData(
        oldData: Triple<String, String, String>,
        newData: Triple<String, String, String>
    ) {
      val modifiedData =  _someData.map { oldValue ->
            if (oldData.first == oldValue.first && oldData.third == oldValue.third) {
                Triple(newData.first, newData.second, newData.third)
            } else oldValue
        }
        _someData.clear()
        _someData.addAll(modifiedData)
    }
}

