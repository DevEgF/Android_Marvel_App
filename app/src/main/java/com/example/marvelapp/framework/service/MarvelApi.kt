package com.example.marvelapp.framework.service

import com.example.marvelapp.commons.data.network.response.DataWrapperResponse
import com.example.marvelapp.features.heroes.data.response.CharacterResponse
import com.example.marvelapp.features.heroes.data.response.ComicResponse
import com.example.marvelapp.features.heroes.data.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MarvelApi {

    @GET("characters")
    suspend fun getCharacters(
        @QueryMap
        queries: Map<String, String>
    ): DataWrapperResponse<CharacterResponse>

    @GET("characters/{characterId}/comics")
    suspend fun getComics(
        @Path("characterId")
        characterId: Int
    ): DataWrapperResponse<ComicResponse>

    @GET("characters/{characterId}/events")
    suspend fun getEvents(
        @Path("characterId")
        characterId: Int
    ): DataWrapperResponse<EventResponse>
}