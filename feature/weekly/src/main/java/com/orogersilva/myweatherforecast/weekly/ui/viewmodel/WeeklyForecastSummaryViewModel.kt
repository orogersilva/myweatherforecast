package com.orogersilva.myweatherforecast.weekly.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orogersilva.myweatherforecast.data.domain.Result
import com.orogersilva.myweatherforecast.data.domain.model.WeatherForecast
import com.orogersilva.myweatherforecast.weekly.data.repository.WeeklyWeatherForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeeklyForecastSummaryViewModel @Inject constructor(
    private val weeklyWeatherForecastRepository: WeeklyWeatherForecastRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeeklyWeatherForecastSummaryViewState())

    val uiState: StateFlow<WeeklyWeatherForecastSummaryViewState>
        get() = _uiState.asStateFlow()

    fun loadWeeklyWeatherForecastSummary(latitude: Double, longitude: Double) {

        _uiState.update { currentUiState ->
            currentUiState.copy(
                isLoadingWeeklyWeatherForecastSummary = true
            )
        }

        viewModelScope.launch {

            weeklyWeatherForecastRepository.getWeeklyForecast(latitude, longitude)
                .catch { e -> Result.Error(Exception(e)) }
                .collect { weatherForecasts ->
                    _uiState.update { currentUiState ->
                        currentUiState.copy(
                            weatherForecasts = if (weatherForecasts is Result.Success) {
                                weatherForecasts.data.toMutableList()
                            } else {
                                null
                            },
                            isLoadingWeeklyWeatherForecastSummary = false,
                            hasError = weatherForecasts !is Result.Success
                        )
                    }
                }
        }
    }

    data class WeeklyWeatherForecastSummaryViewState(
        val weatherForecasts: MutableList<WeatherForecast>? = null,
        val isLoadingWeeklyWeatherForecastSummary: Boolean = false,
        val hasError: Boolean = false
    )
}