let flagTile = 0;
let selectorGriglia = new Array();

function creaStrumentoGenerativa(){

    let divStrumentiMappa = document.getElementById("info");

    document.getElementsByClassName("break")[0].style.height = "100%";
    divStrumentiMappa.innerHTML = "";

    if (document.getElementById("generative").classList.contains("pressed") === false) {

        for (let child of document.getElementById("strumenti").children) {

            child.classList.remove("pressed");

        }

        if(divStrumentiMappa.children.length === 0) {

            $(divStrumentiMappa).append('<div class = "titleBar" id="titleBar">'+
                '            <img class="iconTitle" src="https://i.postimg.cc/KzPFKBhT/Tavola-disegno-1-9.png" id="title">' +
                '            <label for="title">Strumento IA</label>'+
                '</div>' +
                '<div class="breakDivAction">' +
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Generazione' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <button class = "bottone" onclick="genera()">Genera</button>' +
                '   </div>' +
                '<div id = "errori"></div>' +
                '</div>');

            document.getElementsByClassName("break")[0].style.height = "150%";

        }

        document.getElementById("generative").classList.add("pressed");

    } else {

        document.getElementById("generative").classList.remove("pressed");

    }

}


function creaStrumentoScattering(){

    let divStrumentiMappa = document.getElementById("info");

    document.getElementsByClassName("break")[0].style.height = "100%";
    divStrumentiMappa.innerHTML = "";

    if (document.getElementById("scattering").classList.contains("pressed") === false) {

        for (let child of document.getElementById("strumenti").children) {

            child.classList.remove("pressed");

        }

        if(divStrumentiMappa.children.length === 0) {

            $(divStrumentiMappa).append('<div class = "titleBar" id="titleBar">'+
                '            <img class="iconTitle" src="https://i.postimg.cc/RZV34bmt/Tavola-disegno-1-8.png" id="title">' +
                '            <label for="title">Strumento di scattering</label>'+
                '</div>' +
                '<div class="breakDivAction">' +
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Parametri' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <label for= "cartella">Nome cartella</label>' +
                '       <input type="text" id= "nome" class="inputForm">' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '                  <button class="bottone" onclick="ottieniContenutoCartella(1)">Visualizza entità</button>' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <label for="entità">Entità</label>' +
                '       <div class="entityDiv" style="padding: 8px; border-radius: 2px; height: fit-content; width: 340px; margin-left: 7px;" id="show"></div>' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <label for="percentuale">Percentuale riempimento</label>' +
                '       <input type= "number" id= "percentuale" value="0" class="inputForm">' +
                '   </div>' +
                '<div id="perc"></div>' +
                '<div id = "errori"></div>' +
                '</div>');

            document.getElementsByClassName("break")[0].style.height = "150%";

        }

        document.getElementById("scattering").classList.add("pressed");

    } else {

        document.getElementById("scattering").classList.remove("pressed");

    }

}

function visualizzaEntitàScattering() {

    console.log(document.getElementById("show").children.length);

    for (let i = 0; i < document.getElementById("show").children.length; i++) {

        $("#perc").append(
            '   <div class="actionDiv">' +
            '       <label>Priorità entità ' + i + ' (%)</label>' +
            '       <input type= "number" id="' + i + 'Priorità" value="0" class="inputForm perc">' +
            '   </div>'
        );

    }

    $("#perc").append(

        '<div class="actionDiv">' +
        '   <button class="bottone" onclick="scatter()">Riempi Area</button>' +
        '</div>'
    );

}

