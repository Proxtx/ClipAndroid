package com.proxtx.clip

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proxtx.clip.ui.theme.ClipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClipTheme {
                Surface (modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Main(
                        name = "Android",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Main(name: String, modifier: Modifier = Modifier) {
    Surface (color = Color.Yellow) {
        Column(modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(R.string.hello, name),
                fontSize = 100.sp,
                lineHeight = 116.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(24.dp)
            )

            Text(
                text = "Also $name hi",
                fontSize = 36.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .align(alignment = Alignment.End)
            )

            PImg(modifier = Modifier.align(Alignment.CenterHorizontally), subtitle = "Hi")
        }
    }
}

@Composable
fun PImg(modifier: Modifier = Modifier, subtitle: String = "") {
    val image = painterResource(R.drawable.rastergrafik)
    Box (modifier){
        Image(painter = image, contentDescription = "PFP", contentScale = ContentScale.Crop, alpha = 0.5F)
        Text(text = subtitle, modifier = Modifier
            .fillMaxSize()
            .padding(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ClipTheme {
        Main("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun PImgPreview() {
    ClipTheme {
        PImg(
            subtitle = "Hi"
        )
    }
}
