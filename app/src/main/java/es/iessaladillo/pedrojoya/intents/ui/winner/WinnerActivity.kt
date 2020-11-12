package es.iessaladillo.pedrojoya.intents.ui.winner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import es.iessaladillo.pedrojoya.intents.R
import es.iessaladillo.pedrojoya.intents.data.local.Database
import es.iessaladillo.pedrojoya.intents.data.local.Database.getPokemonById
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.BattleActivityBinding
import es.iessaladillo.pedrojoya.intents.databinding.WinnerActivityBinding
import es.iessaladillo.pedrojoya.intents.ui.selection.SelectionActivity

class WinnerActivity : AppCompatActivity() {

    companion object {

        const val ID = "POKEMON_ID"
        const val ID2 = "POKEMON_ID2"

        fun newIntent(context: Context, pokemonId: Int, pokemonId2: Int): Intent =
            Intent(context,WinnerActivity::class.java)
                .putExtras(bundleOf(ID to pokemonId, ID2 to pokemonId2))
    }

    private lateinit var myPokemon: Pokemon
    private lateinit var myPokemon2: Pokemon
    private lateinit var binding: WinnerActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WinnerActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        setupViews()

    }

    private fun setupViews() {
        winnerSelect()
    }

    private fun winnerSelect() {
        if(myPokemon.force>myPokemon2.force){
            binding.imgWinnerPokemon.setImageDrawable(getDrawable(myPokemon.icon))
            binding.lblWinnerPokemon.setText(myPokemon.name)
        }else {
            binding.imgWinnerPokemon.setImageDrawable(getDrawable(myPokemon2.icon))
            binding.lblWinnerPokemon.setText(myPokemon2.name)
        }
    }

    private fun getIntentData() {
        if (intent == null || !intent.hasExtra(ID) || !intent.hasExtra(ID2)
        ) {
            throw RuntimeException(
                "winnerActivity needs a pokemon")
        }
        myPokemon =getPokemonById(intent.getIntExtra(ID, 0).toLong())!!
        myPokemon2=getPokemonById(intent.getIntExtra(ID2,1).toLong())!!

    }

}