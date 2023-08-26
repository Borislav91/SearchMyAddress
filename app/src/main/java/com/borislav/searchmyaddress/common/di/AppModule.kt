package com.borislav.searchmyaddress.common.di

import com.borislav.searchmyaddress.data.repository.SearchMyAddressApiImpl
import com.borislav.searchmyaddress.domain.repository.SearchMyAddressApi
import com.borislav.searchmyaddress.domain.usecase.SearchAddressUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient {
            install(JsonFeature) {
                val json = Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
                serializer = KotlinxSerializer(json)
            }

            // You can add more Ktor features here if needed
        }
    }

    @Singleton
    @Provides
    fun provideSearchAddressUseCase(apiService: SearchMyAddressApi): SearchAddressUseCase {
        return SearchAddressUseCase(apiService)
    }

    @Provides
    @Singleton
    fun provideSearchMyAddressApi(client: HttpClient): SearchMyAddressApi {
        return SearchMyAddressApiImpl(client)
    }

}
