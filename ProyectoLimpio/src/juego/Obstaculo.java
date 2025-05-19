package juego;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
public class Obstaculo {
 double x,y;
 double escala;
 Image imgO;
 double ancho;
 double alto;
 Entorno e;
 double bordeDerecho;
    double bordeIzquierdo;
    double bordeSuperior;
    double bordeInferior;
    
 public Obstaculo(double x, double y, Entorno e) {
  
  this.x = x;
  this.y = y;
  //this.escala modifica el tamaño de los obstaculos.
  this.escala =0.15;
  this.imgO= Herramientas.cargarImagen("roca.png");
  this.e=e;
  this.alto = imgO.getHeight(null)*this.escala;
  this.ancho = imgO.getWidth(null)*this.escala;
  //limite derecho del obstaculo: funciona
  this.bordeDerecho=this.x+this.ancho/2;
  //limite izquierdo del obstaculo: funciona
  this.bordeIzquierdo=this.x-this.ancho/2;
  //checkear los bordes superior e inferior, Gondolf los pasa
  this.bordeSuperior=this.y-this.alto/2;
  this.bordeInferior=this.y+this.alto/2;
  
 }
  public void dibujar () {
   e.dibujarImagen(imgO, this.x, this.y,0,this.escala);
  }
 
}
