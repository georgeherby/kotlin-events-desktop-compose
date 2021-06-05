package extensions

fun String.parseHtml(): String {
    return this
        .replace("<p>", "\n")
        .replace("</p>", "")
}
