const observer = new IntersectionObserver((entries) => {

    entries.forEach((entry) =>{

        if (entry.isIntersecting) {

            entry.target.classList.add("show");
            entry.target.classList.remove("notShow");

        } else {

            entry.target.classList.add("notShow");
            entry.target.classList.remove("show");

        }

    });

});

const observerDemo = new IntersectionObserver((entries) => {

    entries.forEach((entry) =>{

        if (entry.isIntersecting) {

            entry.target.innerHTML = "";

        }

    });

});

const mostra = document.getElementsByClassName("notShow");
const reset = document.getElementsByClassName("demo");

for (let mostraElement of mostra) {
    observer.observe(mostraElement);

}
for (let demoElement of reset) {

    observerDemo.observe(demoElement);

}

function demo(){

    let scrivere = document.getElementsByClassName("demotesto")[0];

    scrivere.innerHTML = "";

    scrivere.innerHTML = "Nome evento: Demo";
    scrivere.append(document.createElement("br"));
    scrivere.append(document.createElement("br"));
    scrivere.innerHTML += document.getElementById("istruzione1").value;

}

const observerMappa = new IntersectionObserver((entries) => {

    entries.forEach((entry) =>{

        if (entry.isIntersecting) {

            let mappa = document.getElementById("mappa");

            mappa.innerHTML = "";

            let righe = parseInt(document.getElementById("altezza").value);
            let colonne = parseInt(document.getElementById("largezza").value);

            disegnaMappa(mappa, righe, colonne);

            document.getElementById("colore").classList.remove("selected");
            document.getElementById("coloreMappa").classList.add("selected");

        }

    });

});

const cancella = document.getElementsByClassName("mappa");

for (let mappa of cancella){

    observerMappa.observe(mappa);

}

const observerDemoColora= new IntersectionObserver((entries) => {

    entries.forEach((entry) =>{

        if (entry.isIntersecting) {

            let griglia = document.getElementById("pixelArt");
            griglia.innerHTML = "";

            disegnaDiv(griglia, 5, 5);

            if (document.head.children.length > 3) {

                document.head.children[3].remove();

            }

            let style = document.createElement("style");

            style.appendChild(document.createTextNode(
                ".griglia{ " +
                "    display: grid;" +
                "    grid-template-columns: repeat(5, 32px);" +
                "    width: fit-content;" +
                "    top: 32px;" +
                "    margin-top: 7%;" +
                "    margin-left: 15%;" +
                "    block-size: fit-content;" +
                "}"));

            document.head.append(style);

            document.getElementById("colore").classList.add("selected");
            document.getElementById("coloreMappa").classList.remove("selected");

        }

    });

});

const tile = document.getElementsByClassName("pixelart");

for(let element of tile){

    observerDemoColora.observe(element);

}
