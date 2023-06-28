
public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    public void recorrer(){
        for(Nodo n : raiz.getHijos()){
            Token t = n.getValue();
            switch (t.tipo){
                // Operadores aritméticos
                case MAS:
                case MENOS:
                case ASTERISCO:
                case DIAGONAL:
                    SolverArit solver = new SolverArit(n);
                    Object res = solver.resolver();
                    System.out.println(res);
                break;
                case VAR:
                // Crear una variable. Usar tabla de simbolos
                n.getHijos();
                SolverArit sol = new SolverArit(n);
                    Object resultado = sol.resolver();
                    System.out.println(resultado);
               
                break;
                case SI:
                break;
            }
        }
    }
}
