class BootStrap {

     def init = { servletContext ->
        // Create default search for "swine flu"
        new MessageSearch(query: "swine flu").save(failOnError: true)
     }
     
     def destroy = {
     }
}

