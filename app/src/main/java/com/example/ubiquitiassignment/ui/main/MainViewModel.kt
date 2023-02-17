package com.example.ubiquitiassignment.ui.main

import androidx.lifecycle.ViewModel
import com.example.ubiquitiassignment.repository.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
): ViewModel() {

}