function creaDivStrumentoRettangolareMappa(){

    let divStrumentiMappa = document.getElementById("info");

    document.getElementsByClassName("break")[0].style.height = "100%";
    divStrumentiMappa.innerHTML = "";

    if (document.getElementById("rettangolare").classList.contains("pressed") === false) {

        for (let child of document.getElementById("strumenti").children) {

            child.classList.remove("pressed");

        }

        if(divStrumentiMappa.children.length === 0) {

            $(divStrumentiMappa).append('<div class = "titleBar" id="titleBar">'+
                '            <img class="iconTitle" src="https://i.postimg.cc/sggnzvSj/Tavola-disegno-1-7.png" id="title">' +
                '            <label for="title">Strumento selezione rettangolare</label>'+
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
                '</div>' +
                '<div class="breakDivAction">' +
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Punti di selezione' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <label for= "selectedPointOne">Primo punto - </label>' +
                '       <div id="selectedPointOne"></div>' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <label for="selectedPointTwo">Secondo punto - </label>' +
                '       <div id="selectedPointTwo"></div>' +
                '   <div class="actionDiv" style="margin: 8px 8px 16px 0px;">' +
                '       <button onclick="selezione()" class="bottone selezione">Selezione area</button>' +
                '   </div>' +
                '   </div>' +
                '</div>');

            document.getElementsByClassName("break")[0].style.height = "150%";

        }

        document.getElementById("rettangolare").classList.add("pressed");

    } else {

        document.getElementById("rettangolare").classList.remove("pressed");

    }

}


