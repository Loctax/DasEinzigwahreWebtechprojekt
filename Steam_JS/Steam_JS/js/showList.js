function showList() {

    const list = sessionStorage.getItem("seriesList");
    const seriesList = JSON.parse(list);

    const table = document.getElementById("series-table");


    let i = 1;
    seriesList.forEach(series => {
        let row = table.insertRow(i);
        row.id = "row" + i;
        let cellTitle = row.insertCell(0);
        cellTitle.id = "title" + i;
        let cellSeasons = row.insertCell(1);
        cellSeasons.id = "seasons" + i;
        let cellGenre = row.insertCell(2);
        cellGenre.id = "genre" + i;
        let cellProvider = row.insertCell(3);
        cellProvider.id = "provider" + i;
        let cellSeenBy = row.insertCell(4);
        let cellScore = row.insertCell(5);
        cellScore.id = "score" + i;
        let cellRemark = row.insertCell(6);
        cellRemark.id = "remark" +i;
        cellRemark.classList.add("remarkCell");
        let cellEdit = row.insertCell(7);

        cellTitle.innerHTML = series.title;
        cellSeasons.innerHTML = series.numberOfSeasons;
        cellGenre.innerHTML = series.genre;
        cellProvider.innerHTML = series.streamedBy;

        if (series.score !== undefined){
            cellScore.innerHTML = series.score;
            cellRemark.innerHTML = series.remark;
        }

        series.seenBy.forEach(user => {
            cellSeenBy.innerHTML = cellSeenBy.innerHTML + " " + user.username + ",";
        });
        cellSeenBy.innerHTML = cellSeenBy.innerHTML.slice(0, -1);

        var editButton ="<form>" +
            "<input" +
            " type=\"button\"" +
            " id=\"edit_button" + i + "\"" +
            " value=\"Edit\"" +
            " class=\"inputButton\"" +
            " onclick=\"editRow('" + i +"')\"" +
            " style=\"display: block;\"" +
            ">" +
            "</form>";
        var saveButton = "<form>" +
            "<input" +
            " type=\"button\"" +
            " id=\"save_button" + i + "\"" +
            " value=\"Save\"" +
            " class=\"inputButton\"" +
            " onclick=\"saveRow('" + i + "')\"" +
            " style=\"display: none;\"" +
            ">" +
            "</form>";
        cellEdit.innerHTML = editButton + saveButton;

        i = i + 1;
    });
}
