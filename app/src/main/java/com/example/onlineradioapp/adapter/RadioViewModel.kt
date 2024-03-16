package com.example.onlineradioapp.adapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineradioapp.repo.RadioModel
import com.example.onlineradioapp.repo.RadioRepository
import com.example.onlineradioapp.ui.RadioPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
data class RosterViewState(
    val items: List<RadioModel> = listOf(),
    val filterMode: RadioRepository.FilterMode = RadioRepository.FilterMode.ALL

)
@HiltViewModel
class RadioViewModel @Inject constructor(private val radioRepository: RadioRepository): ViewModel() {
    var radioStations = radioRepository.getAll()
    var currentRadioStation: RadioModel? = null
    @Inject
    lateinit var radioPlayer: RadioPlayer
    private val _states = MutableStateFlow(RosterViewState())
    val states = _states.asStateFlow()
    private var job: Job? = null
    init {
        load(RadioRepository.FilterMode.ALL)
    }
    fun load(filterMode: RadioRepository.FilterMode) {
        job?.cancel()

        job = viewModelScope.launch {
            radioRepository.items(filterMode).collect {
                _states.emit(RosterViewState(it, filterMode))
            }
        }
    }
    fun save(model: RadioModel) {
        viewModelScope.launch {
            radioRepository.save(model)
        }
    }
}