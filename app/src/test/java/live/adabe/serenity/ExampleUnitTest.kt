package live.adabe.serenity

import live.adabe.serenity.utils.milliSecondsToTimer
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `test format string`(){
        val msec = 2120000L
        val result = milliSecondsToTimer(msec)
        assertEquals(result, "")
    }
}