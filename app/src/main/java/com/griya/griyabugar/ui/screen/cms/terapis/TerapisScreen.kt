package com.griya.griyabugar.ui.screen.cms.terapis

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.DataTerapis
import com.griya.griyabugar.ui.components.dialog.QuestionDialog
import com.griya.griyabugar.ui.components.loading.LoadingAnimation2
import com.griya.griyabugar.ui.components.terapis.CardItemTerapis
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.screen.SharedViewModel
import com.griya.griyabugar.ui.screen.cms.terapis.previewterapis.TerapisViewModel
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TerapisScreen(
    innerPadding: PaddingValues,
    rootNavController: NavHostController = rememberNavController(),
    terapisViewModel: TerapisViewModel = hiltViewModel(),
    scope: CoroutineScope = rememberCoroutineScope()
){
    val context = LocalContext.current

    val sharedViewModel: SharedViewModel = hiltViewModel(context as ComponentActivity)

    val dataTerapis: List<DataTerapis> by terapisViewModel.dataTerapis.collectAsState()

    val isLoading by terapisViewModel.isLoadingTerapis.collectAsState()

    val isError by terapisViewModel.isErrorTerapis.collectAsState()
    val errorMessage by terapisViewModel.errorMessageTerapis.collectAsState()

    var showQuestionDeleteDialog by rememberSaveable { mutableStateOf(false) }
    var documentId by rememberSaveable { mutableStateOf("") }

    if (showQuestionDeleteDialog){
        QuestionDialog(
            onDismiss = {},
            title = "Apakah kamu yakin?",
            description = "Apakah kamu yakin ingin menghapusnya?",
            btnClickNo = {
                showQuestionDeleteDialog = false
            },
            btnClickYes = {
                scope.launch {
                    terapisViewModel.deleteTerapis(id = documentId).collect { event ->
                        when(event){
                            is Resource.Loading -> {
                                Toast.makeText(context, "Proses delete", Toast.LENGTH_SHORT).show()
                            }
                            is Resource.Success -> {
                                Toast.makeText(context, event.data, Toast.LENGTH_SHORT).show()
                            }
                            is Resource.Error -> {
                                Toast.makeText(context, event.errorMessage, Toast.LENGTH_LONG).show()
                            }
                            else -> {}
                        }
                    }
                }
                showQuestionDeleteDialog = false
            }
        )
    }

    LaunchedEffect(Unit){

        terapisViewModel.getAllTerapis()

        if(isError){
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }



    if (isLoading){
        LoadingAnimation2()
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 32.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                dataTerapis.forEach {
                    CardItemTerapis(
                        items = it,
                        onClickEdit = {
                            rootNavController.navigate(Screen.EditTerapis.createRoute(it.id ?: ""))
                        },
                        onClickDelete = {
                            documentId = it.id ?: ""
                            showQuestionDeleteDialog = true
                        }
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TerapisPreview(){
    GriyaBugarTheme {
        TerapisScreen(
            innerPadding = PaddingValues(0.dp),
        )
    }
}