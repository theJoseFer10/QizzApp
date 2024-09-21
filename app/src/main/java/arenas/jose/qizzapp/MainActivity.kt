package arenas.jose.qizzapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import arenas.jose.qizzapp.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()/*-->Es un delegado que se encarga de crear
    las instacias del ViewModel. El cual no se va a utilizar hasta que llamemos a un elemento del viewmodel,
    ya sea con el tag o con una variable.*/

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
    // Handle the result
        if (result.resultCode == Activity.RESULT_OK){
            quizViewModel.isCheater=
                result.data?.getBooleanExtra(EXTRA_ANSWARE_SHOWN, false)?:false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.count)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Observador del contador
        quizViewModel.contador.observe(this) { nuevoContador ->
            binding.textView.text = nuevoContador.toString()
        }


        // Configurar los listeners de clic para los botones
        binding.trueButton.setOnClickListener() { view: View ->
            checkAnswer(true)
        }

        // Configurar los listeners de clic para los botones
        binding.falseButton.setOnClickListener() { view: View ->
             checkAnswer(false)
        }

        // Configurar los listeners de clic para los botones
        binding.nextButton.setOnClickListener() { view: View ->
            quizViewModel.moveToNext()
            updateQuestion()
        }

        binding.cheatBuuton.setOnClickListener(){
            //Start cheatActivity.
            //val intent = Intent(this, CheatActivity::class.java)
            val answareIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answareIsTrue)
            //startActivity(intent)
            cheatLauncher.launch(intent)
        }

        // Configurar los listeners de clic para los botones
        binding.backButton.setOnClickListener() { view: View ->
            quizViewModel.moveToBack()
            updateQuestion()
        }

        // Actualizar la pregunta inicial
        updateQuestion()
        // Configurar el listener de clic para el botón de cierre
        Log.d(TAG, "Pasé por el metodo onCreate")
        Log.d(TAG, "Tengo un QuizViewModel: $quizViewModel")

    }

    // Actualizar la pregunta en el TextView
    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.textoPregunta.setText(questionTextResId)
    }

    // Verificar la respuesta del usuario
    private fun checkAnswer(userAnswer: Boolean) {
        // Obtener la respuesta correcta
        val correctAnswer = quizViewModel.currentQuestionAnswer

        // Verificar si la respuesta es correcta
        if (userAnswer == correctAnswer) {
            quizViewModel.actualizar_contador() // Incrementa el contador si la respuesta es correcta
        }

        /*
        // Mostrar un mensaje Toast en función de la respuesta
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.Correct_toast
        } else {
            R.string.Incorrect_toast
        }*/

        val messageRestId = when{
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.Correct_toast
            else -> R.string.Incorrect_toast

        }
        Toast.makeText(this, messageRestId, Toast.LENGTH_SHORT).show()
    }

}