import com.vividsolutions.jts.geom.Point  

class Message {
    int statusId
    String user
    String imageUrl
    String text
    Date date
    Point location
    boolean processed = false

    static constraints = {
        statusId(nullable: false, unique: true) 
        user(nullable: false, blank: false, maxSize: 20)
        imageUrl(nullable: true, blank: false, maxSize: 256)
        text(nullable: false, blank: false, maxSize: 140)
        date(nullable: false)
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
