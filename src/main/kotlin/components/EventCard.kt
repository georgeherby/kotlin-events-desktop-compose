package components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import extensions.parseHtml
import models.Event

@Composable
fun eventCard(event: Event?) {
    if (event == null)
        return

    Card(
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .fillMaxWidth(1f).padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth().padding(12.dp)
            ) {
                Column {
                    Text(event.title ?: "Title", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
                    Box(modifier = Modifier.height(12.dp))
                    Text(event.location ?: "Unknown", style = TextStyle(fontSize = 14.sp))

                }
                Spacer(Modifier.weight(1f))
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        if (event.startDate == event.endDate)
                            event.startDate ?: "Unknown"
                        else
                            "${event.startDate ?: "Unknown"} to ${event.endDate ?: "Unknown"} ",
                        style = TextStyle(fontSize = 14.sp)
                    )
                }
            }
            Divider()
            Row(
                modifier = Modifier.fillMaxWidth().padding(12.dp)
            ) {
                Text(
                    event.description?.trim()?.parseHtml() ?: "Unknown",
                    style = TextStyle(fontSize = 14.sp)
                )
            }
        }
    }
}
