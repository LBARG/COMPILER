
import java.util.List;

public class Parser 
{
    private final List<Token> tokens;

    private final Token y = new Token(TipoToken.Y, "y");
    private final Token clase = new Token(TipoToken.CLASE, "clase");
    private final Token ademas = new Token(TipoToken.ADEMAS,"ademas");
    private final Token falso = new Token(TipoToken.FALSO,"falso");
    private final Token para = new Token(TipoToken.PARA,"para");
    private final Token fun = new Token(TipoToken.FUN,"fun");
    private final Token si = new Token(TipoToken.SI,"si");
    private final Token nulo = new Token(TipoToken.NULO,"nulo");
    private final Token o = new Token(TipoToken.O,"o");
    private final Token imprimir = new Token(TipoToken.IMPRIMIR,"imprimir");
    private final Token retornar = new Token(TipoToken.RETORNAR,"retornar");
    private final Token super_ = new Token(TipoToken.SUPER,"super");
    private final Token este = new Token(TipoToken.ESTE,"este");
    private final Token verdadero = new Token(TipoToken.VERDADERO,"verdadero");
    private final Token var = new Token(TipoToken.VAR,"var");
    private final Token meintras = new Token(TipoToken.MIENTRAS,"mientras");
    private final Token eof = new Token(TipoToken.EOF, "$");
    private final Token par_izq = new Token(TipoToken.PAR_IZQ,"(");
    private final Token par_der = new Token(TipoToken.PAR_DER,")");
    private final Token llave_izq = new Token(TipoToken.LLAVE_IZQ,"{");
    private final Token llave_der = new Token(TipoToken.LLAVE_DER,"}");
    private final Token coma = new Token(TipoToken.COMA,",");
    private final Token punto = new Token(TipoToken.PUNTO,".");
    private final Token punto_coma = new Token(TipoToken.PUNTO_COMA,";");
    private final Token menos = new Token(TipoToken.MENOS,"-");
    private final Token mas = new Token(TipoToken.MAS,"+");
    private final Token asterisco = new Token(TipoToken.ASTERISCO,"*");
    private final Token diagonal = new Token(TipoToken.DIAGONAL,"/");
    private final Token negacion = new Token(TipoToken.NEGACION,"!");
    private final Token diferente = new Token(TipoToken.DIFERNETE,"!=");
    private final Token igual = new Token(TipoToken.IGUAL,"=");
    private final Token doble_igual = new Token(TipoToken.DOBLE_IGUAL,"==");
    private final Token menor = new Token(TipoToken.MENOR,"<");
    private final Token menor_igual = new Token(TipoToken.MENOR_IGUAL,"<=");
    private final Token mayor = new Token(TipoToken.MAYOR,">");
    private final Token mayor_igual = new Token(TipoToken.MAYOR_IGUAL,">=");
    private final Token identificador = new Token(TipoToken.IDENTIFICADOR,"");
    private final Token cadena = new Token(TipoToken.CADENA,"");
    private final Token numero = new Token(TipoToken.NUMERO,"");


    private int i = 0;
    private boolean hayErrores = false;

    private Token preanalisis;

    public Parser(List<Token>tokens)
    {
        this.tokens = tokens;
    }

    public void parse()
    {
        i = 0;
        preanalisis = tokens.get(i);
        if(!(preanalisis.equals(eof)))
        {
            PROGRAM();
        }
        if(!hayErrores && !preanalisis.equals(eof)){
            System.out.println("Error en la posición " + preanalisis.linea + ". No se esperaba el token " + preanalisis.tipo);
        }
        else if(!hayErrores && preanalisis.equals(eof)){
            System.out.println("Consulta válida");
        }

    }

    void PROGRAM()
    {
        if(preanalisis.equals(clase)||preanalisis.equals(fun)||preanalisis.equals(var)||preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_)||preanalisis.equals(para)||preanalisis.equals(si)||preanalisis.equals(imprimir)||preanalisis.equals(retornar)||preanalisis.equals(meintras)||preanalisis.equals(llave_izq))
        {
            DECLARATION();
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }
    
