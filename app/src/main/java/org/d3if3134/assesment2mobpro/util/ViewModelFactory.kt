package org.d3if3134.assesment2mobpro.util

import androidx.compose.runtime.internal.illegalDecoyCallException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3134.assesment2mobpro.database.BanDao
import org.d3if3134.assesment2mobpro.ui.screen.DetailViewModel
import org.d3if3134.assesment2mobpro.ui.screen.MainViewModel

class ViewModelFactory (
    private val dao: BanDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(dao) as T
        }
        throw illegalDecoyCallException("Unknown ViewModel class")
    }
}