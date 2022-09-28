package com.orogersilva.myweatherforecast.daily.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orogersilva.myweatherforecast.daily.data.repository.DailyWeatherForecastRepository
import com.orogersilva.myweatherforecast.data.domain.Result
import com.orogersilva.myweatherforecast.data.domain.model.DailyWeatherForecast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyWeatherForecastViewModel @Inject constructor(
    private val dailyWeatherForecastRepository: DailyWeatherForecastRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DailyWeatherForecastViewState())

    val uiState: StateFlow<DailyWeatherForecastViewState>
        get() = _uiState.asStateFlow()

    fun loadDailyWeatherForecast(
        latitude: Double,
        longitude: Double,
        startDateStr: String,
        endDateStr: String
    ) {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                isLoadingDailyWeatherForecast = true
            )
        }

        viewModelScope.launch {
            dailyWeatherForecastRepository.getDailyForecast(
                latitude,
                longitude,
                startDateStr,
                endDateStr
            ).catch { e -> Result.Error(Exception(e)) }
                .collect { dailyWeatherForecast ->
                    _uiState.update { currentUiState ->

                        currentUiState.copy(
                            dailyWeatherForecast = if (dailyWeatherForecast is Result.Success) {
                                dailyWeatherForecast.data
                            } else {
                                null
                            },
                            isRequiredInitialDailyWeatherForecastLoad = false,
                            isLoadingDailyWeatherForecast = false,
                            hasError = dailyWeatherForecast !is Result.Success
                        )
                    }
                }
        }
    }

    data class DailyWeatherForecastViewState(
        val dailyWeatherForecast: DailyWeatherForecast? = null,
        val isRequiredInitialDailyWeatherForecastLoad: Boolean = true,
        val isLoadingDailyWeatherForecast: Boolean = false,
        val hasError: Boolean = false
    )
}
