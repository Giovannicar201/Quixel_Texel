function creaDivCartellaMappa(){

    let divStrumentiMappa = document.getElementById("info");

    document.getElementsByClassName("break")[0].style.height = "100%";
    divStrumentiMappa.innerHTML = "";

    if (document.getElementById("cartelle").classList.contains("pressed") === false) {

        for (let strumenti of document.getElementById("strumenti").children) {

            strumenti.classList.remove("pressed");

        }

        if(divStrumentiMappa.children.length === 0) {

            $(divStrumentiMappa).append('<div class = "titleBar" id="titleBar">'+
                '            <img class="iconTitle" src="https://i.postimg.cc/BnB9xWyG/Tavola-disegno-1-4.png" id="title">' +
                '            <label for="title">Gestore Cartelle</label>'+
                '</div>'+
                ' <div class="breakDivAction">'+
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Creazione' +
                '   </div>' +
                '<div class="actionDiv">' +
                '                  <label for="nome">Nome:</label>' +
                '                  <input type="text" id="nome" class="inputForm">' +
                '              </div>'+
                '<div class="actionDiv">' +
                '                  <button class="bottone" onclick="creaCartella()">Crea Cartella</button>' +
                '              </div>' +
                '</div>' +
                '<div class="breakDivAction">'+
                '   <div class="topActionDiv" style="margin: 12px 8px 8px 8px;">' +
                '       Cartelle' +
                '   </div>' +
                '   <div class="actionDiv" id="show">' +
                '   </div> ' +
                '</div>');

            document.getElementsByClassName("break")[0].style.height = "150%";

        }

        document.getElementById("cartelle").classList.add("pressed");
        visualizzaListaCartelle();

    } else {

        document.getElementById("cartelle").classList.remove("pressed");

    }

}

function creaCartella(){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/gestoreCartelle/creaCartella', true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {

            alert("Cartella creata con successo!");
            visualizzaListaCartelle();

        }

        if (xhr.readyState === 4 && xhr.status === 500) {



        }

    };

    xhr.send(document.getElementById("nome").value);
    xhr.close;

}

function visualizzaListaCartelle(){

    let xhr = new XMLHttpRequest();

    xhr.open('GET', '/gestoreCartelle/visualizzaListaCartelle', true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4) {

            if (xhr.status === 200){

                $("#show").empty();

                let x = JSON.parse(xhr.responseText);

                x.nomiCartelle.forEach(function (nome){

                    $("#show").append(

                        '<label>' + nome +'</label>');

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

function ottieniContenutoCartella(){

    let xhr = new XMLHttpRequest();

    xhr.open('POST', '/matita/visualizzaListaEntitaInCartella', true);

    let richiesta = {};
    let nome = document.getElementById("nome");

    richiesta.nome = nome.value;

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4) {

            if (xhr.status === 200){

                $("#show").empty();

                let x = JSON.parse(xhr.responseText);

                x.blobImmagini.forEach(function (immagine) {

                    let id = Object.keys(immagine)[0];
                    let src = "data:image;base64," + immagine[id];

                    $("#show").append(
                        '<img id="' + id + '" src="' + src + '" style ="width: 64px; height: 64px;" class="imgEntity">');

                });

                $(".imgEntity").click(function (){

                    $(".imgEntity").removeClass("selected");

                    $(".imgEntity").css("border", "none");

                    $(".imgEntity").css("filter", "none");

                    $(this).addClass("selected");

                    $(this).css("border", "solid 1px #516f96");

                    $(this).css("filter", "contrast(0.5)");

                });

            }

        }

    };

    xhr.send(JSON.stringify(richiesta));
    xhr.close;

}
