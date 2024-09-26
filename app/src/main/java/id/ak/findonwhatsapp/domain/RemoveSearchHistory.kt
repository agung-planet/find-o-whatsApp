package id.ak.findonwhatsapp.domain

import id.ak.findonwhatsapp.db.AppDatabase
import javax.inject.Inject

class RemoveSearchHistory @Inject constructor(private val db: AppDatabase) {
    suspend operator fun invoke(phoneNumber: String) {
        db.historyDao().remove(phoneNumber)
    }
}