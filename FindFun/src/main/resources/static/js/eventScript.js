
$(document).ready(function () {

    var fetchEventLink = 'http://localhost:8085/api/events/' + eventId;

    fetch(fetchEventLink).then(r => r.json()).then(data => {

        var eventId = data.id;
        var eventName = data.name;
        var eventImg = data.imgPath;
        var eventLat = data.lat
        var eventLng = data.lng

        var map = L.map('smallMap').setView([eventLat, eventLng], 14);

        map.addControl(new L.Control.Fullscreen());

        L.control.locate().addTo(map);

        L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
        }).addTo(map);

        var eventMarker = L.marker([eventLat, eventLng]).addTo(map)

        var eventMarkerContent = document.createElement("div")
        eventMarkerContent.setAttribute("id", eventId)
        eventMarker.bindPopup(eventMarkerContent)

        ReactDOM.render(<MarkerEvent
            event={{name: eventName, img: eventImg}}
        />, eventMarkerContent)

    })
    $('form').each(function () {
        $('input').keypress(function (e) {
            // Enter pressed?
            if (e.which == 10 || e.which == 13) {
                this.form.submit();
            }
        });

        $('input[type=submit]').hide();
    })

})




