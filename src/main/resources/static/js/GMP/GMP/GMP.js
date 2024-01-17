let x;
let y;

function creaDivMappa(){

    let divStrumentiMappa = document.getElementById("info");

    document.getElementsByClassName("break")[0].style.height = "100%";
    divStrumentiMappa.innerHTML = "";

    if (document.getElementById("crea").classList.contains("pressed") === false) {

        if(divStrumentiMappa.children.length === 0) {

            for (let strumenti of document.getElementById("strumenti").children) {

                strumenti.classList.remove("pressed");

            }

            $(divStrumentiMappa).append('<div class = "titleBar" id="titleBar">'+
                '            <img class="iconTitle" src="https://i.postimg.cc/d1jQtQT7/Tavola-disegno-1-3.png" id="title">' +
                '            <label for="title">Gestore Mappa</label>'+
                '</div>'+
                ' <div class="breakDivAction">'+
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Creazione' +
                '   </div>' +
                '  <div class="actionDiv">'+
                '                  <label for="nomeMappa">Nome:</label>' +
                '                  <input type="text" id="nomeMappa" class="inputForm" required>' +
                '              </div>' +
                '<div class="actionDiv">'+
                '                  <label for="larghezza">Larghezza:</label>' +
                '                  <input type="number" id="larghezza" class="inputForm" max="32" required>' +
                '              </div>' +
                '<div class="actionDiv">' +
                '                  <label for="altezza">Altezza:</label>' +
                '                  <input type="number" id="altezza" class="inputForm" max="32" required>' +
                '              </div>' +
                '<div class="actionDiv">' +
                '                  <button class="bottone" onclick="creaGriglia()">Crea Mappa</button>' +
                '              </div>' +
                '<div id = "errori"></div>' +
                '</div>'+
                '<div class="breakDivAction">' +
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Statistiche' +
                '   </div>' +
                '   <div class="actionDiv" style="display: flex;">' +
                '       <label for= "LargezzaMappa">Larghezza Mappa - </label>' +
                '       <div id="LarghezzaMappa" style="margin-left: 8px;"></div>' +
                '   </div>' +
                '   <div class="actionDiv" style="display: flex;">' +
                '       <label for= "AltezzaMappa">Altezza Mappa - </label>' +
                '       <div id="AltezzaMappa" style="margin-left: 8px;"></div>' +
                '   </div>' +
                '   <div class="actionDiv" style="display: flex;">' +
                '       <label for= "copertura">Entità Piazzate - </label>' +
                '       <div id="copertura" style="margin-left: 8px;"></div>' +
                '   </div>' +
                '   <div class="actionDiv" style="display: flex;">' +
                '       <label for= "coperturaPercentuale">Entità Piazzate Percentuale - </label>' +
                '       <div id="coperturaPercentuale" style="margin-left: 8px;"></div>' +
                '   </div>' +
                '   <div class="actionDiv" style="display: flex;">' +
                '       <label for= "celle">Celle - </label>' +
                '       <div id="celle" style="margin-left: 8px;"></div>' +
                '   </div>' +
                '   <div class="actionDiv" style="display: flex;">' +
                '       <label for= "celleVuote">Celle vuote - </label>' +
                '       <div id="celleVuote" style="margin-left: 8px;"></div>' +
                '   </div>' +
                '   <div class="actionDiv" style="display: flex;">' +
                '       <label for= "celleVuotePercentuale">Celle vuote percentuale - </label>' +
                '       <div id="celleVuotePercentuale" style="margin-left: 8px;"></div>' +
                '   </div>' +
                '</div>' +
                '<div class="breakDivAction">' +
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Cursore' +
                '   </div>' +
                '   <div class="actionDiv" style="display: flex;">' +
                '       <label for= "ascissa">Coordinata X -  </label>' +
                '       <div id="ascissa"></div>' +
                '   </div>' +
                '   <div class="actionDiv" style="display: flex;">' +
                '       <label for="ordinata">Coordinata Y -  </label>' +
                '       <div id="ordinata"></div>' +
                '   </div>' +
                '</div>');

            document.getElementsByClassName("break")[0].style.height = "150%";

        }

        document.getElementById("crea").classList.add("pressed");
        visualizzaStatisticheMappa();

    } else {

        document.getElementById("crea").classList.remove("pressed");

    }
}

function creaGriglia(){

    let contenitoreGriglia = document.getElementById("griglia");

    contenitoreGriglia.innerHTML = "";

    let righe = parseInt(document.getElementById("altezza").value);
    let colonne = parseInt(document.getElementById("larghezza").value);

    if(document.getElementsByClassName("break")[0] !== undefined){

        document.getElementsByClassName("break")[0].style.height = "150%";

    }

    creaMappa(contenitoreGriglia, righe, colonne, document.getElementById("nomeMappa").value);

}

function disegnaMappa(div, righe, colonne) {

    x = parseInt(righe)
    y = parseInt(colonne);
    let px = "32px";

    for(let i = 0; i < x; i++){

        let j = 0

        for(; j < y; j++){

            let cella = document.createElement("div");

            let img = document.createElement("img");
            img.className = "cella";

            cella.id = i + "," + j;
            cella.className = "square";

            cella.append(img);

            cella.onmouseover = function test() {

                document.getElementById("ascissa").innerHTML = cella.id.split(",")[1];
                document.getElementById("ordinata").innerHTML = cella.id.split(",")[0];

            };

            inizializzaStrumenti(cella, 1);

            div.append(cella);

        }

    }

    creaStile(colonne, px);

}

