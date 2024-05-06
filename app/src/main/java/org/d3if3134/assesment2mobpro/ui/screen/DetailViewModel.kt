package org.d3if3134.assesment2mobpro.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3134.assesment2mobpro.database.BanDao
import org.d3if3134.assesment2mobpro.model.Ban

class DetailViewModel(private val dao: BanDao) : ViewModel() {

    fun insert(merk: String, jenis: String, ukuran: String) {
        val ban = Ban(
            merk = merk,
            jenis = jenis,
            ukuran = ukuran
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(ban)
        }
    }

    suspend fun getBan(id: Long): Ban? {
        return dao.getBanById(id)
    }

    fun update(id: Long, merk: String, jenis: String, ukuran: String) {
        val ban = Ban(
            id = id,
            merk = merk,
            jenis = jenis,
            ukuran = ukuran
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(ban)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

}