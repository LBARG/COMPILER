import java.util.List;

public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    boolean hayVar = false;
    String lex = "";
    Object val = null;

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
                if(!(n.getHijos() == null))
                {
                    
                for(Nodo node : n.getHijos())
                {
                    Token tkn = node.getValue();
                    //System.out.println(tkn.lexema);
                    switch(tkn.tipo)
                    {
                        case IDENTIFICADOR:
                        if(!(TablaSimbolos.existeIdentificador(tkn.lexema)))
                        {
                        lex = tkn.lexema;
                        }
                        else 
                        {
                            Interprete.error("Variable previamente definida");
                        }
                        break;
                        case CADENA:
                        val = tkn.literal;
                        break;
                        case NUMERO:
                        val = tkn.literal;
                        break;
                    }
                    TablaSimbolos.asignar(lex, val);
                }
                }
                break;
                case SI:
                SolverArit sol = new SolverArit(n);
                    Object r = sol.resolver();
                    System.out.println(r);
                break;
                case IMPRIMIR:
                if(!(n.getHijos() == null))
                {
                   for (Nodo nod : n.getHijos())
                   {
                    Arbol programa = new Arbol(nod);
                    programa.recorrer(postfija);
                   }
                }
            }
        }
    }
}
