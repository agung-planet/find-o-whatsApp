package id.ak.findonwhatsapp.domain

import id.ak.findonwhatsapp.db.AppDatabase
import id.ak.findonwhatsapp.db.HistoryDao
import id.ak.findonwhatsapp.db.SearchHistory
import javax.inject.Inject

class AddSearchHistory @Inject constructor(private val db: AppDatabase) {
    suspend operator fun invoke(phoneNumber: String) {
        db.historyDao().insert(SearchHistory(phoneNumber))
    }
}