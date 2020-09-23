function searchSeries(genre, provider, score) {
    
    return new Promise(async resolve => {
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                let list = JSON.parse(this.responseText);
                sessionStorage.removeItem("seriesList");
                sessionStorage.setItem("seriesList", JSON.stringify(list));
                resolve();
            }
        }

        xhttp.open("GET", "http://localhost:8080/steam_v2-master/resources/series/search?user=" + getUsername() +"&genre=" + genre + "&provider=" + provider + "&score=" + score, true);
        xhttp.send();
        alert("Suche beendet");
    });
}
