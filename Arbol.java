import java.util.List;

public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    boolean hayVar = false;

    public void recorrer(List <Token> postfija){
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
                for(Token tk : postfija)
                {
                    String lex = "";
                    switch(tk.tipo)
                    {
                        case IDENTIFICADOR:
                        //System.out.println(tk.lexema);
                        if(TablaSimbolos.existeIdentificador(tk.lexema))
                        {
                            Interprete.error("Variable previamente definida");
                        }
                        else
                        {
                            lex = tk.lexema;
                            TablaSimbolos.asignar(tk.lexema);

                        }
                        break;
                        case NUMERO:
                        System.out.println(tk.literal);

                        TablaSimbolos.asignar(lex, tk.literal);
                        break;
                        case CADENA:
                        System.out.println(tk.literal);
                        TablaSimbolos.asignar(lex, tk.literal);
                        break;
                        default:

                    }
                }
                break;
                case SI:
                SolverArit sol = new SolverArit(n);
                    Object r = sol.resolver();
                    System.out.println(r);
                break;
            }
        }
    }
}
