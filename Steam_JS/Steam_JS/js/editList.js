function editRow(i) {
    if (getUsername() === null) {
        alert("You must be logged in to edit");
    } else {
        document.getElementById("edit_button" + i).style.display = "none";
        document.getElementById("save_button" + i).style.display = "block";

        let seasonsCell = document.getElementById("seasons" + i);
        let genreCell = document.getElementById("genre" + i);
        let providerCell = document.getElementById("provider" + i);
        let scoreCell = document.getElementById("score" + i);
        let remarkCell = document.getElementById("remark" + i);

        let seasonsData = seasonsCell.innerHTML;
        let genreData = genreCell.innerHTML;
        let providerData = providerCell.innerHTML;
        let scoreData = scoreCell.innerHTML;
        let remarkData = remarkCell.innerHTML;

        seasonsCell.innerHTML = "<input type='number' id='seasonsEdit" + i + "' value='" + seasonsData + "' required>";
        genreCell.innerHTML = "<select name=\"genre\" id=\"genreEdit" + i + "\">\n" +
            "            <option value='Thriller'>Thriller</option>\n" +
            "            <option value='ScienceFiction'>Science Fiction</option>\n" +
            "            <option value='Drama'>Drama</option>\n" +
            "            <option value='Comedy'>Comedy</option>\n" +
            "            <option value='Documentary'>Documentary</option>\n" +
            "        </select>";
        selectElement('genreEdit' + i, genreData);

        providerCell.innerHTML = "<select name=\"provider\" id=\"providerEdit" + i + "\">\n" +
            "            <option value=\"Netflix\">Netflix</option>\n" +
            "            <option value=\"AmazonPrime\">Amazon Prime</option>\n" +
            "            <option value=\"Skye\">Skye</option>\n" +
            "        </select>";
        selectElement('providerEdit' + i, providerData);

        scoreCell.innerHTML = "<select name=\"score\" id='scoreEdit" + i + "'>\n" +
            "            <option value=\"very_good\">Very good</option>\n" +
            "            <option value=\"good\">good</option>\n" +
            "            <option value='mediocre'>mediocre</option>\n" +
            "            <option value=\"bad\">bad</option>\n" +
            "            <option value=\"very_bad\">very bad</option>\n" +
            "        </select>";
        selectElement("scoreEdit" + i, scoreData);

        remarkCell.innerHTML = "<input type=\"text\" id='remarkEdit" + i + "' name=\"remark\" value='" + remarkData + "'>";
    }
}

async function saveRow(i) {
    let titleValue = document.getElementById("title"+i).innerHTML;
    let seasonsValue = document.getElementById("seasonsEdit" + i).value;
    let genreValue = document.getElementById("genreEdit" + i).value;
    let providerValue = document.getElementById("providerEdit" + i).value;
    let scoreValue = document.getElementById("scoreEdit" + i).value;
    let remarkValue = document.getElementById("remarkEdit" + i).value;

    const seriesObj = {
        "genre": genreValue,
        "numberOfSeasons": seasonsValue,
        "streamedBy": providerValue,
        "title": titleValue
    };

    const ratingObj = {
        "score": scoreValue,
        "remark": remarkValue
    }

    const seriesJSON = JSON.stringify(seriesObj);
    const ratingJSON = JSON.stringify(ratingObj);

    await saveSeriesToPersistence(seriesJSON);
    await saveRatingToPersistence(ratingJSON, seriesObj.title);
    window.location.href = "index.html";

}
