function Event({event}) {

    var eventlink = "/events/" + event.id;

    return (
        <div className="card horizontal">
            <div className="card-image">
                <img src={event.img}/>
            </div>
            <div className="card-stacked">
                <div className="card-content">
                    <p className="eventTitle">{event.name}</p>
                    <p>{event.date}</p>
                    <p>Hosted by <span>{event.created}</span></p>
                    <p><span>{event.interested}</span> interested</p>
                </div>
                <div className="card-action">
                    <a href={eventlink}> Go to Event</a>
                </div>
            </div>
        </div>
    );
}

function MarkerEvent({event}) {

    var eventlink = "/events/" + event.id;

    return (
        <div className="card markerCard">
            <div className="card-image">
                <img src={event.img} alt={event.img}/>

            </div>
            <div className="card-content">
                <a href={eventlink} ><span className="markerCardTitle">{event.name}</span></a>
            </div>
        </div>
    )
}

function InviteEvent({event}) {

    var eventlink = "/events/" + event.id;

    return (
        <div className="card horizontal">
            <div className="card-image">
                <img src={event.img}/>
            </div>
            <div className="card-stacked">
                <div className="card-content">
                    <p className="eventTitle">{event.name}</p>
                    <p>{event.date}</p>
                    <p>Invited by <span>{event.created}</span></p>
                    <p><span>{event.interested}</span> interested</p>
                </div>
                <div className="card-action">
                    <a href={eventlink}> Go to Event</a>
                </div>
            </div>
        </div>
    );
}

