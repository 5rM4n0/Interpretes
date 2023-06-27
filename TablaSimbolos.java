/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpretes;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {

    private static final Map<String, Object> values = new HashMap<>();

    public static boolean existeIdentificador(String identificador){
        return values.containsKey(identificador);
    }

    public static Object obtener(String identificador) {
        if (values.containsKey(identificador)) {
            return values.get(identificador);
        }
        throw new RuntimeException("Variable no definida '" + identificador + "'.");
    }

    public static void asignar(String identificador, Object valor){
        values.put(identificador, valor);
    }


}