import grails.converters.JSON

class TwitterSearchService {
    private static final String BASE_URL = "http://search.twitter.com/search.json"

    boolean transactional = true

    def searchMessages(def params) {
        // Build request URL based on parameters
        def paramStrings = []
        if (params.query) {
            paramStrings += "q=${params.query.encodeAsURL()}"
        }
        
        // Fetch content as JSON
        def jsonArray = fetchJson(BASE_URL + "?" + paramStrings.join("&"))
        // Postprocess returned JSON
        return processJson(jsonArray)
    }

    def searchForNewMessages(def refreshUrl) {
        // Fetch content as JSON
        def jsonArray = fetchJson(BASE_URL + refreshUrl)
        // Postprocess returned JSON
        return processJson(jsonArray)
    }

    private def fetchJson(String urlString) {
        // Create URL instance represeting given URL
        def url = new URL(urlString)
        // Fetch content
        def content = url.text
        // Parse content
        def jsonArray = JSON.parse(content)
        
        return jsonArray
    }

    private def processJson(def jsonArray) {
        [ 
            messages: jsonArray.results.collect { message ->
                [
                    statusId: message.id as int,
                    user: message.from_user,
                    imageUrl: message.profile_image_url,
                    text: message.text.decodeHTML(),
                    date: new Date(message.created_at)
                ]
            },
            refreshUrl: jsonArray.refresh_url 
        ]
    }
}
