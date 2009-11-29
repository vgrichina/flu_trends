class MessageSearch {
    String query
    String refreshUrl
    int lastStatusId
    
    static constraints = {
        query(nullable: false, blank: false)
        refreshUrl(nullable: true, blank: false)
    }
}
