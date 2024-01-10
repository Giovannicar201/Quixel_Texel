let flagTile = 0;
let selectorGriglia = new Array();

function creaStrumentoGenerativa(){

    let divStrumentiMappa = document.getElementById("info");

    document.getElementsByClassName("break")[0].style.height = "100%";
    divStrumentiMappa.innerHTML = "";

    if (document.getElementById("generative").classList.contains("pressed") === false) {

        for (let bottoniDiv of document.getElementById("strumenti").children) {

            bottoniDiv.classList.remove("pressed");

        }

        if(divStrumentiMappa.children.length === 0) {

            $(divStrumentiMappa).append('<div class = "titleBar" id="titleBar">'+
                '            <img class="iconTitle" src="https://i.postimg.cc/KzPFKBhT/Tavola-disegno-1-9.png" id="title">' +
                '            <label for="title">Strumento di scattering</label>'+
                '</div>' +
                '<div class="breakDivAction">' +
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Generazione' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <button class = "bottone" onclick="genera()">Genera</button>' +
                '   </div>');

            document.getElementsByClassName("break")[0].style.height = "150%";

        }

        document.getElementById("generative").classList.add("pressed");

    } else {

        document.getElementById("generative").classList.remove("pressed");

    }

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
                '                  <button class="bottone" onclick="ottieniContenutoCartella()">Visualizza entità</button>' +
                '</div>' +
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
                piazzaEntita(document.getElementsByClassName("selected")[0].id, cella);

            } else{

                cella.style.backgroundColor = document.getElementsByClassName("selected")[0].style.backgroundColor;

            }

        }

        if(flagTile == 1 && cella.className === "square"){

            cella.innerHTML = "";

            let img = document.createElement("img");
            img.className = "cella";
            cella.append(img);

        } else if(flagTile == 1 && cella.className === "squarePixelArt") {

            cella.style.backgroundColor = "#3F3F3F";

        }

        if(flagTile == 2){

            if (selectorGriglia.length < 2) {

                selectorGriglia[selectorGriglia.length] = cella.id;

                if (selectorGriglia.length == 2) {

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

function piazzaEntita(nome, div){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/matita/piazzaEntita', true);

    let entita = {};

    entita.nome = nome;
    entita.riga = div.id.split(",")[0].toString();
    entita.colonna = div.id.split(",")[1].toString();

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {



        }

    };

    xhr.send(JSON.stringify(entita));
    xhr.close;

}

function genera(){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/IA/genera', true);

    //TO-DO

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {

            recuperaMappa()

        }

    };

    xhr.send();
    xhr.close;

}
