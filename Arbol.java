public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    String lex = "";
    Object val = null;
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
                case IGUAL:
                Nodo identificador = n.getHijos().get(0);
                if(TablaSimbolos.existeIdentificador(identificador.getValue().lexema))
                {
                Nodo exprI = n.getHijos().get(1); 
                SolverArit asignar = new SolverArit(exprI);
                val = asignar.resolver();
                TablaSimbolos.asignar(identificador.getValue().lexema, val);
                }
                else
                {
                    Interprete.error("La variable"+ identificador.getValue().lexema + "no definida");
                }
                break;
                case VAR:
                // Crear una variable. Usar tabla de simbolos
                Nodo id = n.getHijos().get(0);
                if(id.getValue().tipo == TipoToken.IDENTIFICADOR)
                {
                    if(!(TablaSimbolos.existeIdentificador(id.getValue().lexema)))
                    {
                        lex = id.getValue().lexema;
                    }
                    else
                    {
                        Interprete.error("Variable: " + id.getValue().lexema + " previamente definida");
                    }
                }
                Nodo exprV = n.getHijos().get(1);
                SolverArit solV = new SolverArit(exprV);
                Object valor = solV.resolver();
                TablaSimbolos.asignar(lex, valor);
                
                break;
                case SI:
                    Nodo condicion = n.getHijos().get(0);
                    SolverArit sol = new SolverArit(condicion);
                    Object r = sol.resolver();

                    Nodo raizIf = new Nodo(null);

                    if((r instanceof Boolean) && (Boolean)r == true){
                        for(int i = 1; i<n.getHijos().size(); i++){

                            if(n.getHijos().get(i).getValue().tipo == TipoToken.ADEMAS){
                                break;
                            }
                            raizIf.insertarSiguienteHijo(n.getHijos().get(i));

                        }

                        Arbol nueArbol = new Arbol(raizIf);
                        nueArbol.recorrer();


                    }
                    else if((r instanceof Boolean) && (Boolean)r == false){
                        Nodo nodoElse = n.getHijos().get( n.getHijos().size() - 1 );
                        if(nodoElse.getValue().tipo == TipoToken.ADEMAS){
                            Arbol nueArbol = new Arbol(nodoElse);
                            nueArbol.recorrer();
                        }
                    }
                    else
                    {
                        Interprete.error("La condicion no cumple con el tipo booleano");
                    }
                break;
                case MIENTRAS:
                    Nodo condicionMientras = n.getHijos().get(0);
                    SolverArit solucion = new SolverArit(condicionMientras);
                    Object resol = solucion.resolver();

                    Nodo raizWhile = new Nodo(null);
                    if((resol instanceof Boolean) && (Boolean)resol == true){
                        for(int i = 1; i<n.getHijos().size(); i++){

                            
                            raizWhile.insertarSiguienteHijo(n.getHijos().get(i));

                        }
                        while((Boolean)resol == true)
                        {
                        Arbol nueArbol = new Arbol(raizWhile);
                        nueArbol.recorrer();
                        solucion = new SolverArit(condicionMientras);
                        resol = solucion.resolver();
                        }


                    }
                    else
                    {
                        Interprete.error("La condicion no cumple con el tipo booleano");
                    }
                break;

                case IMPRIMIR:
                    Nodo expr = n.getHijos().get(0);
                    SolverArit s = new SolverArit(expr);
                    Object resI = s.resolver();
                    System.out.println(resI);
                break;
                case PARA:
                    Nodo initPara = n.getHijos().get(0);
                    Nodo raizInit = new Nodo(null);
                    raizInit.insertarSiguienteHijo(initPara);
                    Arbol arbol = new Arbol(raizInit);
                    arbol.recorrer();
                    Nodo condicionPara = n.getHijos().get(1);
                    SolverArit solucionar = new SolverArit(condicionPara);
                    Object cond = solucionar.resolver();

                    Nodo raizFor = new Nodo(null);
                    if((cond instanceof Boolean)&&(Boolean)cond == true)
                    {
                        for(int i = 3; i<n.getHijos().size(); i++)
                        {                            
                            raizFor.insertarSiguienteHijo(n.getHijos().get(i));

                        }

                        while((Boolean)cond == true)
                        {
                            Arbol newArbol = new Arbol(raizFor);
                            newArbol.recorrer();
                            Nodo incrementoPara = n.getHijos().get(2);
                            Nodo raizIncremento = new Nodo(null);
                            raizIncremento.insertarSiguienteHijo(incrementoPara);
                            Arbol nueArbol = new Arbol(raizIncremento);
                            nueArbol.recorrer();
                            solucionar = new SolverArit(condicionPara);
                            cond = solucionar.resolver();
                        }
                    }
                    else 
                    {
                        Interprete.error("La condicion no es de tipo booleano");
                    }
                break;
            }
        }
    }
}
