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
    
        def search = twitterSearchService.searchMessages(query: "swine flu")

        search.messages.each {
            // Try to find message in DB
            def message = Message.findByStatusId(it.statusId)
            println message
            println it
            // If message not found, save it to DB
            if (!message) {
                message = new Message(it)
                message.save(failOnError: true)
                println "saved"
            }
            println message
        }
    }
}
