package org.d3if3134.assesment2mobpro.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3134.assesment2mobpro.database.BanDao
import org.d3if3134.assesment2mobpro.model.Ban
class MainViewModel(dao: BanDao) : ViewModel() {

    val data: StateFlow<List<Ban>> = dao.getBan().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}