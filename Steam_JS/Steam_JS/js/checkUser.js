function loginUser(username2, password2){

    return new Promise(resolve => {
        var username = document.getElementById("username").value;
        var password = document.getElementById("password").value;

        var xhttp = new XMLHttpRequest;
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                
                    sessionStorage.setItem("username", username);
                    sessionStorage.setItem("password", password);
                    window.location.href = "./index.html";
                    resolve();
            }    
            
        }

        xhttp.open("GET", "http://localhost:8080/steam_v2-master/resources/users/login", true);
        xhttp.setRequestHeader("Authorization", authenticateUser(username, password));
        xhttp.withCredentials = true;
        
        xhttp.send();
        alert("Eingeloggt");
    });
}

function registerUser(){
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    var obj = {
        "username": username,
        "password": password
    };
    
    var myJSON = JSON.stringify(obj);
    console.log(myJSON);
    
    var xhttp = new XMLHttpRequest;
    console.log(username);
    console.log(password); 
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {

            if(JSON.parse(this.responseText)) {
                
                sessionStorage.setItem("username", username);
                sessionStorage.setItem("password", password);
                window.location.href = "./index.html"; 
            }
            else {      
                alert("Leider ist dieser Name schon vergeben :(");
            }
            
            
        }
        
    }
    xhttp.open("POST", "http://localhost:8080/steam_v2-master/resources/register", true);
    xhttp.setRequestHeader("content-type", "application/json");
    xhttp.setRequestHeader("Authorization", authenticateUser(username, password));
    xhttp.withCredentials = true;
      
    xhttp.send(myJSON);       
}

function authenticateUser(user, password)
{
    var token = user + ":" + password;

 
    var hash = btoa(token); 

    return hash;
}

function getUsername() {
    return sessionStorage.getItem("username");
}

function getPassword() {
    return sessionStorage.getItem("password");
}
