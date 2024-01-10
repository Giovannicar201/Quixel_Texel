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
            '            <img class="iconTitle" src="https://i.postimg.cc/HkGsVN4m/img-1.png" id="title">' +
            '            <label for="title">Gestore Immagini</label>'+
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

function downloadFormato(){

    switch (document.getElementsByClassName("selected")[0].innerHTML){

        case "PNG": ottieniImmagineDallaMappa("png"); break;
        case "JPG": ottieniImmagineDallaMappa("jpg"); break;
        case "JSON": break;
        default:

    }

}

function ottieniImmagineDallaMappa(formato) {

    scaricaImmagine(formato);

}

async function effettuaDownload(url, formato) {

    const linkImmagine = document.createElement("a");

    linkImmagine.href = url.toDataURL();
    linkImmagine.download = "mappa." + formato;

    document.body.appendChild(linkImmagine);

    linkImmagine.click();

    document.body.removeChild(linkImmagine);

}

function scaricaImmagine(formato){

    html2canvas(document.querySelector("#griglia")).then(canvas => {

        document.getElementById("result").append(canvas);

        effettuaDownload(canvas, formato);

        document.getElementById("result").removeChild(canvas);

    });

}

function visualizzaNomeImmagine(nomeImmagine) {

    $('#nomeIns').val(nomeImmagine);
}

function caricaImmagine() {

    let xhr = new XMLHttpRequest();
    let formDataImmagine = new FormData(document.getElementById("file"));

    xhr.open('POST', '/gestoreImmagini/caricaImmagine', true);

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                visualizzaListaImmagini();
            } else {

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

                alert("errore");

            }

        }

    };

    xhr.send();
    xhr.close;
}
