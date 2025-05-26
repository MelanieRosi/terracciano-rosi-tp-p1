package juego;
public class Hechizo {
    String nombre;
    int costoEnergia;
    int areaEfecto;
    boolean seleccionado;

    public Hechizo(String nombre, int costoEnergia, int areaEfecto) {
        this.nombre = nombre;
        this.costoEnergia = costoEnergia;
        this.areaEfecto = areaEfecto;
        this.seleccionado = false;
    }

    public void seleccionar() {
        this.seleccionado = true;
    }

    public void deseleccionar() {
        this.seleccionado = false;
    }
}