package arenas.jose.qizzapp

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.*
import org.junit.Test

class QuizViewModelTest{
    @Test
    fun providesExpectedQuestionText(){
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.Cambio_carrera, quizViewModel.currentQuestionText)
        quizViewModel.moveToNext()
        assertEquals(R.string.Oceano, quizViewModel.currentQuestionText)
    }
}