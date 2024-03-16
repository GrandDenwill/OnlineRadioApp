package com.example.onlineradioapp.repo

import com.example.onlineradioapp.db.RadioEntity
import com.example.onlineradioapp.ui.RadioPlayer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RadioRepository @Inject constructor(private val store: RadioEntity.Store) {
    /*val stations = arrayListOf<RadioStationModel>(
        RadioStationModel("http://vladfm.ru:8000/vfm", radioName = "Радио Владивосток"),
        RadioStationModel("http://stream.zhizn-fm.ru:8000/life", radioName = "Радио Жизнь"),
        RadioStationModel("http://pub0201.101.ru:8000/stream/pull/aac/64/301", radioName = "Радио Маруся"),
        RadioStationModel("http://radio-holding.ru:9000/rus", radioName = "Радио Русь"),
        RadioStationModel("http://radio-holding.ru:9000/russian", radioName = "Русское Радион"),
        RadioStationModel("http://stream.antenne.de:80/rockantenne", radioName = "Radio Antenne")
    )*/
    @Inject 
    lateinit var appScope: RadioPlayer
    enum class FilterMode { ALL, OUTSTANDING, COMPLETED,DATE,IS_FAVORITE }
    fun getAll(): MutableList<RadioModel> {
        var temp = mutableListOf<RadioModel>()
        this.store.getAll().forEach { item -> item.stations.forEach { temp.add(it.toModel()) } }
        //return temp
        return  mutableListOf<RadioModel>().apply { store.getAll().forEach { item -> item.stations.forEach { this.add(it.toModel()) } } }
    }
    fun items(filterMode: RadioRepository.FilterMode = RadioRepository.FilterMode.ALL): Flow<List<RadioModel>> =
            filteredEntities(filterMode).map { all -> all.map { it.toModel() } }

    private fun filteredEntities(filterMode: FilterMode) = when (filterMode) {
        RadioRepository.FilterMode.ALL -> store.getAllRadio()
        else -> store.getAllRadio()
        //FilterMode.OUTSTANDING -> store.filtered(isCompleted = false)
        //FilterMode.COMPLETED -> store.filtered(isCompleted = true)
    }
    suspend fun save(model: RadioModel) {
        /*withContext(appScope.coroutineContext) {
            store.save(SalaryEntity(model))
        }*/
    }
    suspend fun saveAlternative(){

    }
}