import grails.converters.JSON

class MessageController {
    def scaffold = true
    
    def messagesJson = {
        // Get messages list
        def messages = getMessages()

        // Render messages list as JSON
        render(messages as JSON)
    }

    private def getMessages() {
        def messages = Message.findAllByLocationIsNotNull()
         
        // Return only the needed fields
        // Results are also grouped by the coords
        messages.collect { it ->
            [
                screenName: it.user,
                pictureUrl: it.imageUrl,
                status: it.text,
                coords: [latitude: it.location.x, longitude: it.location.y]
             ]    
        }.findAll { it.coords != null }.groupBy { it.coords }.collect { it.value }
    }
}
