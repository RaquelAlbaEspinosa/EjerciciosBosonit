"use strict";
let usuario1 = { nombreUsuario: "Juan", password: "1234" };
console.log(usuario1);
console.log(usuario1.nombreUsuario);
// con las interfaces estás firmando un contrato
let avion = {
    abordarTransporte: function () {
        console.log("abordando");
    }
};
avion.abordarTransporte();
