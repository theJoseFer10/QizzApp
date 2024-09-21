package arenas.jose.qizzapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import arenas.jose.qizzapp.databinding.ActivityCheatBinding

val EXTRA_ANSWARE_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"
private const val EXTRA_ANSWARE_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"

class CheatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheatBinding
    private var answareIsTrue = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_cheat)
        binding=ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        answareIsTrue = intent.getBooleanExtra(EXTRA_ANSWARE_IS_TRUE, false)

        binding.showAnswareButton.setOnClickListener(){
            val answareText = when{
                answareIsTrue -> R.string.True_Button
                else -> R.string.False_Button
            }
            binding.answareTextView.setText(answareText)
            setAnswareShowResult(true)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setAnswareShowResult(isAswareShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWARE_SHOWN, isAswareShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object{
        fun newIntent (packageContext: Context, answareIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWARE_IS_TRUE, answareIsTrue)
            }
        }
    }
}