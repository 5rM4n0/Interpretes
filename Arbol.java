
import java.util.ArrayList;
import java.util.List;

public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }
    
    public void recorrer(){
        TablaSimbolos tabla = new TablaSimbolos();
        for(Nodo n : raiz.getHijos()){

            Token t = n.getValue();
            switch (t.tipo){
                // Operadores aritméticos
                case MAS:
                case MENOS:
                case POR:
                case ENTRE:
                    SolverAritmetico solver = new SolverAritmetico(n,tabla);
                    Object res = solver.resolver();
                    if(res!=null){
                        System.out.println(res);
                    }else{
                        System.out.println("La operación no es valida");
                        System.exit(0);
                    }
                break;
                case MAYOR:
                case MENOR:
                case MAYOR_O_IGUAL:
                case MENOR_O_IGUAL:
                case IGUAL:
                    SolverAritmetico solv = new SolverAritmetico(n,tabla);
                    Object bul = solv.resolver();
                    if(bul!=null){
                        //System.out.println("la sentencia es "+bul);
                    }else{
                        System.out.println("La comparación no es valida");
                        System.exit(0);
                    }
                    break;
                case ASIGNACION:
                    Nodo resu=n.getHijos().get(1);                   
                    SolverAritmetico op = new SolverAritmetico(resu, tabla);
                    Object valor = op.resolver();
                    
                    if(!tabla.existeIdentificador(n.getHijos().get(0).getValue().lexema)){
                        System.out.println("No existe la variable " + n.getHijos().get(0).getValue().lexema);
                    }else{
                        tabla.asignar(n.getHijos().get(0).getValue().lexema,valor );
                    }
                    break;
                case Y:
                case O:
                    SolverAritmetico solver3 = new SolverAritmetico(n,tabla);
                    Object boleano = solver3.resolver();
                    
                    if(boleano!=null){
                        //System.out.println("la sentencia es "+boleano);
                    }else{
                        System.out.println("La lógica no es correcta");
                        System.exit(0);
                    }
                    break;
                case VAR:
                    // Crear una variable. Usar tabla de simbolos+-
                    if(tabla.existeIdentificador(n.getHijos().get(0).getValue().lexema)){
                        System.out.println("La variable " + n.getHijos().get(0).getValue().lexema+" ya ha sido declarada anteriormente");
                        System.exit(0);
                    }else if(n.getHijos().size()>1){
                        Nodo resultado = n.getHijos().get(1);
                        SolverAritmetico solver2 = new SolverAritmetico(resultado);
                        Object result = solver2.resolver();                       
                        tabla.asignar(n.getHijos().get(0).getValue().lexema,result );
   
                    }else{                      
                        tabla.asignar(n.getHijos().get(0).getValue().lexema,null );
                        
                    }

                    
                    break;
                case IMPRIMIR:
                    Nodo cadena=n.getHijos().get(0);                   
                    SolverAritmetico conc = new SolverAritmetico(cadena, tabla);
                    Object cadenas = conc.resolver();
                    System.out.println(cadenas);
                    break;
                    
                    
                case SI:
                    Nodo si=n.getHijos().get(0);
                    SolverAritmetico cond = new SolverAritmetico(si,tabla);
                    Object condic = cond.resolver();
                    if ((boolean) condic) {
                        Arbol bloquesi = new Arbol(n);
                        if(n.getValue().tipo!=TipoToken.ADEMAS){
                            bloquesi.recorrer();
                        } 
                                              
                    }else{
                        Nodo adema=n.getHijos().get(n.getHijos().size()-1);
                        Arbol bloquesi = new Arbol(adema);                      
                            bloquesi.recorrer();                       
                    }
                    break;
               
                case PARA:
                    
                    Nodo ini=n.getHijos().get(0);
                    Nodo condi=n.getHijos().get(1);
                    Nodo aum=n.getHijos().get(3);
                    SolverAritmetico solini = new SolverAritmetico(ini,tabla);
                    SolverAritmetico solcondi = new SolverAritmetico(condi,tabla);
                    SolverAritmetico solaum = new SolverAritmetico(aum,tabla);
                    Object resini = solini.resolver();
                    Object rescondi = solcondi.resolver();
                    Object resaum = solaum.resolver();
                    while ((boolean) rescondi) {
                        Arbol bloque = new Arbol(n);
                        bloque.recorrer();
                        resaum = solaum.resolver();
                        rescondi = solcondi.resolver();
                    }
                    break;

                case MIENTRAS:
                    Nodo mientras=n.getHijos().get(0);
                    SolverAritmetico relacion = new SolverAritmetico(mientras,tabla);
                    Object condicion = relacion.resolver();
                    while ((boolean) condicion) {
                        Arbol bloque = new Arbol(n);
                        bloque.recorrer();
                        condicion = relacion.resolver();
                    }
                    break;
                

            }
        }
    }
    

}

