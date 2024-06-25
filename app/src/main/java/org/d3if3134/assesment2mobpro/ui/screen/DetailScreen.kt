package org.d3if3134.assesment2mobpro.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3134.assesment2mobpro.ui.theme.Assesment2MobproTheme
import org.d3if3134.assesment2mobpro.R
import org.d3if3134.assesment2mobpro.database.BanDb
import org.d3if3134.assesment2mobpro.util.ViewModelFactory


const val KEY_ID_BAN = "idBan"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = BanDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var merk by remember { mutableStateOf("") }
    var jenis by remember { mutableStateOf("") }
    var ukuran by remember { mutableStateOf("") }
    val selectedOptionsIndex = remember { mutableIntStateOf(-1) }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getBan(id) ?: return@LaunchedEffect
        if ( data != null) {
            merk = data.merk
            jenis = data.jenis
            ukuran = data.ukuran
            selectedOptionsIndex.intValue = getSelectedOptionIndex(ukuran)
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_ban))
                    else
                        Text(text = stringResource(id = R.string.edit_ban))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        if (merk == "" || jenis == "" || ukuran == "") {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null) {
                            viewModel.insert(merk, jenis, ukuran)
                        } else {
                            viewModel.update(id, merk, jenis, ukuran)
                        }
                        navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        DeleteAction { showDialog = true}
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = {showDialog = false }) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ){ padding ->
        FormBan(
            merk = merk,
            onMerkChange = {merk = it},
            jenis = jenis,
            onJenisChange = {jenis = it},
            ukuran = ukuran,
            onUkuranChange = { ukuran = it },
            modifier = Modifier.padding(padding)
        )
    }
}

private fun getSelectedOptionIndex(ukuran: String): Int {
    return when (ukuran) {
        "180/60" -> 0
        "170/60" -> 1
        "160/60" -> 2
        "150/60" -> 3
        "140/60" -> 4
        else -> -1
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormBan(
    merk: String, onMerkChange: (String) -> Unit,
    jenis: String, onJenisChange: (String) -> Unit,
    modifier: Modifier,
    ukuran: String,
    onUkuranChange: (String) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp).verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = merk,
            onValueChange = { onMerkChange(it) },
            label = { Text(text = stringResource(id = R.string.merk)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

//      JENIS BAN
        Text(text = stringResource(id = R.string.jenis_ban))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp)),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ){
            val jenisOptions = listOf(
                "PIRELLI DIABLO ROSSO SPORT",
                "PIRELLI DIABLO SUPERCORSA",
                "PIRELLI ANGEL SCOOTER",
                "CORSA PLATINUM R93",
                "CORSA PLATINUM R46",
                "CORSA PLATINUM R26",
            )
            jenisOptions.forEach { jenisOption ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = jenis == jenisOption,
                        onClick = { onJenisChange(jenisOption) },
                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        text = jenisOption,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            }
        }

//      UKURAN BAN
        Text(text = stringResource(id = R.string.ukuran_ban))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp)),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            val options = listOf(
                "180/60" to "17",
                "170/60" to "17",
                "160/60" to "17",
                "150/60" to "17"
            )
            options.forEach { (option, ringVelg) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = ukuran == option,
                        onClick = { onUkuranChange(option) },
                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        text = option,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                    Text(
                        text = "Ring Velg: $ringVelg\"",
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            }
        }
    }
}


@Composable
fun DeleteAction(delete: () -> Unit) {
    var expended by remember {
        mutableStateOf(false)
    }

    IconButton(onClick = { expended = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expended,
            onDismissRequest = { expended = false })
        {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.hapus)) },
                onClick = {
                    expended = false
                    delete()
                })
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    Assesment2MobproTheme {
        DetailScreen(rememberNavController())
    }
}