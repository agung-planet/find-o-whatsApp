package id.ak.findonwhatsapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ak.findonwhatsapp.domain.AddSearchHistory
import id.ak.findonwhatsapp.domain.GetRecentSearches
import id.ak.findonwhatsapp.domain.RemoveSearchHistory
import id.ak.findonwhatsapp.utils.formatAsPhoneNumber
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val addSearchHistory: AddSearchHistory,
    private val removeSearchHistory: RemoveSearchHistory,
    getRecentSearches: GetRecentSearches
) : ViewModel() {
    val recentPhoneNumbers = getRecentSearches.result
    var whatsAppUrl by mutableStateOf("")
        private set

    fun addPhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            addSearchHistory(phoneNumber)
        }
    }

    fun removePhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            removeSearchHistory(phoneNumber)
        }
    }

    fun generateWhatsAppUrl(phoneNumber: String) {
        whatsAppUrl = "https://wa.me/62$phoneNumber"
        viewModelScope.launch {
            delay(300)
            whatsAppUrl = ""
        }
    }
}