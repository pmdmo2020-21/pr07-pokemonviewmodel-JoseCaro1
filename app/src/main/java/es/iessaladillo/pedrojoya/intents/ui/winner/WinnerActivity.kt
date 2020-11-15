package es.iessaladillo.pedrojoya.intents.ui.winner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.intents.R
import es.iessaladillo.pedrojoya.intents.data.local.Database
import es.iessaladillo.pedrojoya.intents.data.local.Database.getPokemonById
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.BattleActivityBinding
import es.iessaladillo.pedrojoya.intents.databinding.WinnerActivityBinding
import es.iessaladillo.pedrojoya.intents.ui.selection.SelectionActivity

class WinnerActivity : AppCompatActivity() {

    companion object {

        const val EXTRA_POKEMON = "POKEMON"


        fun newIntent(context: Context, pokemon: Pokemon): Intent =
            Intent(context, WinnerActivity::class.java)
                .putExtras(bundleOf(EXTRA_POKEMON to pokemon))
    }

    private lateinit var binding: WinnerActivityBinding
    private val viewModel: WinnerActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WinnerActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            getIntentData()
        }
        savePokemon()

    }

    private fun savePokemon() {
        viewModel.pokemon.observe(this,
            { setPokemon(it) })
    }

    private fun setPokemon(it: Pokemon) {
        binding.imgWinnerPokemon.setImageDrawable(getDrawable(it.icon))
        binding.lblWinnerPokemon.setText(it.name)
    }


    private fun getIntentData() {
        if (intent == null || !intent.hasExtra(EXTRA_POKEMON)
        ) {
            throw RuntimeException(
                "winnerActivity needs a pokemon")
        }
        viewModel.viewModelChange(intent.getParcelableExtra<Pokemon>(EXTRA_POKEMON) as Pokemon)


    }

}