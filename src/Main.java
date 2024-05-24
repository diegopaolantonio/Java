public class Main {
    public static void main(String[] args){
//        Numero flotante original
        float numeroFloat= 524.874f;

//        Variables a imprimir
        int parteEntera;
        float parteDecimal;

//        Transforma de float a int para dejar solo la parte entera
        parteEntera = (int)numeroFloat;
//        Resta el entero a numero original para dejar solo el decimal
        parteDecimal = numeroFloat - parteEntera;

//        Imprimo el numero original, la parte entera y la decimal
        System.out.println("Numero Original: " + numeroFloat);
        System.out.println("Parte entera: " + parteEntera);
        System.out.println("Parte decimal: " + String.format("%.3f", parteDecimal));

    }
}