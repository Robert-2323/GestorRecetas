public class Receta {
    private int numReceta=count;
    private String nombre;
    private String[] ingredientes;
    private String preparacion;
    private double tiempoPreparacion;
    static int count=1;

    public Receta(String nombre,String[] ingredientes,String preparacion,double tiempoPreparacion){
        count++;
        this.nombre=nombre;
        this.ingredientes=ingredientes;
        this.preparacion=preparacion;
        this.tiempoPreparacion=tiempoPreparacion;
    }

    public double getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public String getPreparacion() {
        return preparacion;
    }

    public void setPreparacion(String preparacion) {
        this.preparacion = preparacion;
    }

    public String[] getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String[] ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumReceta() {
        return numReceta;
    }

}
