import com.vividsolutions.jts.geom.Point  

class Message {
    String user
    String imageUrl
    String status
    Point location
    boolean processed = false

    static constraints = {
        user(nullable: false, blank: false, maxSize: 20)
        imageUrl(nullable: true, blank: false, maxSize: 256)
        status(nullable: false, blank: false, maxSize: 140)
        location(nullable: true)
        processed(nullable: false)
    }
    static mapping = {
        columns {
            location(type: org.hibernatespatial.GeometryUserType,
                        sqlType: "POINT") 
        }
    }
}
