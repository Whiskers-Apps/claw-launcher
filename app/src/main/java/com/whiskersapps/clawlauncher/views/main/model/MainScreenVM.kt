package com.whiskersapps.clawlauncher.views.main.model

import android.app.Application
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainScreenVM @Inject constructor(
    private val app: Application
) : ViewModel() {


}