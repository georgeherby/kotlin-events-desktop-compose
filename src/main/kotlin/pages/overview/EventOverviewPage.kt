package pages.overview

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.emptyWidget
import components.errorWidget
import components.eventCard
import models.Event
import pages.overview.states.EmptyState
import pages.overview.states.ErrorState
import pages.overview.states.LoadedState
import pages.overview.states.LoadingState

class EventPage

@Composable
fun eventPage(viewModel: EventOverviewViewModel) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
//        appBar("Search Anime")
        when (viewModel.state) {
            is LoadingState -> CircularProgressIndicator(modifier = Modifier.padding(50.dp))
            is LoadedState -> loaded(
                (viewModel.state as LoadedState).events
            )
            is ErrorState -> Box(modifier = Modifier.padding(20.dp)) { errorWidget() }
            is EmptyState -> Box(modifier = Modifier.padding(20.dp)) { emptyWidget() }

        }
    }
}

@Composable
fun loaded(events: List<Event>?) {
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


