let x1;
let y1;

function creaDivPixelArt(){

    let div = document.getElementById("info");

    document.getElementsByClassName("break")[0].style.height = "100%";
    div.innerHTML = "";

    if (document.getElementById("crea").classList.contains("pressed") === false) {

        if(div.children.length === 0) {

            for (let child of document.getElementById("strumenti").children) {

                child.classList.remove("pressed");

            }

            $(div).append('<div class = "titleBar" id="titleBar">'+
                '            <img class="iconTitle" src="https://i.postimg.cc/d1jQtQT7/Tavola-disegno-1-3.png" id="title">' +
                '            <label for="title">Gestore Pixel Art</label>'+
                '</div>'+
                ' <div class="breakDivAction">'+
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Creazione' +
                '   </div>' +
                '   <div class="actionDiv">' +
                '       <label for="nomeImmagine">Nome immagine</label>' +
                '       <input type="text" id="nomeImmagine" class="inputForm" style="margin-top: 2px">' +
                '   </div>' +
                '   <div class="actionDiv">'+
                '        <button onclick="creaPixelArt()" class="bottone">Crea immagine vuota</button>' +
                '   </div>' +
                '</div>'+
                ' <div class="breakDivAction">'+
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Integrazione' +
                '   </div>' +
                '   <div class="actionDiv">'+
                '       <button onclick="integraImmagine()" class="bottone" style="margin-top: 1px;">Integra immagine</button>' +
                '   </div>' +
                '   </div>' +
                '</div>');

            document.getElementsByClassName("break")[0].style.height = "150%";

        }

        document.getElementById("crea").classList.add("pressed");

    } else {

        document.getElementById("crea").classList.remove("pressed");

    }
}

function creaPixelArt(){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/gestorePixelArt/creaPixelArt', true);

    let pixelArt = {};

    pixelArt.nome = document.getElementById("nomeImmagine").value;
    pixelArt.altezza = 32 + "";
    pixelArt.larghezza = 32 + "";

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status == 200){

            disegnaPixelArt(document.getElementById("griglia"));

        }

    };

    xhr.send(JSON.stringify(pixelArt));
    xhr.close;

}




function disegnaPixelArt(divTile) {

    x1 = parseInt("32")
    y1 = parseInt("32");
    let px = "32px";

    divTile.innerHTML = "";

    disegnaDiv(divTile, x1, y1);

    if (document.head.children.length > 2) {

        document.head.children[2].remove();

    }

    let style = document.createElement("style");
    style.appendChild(document.createTextNode(
        ".griglia{ " +
        "    display: grid;" +
        "    grid-template-columns: repeat(" + x1 +","  + px +");" +
        "    width: fit-content;" +
        "    top: 50px; " +
        "    margin-left: 1.5%;" +
        "    block-size: fit-content;" +
        "}"));

    document.head.append(style);

}

function disegnaDiv(divTile, x, y){

    for(let i = 0; i < x; i++){

        let j = 0

        for(; j < y; j++){

            let cella = document.createElement("div");

            cella.id = i + "," + j;

            cella.className = "squarePixelArt";

            cella.onmouseover = function test() {

                document.getElementById("ascissa").innerHTML = cella.id.split(",")[1];
                document.getElementById("ordinata").innerHTML = cella.id.split(",")[0];

            };

            inizializzaStrumenti(cella,2);

            divTile.append(cella);

        }

    }

}


function creaPalette(){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/pixelArt/creaPalette', true);

    let palette = {};

    palette.nome = document.getElementById("nome").value;
    palette.esadecimali = prendiEsadecimale();

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {

            if ($("#errori").children().length > 0){

                $("#errori").empty();

            }

            visualizzaListaPalette();

        }

        if (xhr.readyState === 4 && xhr.status === 500) {

            let messaggio = JSON.parse(xhr.responseText);

            errorePixelArt(messaggio.message);

        }

        if (xhr.readyState === 4 && xhr.status === 302) {

            let messaggio = JSON.parse(xhr.responseText);

            errorePixelArt(messaggio.message);

        }

    };

    xhr.send(JSON.stringify(palette));
    xhr.close;

}

function prendiEsadecimale(){

    let esadecimali = [];

    for (let elementoPalette of document.getElementsByClassName("squareColor")) {
        esadecimali.push(elementoPalette.style.backgroundColor);
    }

    return esadecimali;

}

