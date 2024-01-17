function creaEventi(){

    let div = document.getElementById("info");

    document.getElementsByClassName("break")[0].style.height = "100%";
    div.innerHTML = "";

    if (document.getElementById("creaEventi").classList.contains("pressed") === false) {

        if(div.children.length === 0) {

            for (let child of document.getElementById("strumenti").children) {

                child.classList.remove("pressed");

            }

            document.getElementsByClassName("break")[0].style.height = "150%";

        }

        $(div).append('<div class = "titleBar" id="titleBar">'+
            '            <img class="iconTitle" src="https://i.postimg.cc/PxkLPt7x/event.png" id="title">' +
            '            <label for="title">Gestore eventi</label>'+
            '</div>'+
            ' <div class="breakDivAction">'+
            '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
            '       Creazione' +
            '   </div>' +
            '<div class="actionDiv">'+
            '                  <label for="nome">Nome Evento:</label>' +
            '                  <input type="text" id="evento" class="inputForm">' +
            '              </div>' +
            '<div class="actionDiv">' +
            '                  <label>Posizione:</label>' +
            '                  <input type="number" id="riga" class="inputForm" style="grid-row: 2; grid-column: 1; width: 170px;">' +
            '                  <input type="number" id="colonna" class="inputForm" style="grid-row: 2; grid-column: 2; width: 170px;">' +
            '              </div>' +
            '<div id="istruzioni">' +
            '</div>' +
            '<div class="actionDiv">' +
            '                  <button class="bottone" onclick="aggiungiEvento('+  '\'Dialogo\'' + ')">Aggiungi istruzione "Dialogo"</button>' +
            '              </div>' +
            '<div class="actionDiv">' +
            '                  <button class="bottone" onclick="aggiungiEvento('+  '\'Inizia\'' + ')">Aggiungi istruzione "Inizia Ciclo"</button>' +
            '              </div>' +
            '<div class="actionDiv">' +
            '                  <button class="bottone" onclick="aggiungiEvento('+  '\'Fine\'' + ')">Aggiungi istruzione "Fine Ciclo"</button>' +
            '              </div>' +
            '<div class="actionDiv">' +
            '                  <button class="bottone" onclick="aggiungiEvento('+  '\'Mostra\'' + ')">Aggiungi istruzione "Mostra Testo"</button>' +
            '              </div>' +
            '<div class="actionDiv">' +
            '                  <button class="bottone" onclick="creaEvento()">Crea Evento</button>' +
            '              </div>' +
            '<div id="errori"></div>' +
            '</div>' +
            '<div class="breakDivAction">'+
            '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
            '       Anteprima' +
            '   </div>' +
            '<div class="actionDiv">'+
            '                  <label>Nome evento:</label>' +
            '                   <input type="text" class="inputForm" id="nomeEvento">' +
            '</div>' +
            '<div class="actionDiv">' +
            '                   <button class="bottone" onclick="visualizzaAnteprima()">Anteprima Evento</button>' +
            '              </div>' +
            '</div>' +
            '<div class="breakDivAction">' +
            '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
            '       Eventi' +
            '       </div>' +
            '           <div class="actionDiv" id="show">' +
            '       </div> ' +
            '   </div>' +
            '</div>');

        document.getElementById("creaEventi").classList.add("pressed");
        trovaEventi()
    } else {

        document.getElementById("creaEventi").classList.remove("pressed");

    }

}

function aggiungiEvento(flag){

    switch (flag){

        case "Dialogo": $("#istruzioni").append('' +
            '<div class="actionDiv">'+
            '                  <label>Istruzione Mostra Dialogo (Testo)</label>' +
            '                   <input type="text" class="inputForm istruzione dialogo">' +
            '</div>'); break;

        case "Inizia": $("#istruzioni").append('' +
            '<div class="actionDiv">'+
            '                  <label>Istruzione Inizia Ciclo (Ripetizioni)</label>' +
            '                   <input type="number" class="inputForm istruzione inizio">' +
            '</div>'); break;

        case "Fine": $("#istruzioni").append('' +
            '<div class="actionDiv">'+
            '                  <label>Istruzione "Fine Ciclo"</label>' +
            '                   <input type="hidden" class="inputForm istruzione fine" value="fine-ciclo">' +
            '</div>'); break;

        case "Mostra": $("#istruzioni").append('' +
            '<div class="actionDiv">'+
            '                  <label>Istruzione Mostra Testo (Testo)</label>' +
            '                   <input type="text" class="inputForm istruzione testo">' +
            '</div>'); break;

    }

    if(document.getElementById("istruzioni").children.length === 4){

        document.getElementsByClassName("break")[0].style.height = "300%";

    }

}

