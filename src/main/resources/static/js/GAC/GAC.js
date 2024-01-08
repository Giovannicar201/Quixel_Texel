function creaFormLoginERegistrazione(){

    let divAutenticazione = document.getElementById("login");

    document.getElementsByClassName("break")[0].style.height = "100%";
    divAutenticazione.innerHTML = "";

    if (document.getElementById("autenticazione").classList.contains("pressed") === false) {

        for (let strumentiDiv of document.getElementById("strumenti").children) {

            strumentiDiv.classList.remove("pressed");

        }

        if(divAutenticazione.children.length === 0) {

            $(divAutenticazione).append('                <div class="titleBar" id="titleBar">' +
                '                    <img src="https://i.postimg.cc/xdwxc1w6/user.png" class="iconTitle" id="user">' +
                '                        <label for="user">Sign-up & Log-in</label>' +
                '                </div>' +
                '                <div class="breakDivAction" id="login">' +
                '                    <label for="formLogin" style="margin-left: 8px; margin-top: 8px">Log-in</label>' +
                '                        <div class="topActionDiv">' +
                '                            <label for="emailLogin">Email</label>' +
                '                            <input type="text" id="emailLogin" name="email" class="inputForm" required>' +
                '                        </div>' +
                '                        <div class="actionDiv">' +
                '                            <label for="passwordLogin">Password</label>' +
                '                            <input type="password" id="passwordLogin" name="password" class="inputForm" required>' +
                '                        </div>' +
                '                        <div class="actionDiv">' +
                '                            <button class="bottone" onclick="login()">Log-in</button>' +
                '                        </div>' +
                '                   <div id = "errori"></div>' +
                '                </div>' +
                '                <div class="breakDivAction" id="registrazioneDiv">' +
                '                    <label for="registrazione" style="margin-left: 8px; margin-top: 8px">Sign-up</label>' +
                '                        <div class="topActionDiv">' +
                '                            <label for="emailRegistrazione">Email</label>' +
                '                            <input type="text" class="inputForm" id="emailRegistrazione" name="email"' +
                '                                   required>' +
                '                        </div>' +
                '                        <div class="actionDiv">' +
                '                            <label for="nomeRegistrazione">Nome</label>' +
                '                            <input type="text" class="inputForm" id="nomeRegistrazione" name="nome" required>' +
                '                        </div>' +
                '                        <div class="actionDiv">' +
                '                            <label for="PasswordRegistrazione">Password</label>' +
                '                            <input type="password" class="inputForm" id="PasswordRegistrazione" name="password"' +
                '                                   required>' +
                '                        </div>' +
                '                        <div class="actionDiv">' +
                '                            <label for="RipetiPasswordRegistrazione">Ripeti Password</label>' +
                '                            <input type="password" class="inputForm" id="RipetiPasswordRegistrazione" name="passwordRipetuta" required>' +
                '                        </div>' +
                '                        <div class="actionDiv">' +
                '                            <button class="bottone" onclick="signup()">Sign-up</button>' +
                '                        </div>' +
                '                   <div id = "erroriSignUp"></div>' +
                '                </div>');

            document.getElementsByClassName("break")[0].style.height = "150%";

        }

        document.getElementById("autenticazione").classList.add("pressed");

    } else {

        document.getElementById("autenticazione").classList.remove("pressed");

    }

}

function erroreLogin(messaggio){

    if ($("#errori").children().length > 0){

        $("#errori").empty();

    }

    switch (messaggio){

        case "UNFE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore durante il login! <br>" +
            "                                                       L'utente non è stato trovato!</label>" +
            '</div>'

        );   break;

        case "LPME": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore durante il login! <br>" +
            "                                                       Password Errata!</label>" +
            '</div>'

        );   break;

    }

}
function erroreSignup(messaggio){

    if ($("#erroriSignUp").children().length > 0){

        $("#erroriSignUp").empty();

    }

    switch (messaggio){

        case "SPME": $("#erroriSignUp").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore durante la registrazione!<br>" +
            "                                                       Le password non coincidono!</label>" +
            '</div>'

        );   break;

        case "INE": $("#erroriSignUp").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore durante la registrazione!<br>" +
            "                                                       Nome non valido (MIN 1/MAX 32 caratteri)!</label>" +
            '</div>'

        );   break;

        case "IPE": $("#erroriSignUp").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore durante la registrazione!<br>" +
            "                                                       La password deve contenere almeno una maiuscola,<br>" +
            "                                                       almeno una maiuscola e almeno un carattere speciale!</label>" +
            '</div>'

        );   break;

        case "IEE": $("#erroriSignUp").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore durante la registrazione!<br>" +
            "                                                       Email non valida!</label>" +
            '</div>'

        );   break;

        case "NUUE": $("#erroriSignUp").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore durante la registrazione!<br>" +
            "                                                       Email già registrata!</label>" +
            '</div>'

        );   break;

    }
}

function login() {

    let xhr = new XMLHttpRequest();
    let email = document.getElementById("emailLogin").value;
    let password = document.getElementById("passwordLogin").value;

    let formAccesso = {};

    formAccesso.email = email;
    formAccesso.password = password;

    xhr.open('POST', '/gestoreAccessi/login', true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4) {

            if (xhr.status === 200) {

                window.location.reload();

            }

            if (xhr.status === 500) {

                let messaggio = JSON.parse(xhr.responseText);

                erroreLogin(messaggio.message);

            }

        }

    };

    xhr.send(JSON.stringify(formAccesso));
    xhr.close;

}

function signup() {

    let xhr = new XMLHttpRequest();

    let email = document.getElementById("emailRegistrazione").value;
    let nome = document.getElementById("nomeRegistrazione").value;
    let password = document.getElementById("PasswordRegistrazione").value;
    let passwordRipetuta = document.getElementById("RipetiPasswordRegistrazione").value;

    let formRegistrazione = {};

    formRegistrazione.email = email;
    formRegistrazione.nome = nome;
    formRegistrazione.password = password;
    formRegistrazione.passwordRipetuta = passwordRipetuta;

    xhr.open('POST', '/gestoreAccessi/signup', true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4) {

            if (xhr.status === 200) {

                window.location.reload();

            }

            if (xhr.status === 500) {

                let messaggio = JSON.parse(xhr.responseText);

                erroreSignup(messaggio.message);

            }

        }

    };

    xhr.send(JSON.stringify(formRegistrazione));
    xhr.close;

}

function logout(){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/gestoreAccessi/logout', true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4) {

            if (xhr.status === 200) {

                window.location.reload();

            }

        }

    };

    xhr.send();
    xhr.close;

}