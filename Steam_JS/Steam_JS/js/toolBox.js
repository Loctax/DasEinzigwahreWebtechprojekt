function getPathAfterResources(pathElements){
    var path = "http://localhost:8080/steam_v2-master/resources/";

    pathElements.forEach(function(pathElement) {
       if (pathElement === 0) {
           path = path + getUsername() + "/";
       } else {
           if (pathElement != "") {
               path = path + pathElement + "/";
           }
       }
    });
    return path;
}

function replaceSpaces(nameWithRegExp){
    var nameWORegExp = nameWithRegExp;
    while (nameWORegExp.includes("%20")) {
        nameWORegExp = nameWORegExp.replace("%20", " ");
    }
    return nameWORegExp;
}

function openForm(formName) {
    document.getElementById(formName).style.display = "block";
}

function closeForm(formName) {
    document.getElementById(formName).style.display = "none";
}

function selectElement(id, valueToSelect) {
    let element = document.getElementById(id);
    element.value = valueToSelect;
}
