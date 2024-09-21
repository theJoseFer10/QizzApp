/*Creamos un ViewModel en el cual se va a almacenar la información que mostraremos en el MainActiviy
de esta manera la información no se pierde al momento de girar la pantalla.*/
package arenas.jose.qizzapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURREN_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS CHEATER KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle):ViewModel() {
    /*Creamos el banco de preguntas, el cual se va a encargar de almacenar las preguntas y respuestas que se van a
    mostrar en el MainActivity.*/
    val banco_de_preguntas = listOf(
        Question(R.string.Cambio_carrera, false),
        Question(R.string.Oceano, true),
        Question(R.string.Texto_pregunta, true),
        Question(R.string.Rio_Nilo, false),
        Question(R.string.Amlo, true),
        Question(R.string.trabajo, false)
    )

    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    private var currentIndex: Int
        //Al iniciar la app, el curren index sera nulo y con el get el valor de curren index sera 0
        get()= savedStateHandle.get(CURREN_INDEX_KEY) ?: 0
        //Guardamos el valor actual del curren index. su valor se guarda automaticamente cuando el valor del
        //index de actualiza.
        set(value) = savedStateHandle.set(CURREN_INDEX_KEY, value)

    /*Creamos una variable de tipo boleano que va a almacenar la respuesta de la pregunta actual utilizando el
    index del banco de preguntas.*/
    val currentQuestionAnswer: Boolean
        get() = banco_de_preguntas[currentIndex].answer

    /*Hacemos la mismo con el texto de las preguntas.*/
    val currentQuestionText: Int
        get() = banco_de_preguntas[currentIndex].textResId

    //Creamos una variable que nos servirá como contador.
    private val _contador = MutableLiveData(0)
    val contador: MutableLiveData<Int> = _contador

    //Hacemos la forma de recoreer el banco de preguntas hacia adelante.
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % banco_de_preguntas.size
    }

    //Hacemos la forma de recoreer el banco de preguntas hacia atras.
    fun moveToBack() {
        if (currentIndex == 0) {
            currentIndex = banco_de_preguntas.size - 1
        } else {
            currentIndex = (currentIndex - 1) % banco_de_preguntas.size
        }
    }

    //Hacemos la forma de actualizar el contador.
    fun actualizar_contador(){
        _contador.value = (_contador.value ?: 0) + 1
    }
}