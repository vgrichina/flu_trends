class FetchMessagesJob {
    /*
     * Job execution timeout (in milliseconds), set to 30 seconds.
     */
    def timeout = 30000

    /*
     * TwitterSearchService instance used to access Twitter Search API
     */
    def twitterSearchService

    /*
     * Executes task
     */
    def execute() {
        MessageSearch.withTransaction {
            MessageSearch.list().each { search ->
                // Search either using refresh URL or using query
                def searchResults = (search.refreshUrl ? twitterSearchService.searchNewMessages(search.refreshUrl)
                                        : twitterSearchService.searchMessages(query: search.query))

                // Loop through search results
                searchResults.messages.each {
                    // Try to find message in DB
                    def message = Message.findByStatusId(it.statusId)
                    println it
                    println message
                    // If message not found, save it to DB
                    if (!message) {
                        message = new Message(it)
                        message.save(failOnError: true)
                        println "saved"
                    }
                    println message
                }

                // Update refresh URL
                search.refreshUrl = searchResults.refreshUrl
                // Save updated search in DB
                search.save(failOnError: true)
            }
        }
    }
}
