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
                '                  <input type="number" id="larghezza" class="inputForm" required>' +
                '              </div>' +
                '<div class="actionDiv">' +
                '                  <label for="altezza">Altezza:</label>' +
                '                  <input type="number" id="altezza" class="inputForm" required>' +
                '              </div>' +
                '<div class="actionDiv">' +
                '                  <button class="bottone" onclick="creaGriglia()">Crea Mappa</button>' +
                '              </div>' +
                '</div>' +
                '<div class="breakDivAction">'+
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Esportazione' +
                '   </div>' +
                '<div class="actionDiv">'+
                '                  <label for="formato">Formato:</label>' +
                '                  <div style="display: flex; margin-top: 8px;">' +
                '                       <button onclick="bottoneDownload(\'png\')" class="bottone download png" style="width: 114px; background-color: #1A1A1A;">PNG</button>' +
                '                       <button onclick="bottoneDownload(\'jpg\')" class="bottone download jpg" style="width: 114px; margin-left: 5px; margin-right: 5px; background-color: #1A1A1A;">JPG</button>' +
                '                       <button onclick="bottoneDownload(\'json\')" class="bottone download json" style="width: 114px; background-color: #1A1A1A;">JSON</button>' +
                '                  </div>' +
                '              </div>' +
                '<div class="actionDiv">' +
                '                  <button class="bottone" onclick="downloadFormato()">Salva Mappa</button>' +
                '              </div>' +
                '</div>' +
                '<div class="breakDivAction">' +
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Statistiche' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <label for= "LargezzaMappa">Larghezza Mappa - </label>' +
                '       <div id="LarghezzaMappa"></div>' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <label for= "AltezzaMappa">Altezza Mappa - </label>' +
                '       <div id="AltezzaMappa"></div>' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <label for= "copertura">Entità Piazzate - </label>' +
                '       <div id="copertura"></div>' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <label for= "coperturaPercentuale">Entità Piazzate Percentuale - </label>' +
                '       <div id="coperturaPercentuale"></div>' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <label for= "celleVuote">Celle vuote - </label>' +
                '       <div id="celleVuote"></div>' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <label for= "celleVuotePercentuale">Celle vuote percentuale - </label>' +
                '       <div id="celleVuotePercentuale"></div>' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <label for= "coperturaEventi">Eventi - </label>' +
                '       <div id="coperturaEventi"></div>' +
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

    } else {

        document.getElementById("crea").classList.remove("pressed");

    }
}

function bottoneDownload(selezione){

    for (let bottoniSelezioneFormato of document.getElementsByClassName("bottone")) {

        if (bottoniSelezioneFormato.classList.contains("download")) {

            bottoniSelezioneFormato.style.backgroundColor = "#1A1A1A";
            bottoniSelezioneFormato.classList.remove("selected");

        }

        if(bottoniSelezioneFormato.classList.contains(selezione)){

            bottoniSelezioneFormato.style.backgroundColor = "#516f96";
            bottoniSelezioneFormato.classList.add("selected");

        }

    }

}

function creaMappa(altezza, larghezza, nome){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/gestoreMappa/creaMappa', true);

    let map = {};

    map.nome = nome;

    console.log(altezza + " " + larghezza);

    map.altezza = altezza.toString();
    map.larghezza = larghezza.toString();

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {

            alert("Creazione avvenuta con successo!");

        }

        if (xhr.readyState === 4 && xhr.status === 302) {



        }

    };

    xhr.send(JSON.stringify(map));
    xhr.close;

}

function creaGriglia(){

    let contenitoreGriglia = document.getElementById("griglia");

    contenitoreGriglia.innerHTML = "";

    let righe = parseInt(document.getElementById("altezza").value);
    let colonne = parseInt(document.getElementById("larghezza").value);

    if(document.getElementsByClassName("break")[0] !== undefined){

        document.getElementsByClassName("break")[0].style.height = "150%";

    }

    disegnaMappa(contenitoreGriglia, righe, colonne);
    creaMappa(righe, colonne, document.getElementById("nomeMappa").value);

}

function recuperaMappa(){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/gestoreMappa/recuperaMappa', true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {

            let map = JSON.parse(xhr.responseText);

            console.log(map);

            $("#griglia").empty();

            map.mappa.forEach(function (mapDiv){

                prendiMappaDallaSessione(mapDiv);

            });

            console.log(map.mappa[map.mappa.length - 1].colonna);

            creaStile(parseInt(map.mappa[map.mappa.length - 1].colonna) + 1, "32px");

        }

    };

    xhr.send();
    xhr.close;

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
