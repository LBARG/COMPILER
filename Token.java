public class Token {

    final TipoToken tipo;
    final String lexema;
    Object literal;
    int linea;

    public Token(TipoToken tipo, String lexema, Object literal, int linea) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.linea = linea;
    }
    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.linea = 0;
    }
   public Token(TipoToken tipo, String lexema, Object literal) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
    } 
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Token)) {
            return false;
        }

        if(this.tipo == ((Token)o).tipo){
            return true;
        }

        return false;
    }

    public String toString(){
        return tipo + " " + lexema + " " + literal;
    }
    // Métodos auxiliares
    public boolean esOperando(){
        switch (this.tipo){
            case IDENTIFICADOR:
            case NUMERO:
            case CADENA:
                return true;
            default:
                return false;
        }
    }

    public boolean esOperador(){
        switch (this.tipo){
            case MAS:
            case MENOS:
            case ASTERISCO:
            case DIAGONAL:
            case IGUAL:
            case MAYOR:
            case MAYOR_IGUAL:
            case MENOR:
            case MENOR_IGUAL:
            case Y:
            case O:
            case DOBLE_IGUAL:
            case DIFERNETE:
                return true;
            default:
                return false;
        }
    }

     public boolean esPalabraReservada(){
        switch (this.tipo){
            case VAR:
            case SI:
            case IMPRIMIR:
            case ADEMAS:
            case MIENTRAS:
            case PARA:
                return true;
            default:
                return false;
        }
    }

    public boolean esEstructuraDeControl(){
        switch (this.tipo){
            case SI:
            case ADEMAS:
            case MIENTRAS:
            case PARA:
                return true;
            default:
                return false;
        }
    }

    public boolean precedenciaMayorIgual(Token t){
        return this.obtenerPrecedencia() >= t.obtenerPrecedencia();
    }

    private int obtenerPrecedencia(){
        switch (this.tipo){
            case ASTERISCO:
            case DIAGONAL:
                return 7;
            case MAS:
            case MENOS:
                return 6;
            case MAYOR:
            case MAYOR_IGUAL:
            case MENOR:
            case MENOR_IGUAL:
                return 5;
            case DOBLE_IGUAL:
            case DIFERNETE:
                return 4;
            case Y:
                return 3;
            case O:
                return 2;
            case IGUAL:
                return 1;
          
        }

        return 0;
    }

    public int aridad(){
        switch (this.tipo) {
            case ASTERISCO:
            case DIAGONAL:
            case MAS:
            case MENOS:
            case IGUAL:
            case MAYOR:
            case MAYOR_IGUAL:
            case MENOR:
            case MENOR_IGUAL:
            case DOBLE_IGUAL:
            case DIFERNETE:
            case Y:
            case O:
                return 2;
        }
        return 0;
    }

}
