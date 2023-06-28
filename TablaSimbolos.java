import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {

    private static final Map<String, Object> values = new HashMap<>();
    

    static boolean existeIdentificador(String identificador){
        return values.containsKey(identificador);
    }

    static Object obtener(String identificador) {
        if (values.containsKey(identificador)) {
            return values.get(identificador);
        }
        throw new RuntimeException("Variable no definida '" + identificador + "'.");
    }

    static void asignar(String identificador, Object valor){
        values.put(identificador, valor);
    }
    
      private final Nodo nodo;

    public TablaSimbolos(Nodo nodo) {
        this.nodo = nodo;
    }

    public Object agregar(){
        return agregar(nodo);
    }
    private Object agregar (Nodo n){
        // No tiene hijos, es un operando
        if(n.getHijos() == null){
            if(n.getValue().tipo == TipoToken.IDENTIFICADOR){
                // Ver la tabla de símbolos
                Token tkn = n.getValue();
                TablaSimbolos.obtener(tkn.lexema);
            }
        }

        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izq = n.getHijos().get(0);
        Nodo der = n.getHijos().get(1);

        agregar(izq);
        agregar(der);

        return null;
  
    }
}


