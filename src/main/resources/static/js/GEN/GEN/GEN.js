function creaDivGestoreEntità(){

    let div = document.getElementById("info");

    document.getElementsByClassName("break")[0].style.height = "100%";
    div.innerHTML = "";

    if (document.getElementById("creaGestore").classList.contains("pressed") === false) {

        if(div.children.length === 0) {

            for (let bottoniUtilità of document.getElementById("strumenti").children) {

                bottoniUtilità.classList.remove("pressed");

            }

            document.getElementsByClassName("break")[0].style.height = "150%";

        }

        document.getElementById("creaGestore").classList.add("pressed");

        document.getElementById("creaGestore").classList.add("pressed");


        visualizzaListaEntità();
        $(div).append('<div class = "titleBar" id="titleBar">'+
            '            <img class="iconTitle" src="https://i.postimg.cc/nz23gNTt/ent.png" id="title">' +
            '            <label for="title">Gestore entità</label>'+
            '</div>'+
            ' <div class="breakDivAction">'+
            '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
            '       Creazione' +
            '   </div>' +
            '<div class="actionDiv">'+
            '                  <label for="nome">Nome immagine:</label>' +
            '                  <input type="text" id="nomeImmagine" class="inputForm">' +
            '              </div>'+
            '<div class="actionDiv">'+
            '                  <label>Nome entità:</label>' +
            '                  <input type="text" id="nome" class="inputForm">' +
            '              </div>' +
            '<div class="actionDiv">' +
            '                  <label>Collisioni:</label>' +
            '                   <button onclick="selectButton(\'si\')" value = "si" class="bottone collisioni" style="width: 170px; background-color: #1A1A1A; grid-column: 1;">SI</button>' +
            '                   <button onclick="selectButton(\'no\')" value = "no" class="bottone collisioni" style="width: 170px; background-color: #1A1A1A; grid-column: 2;">NO</button>' +
            '              </div>' +
            '<div class="actionDiv">'+
            '                  <label>Nome cartella:</label>' +
            '                  <input type="text" id="nomeCartella" class="inputForm">' +
            '              </div>' +
            '<div id="proprietà">' +
            '</div>' +
            '<div class="actionDiv">' +
            '                  <button class="bottone" onclick="creaDivProprietàEntità()">Aggiungi Proprietà</button>' +
            '              </div>' +
            '<div class="actionDiv">' +
            '                  <button class="bottone" onclick="creaEntità()">Crea Entità</button>' +
            '              </div>' +
            '<div id="creazione">' +
            '</div>' +
            '</div>');
    } else {

        document.getElementById("creaGestore").classList.remove("pressed");

    }

}

function selectButton(scelta){

    for (let collisioni of document.getElementsByClassName("collisioni")) {

        if (collisioni.value === scelta){

            collisioni.style.backgroundColor = "#516f96";
            collisioni.classList.add("selezionato");

        } else{

            collisioni.style.backgroundColor = "#1A1A1A";
            collisioni.classList.remove("selezionato");

        }

    }

}

function creaDivProprietàEntità(){

    if(document.getElementById("proprietà").children.length <= 6) {

        if(document.getElementById("proprietà").children.length === 0) {

            document.getElementsByClassName("break")[0].style.height = "250%";

        }

        $("#proprietà").append('' +
            '<div class="actionDiv">' +
            '                  <label>Nome Proprietà</label>' +
            '                   <input type="text" class="inputForm NomeProprietà">' +
            '</div>' +
            '<div class="actionDiv">' +
            '                  <label>Valore Proprietà</label>' +
            '                   <input type="text" class="inputForm ValoreProprietà">' +
            '</div>');


    } else {

        if ($("#creazione").children().length > 0){

            $("#creazione").empty();

        }

        $("#creazione").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Non puoi aggiungere più di 4 proprietà!</label>" +
            '</div>'

        );

    }

}

function erroreCreaEntità(){

    if ($("#creazione").children().length > 0){

        $("#creazione").empty();

    }

    $("#creazione").append(

        '<div class="actionDiv">'+
        "                  <label style='color:rgb(175,80,92);'>Errore Nella Creazione Dell'entità</label>" +
        '</div>'

    );

}

function showFile(){

    let label = document.getElementById("label");

    if(!label.classList.contains("file")) {

        label.innerHTML = document.getElementById("fileInput").files[0].name;
        label.classList.add("file");

    } else{

        label.innerHTML = "Carica File";
        label.classList.remove("file");

    }
}


function creaEntità() {

    let xhr = new XMLHttpRequest();

    let entità = ottieniEntità();

    xhr.open('POST', '/gestoreEntita/creaEntità', true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {

            alert("entità creato con successo");

        }

        if (xhr.readyState === 4 && xhr.status === 500) {

            erroreCreaEntità();

        }

    };

    xhr.send(entità);
    xhr.close;

}

function ottieniEntità(){

    let entità = {};

    entità.nomeImmagine =  document.getElementById("nomeImmagine").value;
    entità.nome = document.getElementById("nome").value;
    entità.collisioni =  document.getElementsByClassName("selezionato")[0].value;
    entità.nomeCartella = document.getElementById("nomeCartella").value;

    let proprietà = [];

    let nomi = document.getElementsByClassName("NomeProprietà");
    let valori = document.getElementsByClassName("ValoreProprietà");

    for (let i = 0; i < nomi.length; i++){

        let elemento = {};

        elemento.nomeProprieta = nomi[i].value;
        elemento.valoreProprieta = valori[i].value;

        proprietà.push(elemento);

    }

    entità.proprieta = proprietà;

    return JSON.stringify(entità);

}

function visualizzaListaEntità(){
    let xhr= new XMLHttpRequest();

    xhr.open('POST', '/gestoreEntita/visualizzaListaEntità', true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4) {

            if (xhr.status === 200) {

                $("#show1").empty();

                let x = JSON.parse(xhr.responseText);
                console.log(x);

                x.blobImmagini.forEach(function (entita){

                    let nome = Object.keys(entita)[0];
                    let src = "data:image;base64," + entita[nome];

                    let entitaWrapper = document.createElement('div');
                    entitaWrapper.classList.add('imageContainer');

                    entitaWrapper.innerHTML = '<div class="overlay" onclick="visualizzaNomeImmagine(\'' + nome + '\')">' +
                        '<img id="' + nome + '" src="' + src + '" style="width: 64px; height: 64px;" class="imgEntity">' +
                        '</div>';

                    $("#show1").append(entitaWrapper);
                });

            }

            if (xhr.status === 500) {

                alert("errore");

            }

        }

    };

    xhr.send();
    xhr.close;
}
