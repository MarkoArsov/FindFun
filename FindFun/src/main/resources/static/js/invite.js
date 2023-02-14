function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

$(document).ready(function () {

    console.log("FWIKJNWFOIJFWI")

    var map = L.map('main-map').setView([41.9981, 21.4254], 14);

    map.addControl(new L.Control.Fullscreen());

    L.control.locate().addTo(map);

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);

    var url = 'http://localhost:8085/api/events/invitations'

    fetch(url).then(r => r.json()).then(data => {

        var mainEvents = document.querySelector("#list-events")

        removeAllChildNodes(mainEvents)

        for (let i = 0; i < data.length; i++) {
            var eventId = data[i].id;
            var eventName = data[i].name;
            var eventImg = data[i].imgPath;
            var eventCreated = data[i].created;
            var date = data[i].date
            var interested = data[i].interested

            var event = document.createElement("div")
            event.setAttribute("class", "event")
            event.setAttribute("id", eventId)
            mainEvents.append(event)

            ReactDOM.render(<InviteEvent
                event={{name: eventName, img: eventImg, id: eventId, created: eventCreated, date: date, interested: interested}}
            />, event)

            var eventLat = data[i].lat
            var eventLng = data[i].lng
            var eventMarker = L.marker([eventLat, eventLng]).addTo(map)

            var eventMarkerContent = document.createElement("div")
            eventMarkerContent.setAttribute("id", eventId)
            eventMarker.bindPopup(eventMarkerContent)

            ReactDOM.render(<MarkerEvent
                event={{id: eventId, name: eventName, img: eventImg}}
            />, eventMarkerContent)

        }
    })

})