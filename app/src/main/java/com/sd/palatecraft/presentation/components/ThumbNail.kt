package com.sd.palatecraft.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.PlayArrow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sd.palatecraft.R
import com.sd.palatecraft.presentation.components.videoplayer.YoutubePlayer
import com.sd.palatecraft.remote.Meal
import com.sd.palatecraft.ui.theme.Black40
import com.sd.palatecraft.ui.theme.BlackA40
import com.sd.palatecraft.ui.theme.BlackA60
import com.sd.palatecraft.ui.theme.WhiteA90
import com.sd.palatecraft.util.backgrounds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThumbNail(recipe: Meal) {
    val background by remember{ mutableIntStateOf(backgrounds.random()) }
    val width = LocalConfiguration.current.screenWidthDp
    var isSheetOpen by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current
    Box(
        modifier = Modifier
            .aspectRatio(0.5f)
            .paint(
                painter = painterResource(id = background),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(color = BlackA60, blendMode = BlendMode.Darken)
            )
            .padding(PaddingValues(15.dp, 85.dp, 15.dp, 30.dp)),
        contentAlignment = Alignment.TopCenter
    ){
        Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(colors = listOf(Black40, BlackA60)),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(PaddingValues(start = 40.dp, top = 20.dp, end = 40.dp, bottom = 20.dp))
        ){
            Text(text = "Recipe of the Day",
                fontSize =40.sp,
                fontWeight = FontWeight.ExtraBold,
                color = WhiteA90,
                modifier = Modifier
                    .padding(10.dp),
                fontFamily = FontFamily.Cursive,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.size(30.dp))
            AsyncImage(model = recipe.strMealThumb, contentDescription = "RecipeImg",
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.size(30.dp))
            Text(text = recipe.strMeal,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                lineHeight = 35.sp,
                fontFamily = FontFamily.SansSerif,
                color = WhiteA90,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(min = (width*0.6).toInt().dp, max = (width*0.9).toInt().dp)
            )
            Spacer(modifier = Modifier.size(15.dp))
            Column{
                Text(text = "Cuisine : ${recipe.strArea}",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = WhiteA90)
                Spacer(modifier = Modifier.size(5.dp))
                Text(text = "Course/Category : ${recipe.strCategory}",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = WhiteA90)
                Spacer(modifier = Modifier.size(25.dp))
            }
            ElevatedButton(onClick = { isSheetOpen = true },
                modifier = Modifier.shadow(elevation = 8.dp, ambientColor = Color.Red, spotColor = Color.Red),
                colors = ButtonDefaults.filledTonalButtonColors(Color.Red)
            ) {
                Text(text = "Open in YouTube",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = WhiteA90)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.TwoTone.PlayArrow,
                    contentDescription = "Play on Youtube",
                    tint = WhiteA90,
                    modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.size(25.dp))
            Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.offset(10.dp)){
                Text(text = "Swipe Up for Instructions",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = WhiteA90)
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_up_foreground),
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .aspectRatio(1f))
            }
            if (isSheetOpen){
                ModalBottomSheet(onDismissRequest = { isSheetOpen = false }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                            .padding(vertical = 8.dp, horizontal = 8.dp),
                        contentAlignment = Alignment.TopCenter){
                        Column (
                            verticalArrangement = Arrangement.Center,
                        ){
                            Text(text = "Video",
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                fontFamily = FontFamily.SansSerif,
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(
                                thickness = 3.dp,color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            YoutubePlayer(youtubeVideoId = recipe.strYoutube.substringAfter("="), lifecycleOwner = lifecycleOwner)
                        }
                    }
                }
            }
        }
    }

}


