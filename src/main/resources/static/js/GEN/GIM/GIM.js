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
