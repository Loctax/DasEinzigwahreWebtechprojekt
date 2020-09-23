
function main() {
    // sessionStorage.clear();
    if (getUsername() === null){
        document.getElementById('loginForm').addEventListener('submit', async function () {
            let username = document.getElementById("username").value;
            let password = document.getElementById("password").value;
            console.log(username);
            console.log(password);
            await loginUser(username, password);
        });
    } else {
        closeForm("navLogin");
        openForm("showUsername");
        document.getElementById("showUsername").innerHTML = "User: " + getUsername();
    }

    document.getElementById('addSeriesForm').onsubmit = function () {
        let title = document.getElementById("title").value;
        let numberOfSeasons = document.getElementById("numberOfSeasons").value;
        let genre = document.getElementById("genre").value;
        let provider = document.getElementById("provider").value;
        let score = document.getElementById("score").value;
        let remark = document.getElementById("remark").value;

        addSeries(title, numberOfSeasons, genre, provider, score, remark);
       
    }

    document.getElementById('searchForm').onsubmit = async function () {
        let genre = document.getElementById("genreSearch").value;
        let provider = document.getElementById("providerSearch").value;
        let score = document.getElementById("scoreSearch").value;

        
        await searchSeries(genre, provider, score);
    }



    initList(sessionStorage.getItem("seriesList"), () => showList());
}

async function initList(seriesList, callback) {
    
    if(seriesList===null){
        await initSeries().then(async function () {
            await initRatings().then(function () {
                callback();
            });
        });
    } else {
        await initRatings().then(function () {
            callback();
        });
    }


function initSeries(){
    return new Promise(resolve => {
        if (getUsername() === null) {
        var xhttp = new XMLHttpRequest;

        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                let list = JSON.parse(this.responseText);
                sessionStorage.removeItem("seriesList");
                sessionStorage.setItem("seriesList", JSON.stringify(list));
                resolve();
            }
        };

        xhttp.open("GET", "http://localhost:8080/steam_v2-master/resources/series", true);
        xhttp.send();

        }

    })
}

async function initRatings() {
    if (getUsername() !== null) {
        return new Promise(async resolve => {
            if (getUsername() === null){
                resolve();
            }

            const list = sessionStorage.getItem("seriesList");
            const seriesList = JSON.parse(list);
            let seriesListWithRatings = [];

            // seriesList.forEach(async function (series) {
            for (const series of seriesList) {
                let genre = series.genre;
                let numberOfSeasons = series.numberOfSeasons;
                let streamedBy = series.streamedBy;
                let title = series.title;
                let seenBy = series.seenBy;

                let rating = await new Promise((resolve1, reject) => {
                    let xhttp = new XMLHttpRequest;
                    xhttp.onreadystatechange = function () {
                        if (this.readyState === 4 && this.status === 200) {
                            const rating = JSON.parse(this.responseText);
                            resolve1(rating);
                        } else if (this.readyState === 4 && this.status === 204){
                            resolve1(null);
                        }
                    }
                    xhttp.open("GET", getPathAfterResources(["users",0,"series", series.title, "rating"]), true);
                    xhttp.setRequestHeader("content-type", "application/json");
                    xhttp.setRequestHeader("Authorization", authenticateUser(getUsername(), getPassword()));
                    xhttp.withCredentials = true;

                    xhttp.send();
                })

                let obj;
                if (rating === null){
                    obj = {
                        "genre": genre,
                        "numberOfSeasons": numberOfSeasons,
                        "streamedBy": streamedBy,
                        "title": title,
                        "seenBy": seenBy,
                        "score": null,
                        "remark": null
                    };
                } else {
                    obj = {
                        "genre": genre,
                        "numberOfSeasons": numberOfSeasons,
                        "streamedBy": streamedBy,
                        "title": title,
                        "seenBy": seenBy,
                        "score": rating.score,
                        "remark": rating.remark
                    };
                }
                seriesListWithRatings.push(obj);
            }
            sessionStorage.setItem("seriesList", JSON.stringify(seriesListWithRatings));
            resolve();
        });
    } else {
        return new Promise(async resolve => {
            resolve();
        });
    }

}}