function visualizzaListaPalette(){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/pixelArt/visualizzaListaPalette', true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {

            let nomi = JSON.parse(xhr.responseText);

            $("#nomiPalette").empty();

            nomi.nomiPalette.forEach(function (nome){

                $("#nomiPalette").append(

                    '<label>' + nome +'</label>');

            });

        }

        if (xhr.readyState === 4 && xhr.status === 302) {

            let messaggio = JSON.parse(xhr.responseText);

            errorePixelArt(messaggio.message);

        }

    };

    xhr.send();
    xhr.close;

}

function caricaPalette(){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/matita/visualizzaCollezioneElementi', true);

    let pixelArt = {};

    pixelArt.nome = document.getElementById("nomePalette").value;

    pixelArt.semaforo = "pixelArt";

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {

            if ($("#errori").children().length > 0){

                $("#errori").empty();

            }

            $("#coloriPalette").empty();

            let risposta = JSON.parse(xhr.responseText);

            risposta.colori.forEach(function (nome){

                let color = document.createElement('div');
                color.classList.add("squareColor");
                color.classList.add("palette");
                color.style.backgroundColor = nome;

                $("#coloriPalette").append(color);

            });

            $(".palette").click(function (){

                $(".palette").removeClass('selected');
                $(".palette").css("border", "none");

                $(this).addClass('selected');
                $(this).css("border", "solid 1px #516f96");

            });

        }

        if (xhr.readyState === 4 && xhr.status === 500){

            let messaggio = JSON.parse(xhr.responseText);

            errorePixelArt(messaggio.message);

        }

    };

    xhr.send(JSON.stringify(pixelArt));
    xhr.close;

}

function recuperaPA(){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/gestorePixelArt/recuperaPixelArt', true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {

            let pa = JSON.parse(xhr.responseText);

            $("#griglia").empty();

            pa.pixelArt.forEach(function (jsonPA){

                prendiPADallaSessione(jsonPA);

            });

            creaStile(parseInt(pa.pixelArt[pa.pixelArt.length - 1].colonna) + 1, "32px");

        }

        if (xhr.readyState === 4 && xhr.status === 302) {

            let messaggio = JSON.parse(xhr.responseText);

            errorePixelArt(messaggio.message);

        }

    };

    xhr.send();
    xhr.close;

}

function prendiPADallaSessione(jsonPA){

    let divPA = document.getElementById("griglia");

    let cella = document.createElement("div");


    cella.id = jsonPA.riga + "," + jsonPA.colonna;
    cella.className = "squarePixelArt";

    if(jsonPA.colore === "" ){

        cella.style.backgroundColor = "none";

    } else {

        cella.style.backgroundColor = jsonPA.colore;

    }

    inizializzaStrumenti(cella, 0);

    divPA.append(cella);

}

function integraImmagine(){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/gestorePixelArt/integraImmagine', true);

    let immagine = {};

    let stile = document.createElement("style");

    stile.appendChild(document.createTextNode(".griglia div{" +
        "" +
        "border: none;" +
        "" +
        "}"));

    document.head.appendChild(stile);

    html2canvas(document.querySelector("#griglia")).then(canvas => {

        canvas.style.width = "32px";
        canvas.style.height = "32px";

        let base64 = canvas.toDataURL('image/png');

        base64 = base64.replaceAll("data:image/png;base64,", "");

        immagine.immagine = base64;

        xhr.send(JSON.stringify(immagine));
        xhr.close;

    });

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {

            if ($("#errori").children().length > 0){

                $("#errori").empty();

            }

        }

        if (xhr.readyState === 4 && xhr.status === 500) {

            let messaggio = JSON.parse(xhr.responseText);

            errorePixelArt(messaggio.message);

        }

        if (xhr.readyState === 4 && xhr.status === 302) {

            let messaggio = JSON.parse(xhr.responseText);

            errorePixelArt(messaggio.message);

        }

    };

    document.head.removeChild(stile);

}

function errorePixelArt(messaggio){

    if ($("#errori").children().length > 0){

        $("#errori").empty();

    }

    switch (messaggio){

        case "NUIE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Immagine già integrata!</label>" +
            '</div>'

        );   break;

        case "IPNE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Nome palette non valido!</label>" +
            '</div>'

        );   break;

        case "INCE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Numero colori non valido!</label>" +
            '</div>'

        );   break;

        case "PNFE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Palette non valida, prova a crearla!</label>" +
            '</div>'

        );   break;

        case "PCE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Palette già esistente!</label>" +
            '</div>'

        );   break;

        case "IRE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Riga Non Valida!</label>" +
            '</div>'

        );   break;

        case "ICE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Colonna non valida!</label>" +
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
