package pages.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import models.Events
import org.jetbrains.skija.impl.Log
import pages.overview.states.EmptyState
import pages.overview.states.ErrorState
import pages.overview.states.EventState
import pages.overview.states.LoadedState
import pages.overview.states.LoadingState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import services.EventsService

class EventOverviewViewModel(private val eventsService: EventsService) {
    var state by mutableStateOf<EventState>(EmptyState())


    fun getEvents() {
        state = LoadingState()

        try {
            GlobalScope.async {
                eventsService.get().enqueue(object : Callback<Events> {
                    override fun onFailure(call: Call<Events>?, t: Throwable?) {
                        Log.info("failed")
                    }

                    override fun onResponse(call: Call<Events>?, response: Response<Events>?) {
                        state = if (response!!.body()!!.events.isEmpty()) {
                            EmptyState()
                        } else {
                            LoadedState(response.body()!!.events)
                        }
                    }
                })
            }
        } catch (e: Exception) {
            state = ErrorState()
        }
    }

    fun filterByLang(lang: String) {
        if (state is LoadedState) {
            val filteredEvents = (state as LoadedState).events.filter {
                it.lang.equals(lang, ignoreCase = false)
            }
            state = LoadedState(filteredEvents)
        }
    }
}
