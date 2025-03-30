package com.example.generovanilesa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.generovanilesa.ui.theme.GenerovaniLesaTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch(Dispatchers.IO) {
            val game = Game()
            game.run()
        }

        setContent {
            AppPreview()
        }
    }
}

@Composable
fun AppPreview() {
    Text("Hra běží v konzoli (Logcat).")
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    AppPreview()
}