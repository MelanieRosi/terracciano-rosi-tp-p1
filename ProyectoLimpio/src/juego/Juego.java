package juego;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;
import java.awt.Color;

public class Juego extends InterfaceJuego
{
 // El objeto Entorno que controla el tiempo y otros
 // Variables y métodos propios de cada grupo
 // ...
 Entorno entorno;
 Image fondo;
 Murcielagos murcielagos;
 Personaje mago;
 //Menu menu;
 Obstaculo[] rocas;
 boolean inhArriba;
 boolean inhAbajo;
 boolean inhIzquierda;
 boolean inhDerecha;
 
 int totalEnemigos = 50; // Cantidad total de murciélagos que pueden aparecer en todo el juego
 int maxEnemigosPantalla = 10; // Máximo de murciélagos que pueden estar en pantalla al mismo tiempo
 int enemigosVivos = 0; // Contador de murciélagos que están actualmente activos en pantalla
 int enemigosDerrotados = 0; // Contador de murciélagos eliminados
 Murcielagos[] murcielagosEnPantalla; // Arreglo que almacena los murciélagos activos

 Juego()
 {
  // Inicializa el objeto entorno
  this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
  //menu=new Menu(200,600,entorno);
  fondo=Herramientas.cargarImagen("fondo.png");
  mago= new Personaje(300.0,300.0,entorno);
  rocas=new Obstaculo[5];
  //murcielagos= new Murcielagos(100,200,entorno);
  int [] corX={70,400,100,450,180};
  int [] corY={70,100,500,515,300};
  for(int i=0 ; i<rocas.length;i++) {
  rocas[i] =new Obstaculo(corX[i],corY[i],entorno); 

  }
  murcielagosEnPantalla = new Murcielagos[maxEnemigosPantalla];
  // Se crea el arreglo con espacio para hasta 10 murciélagos simultáneamente
  this.entorno.iniciar();
}
 
   public void tick() {

//aparece gondolf: 2: hacia arriba o abajo, 0:rota; 1:zoom	   
   entorno.dibujarImagen(fondo,entorno.ancho()/2,entorno.alto()/2,0,1);
    mago.dibujar();
      
//movimiento de Gondolf en el entorno según los comando del teclado: funciona
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
      
/////////////////////////////murcielagos//////////////////////////////////////////
      if (enemigosVivos < maxEnemigosPantalla 
              && (enemigosDerrotados + enemigosVivos) < totalEnemigos) {
              Murcielagos nuevo = crearMurcielagoEnBorde();
              if (nuevo != null) {
                  agregarMurcielago(nuevo);
                  enemigosVivos++;
              }
          }
          // ----- 2) Mover y dibujar murciélagos, y eliminar los muertos -----
          for (int i = 0; i < murcielagosEnPantalla.length; i++) {
              Murcielagos m = murcielagosEnPantalla[i];
              if (m != null) {
                  m.moverHacia(mago.x, mago.y);
                  m.dibujar(entorno);
                  // Si está muy cerca, lo consideramos "derrotado"
                  if (m.estaCerca(mago.x, mago.y, 15)) {
                      murcielagosEnPantalla[i] = null;
                      enemigosVivos--;
                      enemigosDerrotados++;
                  }
                  
                  // Si toca al mago, le hace daño y desaparece  ← NUEVO
                  if (m.estaCerca(mago.x, mago.y, 30)) {       // ← NUEVO umbral
                      mago.recibirDanio(10);                   // ← NUEVO dañar al mago
                      murcielagosEnPantalla[i] = null;         // ← NUEVO eliminar murciélago
                      enemigosVivos--;                         // ← NUEVO actualizar contador
                  }
          }
      }
/////////////////////////////menu//////////////////////////////////////////

       //fondo grande
       entorno.dibujarRectangulo(700, 300,200,600, 0.0, Color.gray);
       //rectangulos con botones de hechizos
       entorno.dibujarRectangulo(700, 150,185,265, 0.0, Color.darkGray);
       entorno.cambiarFont("Times New Roman", 32, Color.red);
       entorno.escribirTexto("Hechizos", 635, 60);
       //rectangulo con barras de vida
       entorno.dibujarRectangulo(700, 450,185,265, 0.0, Color.darkGray);  
       //boton bomba de agua
       entorno.dibujarRectangulo(700, 190,170,50, 0.0, Color.red);
       entorno.cambiarFont("Times New Roman", 24, Color.cyan);
       entorno.escribirTexto("Bomba de Agua", 620, 200);
       //boton de bomba de fuego
       entorno.dibujarRectangulo(700, 245,170,50, 0.0, Color.red);
       entorno.cambiarFont("Times New Roman", 24, Color.cyan);
       entorno.escribirTexto("Bomba de Fuego",619, 250); 
       //linea de vida 
       entorno.dibujarRectangulo(700, 400,170,30, 0.0, Color.red);
       entorno.cambiarFont("Times New Roman", 26, Color.cyan);
       entorno.escribirTexto("%100", 660, 410);
       //linea de ??
       entorno.dibujarRectangulo(700, 500,170,30, 0.0, Color.red);
       entorno.cambiarFont("Times New Roman", 28, Color.cyan);
       entorno.escribirTexto("%100", 660, 510);
          
 }
   
   public Murcielagos crearMurcielagoEnBorde() {
       int lado = (int)(Math.random() * 4);
       int margen = 20;
       double x = 0, y = 0;

       if (lado == 0) {            // arriba
           x = Math.random() * entorno.ancho();
           y = -margen;
       } else if (lado == 1) {     // abajo
           x = Math.random() * entorno.ancho();
           y = entorno.alto() + margen;
       } else if (lado == 2) {     // izquierda
           x = -margen;
           y = Math.random() * entorno.alto();
       } else {                    // derecha
           x = entorno.ancho() + margen;
           y = Math.random() * entorno.alto();
       }

       return new Murcielagos(x, y);
      
   }

   // AGREGA UN MURCIÉLAGO EN EL PRIMER HUECO LIBRE
  public   void agregarMurcielago(Murcielagos nuevo) {
      for (int i = 0; i < murcielagosEnPantalla.length; i++) {
          if (murcielagosEnPantalla[i] == null) {
              murcielagosEnPantalla[i] = nuevo;
              return;
          }
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
//limite con las rocas por arriba: funciona (corregido) 
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
 
//limite derecho con la pantalla del menu:funciona 
 boolean derecho=this.inhDerecha;
 if(p.bordeDerecho > 600) {
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
