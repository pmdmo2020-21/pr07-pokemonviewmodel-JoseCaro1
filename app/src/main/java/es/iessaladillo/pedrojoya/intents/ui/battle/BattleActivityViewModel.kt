package es.iessaladillo.pedrojoya.intents.ui.battle

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.intents.data.local.Database.getRandomPokemon
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.ui.winner.WinnerActivity
private const val STATE_POKEMON = "EXTRA_POKEMON"
private const val STATE_POKEMON2 = "EXTRA_POKEMON2"

class BattleActivityViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private var _pokemon: MutableLiveData<Pokemon> = savedStateHandle.getLiveData(STATE_POKEMON,
        getRandomPokemon())
    val pokemon: LiveData<Pokemon>
        get() = _pokemon
    private var _pokemon2: MutableLiveData<Pokemon> = savedStateHandle.getLiveData(STATE_POKEMON2,
        getRandomPokemon())
    val pokemon2: LiveData<Pokemon>
        get() = _pokemon2


    fun viewModelChange(pokemonChange: Pokemon) {
        _pokemon.value = pokemonChange
    }

    fun viewModelChange2(pokemonChange: Pokemon) {
        _pokemon2.value = pokemonChange
    }

    fun winnerActivityLayout(context: Context) {

        var pokemon1: Pokemon = if (pokemon.value!!.force > pokemon2.value!!.force) {
            pokemon.value as Pokemon
        } else {
            pokemon2.value as Pokemon
        }

        val intent = WinnerActivity.newIntent(context,
            pokemon1)
        context.startActivity(intent)
    }

}