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

 int energiaMagica = 100;

 // Nuevos hechizos encapsulados
 Hechizo hechizoAgua;
 Hechizo hechizoFuego;

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

  hechizoAgua = new Hechizo("Bomba de Agua", 0, 60);
  hechizoFuego = new Hechizo("Bomba de Fuego", 25, 100);
  this.entorno.iniciar();
}
 
   	public void tick() {
	   
	        entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0, 1);
	        mago.dibujar();

	        for (int i = 0; i < rocas.length; i++) colisionMagoRoca(mago, rocas[i]);
	        colisionMagoPantalla(mago);

	        if (entorno.estaPresionada(entorno.TECLA_DERECHA) && !inhDerecha) mago.mover(1, 0);
	        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && !inhIzquierda) mago.mover(-1, 0);
	        if (entorno.estaPresionada(entorno.TECLA_ARRIBA) && !inhArriba) mago.mover(0, -1);
	        if (entorno.estaPresionada(entorno.TECLA_ABAJO) && !inhAbajo) mago.mover(0, 1);

	        for (int i = 0; i < rocas.length; i++) rocas[i].dibujar();

	        inhAbajo = false;
	        inhArriba = false;
	        inhDerecha = false;
	        inhIzquierda = false;

	        if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
	            int mx = entorno.mouseX();
	            int my = entorno.mouseY();

	            if (mx >= 615 && mx <= 785 && my >= 165 && my <= 215) {
	                hechizoAgua.seleccionar();
	                hechizoFuego.deseleccionar();
	            } else if (mx >= 615 && mx <= 785 && my >= 220 && my <= 270 && energiaMagica >= hechizoFuego.costoEnergia) {
	                hechizoFuego.seleccionar();
	                hechizoAgua.deseleccionar();
	            } else if (mx < 600) {
	                Hechizo hechizoActivo = null;
	                if (hechizoAgua.seleccionado) hechizoActivo = hechizoAgua;
	                else if (hechizoFuego.seleccionado) hechizoActivo = hechizoFuego;

	                if (hechizoActivo != null && energiaMagica >= hechizoActivo.costoEnergia) {
	                    entorno.dibujarCirculo(mx, my, hechizoActivo.areaEfecto * 2, Color.cyan);
	                    for (int i = 0; i < murcielagosEnPantalla.length; i++) {
	                        Murcielagos m = murcielagosEnPantalla[i];
	                        if (m != null) {
	                            double dx = m.x - mx;
	                            double dy = m.y - my;
	                            if (Math.sqrt(dx * dx + dy * dy) <= hechizoActivo.areaEfecto) {
	                                murcielagosEnPantalla[i] = null;
	                                enemigosVivos--;
	                                enemigosDerrotados++;
	                            }
	                        }
	                    }
	                    energiaMagica -= hechizoActivo.costoEnergia;
	                    hechizoActivo.deseleccionar();
	                }
	            }
	        }
	/////////////////////// Murcielagos///////////////////////////////////
	        if (enemigosVivos < maxEnemigosPantalla && (enemigosDerrotados + enemigosVivos) < totalEnemigos) {
	            Murcielagos nuevo = crearMurcielagoEnBorde();
	            if (nuevo != null) {
	                agregarMurcielago(nuevo);
	                enemigosVivos++;
	            }
	        }

	        for (int i = 0; i < murcielagosEnPantalla.length; i++) {
	            Murcielagos m = murcielagosEnPantalla[i];
	            if (m != null) {
	                m.moverHacia(mago.x, mago.y);
	                m.dibujar(entorno);

	                if (m.estaCerca(mago.x, mago.y, 30)) {
	                    mago.recibirDanio(2);  // Daño ajustado para que 50 murciélagos bajen 100 puntos
	                    murcielagosEnPantalla[i] = null;
	                    enemigosVivos--;
	                }

	                if (!mago.estaVivo()) {
	                    entorno.cambiarFont("Arial", 48, Color.RED);
	                    entorno.escribirTexto("¡Juego Terminado!", 250, 300);
	                    return;
	                }
	            }
	        }
	///////////////////////////////////////////////////////////////////////////////
	     // ------------------ PANEL LATERAL ------------------
	        entorno.dibujarRectangulo(700, 300, 200, 600, 0.0, Color.gray);
	        entorno.dibujarRectangulo(700, 150, 185, 265, 0.0, Color.darkGray);
	        entorno.dibujarRectangulo(700, 450, 185, 265, 0.0, Color.darkGray);

	        // ------------------ BOTONES ------------------
	        entorno.dibujarRectangulo(700, 190, 170, 50, 0.0,Color.red);
	        entorno.dibujarRectangulo(700, 245, 170, 50, 0.0, Color.red);
	        entorno.cambiarFont("Times New Roman", 32, Color.red);
	        entorno.escribirTexto("Hechizos", 635, 60);
	        entorno.cambiarFont("Times New Roman", 24, Color.cyan);
	        entorno.escribirTexto("Bomba de Agua", 620, 200);
	        entorno.escribirTexto("Bomba de Fuego", 619, 250);

	     // ------------------ BARRA DE VIDA ------------------
	        double porcentajeVida = (double) mago.vidaActual / mago.vidaMax;
	        int anchoVida = (int)(170 * porcentajeVida);
	        entorno.dibujarRectangulo(700, 350, 170, 30, 0.0, Color.gray);
	        entorno.dibujarRectangulo(700 - (170 - anchoVida) / 2, 350, anchoVida, 30, 0.0, Color.red);
	        entorno.cambiarFont("Times New Roman", 20, Color.white);
	        entorno.escribirTexto((int)(porcentajeVida * 100) + "%", 665, 357);

	        // ------------------ BARRA DE ENERGÍA ------------------
	        double porcentajeEnergia = (double) energiaMagica / 100.0;
	        int anchoEnergia = (int)(170 * porcentajeEnergia);
	        entorno.dibujarRectangulo(700, 400, 170, 30, 0.0, Color.gray);
	        entorno.dibujarRectangulo(700 - (170 - anchoEnergia) / 2, 400, anchoEnergia, 30, 0.0, Color.blue);
	        entorno.cambiarFont("Times New Roman", 20, Color.white);
	        entorno.escribirTexto((int)(porcentajeEnergia * 100) + "%", 665, 407);


	     // ------------------ ESTADÍSTICAS UNIFICADAS (centradas) ------------------
	        entorno.cambiarFont("Georgia", 18, Color.white);
	        entorno.escribirTexto("Derrotados: " + enemigosDerrotados, 640, 460);
	        entorno.escribirTexto("Hechizo: " +
	            (hechizoAgua.seleccionado ? "Agua" :
	             hechizoFuego.seleccionado ? "Fuego" : "Ninguno"),
	            640, 480);

	      
}  

  //Método que crea un murciélago en uno de los bordes de la pantalla
   	public Murcielagos crearMurcielagoEnBorde() {
   	 int lado = (int)(Math.random() * 4);  // Selecciona aleatoriamente un borde: 0=arriba, 1=abajo, 2=izquierda, 3=derecha
   	 int margen = 20;  // Define un margen para que el murciélago aparezca ligeramente fuera del borde
   	 double x = 0, y = 0;

   	 // Según el borde seleccionado, se asignan coordenadas iniciales
   	 if (lado == 0) { // Aparece arriba
   	     x = Math.random() * entorno.ancho(); // X aleatoria dentro del ancho del entorno
   	     y = -margen; // Justo por encima del límite superior
   	 } else if (lado == 1) { // Aparece abajo
   	     x = Math.random() * entorno.ancho();
   	     y = entorno.alto() + margen; // Justo por debajo del límite inferior
   	 } else if (lado == 2) { // Aparece a la izquierda
   	     x = -margen; // Justo fuera del borde izquierdo
   	     y = Math.random() * entorno.alto();
   	 } else { // Aparece a la derecha
   	     x = entorno.ancho() + margen; // Justo fuera del borde derecho
   	     y = Math.random() * entorno.alto();
   	 }

   	 // Crea y retorna un nuevo murciélago con las coordenadas generadas
   	 return new Murcielagos(x, y);
   	}

   	//Método que agrega un nuevo murciélago en la primera posición libre del arreglo
   	public void agregarMurcielago(Murcielagos nuevo) {
   	 for (int i = 0; i < murcielagosEnPantalla.length; i++) {
   	     if (murcielagosEnPantalla[i] == null) { // Busca el primer hueco libre
   	         murcielagosEnPantalla[i] = nuevo;  // Coloca el nuevo murciélago en esa posición
   	         return; // Sale del bucle una vez agregado
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
