import java.util.Scanner;

public class numeroFlotantePaolantonio {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
//        Numero flotante original
        System.out.println("Ingrese un numero float con 5 decimales maximo: ");
        float numeroFloat = scanner.nextFloat();

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
        System.out.println("Parte decimal: " + String.format("%.5f", parteDecimal));
    }
}
