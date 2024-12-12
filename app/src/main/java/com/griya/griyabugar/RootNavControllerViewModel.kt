package com.griya.griyabugar

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RootNavControllerViewModel @Inject constructor() : ViewModel() {
    private val _navController = MutableStateFlow<NavHostController?>(null)
    val navController: StateFlow<NavHostController?> = _navController.asStateFlow()

    fun setNavController(navController: NavHostController){
        _navController.value = navController
    }

    fun navigate(route: String){
        navController.value?.navigate(route) ?: Log.e("NavControllerViewModel", "NavController is null")
    }

    fun popBackStack(){
        navController.value?.popBackStack()
    }
}