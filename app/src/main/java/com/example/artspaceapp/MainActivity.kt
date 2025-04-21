package com.example.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    var currentStep by remember { mutableIntStateOf(0) }

    val artworks = listOf(
        Art(
            imageRes = R.drawable.lago_ness,
            contentDesc = R.string.loch_ness_artwork_title,
            title = R.string.loch_ness_artwork_title,
            location = R.string.loch_ness_location,
            year = R.string.loch_ness_year
        ),
        Art(
            imageRes = R.drawable.monte_everest,
            contentDesc = R.string.mount_everest_artwork_title,
            title = R.string.mount_everest_artwork_title,
            location = R.string.mount_everest_location,
            year = R.string.mount_everest_year
        ),
        Art(
            imageRes = R.drawable.la_gran_muralla_china,
            contentDesc = R.string.great_wall_of_china_artwork_title,
            title = R.string.great_wall_of_china_artwork_title,
            location = R.string.great_wall_of_china_location,
            year = R.string.great_wall_of_china_year
        )
    )

    val currentArt = artworks[currentStep]

    ImageWithTitleAndLocation(
        imageResourceId = currentArt.imageRes,
        contentDescriptionId = currentArt.contentDesc,
        titleResourceId = currentArt.title,
        locationResourceId = currentArt.location,
        yearResourceId = currentArt.year,
        onImageClickNext = {
            currentStep = (currentStep + 1) % artworks.size
        },
        onImageClickPrevious = {
            currentStep = (currentStep - 1 + artworks.size) % artworks.size
        }
    )
}

data class Art(
    val imageRes: Int,
    val contentDesc: Int,
    val title: Int,
    val location: Int,
    val year: Int
)

@Composable
fun ImageWithTitleAndLocation(
    imageResourceId: Int,
    contentDescriptionId: Int,
    titleResourceId: Int,
    locationResourceId: Int,
    yearResourceId: Int,
    onImageClickPrevious: () -> Unit,
    onImageClickNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp, 5.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        BoxWithImage(
            imageResourceId = imageResourceId,
            contentDescriptionId = contentDescriptionId
        )
        Spacer(modifier = Modifier.height(80.dp))
        BoxOfTextWithLocationAndTitle(
            titleResourceId = titleResourceId,
            locationResourceId = locationResourceId,
            yearResourceId = yearResourceId
        )
        Spacer(modifier = Modifier.height(40.dp))
        ButtonsWithActionInRow(
            onImageClickPrevious = onImageClickPrevious,
            onImageClickNext = onImageClickNext
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun BoxWithImage(
    imageResourceId: Int,
    contentDescriptionId: Int,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier
            .size(350.dp, 400.dp)
            .graphicsLayer {
                shadowElevation = 12f
                clip = false
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(imageResourceId),
            contentDescription = stringResource(contentDescriptionId),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(300.dp, 350.dp)
                .padding(10.dp)
        )
    }
}

@Composable
fun BoxOfTextWithLocationAndTitle(
    titleResourceId: Int,
    locationResourceId: Int,
    yearResourceId: Int,
    modifier: Modifier = Modifier
) {
    Column (
        modifier
            .fillMaxWidth()
            .size(350.dp, 100.dp)
            .background(color = colorResource(R.color.gray_100))
            .padding(start = 20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(titleResourceId),
            fontSize = 20.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.DarkGray
        )
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(stringResource(locationResourceId))
                    append(" ")
                }
                withStyle(style = SpanStyle(
                    fontStyle = FontStyle.Italic,
                    color = Color.DarkGray
                    ))
                {
                    append(stringResource(yearResourceId))
                }
            }
        )
    }
}
@Composable
fun ButtonsWithActionInRow(
    onImageClickPrevious: () -> Unit,
    onImageClickNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier.fillMaxWidth()
            .padding(10.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(onClick = onImageClickPrevious, modifier = Modifier.weight(1f)) {
            Text(text = "Previous")
        }
        Spacer(modifier = Modifier.width(50.dp))
        Button(onClick = onImageClickNext, modifier = Modifier.weight(1f)) {
            Text(text = "Next")
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Phone Layout"
)
@Composable
fun ArtSpacePreview() {
    ArtSpaceAppTheme {
        ArtSpaceApp()
    }
}

@Preview(
    showBackground = true,
    name = "Tablet Layout",
    widthDp = 1280,
    heightDp = 800
)
@Composable
fun ArtSpaceTabletPreview() {
    ArtSpaceAppTheme {
        ArtSpaceApp()
    }
}