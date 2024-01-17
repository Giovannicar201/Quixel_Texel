function creaDivImmaginiEntità(){

    let div = document.getElementById("info");

    document.getElementsByClassName("break")[0].style.height = "100%";
    div.innerHTML = "";

    if (document.getElementById("creaImmagini").classList.contains("pressed") === false) {

        if(div.children.length === 0) {

            for (let child of document.getElementById("strumenti").children) {

                child.classList.remove("pressed");

            }

            document.getElementsByClassName("break")[0].style.height = "150%";

        }

        $(div).append('<div class = "titleBar" id="titleBar">'+
            '            <img class="iconTitle" src="https://i.postimg.cc/PxkLPt7x/event.png" id="title">' +
            '            <label for="title">Gestore entità</label>'+
            '</div>'+
            ' <div class="breakDivAction">'+
            '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
            '       Caricamento' +
            '   </div>' +
            '<div class="actionDiv">'+
            '                  <label>Caricamento:</label>' +
            '              </div>' +
            '<div class="actionDiv">' +
            '                   <form id="file" enctype="multipart/form-data">' +
            '                       <label for="fileInput" id="label" style="cursor: pointer; width: 352px; height: 24px; margin-bottom: 8px;" class="inputForm">Carica File</label>' +
            '                       <input name="file" id="fileInput" style="opacity: 0; position: absolute;" type="file" onchange="showFile()" accept="image/*" hidden="hidden"/>' +
            '                   </form>' +
            '              </div>' +
            '<div class="actionDiv" style="margin-top: 0px;">' +
            '                   <button class="bottone" onclick="caricaImmagine()">Carica Immagine</button>' +
            '</div>' +
            '<div id="errori"></div>' +
            '</div>' +
            '<div class="breakDivAction">' +
            '<div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
            '       Immagini </div>' +
            '   <div class="actionDiv">' +
            '       <div class="entityDiv" style="padding: 8px; border-radius: 2px; height: fit-content;" id="show">' +
            '           ' +
            '       </div>' +
            '       <div class="actionDiv" style="margin: 8px 8px 16px 2px;">' +
            '           <label>Nome Immagine</label>' +
            '           <input type="text" class="inputForm" id="nomeImmagine" style="width: 362px;" disabled/>' +
            '       </div>' +
            '   </div> ' +
            '</div>');

        document.getElementById("creaImmagini").classList.add("pressed");
        visualizzaListaImmagini();

    } else {

        document.getElementById("creaImmagini").classList.remove("pressed");

    }

}

function caricaImmagine() {

    let xhr = new XMLHttpRequest();
    let formDataImmagine = new FormData(document.getElementById("file"));

    xhr.open('POST', '/gestoreImmagini/caricaImmagine', true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4) {

            if (xhr.status === 200) {

                if ($("#errori").children().length > 0){

                    $("#errori").empty();

                }

                visualizzaListaImmagini();

            }

            if (xhr.status === 500) {

                let messaggio = JSON.parse(xhr.responseText);
                erroreImmagine(messaggio.message);


            }

            if (xhr.status === 302) {

                window.location.replace("auth");

            }
        }
    };

    xhr.send(formDataImmagine);
    xhr.close;
}

function visualizzaListaImmagini(){

    let xhr = new XMLHttpRequest();

    xhr.open('GET', '/gestoreImmagini/visualizzaListaImmagini', true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4) {

            if (xhr.status === 200) {

                $("#show").empty();

                let x = JSON.parse(xhr.responseText);

                x.blobImmagini.forEach(function (immagine) {

                    let id = Object.keys(immagine)[0];
                    let src = "data:image;base64," + immagine[id];

                    $("#show").append(
                        '<img id="' + id + '" src="' + src + '" style ="width: 64px; height: 64px;" class="imgEntity">');

                });

                $(".imgEntity").click(function (){

                    $("#nomeImmagine").val($(this).attr("id"));

                    $(".imgEntity").css("border", "none");

                    $(this).css("border", "solid 1px #516f96");

                });

            }

            if (xhr.status === 500) {



            }

        }

    };

    xhr.send();
    xhr.close;
}

function erroreImmagine(messaggio){

    if ($("#errori").children().length > 0){

        $("#errori").empty();

    }

    switch (messaggio){

        case "NUIE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       Immagine già esistente!</label>" +
            '</div>'

        );   break;

        case "IFSE": $("#errori").append(

            '<div class="actionDiv">'+
            "                  <label style='color:rgb(175,80,92);'>Errore! <br>" +
            "                                                       L'Immagine deve essere 32 x 32!</label>" +
            '</div>'

        );   break;

    }

}