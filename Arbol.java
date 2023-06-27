import java.util.ArrayList;
import java.util.List;

public class Arbol {
     private final List<Nodo> raiz;

    public Arbol(Nodo raiz){
        this.raiz = new ArrayList<>();
        this.raiz.add(raiz);
    }

    public Arbol(List<Nodo> raiz){
        this.raiz = raiz;
    }

    public void recorrer(){
        for(Nodo n : raiz){
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
            }
        }
    }
}
