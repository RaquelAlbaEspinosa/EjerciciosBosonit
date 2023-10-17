let saludo:string = "HolaMundo de Typescrypt";

let numero:number = 10;

let cualquiera : any;
cualquiera = "hola";
cualquiera = 10;

const PI = 3.14;

function saludar(){
    console.log(saludo);
    console.log(numero);
    console.log(PI);
    
}

saludar();

// al ser TypeScrypt hay que compilarlo para usarlo
// el comando es $ tsc nombreDelArchivo

// al ser TypeScrypt hay que compilarlo para usarlo
// el comando es $ tsc nombreDelArchivo
// para crear el archivo de configuración de TypeScript es con el comando: $ tsc -init
// para que se vaya transformando el typescrypt a js es con el comando: $ tsc -w
