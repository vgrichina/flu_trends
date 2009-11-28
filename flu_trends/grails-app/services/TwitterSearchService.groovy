import grails.converters.JSON

class TwitterSearchService {
    private static final String BASE_URL = "http://search.twitter.com/search.json"

    boolean transactional = true

    def searchMessages(def params) {
        // Build request URL based on parameters
        def requestString = BASE_URL
        def paramStrings = []
        if (params.query) {
            paramStrings += "q=${params.query.encodeAsURL()}"
        }
        def url = new URL(requestString + "?" + paramStrings.join("&"))
        
        // Fetch content
        def content = url.text

        // Parse content
        def jsonArray = JSON.parse(content)
        
        // Postprocess returned JSON
        return  [ 
                    messages: jsonArray.results.collect { message ->
                        [
                            statusId: message.id,
                            userName: message.from_user,
                            imageUrl: message.profile_image_url,
                            date: new Date(message.created_at)
                        ]
                    },
                    refreshUrl: jsonArray.refresh_url 
                ]
    }

    def searchForNewMessage(def refreshUrl) {
        // TODO
    }

}
