package com.example.movietv.di.modules

import com.example.movietv.data.remote.api.ApiService
import com.example.movietv.data.remote.api.RestClient
import dagger.Module
import dagger.Provides

@Module
class RemoteModule {
    @Provides
    fun binApiService(restClient: RestClient) : ApiService{
        return restClient.getClient()
    }

}