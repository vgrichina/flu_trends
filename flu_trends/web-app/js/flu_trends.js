google.load("maps", "2.x");
google.load("jquery", "1.3.1");

google.setOnLoadCallback(function() {
    $(document).ready(function() {
        // Create and configure Google Map control
        var map = new GMap2(document.getElementById("map"));
        var vinnitsa = new GLatLng(49.2325477, 28.4744695);
        map.setCenter(vinnitsa, 4);
        map.addControl(new GLargeMapControl());
        
        // Fetch messages
        $.getJSON("message/messagesJson", function (data) {
            // Clear all markers
            map.clearOverlays();
            // Loop through message list grouped by locations and add markers to map
            $.each(data, function (i, messageList) {
                // Add marker to map
                var marker = new GMarker(new GLatLng(messageList[0].coords.latitude, messageList[0].coords.longitude));
                map.addOverlay(marker);
                // Construct popup HTML
                var popup = '<ul class="messageList">'
                $.each(messageList, function (i, item) {
                    popup += 
                    '<li>' + 
                        '<img src="' + item.pictureUrl + '">' + 
                        '<div>' + 
                            '<div class="name">' + item.screenName + '</div>' + 
                            '<div class="message">' + item.status + '</div>' +
                        '</div>' +
                    '</li>';
                });
                popup += "</ul>";
                // Add popup handler
                GEvent.addListener(marker, "click", function() {
                    marker.openInfoWindowHtml(popup);
                });
            });
        });
        // Indicate that form should not actually be submitted
        return false;
    });
});
