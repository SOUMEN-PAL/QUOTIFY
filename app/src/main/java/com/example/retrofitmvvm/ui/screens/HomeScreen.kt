package com.example.retrofitmvvm.ui.screens

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofitmvvm.R
import com.example.retrofitmvvm.models.Result
import com.example.retrofitmvvm.viewmodels.QuoteViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier , viewModel: QuoteViewModel) {
    val quote = viewModel.quote
    val index = viewModel.index

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

        Column (
            modifier = Modifier
                .fillMaxSize()
                .weight(7f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.weight(1f))

            Text(text = "QUOTIFY", fontSize = 40.sp , fontWeight = FontWeight.Bold , color = Color.White , modifier = Modifier
                .weight(2f)
            )



            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(8f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card (
                    modifier = Modifier
                        .width(width = 340.dp)
                        .wrapContentHeight()
                    ,
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)

                ) {
                    Column (
                        modifier = Modifier
                            .padding(8.dp)

                    ) {
                        Icon(imageVector =  Icons.Filled.FormatQuote,
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .rotate(180f))

                        quote.value?.let {
                            Text(
                                text = it.content,
                                fontSize = 24.sp,
                                color = Color.DarkGray
                            )
                        }


                    }

                    Column (
                        verticalArrangement = Arrangement.Bottom ,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        quote.value?.let { Text(text = it.author , fontSize = 19.sp , fontWeight = FontWeight.Bold , color = Color.Gray) }
                    }
                    Spacer(Modifier.height(12.dp))
                }

                Row(
                    modifier = Modifier.width(340.dp),
                    horizontalArrangement = Arrangement.End
                ) {


                    Surface(
                        modifier = Modifier
                            .size(58.dp)
                            .offset(y = (-28).dp, x = (-10).dp)
                            .clip(CircleShape)
                            .clickable {
                                viewModel.onShare()
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
            }




        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.size(width = 340.dp , height = 100.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "< Previous" , color = Color.White , fontFamily = MyFontFamily , fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {

                        viewModel.prevQuote()
                    }
                )
                Text(text = "Next >" ,color = Color.White ,  fontFamily = MyFontFamily , fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        viewModel.nextQuote()
                    })
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val viewModel = QuoteViewModel(LocalContext.current) // Provide a mock or real ViewModel
    viewModel.quote.value = Result(1, "xyz", "abc", "dfd", "sdff", "sfds", "sdfs", 0) // Set sample quote data
    viewModel.index.intValue = 0 // Set sample index

    HomeScreen(viewModel = viewModel)
}

