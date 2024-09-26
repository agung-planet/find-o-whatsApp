package id.ak.findonwhatsapp.domain

import id.ak.findonwhatsapp.db.AppDatabase
import id.ak.findonwhatsapp.db.HistoryDao
import id.ak.findonwhatsapp.db.SearchHistory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentSearches @Inject constructor(db: AppDatabase) {
    val result = db.historyDao().getRecentSearches()
}