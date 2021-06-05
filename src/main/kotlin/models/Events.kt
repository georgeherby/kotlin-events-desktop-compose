package models

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "events", strict = false)
data class Events constructor(
    @field:ElementList(name = "event", inline = true)
    @param:ElementList(name = "event", inline = true)
    var events: List<Event>
)

data class Event @JvmOverloads constructor(
    @field:Element(name = "endDate", required = false) var endDate: String? = "",
    @field:Element(name = "subject", required = false) var subject: String? = "",
    @field:Element(name = "speaker", required = false) var speaker: String? = "",
    @field:Element(name = "description", required = false, data = true) var description: String? = "",
    @field:Element(name = "location", required = false) var location: String? = "",
    @field:Element(name = "lang", required = false) var lang: String? = "",
    @field:Element(name = "title") var title: String? = "",
    @field:Element(name = "startDate", required = false) var startDate: String? = "",
    @field:Element(name = "url", required = false) var url: String? = "",
)
