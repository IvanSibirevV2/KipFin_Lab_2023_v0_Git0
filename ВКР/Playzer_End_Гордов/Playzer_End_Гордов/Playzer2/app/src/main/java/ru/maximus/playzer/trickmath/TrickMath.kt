package ru.maximus.playzer.trickmath

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import ru.maximus.playzer.R
import kotlin.random.Random

class TrickMath : AppCompatActivity() {

    var score: Long = 1
    var i= 1
    val complexity1 = Array(100,{i++})

    private lateinit var lefttext:TextView
    private lateinit var righttext:TextView
    private lateinit var Scere:TextView
    val peopleToCarsOwned = mapOf(
        "98 - 74" to 24,
        "39 + 7" to 46,
        "99 - 99" to 0,
        "55 - 34" to 21,
        "67 - 33" to 34,
        "12 + 34" to 46,
        "95 - 8" to 87,	"64 - 29" to 35,	"38 - 16" to 22,	"19 + 48" to 67,	"24 + 37" to 61,	"14 + 39" to 53,
        "92 - 23" to 69,	"39 - 15" to 93,	"92 - 48" to 44,	"30 + 47" to 77,	"23 + 76" to 99,	"12 + 35" to 47,
        "36 + 35" to 71,	"84 - 23" to 61,	"13 + 61" to 74,	"63 - 58" to 5,	"24 + 40" to 64,	"68 - 6" to 62,
    )

    var prob: Array<String> = peopleToCarsOwned.keys.toTypedArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trick_math)

        //Scere.text = score.toString()

        lefttext = findViewById<TextView>(R.id.leftView)
        righttext = findViewById<TextView>(R.id.rightView)
        Scere = findViewById<TextView>(R.id.Scere)

        game()
        var leftbuten = findViewById<Button>(R.id.leftrite)
        leftbuten.setOnClickListener {
            if (score <= 20){
                if(lefttext.text.toString().toInt() > righttext.text.toString().toInt()){
                    Scere.text = score++.toString()
                    game()

                }
                else{
                    score = 1
                    Scere.text = score.toString()
                    game()
                }
            }
            else if(score > 20){

                var a = (peopleToCarsOwned[lefttext.text])
                var b = (peopleToCarsOwned[righttext.text])

                if (a != null && b != null) {
                    if(a > b){
                        Scere.text = score++.toString()
                        game()

                    } else{
                        score = 1
                        Scere.text = score.toString()
                        game()
                    }
                }


            }

        }
        var rightbuten = findViewById<Button>(R.id.rightrite)
        rightbuten.setOnClickListener {
            if (score <= 20){
                if(lefttext.text.toString().toInt() < righttext.text.toString().toInt()){
                    Scere.text = score++.toString()
                    game()


                }
                else{
                    score = 1
                    Scere.text = score.toString()
                    game()
                }
            }
            else if(score > 20){

                var a = (peopleToCarsOwned[lefttext.text])
                var b = (peopleToCarsOwned[righttext.text])

                if (a != null && b != null) {
                    if(a < b){
                        game()
                        Scere.text = score++.toString()
                    } else{
                        score = 1
                        Scere.text = score.toString()
                        game()
                    }
                }


            }

        }
        var equalsbten= findViewById<Button>(R.id.equals)
        equalsbten.setOnClickListener {
            if (score <= 20){
                if(lefttext.text.toString().toInt() == righttext.text.toString().toInt()){
                    Scere.text = score++.toString()
                    game()

                }
                else{
                    score = 1
                    Scere.text = score.toString()
                    game()
                }
            }
            else if(score > 20 ){

                var a = (peopleToCarsOwned[lefttext.text])
                var b = (peopleToCarsOwned[righttext.text])

                if(a == b){
                    Scere.text = score++.toString()
                    game()

                }
                else{
                    score = 1
                    Scere.text = score.toString()
                    game()
                }


            }

        }

    }

    fun game(){
        if (score <= 20){
            var rand = Random.nextInt(complexity1.size)
            lefttext.text = complexity1[rand].toString()
            var rand2 = Random.nextInt(complexity1.size)
            righttext.text = complexity1[rand2].toString()
        }


         else if(score > 20 ){
            var rand = Random.nextInt(peopleToCarsOwned.size)
            var rand1 = Random.nextInt(peopleToCarsOwned.size)
            lefttext.text = prob[rand].toString()
            righttext.text = prob[rand1].toString()
        }


}
}