function creaEvento() {

    let xhr = new XMLHttpRequest();

    let eventoJSON = {
        "nome": document.getElementById("evento").value,
        "riga": document.getElementById("riga").value,
        "colonna": document.getElementById("colonna").value,
        "istruzioni" : ottieniIstruzioni()
    };

    xhr.open('POST', '/gestoreEventi/creaEvento', true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {

            if ($("#errori").children().length > 0){

                $("#errori").empty();

            }

            trovaEventi();
            creaEventi();
            creaEventi();

        }

        if (xhr.readyState === 4 && xhr.status === 500) {

            let messaggio = JSON.parse(xhr.responseText);

            erroreEvento(messaggio.message);

        }

        if (xhr.readyState === 4 && xhr.status === 302) {

            let messaggio = JSON.parse(xhr.responseText);

            erroreEvento(messaggio.message);

        }

    };

    xhr.send(JSON.stringify(eventoJSON));
    xhr.close;

}

function ottieniIstruzioni(){

    let istruzioniDiv = document.getElementsByClassName("istruzione");
    let istruzioni = [];

    for (let istruzioniDivElement of istruzioniDiv) {

        let objIstruzione = gestisciIstruzione(istruzioniDivElement);

        istruzioni.push(objIstruzione);

    }

    return istruzioni;

}

function gestisciIstruzione(istruzioneElement){

    let istruzione = {};

    if(istruzioneElement.classList.contains("dialogo")){

        istruzione.nome = "dialogo";
        istruzione.valore = istruzioneElement.value;

    } else if(istruzioneElement.classList.contains("inizio")){

        istruzione.nome = "inizio";
        istruzione.valore = istruzioneElement.value;

    }else if(istruzioneElement.classList.contains("fine")){

        istruzione.nome = "fine";
        istruzione.valore = istruzioneElement.value;

    }else if(istruzioneElement.classList.contains("testo")){

        istruzione.nome = "testo";
        istruzione.valore = istruzioneElement.value;

    }

    return istruzione;

}

function trovaEventi(){

    let xhr = new XMLHttpRequest();

    xhr.open('GET','/gestoreEventi/trovaEventi',true);

    xhr.onreadystatechange=function (){

        if(xhr.readyState === 4){

            if(xhr.status === 200){

                $("#show").empty();

                let x = JSON.parse(xhr.responseText);

                x.nomiEventi.forEach(function (nome){

                    $("#show").append(
                        '<label>' + nome +'</label>');
                });
            }

            if(xhr.status === 500){

            }
        }
    }

    xhr.send();
    xhr.close;
}

function visualizzaAnteprima(){

    let xhr = new XMLHttpRequest();
    let nomeEvento = document.getElementById("nomeEvento").value;

    let requestData = {
        nome: nomeEvento
    };

    xhr.open('POST', '/gestoreEventi/visualizzaAnteprima', true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4) {

            if (xhr.status === 200) {

                let x = JSON.parse(xhr.responseText);
                let ciclo = false;
                let n;

                $("#istruzione").empty();

                x.nomiIstruzioni.forEach(function (nome, index){

                    switch (nome.nome){

                        case 'dialogo':

                            if (ciclo){

                                for (let i = 0; i < n; i++){
                                    $("#istruzione").append(
                                        '<label>' + nome.valore + '</label>');
                                }

                            } else{

                                $("#istruzione").append(
                                    '<label>' + nome.valore + '</label>');

                            } break;

                        case 'inizio': ciclo = true; n = parseInt(nome.valore); break;

                        case 'fine': ciclo = false; break;

                        case 'testo':

                            if (ciclo){

                                for (let i = 0; i < n; i++){
                                    $("#istruzione").append(
                                        '<label>' + nome.valore + '</label>');
                                }

                            } else{

                                $("#istruzione").append(
                                    '<label>' + nome.valore + '</label>');

                            } break;

                    }

                });

            }

        }

    };

    xhr.send(JSON.stringify(requestData));
    xhr.close;

}

function erroreEvento(messaggio){

    if ($("#errori").children().length > 0){

        $("#errori").empty();

    }

    switch (messaggio){

        case "NUITE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       La lunghezza del testo supera i 32 caratteri!</label>" +
            '</div>'

        );   break;

        case "IENE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Nome non valido!</label>" +
            '</div>'

        );   break;

        case "INOCE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Numero di cicli non valido!</label>" +
            '</div>'

        );   break;

        case "INOIE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Numero di istruzioni non valido!</label>" +
            '</div>'

        );   break;

        case "NURACE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Evento già presente per le coordinate!</label>" +
            '</div>'

        );   break;

        case "NURCNE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Evento già presente e coordinate già occupate!</label>" +
            '</div>'

        );   break;

        case "NUEE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Evento già presente!</label>" +
            '</div>'

        );   break;

        case "ICVE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Istruzione Ciclo non valida!</label>" +
            '</div>'

        );   break;

        case "ICE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Numero istruzione Inizio Ciclo e Fine Ciclo non valido!</label>" +
            '</div>'

        );   break;

        case "INCE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Ciclo innestato!</label>" +
            '</div>'

        );   break;

        case "ITVE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Testo non valido!</label>" +
            '</div>'

        );   break;

        case "IPE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Posizione non valida!</label>" +
            '</div>'

        );   break;

        case "IDVE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Dialogo non valido!</label>" +
            '</div>'

        );   break;

        case "INIE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Numero istruzioni non valido!</label>" +
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
