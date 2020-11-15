package es.iessaladillo.pedrojoya.intents.data.local.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// TODO: Define las propiedades de un pokemon
@Parcelize
data class Pokemon(val id:Int,val name: Int,val icon:Int,val force :Int) : Parcelable {


}


