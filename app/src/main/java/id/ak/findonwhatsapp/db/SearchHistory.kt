package id.ak.findonwhatsapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class SearchHistory(
    @PrimaryKey val phoneNumber: String,
    val updatedAt: Long = System.currentTimeMillis()
)
