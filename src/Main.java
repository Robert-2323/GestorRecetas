import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final String RUTA_DIR="Archivos/";
    private static final String RUTA_RECETAS="recetas.dat";

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);

        int option=-1;

        while (option!=0){
            System.out.println("===== GESTOR DE RECETAS=====");
            System.out.println("0-Salir\n" +
                    "1-Crear un catálogo inicial con varias recetas.\n" +
                    "2-Mostrar todas las recetas almacenadas.\n" +
                    "3-Buscar una receta mediante su identificador.\n" +
                    "4-Modificar el tiempo de preparación de una receta.\n" +
                    "5-Añadir nuevas recetas al catálogo.\n" +
                    "6-Exportar recetas en XML\n");
            System.out.print("Seleccione una opcion: ");
            option=sc.nextInt();

            if (option==1){
                crearFicheroRecetas();
            }

            if (option==2){
                leerRecetas();
            }
            if (option==3){
                System.out.print("Introduzca el id de la receta que quiere buscar: ");
                int idBusca=sc.nextInt();
                leerRecetas(idBusca);
            }
            if (option==4){
                System.out.print("Introduzca el id de la receta que quiere modificar el tiempo de preparacion: ");
                int idBusca=sc.nextInt();
                modificarTiempo(idBusca);
            }
        }
    }

    public static Receta[] recetasDefault(){
        String[] ingredientes1 = {"Patatas", "Huevos", "Cebolla", "Aceite de oliva", "Sal"};// 5
        String[] ingredientes2 = {"Espaguetis", "Guanciale o panceta", "Yemas de huevo", "Queso Pecorino Romano", "Pimienta negra"};//5
        String[] ingredientes3 = {"Lechuga romana", "Pechuga de pollo", "Pan de molde", "Queso parmesano", "Salsa César"};//5
        String[] ingredientes4 = {"Pechugas de pollo", "Zumo de limón", "Caldo de pollo", "Maicena", "Ajo", "Aceite de oliva", "Sal"};//7
        String[] ingredientes5 = {"Queso crema", "Nata para montar", "Huevos", "Azúcar", "Galletas tipo María", "Mantequilla"};//6

        String preparacion1="Pelar y cortar las patatas y la cebolla en trozos pequeños, luego freírlas\n " +
                "en una sartén con abundante aceite a fuego lento hasta que estén tiernas. Batir los huevos\n " +
                "en un bol amplio, añadir las patatas y cebolla escurridas junto con la sal, y mezclar bien.\n " +
                "Cuajar la mezcla en una sartén con un toque de aceite por ambos lados hasta el punto deseado.";
        String preparacion2="Cocer la pasta en agua hirviendo con sal hasta que esté al dente. Dorar el guanciale\n " +
                "en una sartén sin aceite hasta que esté crujiente. En un bol, batir las yemas de huevo junto con\n" +
                " el queso rallado y abundante pimienta negra molida. Mezclar la pasta caliente con el guanciale y,\n " +
                "fuera del fuego, incorporar la crema de huevos y queso removiendo rápidamente para evitar que cuaje.";
        String preparacion3="Cocinar la pechuga de pollo a la plancha y cortarla en tiras finas. Cortar el pan en cubos\n " +
                "pequeños y tostarlos en una sartén con un chorrito de aceite hasta que estén dorados y crujientes\n " +
                "(picatostes). Lavar y trocear la lechuga romana, colocarla en una ensaladera y añadir el pollo,\n " +
                "los picatostes, virutas de queso parmesano y la salsa César al gusto.";
        String preparacion4="Cortar las pechugas en filetes o dados, salpimentarlas y dorarlas en una sartén\n " +
                "con aceite de oliva. En un recipiente, mezclar el zumo de limón, el caldo de pollo, un diente\n " +
                "de ajo picado y una cucharadita de maicena disuelta en agua fría. Verter esta mezcla en la sartén\n " +
                "junto con el pollo y cocinar a fuego medio hasta que la salsa reduzca y espese.";
        String preparacion5="Triturar las galletas y mezclarlas con la mantequilla fundida para compactarlas\n " +
                "en la base de un molde desmontable. En un bol, batir el queso crema junto con el azúcar,\n " +
                "los huevos y la nata líquida hasta obtener una mezcla fina y sin grumos. Verter sobre la\n " +
                "base de galletas y hornear a 180°C durante unos 40-45 minutos hasta que esté firme. Dejar enfriar " +
                "antes de servir";

        Receta tortillaDePatata=new Receta("Tortilla de patata",ingredientes1,preparacion1,0.35);
        Receta carbonara=new Receta("Carbonara",ingredientes2,preparacion2,0.2);
        Receta ensalaCesar=new Receta("Ensalada César",ingredientes3,preparacion3,0.15);
        Receta polloAlLimon=new Receta("Pollo al limón",ingredientes4,preparacion4,0.25);
        Receta tartaQueso=new Receta("Tarta de queso",ingredientes5,preparacion5,0.55);

        return new Receta[]{tortillaDePatata,carbonara,ensalaCesar,polloAlLimon,tartaQueso};
    }

    public static void crearFicheroRecetas(){
        try (DataOutputStream dOut=new DataOutputStream(new FileOutputStream(RUTA_DIR+RUTA_RECETAS))){
            File catalogoInicial=new File(RUTA_DIR+RUTA_RECETAS);
            if (catalogoInicial.createNewFile()){
                System.out.println("Archivo creado correctamente");
            }else {
                System.out.println("El archivo ya existe");
            }

            Receta[] recetas=recetasDefault();

            for (int i=0;i<recetas.length;i++){
                int idReceta=recetas[i].getNumReceta();
                String nombre=recetas[i].getNombre();
                String[] ingredientes=recetas[i].getIngredientes();
                String preparacion=recetas[i].getPreparacion();
                double tiempo=recetas[i].getTiempoPreparacion();

                dOut.writeInt(idReceta);
                dOut.writeUTF(nombre);
                dOut.writeInt(ingredientes.length);
                for (int j=0;j<ingredientes.length;j++){
                    dOut.writeUTF(ingredientes[j]);
                }
                dOut.writeUTF(preparacion);
                dOut.writeDouble(tiempo);
            }
            System.out.println("Archivo generado correctamente");

        }catch (IOException e){
            System.out.println("Error al escribir el archivo");
        }
    }
    public static void leerRecetas(){
        try(DataInputStream dIn=new DataInputStream(new FileInputStream(RUTA_DIR+RUTA_RECETAS))){
            while (true){
                int idReceta=dIn.readInt();
                String nombre=dIn.readUTF();
                int ingredientesLength=dIn.readInt();
                ArrayList<String> ingredientes=new ArrayList<>();
                for (int i=0;i<ingredientesLength;i++){
                    ingredientes.add(dIn.readUTF());
                }
                String preparacion=dIn.readUTF();
                double tiempo=dIn.readDouble();

                System.out.println("----"+idReceta+"-"+nombre+"----");
                System.out.println("Ingredientes: ");
                for (int i=0;i<ingredientes.size();i++){
                    System.out.println("-"+ingredientes.get(i));
                }
                System.out.println("Preparacion: ");
                System.out.println(preparacion);
                System.out.println("Tiempo de preparacion: "+tiempo);

            }

        }catch (EOFException e) {
            System.out.println("--- Fin del archivo ---");
        }catch (IOException e){
            System.out.println("Error al leer el archivo Recetas");
        }
    }

    public static void leerRecetas(int id){
        try(DataInputStream dIn=new DataInputStream(new FileInputStream(RUTA_DIR+RUTA_RECETAS))){
            while (true){
                int idReceta=dIn.readInt();
                String nombre = dIn.readUTF();
                int ingredientesLength = dIn.readInt();
                ArrayList<String> ingredientes = new ArrayList<>();
                for (int i = 0; i < ingredientesLength; i++) {
                    ingredientes.add(dIn.readUTF());
                }
                String preparacion = dIn.readUTF();
                double tiempo = dIn.readDouble();
                if(id==idReceta) {
                    System.out.println("----" + idReceta + "-" + nombre + "----");
                    System.out.println("Ingredientes: ");
                    for (int i = 0; i < ingredientes.size(); i++) {
                        System.out.println("-" + ingredientes.get(i));
                    }
                    System.out.println("Preparacion: ");
                    System.out.println(preparacion);
                    System.out.println("Tiempo de preparacion: " + tiempo);
                }

            }

        }catch (EOFException e) {
            System.out.println("--- Fin del archivo ---");
        }catch (IOException e){
            System.out.println("Error al leer el archivo Recetas");
        }
    }

    public static void modificarTiempo(int id){
        Scanner sc=new Scanner(System.in);
        try(DataInputStream dIn=new DataInputStream(new FileInputStream(RUTA_DIR+RUTA_RECETAS))){
            while (true){
                int idReceta=dIn.readInt();
                String nombre = dIn.readUTF();
                int ingredientesLength = dIn.readInt();
                ArrayList<String> ingredientes = new ArrayList<>();
                for (int i = 0; i < ingredientesLength; i++) {
                    ingredientes.add(dIn.readUTF());
                }
                String preparacion = dIn.readUTF();
                double tiempo = dIn.readDouble();
                if(id==idReceta) {
                    System.out.print("Introduzca el nuevo tiempo de preparacion: ");
                    double nuevoTiempo=sc.nextDouble();

                   try(RandomAccessFile random=new RandomAccessFile(RUTA_DIR+RUTA_RECETAS,"rw")){
                    int bytesReceta=contarBytesReceta(idReceta,nombre,ingredientes,preparacion,tiempo);
                    random.seek(bytesReceta-8);
                    System.out.println(bytesReceta);
                    //random.writeDouble(nuevoTiempo);
                   }catch (IOException e){
                       System.out.println("ERROR al leer el archivo: "+e.getMessage());
                   }
                }

            }

        }catch (EOFException e) {
            System.out.println("--- Fin del archivo ---");
        }catch (IOException e){
            System.out.println("Error al leer el archivo Recetas");
        }
    }

    public static int contarBytesReceta(int id,String nombre,ArrayList<String> ingredientes,String preparacion,double tiempo){
        int contadorBytes=0;
        if (id>0 && tiempo>0){
            contadorBytes+=4;//Bytes de la id de cada receta

            contadorBytes+=(nombre.length()*2);
            contadorBytes+=4;// Cuatro bytes mas por el entero escrito con la lonjitud de ingredientes
            for (int i=0;i<ingredientes.size();i++){
                contadorBytes+=(ingredientes.get(i).length()*2);
            }
            contadorBytes+=(preparacion.length()*2);

            contadorBytes+=8;//Bytes del tiempo de preparacion

            return contadorBytes;
        }

        return -1;
    }
}