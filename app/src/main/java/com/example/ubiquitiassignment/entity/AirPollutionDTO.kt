package com.example.ubiquitiassignment.entity

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AirPollutionDTO(

	@field:SerializedName("total")
	val total: String? = null,

	@field:SerializedName("offset")
	val offset: String? = null,

	@field:SerializedName("_links")
	val links: Links? = null,

	@field:SerializedName("records")
	val records: List<RecordsItem?>? = null,

	@field:SerializedName("resource_format")
	val resourceFormat: String? = null,

	@field:SerializedName("limit")
	val limit: String? = null,

	@field:SerializedName("resource_id")
	val resourceId: String? = null,

	@field:SerializedName("__extras")
	val extras: Extras? = null,

	@field:SerializedName("include_total")
	val includeTotal: Boolean? = null,

	@field:SerializedName("fields")
	val fields: List<FieldsItem?>? = null
) : Parcelable

@Parcelize
data class Links(

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("start")
	val start: String? = null
) : Parcelable

@Parcelize
data class Extras(

	@field:SerializedName("api_key")
	val apiKey: String? = null
) : Parcelable

@Parcelize
data class Info(

	@field:SerializedName("label")
	val label: String? = null
) : Parcelable

@Parcelize
data class FieldsItem(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("info")
	val info: Info? = null
) : Parcelable

@Parcelize
data class RecordsItem(

	@field:SerializedName("pm2.5")
	val pm25: String? = null,

	@field:SerializedName("wind_direc")
	val windDirect: String? = null,

	@field:SerializedName("no")
	val no: String? = null,

	@field:SerializedName("o3_8hr")
	val o38hr: String? = null,

	@field:SerializedName("o3")
	val o3: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("county")
	val county: String? = null,

	@field:SerializedName("pm10")
	val pm10: String? = null,

	@field:SerializedName("so2_avg")
	val so2Avg: String? = null,

	@field:SerializedName("co")
	val co: String? = null,

	@field:SerializedName("pollutant")
	val pollutant: String? = null,

	@field:SerializedName("no2")
	val no2: String? = null,

	@field:SerializedName("pm2.5_avg")
	val pm25Avg: String? = null,

	@field:SerializedName("nox")
	val nox: String? = null,

	@field:SerializedName("pm10_avg")
	val pm10Avg: String? = null,

	@field:SerializedName("so2")
	val so2: String? = null,

	@field:SerializedName("publishtime")
	val publishTime: String? = null,

	@field:SerializedName("co_8hr")
	val co8hr: String? = null,

	@field:SerializedName("sitename")
	val siteName: String? = null,

	@field:SerializedName("aqi")
	val aqi: String? = null,

	@field:SerializedName("siteid")
	val siteId: String? = null,

	@field:SerializedName("wind_speed")
	val windSpeed: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null
) : Parcelable
