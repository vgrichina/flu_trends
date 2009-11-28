class FetchMessagesJob {
    def timeout = 60000l // execute job once in 60 seconds

    def twitterSearchService

    def execute() {
        // execute task
    
        def search = twitterSearchService.searchMessages(query: "swine flu")
        search.messages.each {
            println it
            println "+++++++++++++++++"
        }
    }
}
