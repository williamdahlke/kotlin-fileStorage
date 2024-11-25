package dahlke.br.filestorage

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class MainActivity : AppCompatActivity() {

    private val fileName = "myFile.txt"
    private lateinit var editText : EditText
    private lateinit var tvContent : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        tvContent = findViewById(R.id.tvTextoSalvo)
        val savedText = fileRead()
        if (savedText.isNotEmpty())
            tvContent.text = savedText

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun fileRead(): String {
        return if (File(filesDir, fileName).exists()){
            openFileInput(fileName).bufferedReader().useLines {
                lines -> lines.fold(""){ some, text -> if (some.isNotEmpty()) "$some\n$text" else text } }
        } else{
            ""
        }
    }

    fun saveToFile(view : View){
        openFileOutput(fileName, MODE_PRIVATE).use {
            it.write(editText.text.toString().toByteArray())
        }
        tvContent.text = fileRead()
    }
}