package id.ak.findonwhatsapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import id.ak.findonwhatsapp.db.SearchHistory
import id.ak.findonwhatsapp.utils.PhoneNumberSeparatorVisualTransformation
import id.ak.findonwhatsapp.utils.formatAsPhoneNumber
import id.ak.findonwhatsapp.utils.toDateString
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(modifier: Modifier = Modifier, openWhatsApp: (String) -> Unit) {
    val viewModel = viewModel<MainViewModel>()
    val recentPhoneNumbers by viewModel.recentPhoneNumbers
        .collectAsStateWithLifecycle(initialValue = listOf())

    val coroutineScope= rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    val (phoneNumber, setPhoneNumber) = remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.whatsAppUrl) {
        if (viewModel.whatsAppUrl.isNotEmpty()) {
            openWhatsApp(viewModel.whatsAppUrl)
        }
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        enabled = recentPhoneNumbers.isNotEmpty(),
                        onClick = { showBottomSheet = true }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_history_24px),
                            contentDescription = "history"
                        )
                    }
                },
                floatingActionButton = {
                    if (phoneNumber.isNotEmpty()) {
                        FloatingActionButton(onClick = {
                            viewModel.addPhoneNumber(phoneNumber)
                            viewModel.generateWhatsAppUrl(phoneNumber)
                        }) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "search"
                            )
                        }
                    }
                }
            )
        }
    ) {
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                SearchHistoryItem(
                    phoneNumbers = recentPhoneNumbers,
                    onRemove = viewModel::removePhoneNumber,
                    onSelect = { phoneNumber ->
                        viewModel.addPhoneNumber(phoneNumber)
                        viewModel.generateWhatsAppUrl(phoneNumber)
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = setPhoneNumber,
                singleLine = true,
                label = { Text("Nomor HP") },
                prefix = { Text("+62") },
                trailingIcon = {
                    if (phoneNumber.isNotEmpty()) {
                        IconButton(onClick = { setPhoneNumber("") }) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "clear"
                            )
                        }
                    }
                },
                keyboardActions = KeyboardActions(
                    onGo = {
                        viewModel.addPhoneNumber(phoneNumber)
                        viewModel.generateWhatsAppUrl(phoneNumber)
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Go
                ),
                visualTransformation = PhoneNumberSeparatorVisualTransformation()
            )
        }
    }
}

@Composable
fun SearchHistoryItem(
    modifier: Modifier = Modifier,
    phoneNumbers: List<SearchHistory>,
    onRemove: (String) -> Unit,
    onSelect: (String) -> Unit
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(vertical = 16.dp)) {
        items(phoneNumbers) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelect(it.phoneNumber.formatAsPhoneNumber()) }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        it.phoneNumber,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        it.updatedAt.toDateString(),
                        fontSize = 14.sp
                    )
                }
                IconButton(onClick = { onRemove(it.phoneNumber) }) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "delete",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}