function Saludar(target: Function):void {
    target.prototype.saludos = function():void {
        console.log("Hola desde decoradores");
        
    }
}
// este warning es que es experimental, que aún no está incluído tal cual
@Saludar
class Hola {
    constructor(){}
}

let hola1 = new Hola();
hola1.saludos();