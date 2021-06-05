package pages.overview.states

import models.Event


abstract class EventState

class LoadedState(val events: List<Event>) : EventState()

class LoadingState : EventState()

class ErrorState : EventState()
class EmptyState : EventState()
