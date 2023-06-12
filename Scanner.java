import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





public class Scanner {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;

   // private boolean error = false;



    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("y", TipoToken.Y);
        palabrasReservadas.put("clase", TipoToken.CLASE);
        palabrasReservadas.put("ademas", TipoToken.ADEMAS);
        palabrasReservadas.put("falso", TipoToken.FALSO);
        palabrasReservadas.put("para", TipoToken.PARA);
        palabrasReservadas.put("fun", TipoToken.FUN); //definir funciones
        palabrasReservadas.put("si", TipoToken.SI);
        palabrasReservadas.put("nulo", TipoToken.NULO);
        palabrasReservadas.put("o", TipoToken.O);
        palabrasReservadas.put("imprimir", TipoToken.IMPRIMIR);
        palabrasReservadas.put("retornar", TipoToken.RETORNAR);
        palabrasReservadas.put("super", TipoToken.SUPER);
        palabrasReservadas.put("este", TipoToken.ESTE);
        palabrasReservadas.put("verdadero", TipoToken.VERDADERO);
        palabrasReservadas.put("var", TipoToken.VAR); //definir variables
        palabrasReservadas.put("mientras", TipoToken.MIENTRAS);
    }

    Scanner(String source){
        this.source = source;
    }

    List<Token> scanTokens(){
        //Aquí va el corazón del scanner.

        /*
        Analizar el texto de entrada para extraer todos los tokens
        y al final agregar el token de fin de archivo
         */

        int posicion = 0;

        char caracter;
     
        int estado = 0;
     
        String lexema = "";

        caracter = source.charAt(posicion);

        while(caracter != '$'){
        caracter = source.charAt(posicion);

            if(caracter == '\n')
            {
                linea++;
            }
            switch(estado)
                {
                    case 0:
                    {
                        if(caracter == '('){
                            tokens.add(new Token(TipoToken.PAR_IZQ, "(", null, linea));
                        }
                        else if(caracter == ')'){
                            tokens.add(new Token(TipoToken.PAR_DER, ")", null, linea));
                        }
                        else if(caracter == '{'){
                            tokens.add(new Token(TipoToken.LLAVE_IZQ, "{", null, linea));
                        }
                        else if(caracter == '}'){
                            tokens.add(new Token(TipoToken.LLAVE_DER, "}", null, linea));
                        }
                        else if(caracter == ','){
                            tokens.add(new Token(TipoToken.COMA, ",", null, linea));
                        }
                        else if(caracter == '.'){
                            tokens.add(new Token(TipoToken.PUNTO, ".", null, linea));
                        }
                        else if(caracter == ';'){
                            tokens.add(new Token(TipoToken.PUNTO_COMA, ";", null, linea));
                        }
                        else if(caracter == '-'){
                            tokens.add(new Token(TipoToken.MENOS, "-", null, linea));
                        }
                        else if(caracter == '+'){
                            tokens.add(new Token(TipoToken.MAS, "+", null, linea));
                        }
                        else if(caracter == '*'){
                            tokens.add(new Token(TipoToken.ASTERISCO, "*", null, linea));
                        }
                        else if(caracter == '!')
                        {
                            estado = 3;
                        }
                        else if(caracter == '=')
                        {
                            estado = 4;
                        }
                        else if(caracter == '<')
                        {
                            estado = 5;
                        }
                        else if(caracter == '>')
                        {
                            estado = 6;
                        }
                        else if(caracter == '/')
                        {
                            estado = 7;
                        }
                        else if(Character.isAlphabetic(caracter)){
                            estado = 1;
                            lexema += caracter;
                        }
                        else if(Character.isDigit(caracter)){
                            estado = 2;
                            lexema += caracter;
                        }
                        else if(caracter == '"')
                        {
                            lexema += caracter;
                            estado = 12;
                        }
                        else if(caracter == ' ')
                        {

                        }

                        break;
                    }
                    case 1:
                    {
                        if(Character.isAlphabetic(caracter) || Character.isDigit(caracter)){
                            lexema += caracter;
                        }
                        else{
                            TipoToken tt = palabrasReservadas.get(lexema);
                            if(tt == null){
                                tokens.add(new Token(TipoToken.IDENTIFICADOR, lexema, null, linea));
                            }
                            else{
                                tokens.add(new Token(tt, lexema, null, linea));
                            }

                            estado = 0;
                            lexema = "";
                            posicion--;
                                
                            
                        }

                        break;
                    }
                    case 2:
                    {
                        if(Character.isDigit(caracter)|| caracter=='.')
                        {
                            lexema += caracter;
                        }
                        else
                        {
                            tokens.add(new Token(TipoToken.NUMERO, lexema, Double.parseDouble(lexema), linea));
                            estado = 0;
                            lexema = "";
                            posicion--;
                        }


                        break;
                    }
                    case 3:
                    {
                        if(caracter == '=')
                        {
                            tokens.add(new Token(TipoToken.DIFERNETE,"!=", null, linea));
                            estado = 0;
                        }
                        else
                        {
                            tokens.add(new Token(TipoToken.NEGACION, "!", null, linea));
                            estado = 0;
                            posicion --;
                        }
                        break;
                    }
                    case 4:
                    {
                        if(caracter == '=')
                        {
                            tokens.add(new Token(TipoToken.DOBLE_IGUAL,"==", null, linea));
                            estado = 0;
                        }
                        else
                        {
                            tokens.add(new Token(TipoToken.IGUAL, "=", null, linea));
                            estado = 0;
                            posicion --;
                        }
                        break;
                    }
                    case 5:
                    {
                        if(caracter == '=')
                        {
                            tokens.add(new Token(TipoToken.MENOR_IGUAL,"<=", null, linea));
                            estado = 0;
                        }
                        else
                        {
                            tokens.add(new Token(TipoToken.MENOR, "<", null, linea));
                            estado = 0;
                            posicion --;
                        }

                        break;
                    }
                    case 6:
                    {
                        if(caracter == '=')
                        {
                            tokens.add(new Token(TipoToken.MAYOR_IGUAL,">=", null, linea));
                            estado = 0;
                        }
                        else
                        {
                            tokens.add(new Token(TipoToken.MAYOR, ">", null, linea));
                            estado = 0;
                            posicion --;
                        }

                        break;
                    }
                    case 7:
                    {
                        if(caracter == '/')
                        {
                            estado = 9;
                        }
                        else if(caracter == '*')
                        {
                            estado = 10;
                        }
                        break;
                    } 
                    case 9:
                    {
                        if(caracter == '\n')
                        {
                            estado = 0;
                        }
                        
                        break;
                    }
                    case 10:
                    {
                        if(caracter=='*')
                        {
                            estado = 11;
                        }
                        break;
                    }
                    case 11:
                    {
                        if(caracter == '/')
                        {
                            estado = 0;
                        }
                        else{
                            estado = 10;
                        }
                        break;
                    }
                    case 12:
                    {
                        if(caracter != '"')
                        {
                            lexema+=caracter;
                        }
                        else
                        {
                            lexema += caracter;
                            String literal = lexema.substring(1, lexema.length()-1);
                            tokens.add(new Token(TipoToken.CADENA, lexema, literal, linea));
                            estado = 0;
                            lexema = "";
                        }
                        break;
                    }     
                    default:
                    break;  
                }
                posicion++;
        }
        tokens.add(new Token(TipoToken.EOF, "$", null, linea));
                
                    
        return tokens;
    }
    
}


/*
Signos o símbolos del lenguaje:
(
)
{
}
,
.
;
-
+
*
/
!
!=
=
==
<
<=
>
>=
// -> comentarios (no se getokens.add(new Token(TipoTokennera token)
/* ... * / -> comentarios (no se genera token)
Identificador,
Cadena
Numero
Cada palabra reservada tiene su nombre de token

 */