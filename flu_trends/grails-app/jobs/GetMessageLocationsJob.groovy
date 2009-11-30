import com.vividsolutions.jts.geom.Point

class GetMessageLocationsJob {
    /*
     * Job execution timeout (in milliseconds), set to 30 seconds.
     */
    def timeout = 30000

    /*
     * There should not be several instances of this job running concurrently.
     */
    def concurrent = false

    /*
     * TwitterService instance used to access Twitter API
     */
    def twitterService

    /*
     * Executes task
     */
    def execute() {
        // Find all the messages without location
        def messages = Message.findAllByLocationIsNull()
        
        messages.each { println it.text }
    }
}