function prendiMappaDallaSessione(jsonMappa){

    let divMappa = document.getElementById("griglia");

    let cella = document.createElement("div");

    let img = document.createElement("img");
    img.className = "cella";

    if (jsonMappa.immagine !== '') {

        img.src = "data:image;base64," + jsonMappa.immagine;

    }

    cella.id = jsonMappa.riga + "," + jsonMappa.colonna;
    cella.className = "square";

    cella.append(img);

    cella.onmouseover = function test() {

        document.getElementById("ascissa").innerHTML = cella.id.split(",")[1];
        document.getElementById("ordinata").innerHTML = cella.id.split(",")[0];

    };

    inizializzaStrumenti(cella, 1);

    divMappa.append(cella);

}

function creaStile(colonne, px){

    if (document.head.children.length > 3) {

        document.head.children[3].remove();

    }

    let stileMappa = document.createElement("style");

    stileMappa.appendChild(document.createTextNode(
        ".griglia{ " +
        "    display: grid;" +
        "    grid-template-columns: repeat(" + colonne +","  + px +");" +
        "    width: fit-content;" +
        "    top: 50px;" +
        "    margin-left: 1.5%;" +
        "    block-size: fit-content;" +
        "}"));

    document.head.append(stileMappa);

}

function creaMappa(contenitoreGriglia, altezza, larghezza, nome){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/gestoreMappa/creaMappa', true);

    let map = {};

    map.nome = nome;

    map.altezza = altezza.toString();
    map.larghezza = larghezza.toString();

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status == 200){

            if ($("#errori").children().length > 0){

                $("#errori").empty();

            }

            disegnaMappa(contenitoreGriglia, altezza, larghezza);
            visualizzaStatisticheMappa();

        }

        if (xhr.readyState === 4 && xhr.status === 302) {

            let messaggio = JSON.parse(xhr.responseText);

            erroreMappa(messaggio.message);

        }

        if (xhr.readyState === 4 && xhr.status === 500) {

            let messaggio = JSON.parse(xhr.responseText);

            erroreMappa(messaggio.message);

        }

    };

    xhr.send(JSON.stringify(map));
    xhr.close;

}

function recuperaMappa(){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/gestoreMappa/recuperaMappa', true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {

            let map = JSON.parse(xhr.responseText);

            $("#griglia").empty();

            map.mappa.forEach(function (mapDiv){

                prendiMappaDallaSessione(mapDiv);

            });

            creaStile(parseInt(map.mappa[map.mappa.length - 1].colonna) + 1, "32px");
            visualizzaStatisticheMappa();

        }

        if (xhr.readyState === 4 && xhr.status === 302) {

            let messaggio = JSON.parse(xhr.responseText);

            if (messaggio.message === "MSEE"){

                window.location.href = "auth";

            }

        }

    };

    xhr.send();
    xhr.close;

}

function visualizzaStatisticheMappa(){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/gestoreMappa/visualizzaStatisticheMappa', true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {

            let statistiche = JSON.parse(xhr.responseText);

            $("#LarghezzaMappa").html(statistiche.larghezza);
            $("#AltezzaMappa").html(statistiche.altezza);
            $("#copertura").html(statistiche.entitaPiazzate);
            $("#coperturaPercentuale").html(statistiche.entitaPiazzatePercentuale + " %");
            $("#celle").html(statistiche.numeroTotaleCelle);
            $("#celleVuote").html(statistiche.celleVuote);
            $("#celleVuotePercentuale").html(statistiche.celleVuotePercentuale + " %");

        }

        if (xhr.readyState === 4 && xhr.status === 302) {

            let messaggio = JSON.parse(xhr.responseText);

            if (messaggio.message === "MSEE"){

                window.location.href = "auth";

            }

        }

    };

    xhr.send();
    xhr.close;

}

function erroreMappa(messaggio){

    if ($("#errori").children().length > 0){

        $("#errori").empty();

    }

    switch (messaggio){

        case "IMWE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       La lunghezza massima è 32px, quella minima è 1px!</label>" +
            '</div>'

        );   break;

        case "IMHE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       L'altezza massima è 32px, quella minima è 1px!</label>" +
            '</div>'

        );   break;

        case "IMNE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Il nome della mappa può contenere solo lettere!</label>" +
            '</div>'

        );   break;

        case "MSME": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Devi prima creare una Mappa!</label>" +
            '</div>'

        );   break;

        case "MSMSE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Controlla di aver rispettato i requisiti!</label>" +
            '</div>'

        );   break;

        case "ICE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Colonna non valida!</label>" +
            '</div>'

        );   break;

        case "ENFE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Entità non esistente!</label>" +
            '</div>'

        );   break;

        case "IRE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Riga Non Valida!</label>" +
            '</div>'

        );   break;

        case "FNFE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Cartella non Valida!</label>" +
            '</div>'

        );   break;

        case "NQTE":
            window.location.replace("error");
            break;


        case "MSEE":
            window.location.replace("auth");
            break;

    }

}
