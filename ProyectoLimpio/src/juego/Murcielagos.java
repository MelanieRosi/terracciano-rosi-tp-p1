package juego;
//import java.awt.Color; 
import entorno.Entorno;
import java.awt.Image;
import entorno.Herramientas;

public class Murcielagos {
    double x, y;
    double velocidad = 1.5;
    Image img;
    double escala = 0.15;
    boolean muerto = false;

    public Murcielagos(double x, double y) {
        this.x = x;
        this.y = y;
        this.img = Herramientas.cargarImagen("murcielagos.png");
    }

    public void dibujar(Entorno e) {
        e.dibujarImagen(img, x, y, 0, escala);
    }

    public void moverHacia(double xMago, double yMago) {
        double dx = xMago - x, dy = yMago - y;
        double dist = Math.sqrt(dx*dx + dy*dy);
        if (dist > 0) {
            // velocidad variable según la distancia
            double vmax = 3.0, vmin = 0.2, v;
            if (dist > 100) v = vmax;
            else if (dist < 10) v = 0;
            else v = vmin + (vmax - vmin)*(dist - 10)/90;
            x += v * dx/dist;
            y += v * dy/dist;
        }
    }

    public boolean estaCerca(double px, double py, double umbral) {
        double dx = x - px, dy = y - py;
        return Math.sqrt(dx*dx + dy*dy) < umbral;
    }

    public boolean estaMuerto() {
        return muerto;
    }

    public void morir() {
        muerto = true;
    }
}