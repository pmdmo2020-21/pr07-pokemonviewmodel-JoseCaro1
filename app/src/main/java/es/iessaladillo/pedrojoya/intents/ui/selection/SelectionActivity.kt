package es.iessaladillo.pedrojoya.intents.ui.selection

import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.intents.R
import es.iessaladillo.pedrojoya.intents.data.local.Database.getAllPokemons
import es.iessaladillo.pedrojoya.intents.data.local.Database.getPokemonById
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.SelectionActivityBinding

class SelectionActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_POKEMON = "POKEMON"
        const val SCREEN = "SCREEN"


        fun newIntent(context: Context, pokemon: Pokemon, typeScreen: Int): Intent =
            Intent(context, SelectionActivity::class.java)
                .putExtras(bundleOf(EXTRA_POKEMON to pokemon, SCREEN to typeScreen))
    }

    private lateinit var binding: SelectionActivityBinding
    private val viewModel: SelectionActivityViewModel by viewModels()
    private lateinit var listPokemon: List<RadioButton>
    private lateinit var listPokemonImg : List<ImageView>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SelectionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            getIntentData()
        }
        initialStage()
        setupViews()
        savePokemon()

    }

    private fun initialStage() {
        listPokemon = listOf(binding.rdbSelectionPokemon,
            binding.rdbSelectionPokemon2,
            binding.rdbSelectionPokemon3,
            binding.rdbSelectionPokemon4,
            binding.rdbSelectionPokemon5,
            binding.rdbSelectionPokemon6)
        listPokemonImg = listOf(binding.imgSelectionPokemon,
            binding.imgSelectionPokemon2,
            binding.imgSelectionPokemon3,
            binding.imgSelectionPokemon4,
            binding.imgSelectionPokemon5,
            binding.imgSelectionPokemon6)
        tagAssign()


    }

    private fun savePokemon() {
        viewModel.pokemon.observe(this,
            { setPokemon(it) })


    }

    private fun setPokemon(poke: Pokemon) {
        for (i in listPokemon.indices){
            listPokemon[i].isChecked=listPokemon[i].tag==poke
        }
    }



    private fun setupViews() {

        for (i in listPokemon.indices){
            listPokemon[i].setOnClickListener{ viewModel.checkRadioButton(listPokemonImg[i])}
            listPokemonImg[i].setOnClickListener { v -> viewModel.checkRadioButton(v) }
        }

    }





    private fun tagAssign() {
        for (pokemon in getAllPokemons()) {

            when (pokemon.name) {
                R.string.bulbasur -> {
                    binding.rdbSelectionPokemon.tag = pokemon
                    binding.imgSelectionPokemon.tag = binding.rdbSelectionPokemon
                }

                R.string.cubone -> {
                    binding.rdbSelectionPokemon2.tag = pokemon
                    binding.imgSelectionPokemon2.tag = binding.rdbSelectionPokemon2

                }
                R.string.feebas -> {
                    binding.rdbSelectionPokemon3.tag = pokemon
                    binding.imgSelectionPokemon3.tag = binding.rdbSelectionPokemon3
                }
                R.string.giratina -> {
                    binding.rdbSelectionPokemon4.tag = pokemon
                    binding.imgSelectionPokemon4.tag = binding.rdbSelectionPokemon4
                }
                R.string.gyarados -> {
                    binding.rdbSelectionPokemon5.tag = pokemon
                    binding.imgSelectionPokemon5.tag = binding.rdbSelectionPokemon5
                }
                R.string.pikachu -> {
                    binding.rdbSelectionPokemon6.tag = pokemon
                    binding.imgSelectionPokemon6.tag = binding.rdbSelectionPokemon6
                }
            }
        }

    }


    private fun getIntentData() {
        if (intent == null || !intent.hasExtra(EXTRA_POKEMON) || !intent.hasExtra(SCREEN)) {
            throw RuntimeException(
                "SelectionActivity needs a pokemon")
        }
        viewModel.pokemonChange(intent.getParcelableExtra<Pokemon>(EXTRA_POKEMON) as Pokemon)
        viewModel.screenChange(intent.getIntExtra(SCREEN, 0))

    }


    override fun onBackPressed() {
        val result = Intent().putExtras(
            bundleOf(EXTRA_POKEMON to viewModel.pokemon.value, SCREEN to viewModel.screen.value))
        setResult(RESULT_OK, result)
        super.onBackPressed()
    }

}