    void DECLARATION()
    {
        if(hayErrores) return;
        if(preanalisis.equals(clase))
        {
            CLASS_DECL();
            DECLARATION();
        }
        else if(preanalisis.equals(fun))
        {
            FUN_DECL();
            DECLARATION();
        }
        else if(preanalisis.equals(var))
        {
            VAR_DECL();
            DECLARATION();
        }
        else if(preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_)||preanalisis.equals(para)||preanalisis.equals(si)||preanalisis.equals(imprimir)||preanalisis.equals(retornar)||preanalisis.equals(meintras)||preanalisis.equals(llave_izq))
        {
            STATEMENT();
            DECLARATION();
        }
    }
    
    void CLASS_DECL()
    {
        if(hayErrores) return;
        if(preanalisis.equals(clase))
        {
            coincidir(clase);
            coincidir(identificador);
            CLASS_INHER();
            coincidir(llave_izq);
            FUNCTIONS();
            coincidir(llave_der);
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }
   
    void FUN_DECL()
    {
        if(hayErrores) return;
        if(preanalisis.equals(fun))
        {
            coincidir(fun);
            FUNCTION();
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }

    void VAR_DECL()
    {
        if(hayErrores) return;
        if(preanalisis.equals(var))
        {
            coincidir(var);
            coincidir(identificador);
            VAR_INIT();
            coincidir(punto_coma);
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }

    void STATEMENT()
    {
        if(hayErrores) return;
        if(preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
        {
            EXPR_STMT();
        }
        else if(preanalisis.equals(para))
        {
            FOR_STMT();
        }
        else if(preanalisis.equals(si))
        {
            IF_STMT();
        }
        else if(preanalisis.equals(imprimir))
        {
            PRINT_STMT();
        }
        else if(preanalisis.equals(retornar))
        {
            RETURN_STMT();
        }
        else if(preanalisis.equals(meintras))
        {
            WHILE_STMT();
        }
        else if(preanalisis.equals(llave_izq))
        {
            BLOCK();
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }

    void CLASS_INHER()
    {
        if(hayErrores) return;
        if(preanalisis.equals(menor))
        {
            coincidir(menor);
            coincidir(identificador);
        }
    }

    void VAR_INIT()
    {
        if(hayErrores) return;
        if(preanalisis.equals(igual))
        {
            coincidir(igual);
            EXPRESSION();
        }
    }

    void EXPR_STMT()
    {
        if(hayErrores) return;
        if(preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
        {
            EXPRESSION();
            coincidir(punto_coma);
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }

    void FOR_STMT()
    {
        if(hayErrores) return;
        if(preanalisis.equals(para))
        {
        coincidir(para);
        coincidir(par_izq);
        FOR_STMT_1();
        FOR_STMT_2();
        FOR_STMT_3();
        coincidir(par_der);
        STATEMENT();
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }
    void FOR_STMT_1()
    {
        if(hayErrores) return;
        if(preanalisis.equals(var))
        {
            VAR_DECL();
        }
        else if(preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
        {
            EXPR_STMT();
        }
        else if(preanalisis.equals(punto_coma))
        {
        coincidir(punto_coma);
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }
    void FOR_STMT_2()
    {
        if(hayErrores) return;
        if(preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
        {
        EXPRESSION();
        coincidir(punto_coma);
        }
        else if(preanalisis.equals(punto_coma))
        {
        coincidir(punto_coma);
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }
    void FOR_STMT_3()
    {
        if(hayErrores) return;
        if(preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
        {
        EXPRESSION();
        }
    }

    void IF_STMT()
    {
        if(hayErrores) return;
        if(preanalisis.equals(si))
        {
            coincidir(si);
            coincidir(par_izq);
            EXPRESSION();
            coincidir(par_der);
            STATEMENT();
            ELSE_STATEMENT();
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }

    void ELSE_STATEMENT()
    {
        if(hayErrores) return;
        if(preanalisis.equals(ademas))
        {
            coincidir(ademas);
            STATEMENT();
        }
    }

    void PRINT_STMT()
    {
        if(hayErrores) return;
        if(preanalisis.equals(imprimir))
        {
            coincidir(imprimir);
            EXPRESSION();
            coincidir(punto_coma);
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }

    void RETURN_STMT()
    {
        if(hayErrores) return;
        if(preanalisis.equals(retornar))
        {
            coincidir(retornar);
            RETURN_EXP_OPC();
            coincidir(punto_coma);
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }
    void RETURN_EXP_OPC()
    {
        if(hayErrores) return;
        if(preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
        {
            EXPRESSION();
        }
    }

    void WHILE_STMT()
    {
        if(hayErrores) return;
        if(preanalisis.equals(meintras))
        {
            coincidir(meintras);
            coincidir(par_izq);
            EXPRESSION();
            coincidir(par_der);
            STATEMENT();
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
        
    }

    void BLOCK()
    {
        if(hayErrores) return;
        if(preanalisis.equals(llave_izq))
        {
            coincidir(llave_izq);
            BLOCK_DECL();
            coincidir(llave_der);
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }

    void BLOCK_DECL()
    {
        if(hayErrores) return;
        if(preanalisis.equals(clase)||preanalisis.equals(fun)||preanalisis.equals(var)||preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_)||preanalisis.equals(para)||preanalisis.equals(si)||preanalisis.equals(imprimir)||preanalisis.equals(retornar)||preanalisis.equals(meintras)||preanalisis.equals(llave_izq))
        {
            DECLARATION();
            BLOCK_DECL();
        }
    }
    void EXPRESSION()
    {
        if(hayErrores) return;
        if(preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
        {
            ASSIGNMENT();
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }

    void ASSIGNMENT()
    {
        if(hayErrores) return;
        if(preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
        {
            LOGIC_OR();
            ASSIGNMENT_OPC();
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }
    
    void ASSIGNMENT_OPC()
    {
        if(hayErrores) return;
        if(preanalisis.equals(igual))
        {
            coincidir(igual);
            EXPRESSION();
        }
    }

    void LOGIC_OR()
    {
        if(hayErrores) return;
        if(preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
        {
            LOGIC_AND();
            LOGIC_OR_2();
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }

    void LOGIC_OR_2()
    {
        if(hayErrores) return;
        if(preanalisis.equals(o))
        {
            coincidir(o);
            LOGIC_AND();
            LOGIC_OR_2();
        }
    }

    void LOGIC_AND()
    {
        if(hayErrores) return;
        if(preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
        {
            EQUALITY();
            LOGIC_AND_2();
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }

    void LOGIC_AND_2()
    {
        if(hayErrores) return;
        if(preanalisis.equals(y))
        {
            coincidir(y);
            EQUALITY();
            LOGIC_AND_2();
        }
    }

    void EQUALITY()
    {
        if(hayErrores) return;
        if(preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
        {
            COMPARISON();
            EQUALITY_2();
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }

    void EQUALITY_2()
    {
        if(hayErrores) return;
        if(preanalisis.equals(diferente))
        {
            coincidir(diferente);
            COMPARISON();
            EQUALITY_2();
        }
        else if(preanalisis.equals(doble_igual))
        {
            coincidir(doble_igual);
            COMPARISON();
            EQUALITY_2();
        }
    }

    void COMPARISON()
    {
        if(hayErrores) return;
        if(preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
        {
        TERM();
        COMPARISON_2();
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }
    void COMPARISON_2()
    {
        if(hayErrores) return;
        if(preanalisis.equals(mayor))
        {
            coincidir(mayor);
            TERM();
            COMPARISON_2();
        }
        else if(preanalisis.equals(mayor_igual))
        {
            coincidir(mayor_igual);
            TERM();
            COMPARISON_2();
        }
        else if(preanalisis.equals(menor))
        {
            coincidir(menor);
            TERM();
            COMPARISON_2();
        }
        else if(preanalisis.equals(menor_igual))
        {
            coincidir(menor_igual);
            TERM();
            COMPARISON_2();
        }
    }

    void TERM()
    {
        if(hayErrores) return;
        if(preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
        {
            FACTOR();
            TERM_2();
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }

    void TERM_2 ()
    {
        if(hayErrores) return;
        if(preanalisis.equals(menos))
        {
            coincidir(menos);
            FACTOR();
            TERM_2();
        }
        else if(preanalisis.equals(mas))
        {
            coincidir(mas);
            FACTOR();
            TERM_2();
        }
    }

    void FACTOR()
    {
        if(hayErrores) return;
        if(preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
        {
            UNARY();
            FACTOR_2();
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }

    void FACTOR_2()
    {
        if(hayErrores) return;
        if(preanalisis.equals(diagonal))
        {
            coincidir(diagonal);
            UNARY();
            FACTOR_2();
        }
        else if(preanalisis.equals(asterisco))
        {
            coincidir(asterisco);
            UNARY();
            FACTOR_2();
        }
    }

    void UNARY()
    {
        if(hayErrores) return;
        if(preanalisis.equals(diferente))
        {
            coincidir(diferente);
            UNARY();
        }
        else if(preanalisis.equals(menos))
        {
            coincidir(menos);
            UNARY();
        }
        else if(preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
        {
            CALL();
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }

    void CALL()
    {
        if(hayErrores) return;
        if(preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
        {
            PRIMARY();
            CALL_2();
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }

    void CALL_2()
    {
        if(hayErrores) return;
        if(preanalisis.equals(par_izq))
        {
            coincidir(par_izq);
            ARGUMENTS_OPC();
            coincidir(par_der);
            CALL_2();
        }
        else if(preanalisis.equals(punto))
        {
            coincidir(punto);
            coincidir(identificador);
            CALL_2();
        }
    }

    void CALL_OPC()
    {
        if(hayErrores) return;
        if(preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
        {
            CALL();
            coincidir(punto);
        }
    }

    void PRIMARY()
    {
        if(hayErrores) return;
        if(preanalisis.equals(verdadero))
        {
            coincidir(verdadero);
        }
        else if(preanalisis.equals(falso))
        {
            coincidir(falso);
        }
        else if(preanalisis.equals(nulo))
        {
            coincidir(nulo);
        }
        else if(preanalisis.equals(este))
        {
            coincidir(este);
        }
        else if(preanalisis.equals(numero))
        {
            coincidir(numero);
        }
        else if(preanalisis.equals(cadena))
        {
            coincidir(cadena);
        }
        else if(preanalisis.equals(identificador))
        {
            coincidir(identificador);
        }
        else if(preanalisis.equals(par_izq))
        {
            coincidir(par_izq);
            EXPRESSION();
            coincidir(par_der);
        }
        else if(preanalisis.equals(super_))
        {
            coincidir(super_);
            coincidir(punto);
            coincidir(identificador);
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }

    }
    void FUNCTION()
    {
        if(hayErrores) return;
        if(preanalisis.equals(identificador))
        {
            coincidir(identificador);
            coincidir(par_izq);
            PARAMETERS_OPC();
            coincidir(par_der);
            BLOCK();
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }

    void FUNCTIONS()
    {
        if(hayErrores) return;
        if(preanalisis.equals(identificador))
        {
            FUNCTION();
            FUNCTIONS();
        }
    }

    void PARAMETERS_OPC()
    {
        if(hayErrores) return;
        if(preanalisis.equals(identificador))
        {
            PARAMETERS();
        }
    }

    void PARAMETERS()
    {
        if(hayErrores) return;
       if(preanalisis.equals(identificador))
       {
        coincidir(identificador);
        PARAMETERS_2();
       } 
       else
       {
           hayErrores = true;
           System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
       }
    }

    void PARAMETERS_2()
    {
        if(hayErrores) return;
        if(preanalisis.equals(coma))
        {
            coincidir(coma);
            coincidir(identificador);
            PARAMETERS_2();
        }
    }

    void ARGUMENTS_OPC()
    {
            if(hayErrores) return;
            if(preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
            {
                ARGUMENTS();
            }     
    }

    void ARGUMENTS()
    {
        if(hayErrores) return;
        if(preanalisis.equals(negacion)||preanalisis.equals(menos)||preanalisis.equals(verdadero)||preanalisis.equals(falso)||preanalisis.equals(nulo)||preanalisis.equals(este)||preanalisis.equals(numero)||preanalisis.equals(cadena)||preanalisis.equals(identificador)||preanalisis.equals(par_izq)||preanalisis.equals(super_))
        {
            EXPRESSION();
            ARGUMENTS_2();
        }
        else
        {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un identificador.");
        }
    }

    void ARGUMENTS_2()
    {
        if(hayErrores) return;
        if(preanalisis.equals(coma))
        {
            coincidir(coma);
            EXPRESSION();
            ARGUMENTS_2();
        }
    }

    void coincidir(Token t){
        if(hayErrores) return;

        if(preanalisis.tipo == t.tipo){
            i++;
            preanalisis = tokens.get(i);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un  " + t.tipo);

        }
    }


}
