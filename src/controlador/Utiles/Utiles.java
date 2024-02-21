/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Utiles;


/**
 *
 * @author Usuario iTC
 */
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class Utiles {

    //Codigo para validar la cedula
    public static boolean validadorDeCedula(String cedula) {
        boolean cedulaCorrecta = false;

        try {

            if (cedula.length() == 10) // ConstantesApp.LongitudCedula
            {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
// Coeficientes de validación cédula
// El decimo digito se lo considera dígito verificador
                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(cedula.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            System.out.println("Una excepcion ocurrio en el proceso de validadcion");
            cedulaCorrecta = false;
        }

        if (!cedulaCorrecta) {
            System.out.println("La Cédula ingresada es Incorrecta");
        }
        return cedulaCorrecta;
    }
    
    public static Field getField(Class clazz, String attribute){
        Field field = null;
        //Field[] fields = clazz.getFields(); //ver los atributos declarados en la clase padre
        for (Field f: clazz.getFields()) {
            //System.out.println(f.getName() + " " + f.getType().getName());
            if (f.getName().equalsIgnoreCase(attribute)) {
                field = f;
                break;
            }
        }
        for (Field f: clazz.getDeclaredFields()) {
            //System.out.println(f.getName() + " " + f.getType().getName());
            if (f.getName().equalsIgnoreCase(attribute)) {
                field = f;
                break;
            }
        }
        return field;
    }
    
    public static String getDirPoject() {
        return System.getProperty("user.dir");
    }
    
    public static String getOS() {
        return System.getProperty("os.name");
    }
    
    public static void abrirNavegadorPredeterminadorWindows(String url) throws Exception{
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler" + url);
    }
    public static void abrirNavegadorPredeterminadorLinux(String url) throws Exception{
        Runtime.getRuntime().exec("xdg-open " + url);
    }
    public static void abrirNavegadorPredeterminadorMacOsx(String url) throws Exception{
        Runtime.getRuntime().exec("open " + url);
    }
    
    //PARA GUARDAR ARCHIVO EN UTILES

    public static void copiarArchivo(File origen, File destino) throws Exception {
    Files.copy(origen.toPath(),
    (destino).toPath(),
    StandardCopyOption.REPLACE_EXISTING);
    }

    //EN UTILES EXTENSION

    public static String extension(String fileName) {
    String extension = "";

    int i = fileName.lastIndexOf('.');
    if (i > 0) {
    extension = fileName.substring(i + 1);
    }
    return extension;
    }
    
    //EN UTILES CALCULO DE DISTANCIO GEOGRAFICA

public static double coordGpsToKm(double lat1, double lon1, double lat2, double lon2) {        
    double lat1rad = Math.toRadians(lat1);
    double lon1rad = Math.toRadians(lon1);
    double lat2rad = Math.toRadians(lat2);
    double lon2rad = Math.toRadians(lon2);

    double difLatitud = lat1rad - lat2rad;
    double difLongitud = lon1rad - lon2rad;

    double a = Math.pow(Math.sin(difLatitud/2), 2) +
            Math.cos(lat1rad) *
            Math.cos(lat2rad) *
            Math.pow(Math.sin(difLongitud/2), 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

    double radioTierraKm = 6378.0;
    double distancia = radioTierraKm * c;

    return distancia;
}

    public static Double redondear(Double x){
        return Math.round(x * 100.0) / 100.0;
    }
    
     public static void abrirArchivoHTML(String filePath) {
        try {
            // Ejecutar el comando para abrir el archivo HTML en Brave
            Runtime.getRuntime().exec("cmd.exe /c start brave \"" + filePath + "\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
     }
    
}
