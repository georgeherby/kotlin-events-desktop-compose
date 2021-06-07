package pages.overview

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import components.emptyWidget
import components.errorWidget
import components.eventCard
import models.Event
import pages.overview.states.EmptyState
import pages.overview.states.ErrorState
import pages.overview.states.LoadedState
import pages.overview.states.LoadingState
import services.EventsService

class EventPage

@Composable
fun eventScreen(viewModel: EventOverviewViewModel = EventOverviewViewModel(EventsService())) {

    LaunchedEffect(true){
        viewModel.getEvents()
    }

    Scaffold(
        content = {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                when (viewModel.state) {
                    is LoadingState -> CircularProgressIndicator(modifier = Modifier.padding(50.dp))
                    is LoadedState -> loaded(
                        events = (viewModel.state as LoadedState).events,
                        onRefresh = { viewModel.getEvents() },
                        onFilter = { selectedValue: String -> viewModel.filterByLang(selectedValue) }
                    )
                    is ErrorState -> Box(modifier = Modifier.padding(20.dp)) { errorWidget() }
                    is EmptyState -> Box(modifier = Modifier.padding(20.dp)) { emptyWidget() }
                }
            }
        })
}





@Composable
fun loaded(events: List<Event>?, onRefresh: () -> Unit, onFilter: (String) -> Unit) {

    Column {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.weight(0.1f))
            dropdownFilter(
                events?.distinctBy { it.lang }?.mapNotNull { it.lang }?.toList() ?: listOf(),
                onSelect = onFilter
            )
            Spacer(Modifier.weight(1f))
            IconButton(onClick = onRefresh) {
                Icon(Icons.Rounded.Refresh, contentDescription = null)
            }
        }
        listOfEvents(events)
    }
}

@Composable
fun listOfEvents(events: List<Event>?){
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(end = 6.dp)
    ) {
        val state = rememberLazyListState()

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = state
        ) {
            if (events != null) {
                items(events) { event ->
                    eventCard(event)
                }
            } else {
                item { Text("nothing found") }
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(
                scrollState = state
            )
        )
    }
}


@Composable
fun dropdownFilter(items: List<String>, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf("All languages") }
    var offset by remember { mutableStateOf(0f) }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        Row(
            modifier = Modifier.clickable(onClick = { expanded = true })
                .border(1.dp, Color.White, shape = RoundedCornerShape(4.dp)).padding(vertical = 2.dp, horizontal = 4.dp)
        ) {
            Text(selectedValue)
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null, tint = Color.White)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxHeight(0.75f).background(MaterialTheme.colors.surface)
                .scrollable(orientation = Orientation.Vertical,
                    state = rememberScrollableState { delta ->
                        offset += delta
                        delta
                    })
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedValue = items[index]
                    expanded = false
                    onSelect(selectedValue)
                }) {
                    Text(text = s)
                }
            }
        }
    }
}



