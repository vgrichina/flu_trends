<html>
    <head>
        <title>Welcome to Twitter Flu Trends Map</title>
        <meta name="layout" content="main" />

        <script src="http://www.google.com/jsapi?key=${grailsApplication.config.geo_twitter.googleMapsKey}" type="text/javascript"></script>
        <g:javascript src="flu_trends.js" />
    </head>
    <body>
        <div id="map">
        </div>
    </body>
</html>
