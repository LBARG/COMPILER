import javax.lang.model.util.ElementScanner6;

public class SolverArit {

    private final Nodo nodo;

    public SolverArit(Nodo nodo) {
        this.nodo = nodo;
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
                // Ver la tabla de símbolos
                Token tkn = n.getValue();
                TablaSimbolos.obtener(tkn.lexema);
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
                case ASTERISCO:
                    return ((Double)resultadoIzquierdo * (Double) resultadoDerecho);
                case DIAGONAL:
                    return ((Double)resultadoIzquierdo / (Double) resultadoDerecho);
                case MAYOR:
                if((Double)resultadoIzquierdo > (Double) resultadoDerecho)
                {
                    return true;
                }
                else
                {
                    return false;
                }
                   case MAYOR_IGUAL:
                if((Double)resultadoIzquierdo >= (Double) resultadoDerecho)
                {
                    return true;
                }
                else
                {
                    return false;
                }
                case MENOR:
                if((Double)resultadoIzquierdo < (Double) resultadoDerecho)
                {
                    return true;
                }
                else
                {
                    return false;
                }
                case MENOR_IGUAL:
                    if((Double)resultadoIzquierdo <= (Double) resultadoDerecho)
                {
                    return true;
                }
                else
                {
                    return false;
                }
                case DOBLE_IGUAL:
                    if((Double)resultadoIzquierdo == (Double) resultadoDerecho)
                {
                    return true;
                }
                else
                {
                    return false;
                }
                case DIFERNETE:
                    if((Double)resultadoIzquierdo != (Double) resultadoDerecho)
                {
                    return true;
                }
                else
                {
                    return false;
                }
                default:
                Interprete.error("No se esperaba un operador logico o de asignacion");
            }
        }
        else if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String){
            if (n.getValue().tipo == TipoToken.MAS){
                // Ejecutar la concatenación
                return((String)resultadoIzquierdo + (String)resultadoDerecho);
            }
        }
        else{
            // Error por diferencia de tipos
            boolean esCadena = resultadoIzquierdo instanceof String;
            if(esCadena)
            {
                Interprete.error("Error diferencia de tipos se espera tipo String");
            }
            else
            {
                Interprete.error("Error diferencia de tipos se espera tipo Double");
            }
        }

        return null;
    }
}
