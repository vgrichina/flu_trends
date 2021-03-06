dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    username = "flu_trends"
    password = "flu_trends"
}
hibernate {
    cache.use_second_level_cache = true
    caqche.use_query_cache = true
    cache.provider_class = "com.opensymphony.oscache.hibernate.OSCacheProvider"
    dialect = "org.hibernatespatial.mysql.MySQLSpatialDialect"
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://localhost/flu_trends"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://localhost/flu_trends_test"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://localhost/flu_trends_prod"
        }
    }
}
