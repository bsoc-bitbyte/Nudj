package com.tpc.nudj.ui.screens.eventDetailsScreen


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.tpc.nudj.model.Event
import com.tpc.nudj.ui.components.PrimaryButton
import java.util.Locale
import com.tpc.nudj.ui.components.SecondaryButton
import com.tpc.nudj.ui.screens.auth.PreHomeScreen.LoadingScreenOverlay
import com.tpc.nudj.ui.theme.CardBackgroundColor
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.Orange
import com.tpc.nudj.ui.theme.Purple
import java.util.Calendar

@Composable
fun EventDetailsScreen(
    viewModel: EventDetailsScreenViewModel = hiltViewModel(),
    eventId: String
) {
    val event = viewModel.event.collectAsState()
    val isRsvped = viewModel.isRsvped.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchEvent(eventId)
        viewModel.isRsvped(eventId)
    }
    EventDetailsScreenLayout(
        eventId = eventId,
        isRsvped = isRsvped.value,
        viewModel = viewModel,
        event = event.value
    )
    if (isLoading.value) {
        LoadingScreenOverlay()
    }
}

@Composable
fun EventDetailsScreenLayout(
    eventId: String,
    isRsvped: Boolean,
    viewModel: EventDetailsScreenViewModel,
    event: Event?
) {
    val eventName = event?.eventName
    val eventDescription = event?.eventDescription
    val eventDates = event?.eventDates
    val eventBannerUrl = event?.eventBannerUrl
    val organizerName = event?.organizerName
    val faqs = event?.faqs
    val venue = event?.eventVenue
    val scrollState = rememberScrollState()
    Scaffold(
        containerColor = LocalAppColors.current.background
    ){ contentPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding) ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .weight(6f)
                    .verticalScroll(scrollState)
            ) {
                AsyncImage(
                    model = eventBannerUrl,
                    contentDescription = "Event Banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(LocalAppColors.current.fillButtonBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Text(modifier = Modifier
                        .padding(10.dp),
                        text = organizerName?: "",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = ClashDisplay
                        )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier.fillMaxWidth() ,
                    text = eventName?: "",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = ClashDisplay,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    colors = CardDefaults.cardColors(CardBackgroundColor),
                    border = BorderStroke(width = 1.dp, color = if (isSystemInDarkTheme()) {Color.White} else {Color.Black})
                ) {
                    Column(
                        modifier = Modifier.padding(15.dp)
                    ) {
                        Row {
                            Text(
                                text = "Event Dates: ",
                                color = Orange,
                                fontFamily = ClashDisplay
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(
                                Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.Center
                            ) {
                                eventDates?.forEach {
                                    val date = it.startDateTime.toDate()
                                    val calendar = Calendar.getInstance()
                                    calendar.time = date
                                    val year = calendar.get(Calendar.YEAR)
                                    val monthName = calendar.getDisplayName(
                                        Calendar.MONTH,
                                        Calendar.SHORT,
                                        Locale.getDefault()
                                    )
                                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                                    val hour = calendar.get(Calendar.HOUR_OF_DAY)
                                    val minute = calendar.get(Calendar.MINUTE)
                                    val dayName = calendar.getDisplayName(
                                        Calendar.DAY_OF_WEEK,
                                        Calendar.LONG,
                                        Locale.getDefault()
                                    )

                                    Column(
                                        horizontalAlignment = Alignment.End,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {

                                            Text(
                                                text = "$day $monthName $year",
                                                fontFamily = ClashDisplay
                                            )
                                        }
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween

                                        ) {
                                            Text(
                                                text = "Day: ",
                                                color = Orange,
                                                fontFamily = ClashDisplay
                                            )
                                            Text(
                                                text = "$dayName",
                                                fontFamily = ClashDisplay
                                            )
                                        }
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween

                                        ) {
                                            Text(
                                                text = "Time: ",
                                                color = Orange,
                                                fontFamily = ClashDisplay
                                            )
                                            Text(
                                                text = "$hour:$minute",
                                                fontFamily = ClashDisplay
                                            )
                                        }
                                        Spacer(Modifier.height(10.dp))
                                    }
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Venue: ",
                                color = Orange,
                                fontFamily = ClashDisplay
                            )
                            Text(
                                text = venue?: "Not Available",
                                fontFamily = ClashDisplay
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = eventDescription?: "",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "FAQs",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = ClashDisplay,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                faqs?.forEach {
                    var answerIsVisible by remember { mutableStateOf(false) }
                    Column() {
                        Row(
                            modifier = Modifier
                                .clickable(onClick = {
                                    answerIsVisible = !answerIsVisible
                                })
                        ) {
                            if (answerIsVisible) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropUp,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            else
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.onBackground
                                )

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = it.question,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        AnimatedVisibility(visible = answerIsVisible){
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = it.answer,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                if (!isRsvped) {
                    Button(
                        onClick = {
                            viewModel.onClickRsvp(eventId)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = if (isSystemInDarkTheme()) {
                            Orange
                        } else {
                            Purple
                        }),
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        Text("RSVP!",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = ClashDisplay,
                                color = if (isSystemInDarkTheme()) {
                                    Color.White
                                } else {
                                    Orange
                                }
                            ),
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                } else {
                    SecondaryButton(
                        text = "Un-RSVP",
                        onClick = {
                            viewModel.onClickUnRsvp(eventId)
                        },
                        isDarkModeEnabled = isSystemInDarkTheme(),
                        modifier = Modifier.fillMaxWidth(0.9f)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoadingScreenOverlay() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .size(150.dp),
            shape = RoundedCornerShape(percent = 15)
        ) {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}