package com.sd.palatecraft.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay

@Composable
fun WithAnimation(
    modifier: Modifier = Modifier,
    delay: Long = 1,
    animation: EnterTransition,
    content: @Composable ()-> Unit
) {
    val visible = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit){
        delay(delay)
        visible.value = true
    }
    Box(modifier = modifier){
        if(!visible.value){
            Box(modifier = Modifier.alpha(0f)){
                content()
            }
        }
        AnimatedVisibility(visible = visible.value, enter = animation, modifier = modifier) {
            content()
        }
    }
}