package juego;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Personaje {
 double x,y;
 boolean direccion;
 Image imgD;
 Image imgI;
 double escala;
 double alto;
 double ancho;
 Entorno e;
    double bordeDerecho;
    double bordeIzquierdo;
    double bordeSuperior;
    double bordeInferior;
 public Personaje (double x, double y, Entorno e){
  super();
  this.x = x;
  this.y = y;
  this.direccion = false;
  
  this.imgD =Herramientas.cargarImagen("brujoDerecho.png");
  this.imgI = Herramientas.cargarImagen("brujoIzquierdo.png");
  //this.escala modifica el tamaño del personaje.
  this.escala = 0.3;
  this.alto = imgI.getHeight(null)*this.escala;
  this.ancho = imgI.getWidth(null)*this.escala;
  this.e=e;
  
 } 
 public void dibujar(){
  if(this.direccion) {
   e.dibujarImagen(imgD,this.x,this.y,0,this.escala);
  }
  else {
   e.dibujarImagen(imgI,this.x,this.y,0,this.escala);
  }
   
   
 }
 
 public void mover(double dh,double dv) {
  if(dh >0.0) {
   this.direccion =true;
   
  }
  if(dh < 0.0) {
   this.direccion=false;
  }
  this.x +=dh;
  this.y +=dv;
  this.bordeDerecho=this.x+this.ancho/2;
  this.bordeIzquierdo=this.x-this.ancho/2;
  this.bordeSuperior=this.y-this.alto/2;
  this.bordeInferior=this.y+this.alto/2;
  
 }
 }
