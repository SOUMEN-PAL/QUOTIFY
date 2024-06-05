package com.example.quotify.appscreens

import android.content.Context
import android.graphics.drawable.Icon
import android.util.Log
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quotify.R
import com.example.quotify.ViewModel.MainViewModel

@Composable
fun HomeScreen(viewModel: MainViewModel , ActivityContext: Context){
    val quote = viewModel.quote
    val index = viewModel.index
    Log.d("index" , "${index.intValue} ha yeh")
    val MyFontFamily = FontFamily(
        Font(R.font.montserrat_bold, FontWeight.Bold),
        Font(R.font.montserrat_bolditalic, FontWeight.Bold),
        Font(R.font.montserrat_extrabold, FontWeight.ExtraBold),
        Font(R.font.montserrat_extrabolditalic, FontWeight.ExtraBold),
        Font(R.font.montserrat_extralight, FontWeight.ExtraLight),
        Font(R.font.montserrat_extralightitalic, FontWeight.ExtraLight),
        Font(R.font.montserrat_italic, FontWeight.Normal),
        Font(R.font.montserrat_light, FontWeight.Light),
        Font(R.font.montserrat_lightitalic, FontWeight.Light),
        Font(R.font.montserrat_medium, FontWeight.Medium),
        Font(R.font.montserrat_mediumitalic, FontWeight.Medium),
        Font(R.font.montserrat_regular, FontWeight.Normal)
    )



    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF1D2674), Color(0xFF993366))

                )
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        
        Spacer(modifier = Modifier.height(100.dp))
        
        Text(text = "QUOTIFY", fontSize = 40.sp , fontWeight = FontWeight.Bold , color = Color.White)

        Spacer(modifier = Modifier.weight(1f))


        Card (
            modifier = Modifier
                .width(width = 340.dp)
                .wrapContentHeight(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)

        ) {
            Column (
                modifier = Modifier
                    .padding(8.dp)

            ) {
                Icon(imageVector = Icons.Default.FormatQuote ,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .rotate(180f))

                Text(
                    text = quote.value.text,
                    fontSize = 24.sp,
                    color = Color.DarkGray
                )


            }

            Column (
                verticalArrangement = Arrangement.Bottom ,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = quote.value.author , fontSize = 19.sp , fontWeight = FontWeight.Bold , color = Color.Gray)
            }
            Spacer(Modifier.height(12.dp))

        }
        Row(
            modifier = Modifier.width(340.dp),
            horizontalArrangement = Arrangement.End
        ) {


            Surface(
                modifier = Modifier.size(58.dp).offset(y = (-28).dp , x = (-10).dp).clip(CircleShape)
                    .clickable {
                               viewModel.onShare(ActivityContext)
                    }, // Adjust size as needed
                shape = CircleShape,
                color = Color(0xFF1D2674) // Customize background color
            ) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Share",
                    tint = Color.White, // Customize icon color
                    modifier = Modifier.padding(8.dp)// Adjust icon size
                )
            }

        }
        Spacer(Modifier.weight(1f))

        Row(
            modifier = Modifier.size(width = 340.dp , height = 100.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "< Previous" , color = Color.White , fontFamily = MyFontFamily , fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable {

                    viewModel.previousQuote()
                }
            )
            Text(text = "Next >" ,color = Color.White ,  fontFamily = MyFontFamily , fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable {
                    viewModel.nextQuote()
                })
        }

    }
}