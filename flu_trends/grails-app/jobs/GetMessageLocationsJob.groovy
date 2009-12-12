import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.Point
import com.vividsolutions.jts.geom.GeometryFactory

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
     * TwitterService instance used to access Twitter API.
     */
    def twitterService

    /*
     * LocationService instance used to access Geocoding API.
     */
    def locationService

    /*
     * A factory used to create geomertry objects.
     */
    def geometryFactory = new GeometryFactory()

    /*w
     * Executes task
     */
    def execute() {
        // Find all the messages without location
        // TODO: There can be very big list, limit results
        def messages
        Message.withTransaction {
            messages = Message.findAllByLocationIsNull()
        }

        // Simple cache for user locations
        def userLocationCache = [:]

        // For all of the messages try to determine location
        messages.each { message ->
            // If there is no cached location, it needs to be retrieved at first
            if (!userLocationCache[message.user]) {
                // Fetch info about user
                def user = twitterService.show(message.user)
                def coords = locationService.getCoordsFromLocation(user.location)
                if (coords) {
                    userLocationCache[message.user] =
                        geometryFactory.createPoint(new Coordinate(coords.latitude, coords.longitude))
                }
            }
            Message.withTransaction {
                // Set message location, make message as processed, and save it
                message.location = userLocationCache[message.user]
                println "location = ${message.location}"
                message.processed = true
                message.save(failOnError: true)
            }
        }
    }
}
