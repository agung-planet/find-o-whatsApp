package id.ak.findonwhatsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import id.ak.findonwhatsapp.ui.theme.FindOnWhatsAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FindOnWhatsAppTheme {
                MainContent {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(it)
                    startActivity(intent)
                }
            }
        }
    }
}
