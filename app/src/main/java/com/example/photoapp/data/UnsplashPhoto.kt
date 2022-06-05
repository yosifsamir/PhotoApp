package com.example.photoapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashPhoto (val id:String
                          ,val description:String?
                          ,val urls:UnsplashPhotoUrl):Parcelable