function creaDivMatitaMappa(){

    let divStrumentiMappa = document.getElementById("info");

    document.getElementsByClassName("break")[0].style.height = "100%";
    divStrumentiMappa.innerHTML = "";

    if (document.getElementById("draw").classList.contains("pressed") === false) {

        for (let child of document.getElementById("strumenti").children) {

            child.classList.remove("pressed");

        }

        if(divStrumentiMappa.children.length === 0) {

            $(divStrumentiMappa).append('<div class = "titleBar" id="titleBar">'+
                '            <img class="iconTitle" src="https://i.postimg.cc/BQPjnVVL/Tavola-disegno-1-5.png" id="title">' +
                '            <label for="title">Strumento matita</label>'+
                '</div>'+
                ' <div class="breakDivAction">'+
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Entità selezionata' +
                '   </div>' +
                '<div class="actionDiv">'+
                '                  <label for="nome">Nome cartella:</label>' +
                '                  <input type="text" id="nome" class="inputForm">' +
                '              </div>' +
                '<div class="entityDiv" style="padding: 8px; border-radius: 2px; height: fit-content; width: 340px; margin-left: 7px;" id="show">' +
                '           ' +
                '       </div>'+
                '<div class="actionDiv">' +
                '                  <button class="bottone" onclick="ottieniContenutoCartella(0)">Visualizza entità</button>' +
                '</div>' +
                '<div id = "errori"></div>' +
                '</div>'+
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

        document.getElementById("draw").classList.add("pressed");
        disegnaTile();

    } else {

        document.getElementById("draw").classList.remove("pressed");

    }

}

function creaDivStrumentoGommaMappa(){

    let divStrumentiMappa = document.getElementById("info");

    document.getElementsByClassName("break")[0].style.height = "100%";
    divStrumentiMappa.innerHTML = "";

    if (document.getElementById("rubber").classList.contains("pressed") === false) {

        for (let child of document.getElementById("strumenti").children) {

            child.classList.remove("pressed");

        }

        if(divStrumentiMappa.children.length === 0) {

            $(divStrumentiMappa).append('<div class = "titleBar" id="titleBar">'+
                '            <img class="iconTitle" src="https://i.postimg.cc/d0NsYxf3/Tavola-disegno-1-6.png" id="title">' +
                '            <label for="title">Strumento gomma</label>'+
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

        cancellaTile();
        document.getElementById("rubber").classList.add("pressed");

    } else {

        document.getElementById("rubber").classList.remove("pressed");

    }

}

function creaStrumentoGommaPA(){

    let div = document.getElementById("info");

    document.getElementsByClassName("break")[0].style.height = "100%";
    div.innerHTML = "";

    if (document.getElementById("rubber").classList.contains("pressed") === false) {

        for (let child of document.getElementById("strumenti").children) {

            child.classList.remove("pressed");

        }

        if(div.children.length === 0) {

            $(div).append('<div class = "titleBar" id="titleBar">'+
                '            <img class="iconTitle" src="https://i.postimg.cc/d0NsYxf3/Tavola-disegno-1-6.png" id="title">' +
                '            <label for="title">Strumento gomma</label>'+
                '</div>');

            document.getElementsByClassName("break")[0].style.height = "150%";

        }

        cancellaTile();
        document.getElementById("rubber").classList.add("pressed");

    } else {

        document.getElementById("rubber").classList.remove("pressed");

    }

}

function creaPreviewPA(){

    let div = document.getElementById("info");

    document.getElementsByClassName("break")[0].style.height = "100%";
    div.innerHTML = "";

    if (document.getElementById("preview").classList.contains("pressed") === false) {

        for (let child of document.getElementById("strumenti").children) {

            child.classList.remove("pressed");

        }

        if(div.children.length === 0) {

            $(div).append('<div class = "titleBar" id="titleBar">'+
                '            <img class="iconTitle" src="https://i.postimg.cc/Gprst7tc/Tavola-disegno-1.png" id="title">' +
                '            <label for="title">Pattern</label>'+
                '</div>' +
                '<div class="breakDivAction">' +
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Parametri' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <label>Numero ripetizioni</label>' +
                '       <input type="number" id="numero" class="inputForm" style="margin-top: 2px">' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <button onclick="creaAnteprima()" class="bottone">Visualizza anteprima</button>' +
                '   </div>' +
                '   </div>');

            document.getElementsByClassName("break")[0].style.height = "150%";

        }

        document.getElementById("preview").classList.add("pressed");

    } else {

        document.getElementById("preview").classList.remove("pressed");

    }

}
function creaStrumentoGenerazioneCasualePA(){

    let div = document.getElementById("info");

    document.getElementsByClassName("break")[0].style.height = "100%";
    div.innerHTML = "";

    if (document.getElementById("generatore").classList.contains("pressed") === false) {

        for (let child of document.getElementById("strumenti").children) {

            child.classList.remove("pressed");

        }

        if(div.children.length === 0) {

            $(div).append('<div class = "titleBar" id="titleBar">'+
                '            <img class="iconTitle" src="https://i.postimg.cc/vBtg5W1W/Tavola-disegno-1-1.png" id="title">' +
                '            <label for="title">Gestore Palette</label>'+
                '</div>' +
                '<div class="breakDivAction">' +
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Generazione' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <label>Colori generati</label>' +
                '                   <div class="coloriDiv" style="' +
                '    background-color: #1A1A1A; width: 350px; height: 160px;">' +
                '                       <div style="' +
                '    grid-template-columns: repeat(4, 64px); ' +
                '    display: grid; ' +
                '    grid-row-gap: 6%;' +
                '    grid-column-gap: 4%;' +
                '    padding: 2%;" id="coloriPalette"></div>' +
                '                  </div>' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <button onclick="generaPaletteCasuale()" class="bottone">Genera palette</button>' +
                '   </div>' +
                '</div>' +
                '<div class="breakDivAction">' +
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Salvataggio' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <label>Nome</label>' +
                '       <input type="text" id="nome" class="inputForm" style="margin-top: 2px">' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <button onclick="creaPalette()" class="bottone">Salva Palette</button>' +
                '   </div>' +
                '   <div id="errori"></div>' +
                '   </div>' +
                '<div class="breakDivAction">' +
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Palette' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <div id="nomiPalette" style="display: grid;"></div>' +
                '   </div>' +
                '   </div>');

            document.getElementsByClassName("break")[0].style.height = "150%";

            visualizzaListaPalette();

        }

        document.getElementById("generatore").classList.add("pressed");

    } else {

        document.getElementById("generatore").classList.remove("pressed");

    }

}

function creaMatitaPA(){

    let div = document.getElementById("info");

    document.getElementsByClassName("break")[0].style.height = "100%";
    div.innerHTML = "";

    if (document.getElementById("draw").classList.contains("pressed") === false) {

        for (let child of document.getElementById("strumenti").children) {

            child.classList.remove("pressed");

        }

        if(div.children.length === 0) {

            $(div).append('<div class = "titleBar" id="titleBar">'+
                '            <img class="iconTitle" src="https://i.postimg.cc/BQPjnVVL/Tavola-disegno-1-5.png" id="title">' +
                '            <label for="title">Strumento matita</label>'+
                '</div>'+
                ' <div class="breakDivAction">'+
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Palette selezionata' +
                '   </div>' +
                '<div class="actionDiv">'+
                '                  <label for="nome">Nome palette:</label>' +
                '                  <input type="text" id="nomePalette" class="inputForm"/>' +
                '              </div>'+
                '<div class="actionDiv">' +
                '                  <button class="bottone" onclick="caricaPalette()">Visualizza palette</button>' +
                '</div>' +
                '' +
                '<div class="actionDiv">'+
                '                  <label>Colori</label>' +
                '               <div class="coloriDiv" style="' +
                '    background-color: #1A1A1A; width: 350px; height: 160px;">' +
                '                       <div style="' +
                '    grid-template-columns: repeat(4, 64px); ' +
                '    display: grid; ' +
                '    grid-row-gap: 6%;' +
                '    grid-column-gap: 4%;' +
                '    padding: 2%;" id="coloriPalette"></div>' +
                '                  </div>' +
                '                  </div>' +
                '              </div>' +
                '<div id="errori"></div>'  +
                '</div>');

            document.getElementsByClassName("break")[0].style.height = "150%";

        }

        document.getElementById("draw").classList.add("pressed");
        disegnaTile();

    } else {

        document.getElementById("draw").classList.remove("pressed");

    }


}

function selezione(){

    document.getElementsByClassName("selezione")[0].style.backgroundColor = "#516f96";

    selettoreTile();

}

function inizializzaStrumenti(cella, flag) {

    cella.onclick = function test() {

        if(flagTile == 0){

            if(flag === 1) {

                cella.children[0].src = document.getElementsByClassName("selected")[0].src;
                piazzaEntitaMappa(document.getElementsByClassName("selected")[0].id, cella);

            } else{

                cella.style.backgroundColor = document.getElementsByClassName("selected")[0].style.backgroundColor;
                piazzaColorePA(document.getElementsByClassName("selected")[0].style.backgroundColor, cella);

            }

        }

        if(flagTile == 1 && cella.className === "square"){

            cella.innerHTML = "";

            let img = document.createElement("img");
            img.className = "cella";
            cella.append(img);

            rimuoviEntitaMappa(cella);


        } else if(flagTile == 1 && cella.className === "squarePixelArt") {

            cella.style.backgroundColor = "#3F3F3F";
            rimuoviColorePA(cella);

        }

        if(flagTile == 2){

            if (selectorGriglia.length < 2) {

                selectorGriglia[selectorGriglia.length] = cella.id;

                if (selectorGriglia.length == 2) {

                    for (let divCella of document.getElementById("griglia").children) {

                        divCella.removeAttribute("style");

                    }

                    let primaCoordinata = selectorGriglia[0].split(",");
                    let secondaCoordinata = selectorGriglia[1].split(",");

                    selezioneAreaMappa(primaCoordinata, secondaCoordinata);

                    selectorGriglia = [];

                    document.getElementById("selectedPointOne").innerHTML = primaCoordinata;
                    document.getElementById("selectedPointTwo").innerHTML = secondaCoordinata;

                    let x1 = parseInt(primaCoordinata[0]);
                    let x2 = parseInt(secondaCoordinata[0]);
                    let y1 = parseInt(primaCoordinata[1]);
                    let y2 = parseInt(secondaCoordinata[1]);

                    while (x1 <= x2) {

                        y1 = parseInt(primaCoordinata[1]);

                        while(y1 < y2) {
                            selectorGriglia[selectorGriglia.length] = x1 + "," + y1;
                            y1++;
                        }

                        selectorGriglia[selectorGriglia.length] = x1 + "," + y1;
                        x1++;

                    }

                    for(let i = 0; i < selectorGriglia.length; i++) {

                        document.getElementById(selectorGriglia[i]).style.border = "solid 1px #516f96";

                    }

                    document.getElementsByClassName("selezione")[0].style.backgroundColor = "#333333";

                }


            }else{

                for(let i = 0; i < selectorGriglia.length; i++) {

                    let cella = document.getElementById(selectorGriglia[i]);

                    cella.removeAttribute("style");

                }

                selectorGriglia = [];

            }

        }

    };

}

function selezioneAreaMappa(coordinata1, coordinata2){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/selezione/selezioneAreaMappa', true);

    let entita = {};

    entita.primaRiga = coordinata1[0].toString();
    entita.primaColonna = coordinata1[1].toString();
    entita.secondaRiga = coordinata2[0].toString();
    entita.secondaColonna = coordinata2[1].toString();

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {



        }

    };

    xhr.send(JSON.stringify(entita));
    xhr.close;

}

function disegnaTile(){

    flagTile = 0;

}
function selettoreTile() {

    selectorGriglia = [];
    flagTile = 2;

}

function cancellaTile(){

    flagTile = 1;

}

function creaAnteprima(){

    let flag = document.getElementById("numero").value;

    if (document.getElementById("griglia").children.length === 0){


    } else {

        switch (parseInt(flag)) {

            case 1:
                creaRipetizioni(3);
                break;

            case 2:
                creaRipetizioni(5);
                break;

        }

    }

}

function generaPaletteCasuale(){

    let palette = document.getElementById("coloriPalette");
    let children = palette.children;

    if(children.length != 0){

        for (let i = 0; i < children.length; i++) {

            if(children[i].classList != undefined) {

                if (children[i].classList.contains("unlocked")) {

                    let randomHex = Math.floor(Math.random() * 0xffffff).toString(16);
                    randomHex = '#' + randomHex;

                    children[i].style.backgroundColor = randomHex;

                }

            }

        }

    } else {

        for (let i = 0; i < 8; i++) {

            let randomHex = Math.floor(Math.random() * 0xffffff).toString(16);
            randomHex = '#' + randomHex;

            creaDiv(randomHex, palette);

        }

    }

}

function creaDiv(hex, palette){

    let color = document.createElement('div');
    color.classList.add("squareColor");
    color.classList.add("palette");
    color.classList.add("unlocked");
    color.style.backgroundColor = hex;

    let lock = document.createElement("button");
    let img = document.createElement("img");
    img.src = "https://i.postimg.cc/vZkCkKm8/lock-2.png";
    lock.classList.add("blocca");
    lock.style.backgroundColor = "transparent";
    lock.style.border = "none";
    lock.style.padding = "20%";
    lock.append(img)

    lock.addEventListener("click", function lockPalette(){

        if(lock.classList.contains("blocca")){

            color.classList.add("locked");
            color.classList.remove("unlocked");

            lock.classList.add("bloccato");
            lock.children[0].src = "https://i.postimg.cc/HkVfSmdP/lock-1.png";
            lock.classList.remove("blocca");

        } else if(lock.classList.contains("bloccato")){

            color.classList.add("unlocked");
            color.classList.remove("locked");

            lock.classList.add("blocca");
            lock.children[0].src = "https://i.postimg.cc/vZkCkKm8/lock-2.png";
            lock.classList.remove("bloccato");

        }

    });

    color.append(lock);
    palette.appendChild(color);

}

function creaRipetizioni(ripetizioni){

    document.getElementById("result").innerHTML = "";
    document.getElementById("griglia").style.visibility = "visible";

    let stile = document.createElement("style");

    stile.appendChild(document.createTextNode(".griglia div{" +
        "" +
        "border: none;" +
        "" +
        "}"));

    document.head.appendChild(stile);

    document.getElementById("result").innerHTML = "";

    for(let i = 0; i < ripetizioni * ripetizioni; i++){

        html2canvas(document.querySelector("#griglia")).then(canvas => {

            canvas.style.width = "64px";
            canvas.style.height = "64px";

            if(i === ((ripetizioni * ripetizioni)-1)/2){

                document.getElementById("result").append(canvas);

            } else {

                canvas.style.filter = "brightness(50%)";
                document.getElementById("result").append(canvas);

            }

        });

    }

    document.getElementById("griglia").style.visibility = "hidden";
    document.getElementById("result").style.display = "grid";
    document.getElementById("result").style.gridTemplateColumns = "repeat(" + ripetizioni +", 64px)";

    if (ripetizioni == 5){

        document.getElementById("result").style.margin = "14.5% 24%";

    } else {

        document.getElementById("result").style.margin = "17.5% 26%";

    }

    document.getElementById("result").style.position = "absolute";

    document.getElementById("result").addEventListener("click", function test(){

        document.getElementById("result").innerHTML = "";
        document.getElementById("griglia").style.visibility = "visible";

    });

    document.head.removeChild(stile);

}

function piazzaEntitaMappa(nome, div){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/matita/piazza', true);

    let entita = {};

    entita.nome = nome;
    entita.riga = div.id.split(",")[0].toString();
    entita.colonna = div.id.split(",")[1].toString();

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 500) {

            let messaggio = JSON.parse(xhr.responseText);

            erroreMappa(messaggio.message);

        }

    };

    xhr.send(JSON.stringify(entita));
    xhr.close;

}

function scatter(){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/scattering/scatter', true);

    let scattering = {};
    let proprietà = [];
    let entità = document.getElementsByClassName("imgEntity");
    let i = 0;

    scattering.percentualeRiempimento = Math.trunc(document.getElementById("percentuale").value) + "";

    for (let elemento of document.getElementsByClassName("perc")) {

        let oggetto = {};

        oggetto.nome = entità[i].id;
        oggetto.priorita = Math.trunc(elemento.value) + "";

        i++;

        proprietà.push(oggetto);

    }

    scattering.proprieta = proprietà;

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {

            recuperaMappa();

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

    xhr.send(JSON.stringify(scattering));
    xhr.close;

}

function piazzaColorePA(coloreScelto, div){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/matita/piazza', true);

    let colore = {};

    colore.colore = coloreScelto;
    colore.riga = div.id.split(",")[0].toString();
    colore.colonna = div.id.split(",")[1].toString();

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 500) {

            errorePixelArt();

        }

    };

    xhr.send(JSON.stringify(colore));
    xhr.close;
}

function rimuoviEntitaMappa(div){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/gomma/rimuovi', true);

    let entita = {};

    entita.semaforo = "mappa";
    entita.riga = div.id.split(",")[0].toString();
    entita.colonna = div.id.split(",")[1].toString();

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {



        }

    };

    xhr.send(JSON.stringify(entita));
    xhr.close;

}

function rimuoviColorePA(div){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/gomma/rimuovi', true);

    let colore = {};

    colore.semaforo = "pixelArt";
    colore.riga = div.id.split(",")[0].toString();
    colore.colonna = div.id.split(",")[1].toString();

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {



        }

    };

    xhr.send(JSON.stringify(colore));
    xhr.close;

}

function genera(){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/IA/genera', true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {

            recuperaMappa()

        }

        if (xhr.readyState === 4 && xhr.status === 302){

            let messaggio = JSON.parse(xhr.responseText);

            erroreMappa(messaggio.message);

        }

    };

    xhr.send();
    xhr.close;

}
