package es.iessaladillo.pedrojoya.intents.ui.battle


import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.intents.data.local.Database.getPokemonById
import es.iessaladillo.pedrojoya.intents.data.local.Database.getRandomPokemon
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.BattleActivityBinding
import es.iessaladillo.pedrojoya.intents.ui.selection.SelectionActivity
import es.iessaladillo.pedrojoya.intents.ui.winner.WinnerActivity

private const val RC_SELECTION: Int = 1

class BattleActivity : AppCompatActivity() {


    private lateinit var binding: BattleActivityBinding
    private val viewModel: BattleActivityViewModel by viewModels()
    private var selectionActivityCall =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val intentResult = it.data
            if (it.resultCode == RESULT_OK && intentResult != null) {
                extractResult(intentResult)

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BattleActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        savePokemon()
    }




    private fun setupViews() {
        binding.linear1.setOnClickListener { selectionActivityLayout(viewModel.pokemon.value as Pokemon, 1) }
        binding.linear2.setOnClickListener { selectionActivityLayout(viewModel.pokemon2.value as Pokemon, 2) }
        binding.btnFight.setOnClickListener { viewModel.winnerActivityLayout(this) }
    }

    private fun savePokemon() {
        viewModel.pokemon.observe(this,
            { setPokemon(it, 1) })

        viewModel.pokemon2.observe(this, {
            setPokemon(it, 2)
        })

    }

    private fun setPokemon(poke: Pokemon, num: Int) {
        if (num == 1) {
            binding.imgBattlePokemon.setImageDrawable(getDrawable(poke.icon))
            binding.lblBattlePokemon.setText(poke.name)
        } else {
            binding.imgBattlePokemon2.setImageDrawable(getDrawable(poke.icon))
            binding.lblBattlePokemon2.setText(poke.name)
        }
    }




    private fun selectionActivityLayout(pokemon: Pokemon, screen: Int) {
        val intent = SelectionActivity.newIntent(this, pokemon, screen)
        selectionActivityCall.launch(intent)
    }

    private fun extractResult(intent: Intent) {
        if (!intent.hasExtra(SelectionActivity.EXTRA_POKEMON) || !intent.hasExtra(SelectionActivity.SCREEN)) {
            throw RuntimeException(
                "DateSelectionActivity must receive day, month and year in result intent")
        }
        if (intent.getIntExtra(SelectionActivity.SCREEN, 0) == 1) {
            viewModel.viewModelChange(intent.getParcelableExtra<Pokemon>(SelectionActivity.EXTRA_POKEMON) as Pokemon)
        } else {
            viewModel.viewModelChange2(intent.getParcelableExtra<Pokemon>(SelectionActivity.EXTRA_POKEMON) as Pokemon)

        }

    }



}