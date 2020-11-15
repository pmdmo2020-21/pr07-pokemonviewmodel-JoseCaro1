package es.iessaladillo.pedrojoya.intents.ui.winner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.intents.data.local.Database
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon

class WinnerActivityViewModel :ViewModel(){

    private var _pokemon: MutableLiveData<Pokemon> = MutableLiveData()
    val pokemon: LiveData<Pokemon>
        get() = _pokemon


    fun viewModelChange(pokemonChange: Pokemon) {
        _pokemon.value = pokemonChange
    }

}