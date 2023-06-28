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
                return true;
            default:
                return false;
        }
    }

    public boolean esEstructuraDeControl(){
        switch (this.tipo){
            case SI:
            case ADEMAS:
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
                return 3;
            case MAS:
            case MENOS:
                return 2;
            case IGUAL:
                return 1;
            case MAYOR:
            case MAYOR_IGUAL:
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
                return 2;
        }
        return 0;
    }

}
