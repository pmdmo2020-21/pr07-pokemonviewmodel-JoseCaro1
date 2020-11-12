package es.iessaladillo.pedrojoya.intents.ui.selection

import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.get
import es.iessaladillo.pedrojoya.intents.R
import es.iessaladillo.pedrojoya.intents.data.local.Database.getAllPokemons
import es.iessaladillo.pedrojoya.intents.data.local.Database.getPokemonById
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.SelectionActivityBinding

class SelectionActivity : AppCompatActivity() {

    companion object {
        const val ID = "POKEMON_ID"
        const val SCREEN = "SCREEN"


        fun newIntent(context: Context, pokemonId: Int, typeScreen: Int): Intent =
            Intent(context, SelectionActivity::class.java)
                .putExtras(bundleOf(ID to pokemonId, SCREEN to typeScreen))
    }

    private lateinit var binding: SelectionActivityBinding
    private lateinit var myPokemon: Pokemon
    private var num = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SelectionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        initialStage()
        setupViews()
    }

    private fun initialStage() {
        tagAssign()
        checkPokemon()

    }


    private fun setupViews() {
        binding.rdgPokemon1.setOnCheckedChangeListener { group, checkedId ->
            clearCheck(
                group,
                checkedId,
            )
        }
        binding.rdgPokemon2.setOnCheckedChangeListener { group, checkedId ->
            clearCheck(
                group,
                checkedId,
            )
        }
        binding.imgSelectionPokemon.setOnClickListener { v -> checkRadioButton(v) }
        binding.imgSelectionPokemon2.setOnClickListener { v -> checkRadioButton(v) }
        binding.imgSelectionPokemon3.setOnClickListener { v -> checkRadioButton(v) }
        binding.imgSelectionPokemon4.setOnClickListener { v -> checkRadioButton(v) }
        binding.imgSelectionPokemon5.setOnClickListener { v -> checkRadioButton(v) }
        binding.imgSelectionPokemon6.setOnClickListener { v -> checkRadioButton(v) }


    }

    private fun checkRadioButton(v: View?) {
        var img: ImageView = v as ImageView
        var button: RadioButton = img.tag as RadioButton
        binding.rdgPokemon2.clearCheck()
        binding.rdgPokemon1.clearCheck()
        for (i in 0 until binding.rdgPokemon1.childCount) {
            if (binding.rdgPokemon1[i].id == button.id) {
                binding.rdgPokemon1.check(button.id)

            } else {
                binding.rdgPokemon2.check(button.id)

            }
        }
    }


    private fun clearCheck(group: RadioGroup, checkedId: Int) {

        for (i in 0 until group.childCount) {
            if (group[i].id == checkedId) {
                myPokemon = group[i].tag as Pokemon
            }
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
        if (intent == null || !intent.hasExtra(ID) || !intent.hasExtra(SCREEN)) {
            throw RuntimeException(
                "SelectionActivity needs a pokemon")
        }
        myPokemon = getPokemonById(intent.getIntExtra(ID, 0).toLong())!!
        num = intent.getIntExtra(SCREEN, 0)

    }

    private fun checkPokemon() {
        var myPokemon2: Pokemon
        for (i in 0 until binding.rdgPokemon1.childCount) {
            myPokemon2 = if (binding.rdgPokemon1[i].tag is Pokemon) {
                binding.rdgPokemon1[i].tag as Pokemon
            } else {
                binding.rdgPokemon1[i + 1].tag as Pokemon
            }
            if (myPokemon2.id == myPokemon.id) {
                binding.rdgPokemon1.check(binding.rdgPokemon1[i].id)
            }
        }
        for (i in 0 until binding.rdgPokemon2.childCount) {
            myPokemon2 = if (binding.rdgPokemon2[i].tag is Pokemon) {
                binding.rdgPokemon2[i].tag as Pokemon
            } else {
                binding.rdgPokemon2[i + 1].tag as Pokemon
            }
            if (myPokemon2.id == myPokemon.id) {
                binding.rdgPokemon2.check(binding.rdgPokemon2[i].id)
            }
        }
    }


    override fun onBackPressed() {
        val result = Intent().putExtras(
            bundleOf(ID to myPokemon.id, SCREEN to num))
        setResult(RESULT_OK, result)
        super.onBackPressed()
    }

}