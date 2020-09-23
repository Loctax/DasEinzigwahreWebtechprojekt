async function addSeries(title, numberOfSeasons, genre, provider, score, remark) {
    const seriesObj = {
        "genre": genre,
        "numberOfSeasons": numberOfSeasons,
        "streamedBy": provider,
        "title": title
    };

    const ratingObj = {
        "score": score,
        "remark": remark
    }

    const seriesJSON = JSON.stringify(seriesObj);
    const ratingJSON = JSON.stringify(ratingObj);

    await saveSeriesToPersistence(seriesJSON);
    await saveRatingToPersistence(ratingJSON, seriesObj.title);
    window.location.href = "/index.html";
}

function saveSeriesToPersistence(myJSON) {
    return new Promise( resolve => {
        var xhttp = new XMLHttpRequest;
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                sessionStorage.removeItem("seriesList");
                resolve();
            }
        }

        xhttp.open("POST", getPathAfterResources(["users", 0, "series"]), true);
        xhttp.setRequestHeader("content-type", "application/json");
        xhttp.setRequestHeader("Authorization", authenticateUser(getUsername(), getPassword()));
        xhttp.withCredentials = true;

        xhttp.send(myJSON);
    });
}

function saveRatingToPersistence(ratingJSON, title) {
    return new Promise(resolve => {
        var xhttp = new XMLHttpRequest;
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                sessionStorage.removeItem("seriesList");
                resolve();
            }
            // TODO andere Bedingungen resolven
        }

        xhttp.open("PUT", getPathAfterResources(["users", 0, "series", title]), true);
        xhttp.setRequestHeader("content-type", "application/json");
        xhttp.setRequestHeader("Authorization", authenticateUser(getUsername(), getPassword()));
        xhttp.withCredentials = true;

        xhttp.send(ratingJSON);
    });
}
