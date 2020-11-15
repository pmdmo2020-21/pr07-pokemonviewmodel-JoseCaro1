package es.iessaladillo.pedrojoya.intents.ui.selection

import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon

class SelectionActivityViewModel : ViewModel() {

    private var _pokemon: MutableLiveData<Pokemon> = MutableLiveData()
    val pokemon: LiveData<Pokemon>
        get() = _pokemon
    private var _screen: MutableLiveData<Int> = MutableLiveData()
    val screen: LiveData<Int>
        get() = _screen

    fun pokemonChange(pokemonChange: Pokemon) {
        _pokemon.value = pokemonChange
    }
    fun screenChange(numScreen:Int) {
        _screen.value = numScreen
    }
     fun checkRadioButton(v: View?) {
        var img: ImageView = v as ImageView
        var button: RadioButton = img.tag as RadioButton
        pokemonChange(button.tag as Pokemon)
    }
}