package koin.sampleapp.service.json

import weather.lyra.com.weatherapp.model.geocode.Geocode
import weather.lyra.com.weatherapp.model.geocode.Location


fun Geocode.getLocation(): Location? = results.firstOrNull()?.geometry?.location