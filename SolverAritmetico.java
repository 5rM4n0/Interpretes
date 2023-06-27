/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpretes;

public class SolverAritmetico {

    private final Nodo nodo;
    private TablaSimbolos tabla;

    public SolverAritmetico(Nodo nodo) {
        this.nodo = nodo;
    }
    
    public SolverAritmetico(Nodo nodo, TablaSimbolos tabla) {
        this.nodo = nodo;
        this.tabla = tabla;
    }
    
    public Object resolver(){
        return resolver(nodo);
    }
    private Object resolver(Nodo n){
        // No tiene hijos, es un operando
        if(n.getHijos() == null){

            if(n.getValue().tipo == TipoToken.NUMERO || n.getValue().tipo == TipoToken.CADENA){
                return n.getValue().literal;
            }
            else if(n.getValue().tipo == TipoToken.IDENTIFICADOR){
                
                if(!tabla.existeIdentificador(n.getValue().lexema)){
                    System.out.println("El identificador "+n.getValue().lexema+ " no existe");
                    System.exit(0);
                }else{
                    return tabla.obtener(n.getValue().lexema);
                }
            }else if(n.getValue().tipo == TipoToken.VERDADERO){
                return true;
            }else if(n.getValue().tipo == TipoToken.FALSO){
                return false;
            }
        }

        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izq = n.getHijos().get(0);
        Nodo der = n.getHijos().get(1);

        Object resultadoIzquierdo = resolver(izq);
        Object resultadoDerecho = resolver(der);

        if(resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double){
            switch (n.getValue().tipo){
                case MAS:
                    return ((Double)resultadoIzquierdo + (Double) resultadoDerecho);
                case MENOS:
                    return ((Double)resultadoIzquierdo - (Double) resultadoDerecho);
                case POR:
                    return ((Double)resultadoIzquierdo * (Double) resultadoDerecho);
                case ENTRE:
                    return ((Double)resultadoIzquierdo / (Double) resultadoDerecho);
                case MENOR:
                    return ((Double)resultadoIzquierdo < (Double) resultadoDerecho);
                case MAYOR:
                    return ((Double)resultadoIzquierdo > (Double) resultadoDerecho);
                case MENOR_O_IGUAL:
                    return ((Double)resultadoIzquierdo <= (Double) resultadoDerecho);
                case MAYOR_O_IGUAL:
                    return ((Double)resultadoIzquierdo >= (Double) resultadoDerecho);
                case IGUAL:
                    return ((Double)resultadoIzquierdo == (Double) resultadoDerecho);
            }
        }
        else if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String){
            
            switch (n.getValue().tipo){
                case MAS:
                    String concatenacion = (String) resultadoIzquierdo + (String) resultadoDerecho;
                    return concatenacion;
                case IGUAL:
                    
                    return resultadoIzquierdo.equals( resultadoDerecho);
            }
        }else if(resultadoIzquierdo instanceof Boolean && resultadoDerecho instanceof Boolean){
            switch (n.getValue().tipo){
                case O:
                    return ((Boolean)resultadoIzquierdo || (Boolean) resultadoDerecho);
                case Y:
                    return ((Boolean)resultadoIzquierdo && (Boolean) resultadoDerecho);
            }
        }
        else{
            
            System.out.println("Los tipos de dato son distintos");
            System.exit(0);
        }

        return null;
    }
}