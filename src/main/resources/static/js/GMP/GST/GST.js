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
                '<div class="breakDivAction">'+
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Riempimento' +
                '   </div>' +
                '<div class="actionDiv">' +
                '                  <button onclick="matita()" class="bottone matita">Riempi Area</button>' +
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

function matita(){

    document.getElementsByClassName("matita")[0].style.backgroundColor = "#516f96";

    disegnaTileSelezione();

}

let flagTile = 0;
let selectorGriglia = new Array();

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

                    doTheMapSelection(primaCoordinata, secondaCoordinata);

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

function disegnaTile(){

    flagTile = 0;

}

function disegnaTileSelezione(){

    for(let i = 0; i < selectorGriglia.length; i++) {

        let cella = document.getElementById(selectorGriglia[i]);

        if(cella.className === "square") {

            cella.children[0].src = document.getElementsByClassName("selected")[0].src;
            cella.removeAttribute("style");

        } else {

            cella.removeAttribute("style");
            cella.style.backgroundColor = document.getElementsByClassName("selected")[0].style.backgroundColor;

        }

    }

    document.getElementsByClassName("matita")[0].style.backgroundColor = "#333333";

    flagTile = 0;
    selectorGriglia = [];

}

function cancellaTileSelezione(){


    for(let i = 0; i < selectorGriglia.length; i++) {

        let cella = document.getElementById(selectorGriglia[i]);

        if(cella.className === "square") {

            cella.removeAttribute("style");
            cella.innerHTML = "";

            let img = document.createElement("img");
            img.className = "cella";
            cella.append(img);

        } else {

            cella.removeAttribute("style");
            cella.style.backgroundColor = "#3F3F3F";

        }

    }

    document.getElementsByClassName("elimina")[0].style.backgroundColor = "#333333";

    flagTile = 1;
    selectorGriglia = [];

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