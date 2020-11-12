package es.iessaladillo.pedrojoya.intents.ui.battle


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.intents.data.local.Database.getPokemonById
import es.iessaladillo.pedrojoya.intents.data.local.Database.getRandomPokemon
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.BattleActivityBinding
import es.iessaladillo.pedrojoya.intents.ui.selection.SelectionActivity
import es.iessaladillo.pedrojoya.intents.ui.winner.WinnerActivity
private const val RC_SELECTION: Int = 1
class BattleActivity : AppCompatActivity() {


    private lateinit var binding: BattleActivityBinding
    private lateinit var pokemon: Pokemon
    private lateinit var pokemon2: Pokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BattleActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialStage()
        setupViews()


        // TODO
    }

    private fun initialStage() {
        pokemon = getRandomPokemon()
        pokemon2 = getRandomPokemon()
        binding.imgBattlePokemon.setImageDrawable(getDrawable(pokemon.icon))
        binding.imgBattlePokemon2.setImageDrawable(getDrawable(pokemon2.icon))
        binding.lblBattlePokemon.setText(pokemon.name)
        binding.lblBattlePokemon2.setText(pokemon2.name)
    }


    private fun setupViews() {
        binding.linear1.setOnClickListener { selectionActivityLayout(pokemon,1) }
        binding.linear2.setOnClickListener { selectionActivityLayout(pokemon2,2) }
        binding.btnFight.setOnClickListener { winnerActivityLayout() }
    }

    private fun winnerActivityLayout() {
        val intent = WinnerActivity.newIntent(this,pokemon.id,pokemon2.id)
        startActivity(intent)
    }

    private fun selectionActivityLayout(pokemon: Pokemon,screen:Int) {
        val intent = SelectionActivity.newIntent(this,pokemon.id,screen)
        startActivityForResult(intent, RC_SELECTION)
    }

    private fun extractResult(intent: Intent) {
        if (!intent.hasExtra(SelectionActivity.ID)||!intent.hasExtra(SelectionActivity.SCREEN)){
            throw RuntimeException(
                "DateSelectionActivity must receive day, month and year in result intent")
        }
        if(intent.getIntExtra(SelectionActivity.SCREEN,0)==1){
            pokemon= getPokemonById(intent.getIntExtra(SelectionActivity.ID,0).toLong())!!
        }else{
            pokemon2= getPokemonById(intent.getIntExtra(SelectionActivity.ID,0).toLong())!!
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == RESULT_OK && requestCode == RC_SELECTION && intent != null) {
            extractResult(intent)
            changePokemon()
        }
    }

    private fun changePokemon() {
        binding.lblBattlePokemon.setText(pokemon.name)
        binding.imgBattlePokemon.setImageDrawable(getDrawable(pokemon.icon))
        binding.lblBattlePokemon2.setText(pokemon2.name)
        binding.imgBattlePokemon2.setImageDrawable(getDrawable(pokemon2.icon))
    }

}