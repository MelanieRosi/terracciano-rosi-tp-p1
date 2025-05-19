package juego;
import java.awt.Image;

import entorno.Entorno;


import entorno.Herramientas;

import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
 // El objeto Entorno que controla el tiempo y otros
 // Variables y métodos propios de cada grupo
 // ...
 Entorno entorno;
 Image fondo;
 Personaje mago;
 Obstaculo[] rocas;
 boolean inhArriba;
 boolean inhAbajo;
 boolean inhIzquierda;
 boolean inhDerecha;

 Juego()
 {
  // Inicializa el objeto entorno
  this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
  
  fondo=Herramientas.cargarImagen("fondo.png");
  mago= new Personaje(300.0,300.0,entorno);
  rocas=new Obstaculo[5];
  int [] corX={100,400,350,600,550};
  int [] corY={100,500,400,100,250};
  for(int i=0 ; i<rocas.length;i++) {
  rocas[i] =new Obstaculo(corX[i],corY[i],entorno); 
  }
  this.entorno.iniciar();
}
 
   public void tick() {
//movimiento de Gondolf en el entorno según los comando del teclado: funciona
   entorno.dibujarImagen(fondo,entorno.ancho()/2,entorno.alto()/2,0,1);
   mago.dibujar();

   for (int i=0; i< rocas.length;i++) {
    colisionMagoRoca(mago,rocas[i]);
   }
   
   
   colisionMagoPantalla(mago);
   
   
   if(entorno.estaPresionada(entorno.TECLA_DERECHA)
     &&!this.inhDerecha) {
    mago.mover(1,0);
    
  }
   
   if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA)
     &&!this.inhIzquierda) {
    mago.mover(-1,0);
    
  }
   
   if(entorno.estaPresionada(entorno.TECLA_ARRIBA)
     &&!this.inhArriba) {
    mago.mover(0,-1);
    
  }
   
   if(entorno.estaPresionada(entorno.TECLA_ABAJO)
     &&!this.inhAbajo) {
    mago.mover(0,1);
    
  } 
   
   for(int i=0 ; i<rocas.length; i++) {
    rocas[i].dibujar();
    
   }

   this.inhAbajo=false;
   this.inhArriba=false;
   this.inhDerecha=false;
   this.inhIzquierda=false;
      
      for(int i=0 ; i<rocas.length; i++) {
    rocas[i].dibujar();
    
   }
      
 }
  
  public void colisionMagoRoca(Personaje p, Obstaculo o) {

//limite con las rocas izquierdo: funciona  
   if (Math.abs(p.bordeIzquierdo-o.bordeDerecho)< 0.5
  && p.bordeInferior > o.bordeSuperior 
  &&  p.bordeSuperior < o.bordeInferior){
    
    this.inhIzquierda=true;
    
   }
 //limite con las rocas derecho: funciona  
   if (Math.abs(p.bordeDerecho-o.bordeIzquierdo)< 0.5
    && p.bordeInferior > o.bordeSuperior 
    &&  p.bordeSuperior < o.bordeInferior){
      
      this.inhDerecha=true;
      
     }
//limite con las rocas por debajo:funciona (corregido)     
   if (Math.abs(p.bordeInferior-o.bordeSuperior)< 0.5
     && p.bordeIzquierdo < o.bordeDerecho
     && p.bordeDerecho > o.bordeIzquierdo){
    this.inhAbajo=true;
   }
//limite con las rocas por arriba:funciona (corregido)
   if (Math.abs(p.bordeSuperior-o.bordeInferior)< 0.5
     && p.bordeIzquierdo < o.bordeDerecho
     && p.bordeDerecho > o.bordeIzquierdo){
    this.inhArriba=true;
   }

  }
   
   public void colisionMagoPantalla(Personaje p) {
	   
//limite superior con la pantalla del entorno:funciona 
    boolean arriba= this.inhArriba;
    
    if(p.bordeSuperior < 0) {
     this.inhArriba=true;
     
    }
    else {
     this.inhArriba=arriba;
    }
//limite inferior con la pantalla del entorno:funciona 
   boolean abajo=this.inhAbajo;
   if(p.bordeInferior > entorno.alto()) {
    this.inhAbajo=true;
   }
   else {
    this.inhAbajo=abajo ;
   }
//limite izquierdo con la pantalla del entorno:funciona 
   boolean izquierdo =this.inhIzquierda;
   if(p.bordeIzquierdo < 0){
    this.inhIzquierda=true;
   }
   else {
    this.inhIzquierda=izquierdo;
   }
//limite derecho con la pantalla del entorno:funciona 
 boolean derecho=this.inhDerecha;
 if(p.bordeDerecho > entorno.ancho()) {
    this.inhDerecha=true;  
 }
 else {
    this.inhDerecha=derecho;
 }
   }

 @SuppressWarnings("unused")
 public static void main(String[] args)
 {
  Juego juego = new Juego();
 }
}
