package com.example.onlineradioapp.repo

import com.example.onlineradioapp.db.RadioEntity
import com.example.onlineradioapp.ui.RadioPlayer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RadioRepository @Inject constructor(private val store: RadioEntity.Store) {
    @Inject 
    lateinit var appScope: RadioPlayer
    enum class FilterMode { ALL, OUTSTANDING, COMPLETED,DATE,IS_FAVORITE }
    fun getAll(): MutableList<RadioModel> {
        var temp = mutableListOf<RadioModel>()
        this.store.getAll().forEach {
                item ->
            item.stations.forEach { temp.add(it.toModel()) } }
        //return temp
        return  mutableListOf<RadioModel>().apply { store.getAll().forEach { item -> item.stations.forEach { this.add(it.toModel()) } } }
    }
    fun getAllRadio(): MutableList<RadioModel> {
        var temp = mutableListOf<RadioModel>()
        this.store.getAll().forEach {
                item ->
            item.stations.forEach { temp.add(it.toModel()) } }
        //return temp
        return  mutableListOf<RadioModel>().apply { store.getAll().forEach { item -> item.stations.forEach { this.add(it.toModel()) } } }
    }
    fun items(filterMode: FilterMode = FilterMode.ALL): Flow<List<RadioModel>> =
            filteredEntities(filterMode).map { all -> all.map { it.toModel() } }
    fun itemsRadio(filterMode: FilterMode = FilterMode.ALL): Flow<List<RadioModel>> =
        filteredEntities(filterMode).map { all -> all.map { it.toModel() } }
    private fun filteredEntities(filterMode: FilterMode) = when (filterMode) {
        FilterMode.ALL -> store.getAllRadio()
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
        println("saveAlternative2221433")
    }
    suspend fun saveAlternative2(){
        println("ssss")
    }
    suspend fun saveAlternative3(){
        println("ssssssss")
    }

}