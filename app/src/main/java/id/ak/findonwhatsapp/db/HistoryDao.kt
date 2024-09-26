package id.ak.findonwhatsapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM searchhistory ORDER BY updatedAt")
    fun getRecentSearches(): Flow<List<SearchHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: SearchHistory)

    @Query("DELETE FROM searchhistory WHERE phoneNumber = :phoneNumber")
    suspend fun remove(phoneNumber: String)
}