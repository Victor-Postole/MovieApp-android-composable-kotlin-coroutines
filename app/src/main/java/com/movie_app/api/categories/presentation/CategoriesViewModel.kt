package com.movie_app.api.categories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movie_app.api.categories.domain.repository.CategoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {

    private val _categoriesState = MutableStateFlow(CategoriesState())
    val categoriesState = _categoriesState.asStateFlow()

    init {
        viewModelScope.launch {
            _categoriesState.update {
                it.copy(
                    actionAndAdventureList = categoriesRepository.getActionAndAdventure(),
                    dramaList = categoriesRepository.getDrama(),
                    comedyList = categoriesRepository.getComedy(),
                    sciFiAndFantasyList = categoriesRepository.getSciFiAndFantasy(),
                    animationList = categoriesRepository.getAnimation(),
                )
            }
        }
    }
}








