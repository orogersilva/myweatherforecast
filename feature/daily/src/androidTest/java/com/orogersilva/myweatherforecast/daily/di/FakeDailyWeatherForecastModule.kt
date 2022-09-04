package com.orogersilva.myweatherforecast.daily.di

import com.orogersilva.myweatherforecast.daily.data.repository.DailyWeatherForecastRepository
import com.orogersilva.myweatherforecast.daily.data.repository.FakeDailyWeatherForecastRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DailyWeatherForecastModule::class]
)
abstract class FakeDailyWeatherForecastModule {

    @Binds abstract fun bindDailyForecastRepository(
        fakeDailyWeatherForecastRepository: FakeDailyWeatherForecastRepository
    ): DailyWeatherForecastRepository
}
