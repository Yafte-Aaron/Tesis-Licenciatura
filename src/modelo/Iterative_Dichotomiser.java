package modelo;

import java.util.HashMap;
import java.util.ArrayList;

public class Iterative_Dichotomiser extends Clasificador{
	
	//Variables
	Nodo arbol;	

	@Override
	public void inicializarDatos(int[][] entrenamiento) {
		arbol = iD3(entrenamiento);
	}

	private Nodo iD3(int[][] entrenamiento){
		//Datos
		int numFilas= entrenamiento.length;
		int numColumnas= entrenamiento[0].length;
		int columnaClase= numColumnas-1;
		
		if(sonClasesIguales(entrenamiento)){
			//Variable que indica el indice del atributo clase.			
			int dato = getClasePromedio(entrenamiento,numFilas,columnaClase);
			//Se crea el nodo clase
			Nodo nodoClase = new Nodo();
			nodoClase.setDato(dato);
			nodoClase.setTipo("Clase");
			return nodoClase;
		}else{
			//Arreglo que guardara el numero de elementos distintos de cada columna.
			int [] numElemDist = new int [numColumnas];
			//Arreglo de listas que guardara todos los valores distintos de cada columna.
			ArrayList [] valElemDist = new ArrayList[numColumnas];			
			//Obtenemos los elementos diferentes de cada atributo.
			for (int columna = 0; columna < numColumnas; columna++){
				valElemDist[columna] = dameValorDist(entrenamiento,columna);
				numElemDist[columna] = valElemDist[columna].size();
			}
			
			//Obtenemos el numero de clases
			int numClases= numElemDist[columnaClase];
			//HashMap que representara el modelo probabilistico.
			HashMap [][] modelo = new HashMap [numClases][numColumnas];

			//Calculamos el conteo de elementos.
			conteo(entrenamiento,valElemDist,numElemDist,modelo);
			
			//Calculamos la Cantidad de informacion
			double cantidadInfo = 0.0;
			cantidadInfo = numFilas * log2(numFilas);
			for (int clase = 0; clase < numClases ; clase++)
				for (int k = 0; k < numElemDist[columnaClase]; k++) {
					int valor = (int) valElemDist[columnaClase].get(k);
					int conteo = (int) modelo[clase][columnaClase].get(valor);
					cantidadInfo -= conteo*log2(conteo);
				}		
			cantidadInfo /= numFilas;
			
			//Calculamos la Entropia de los atributos
			double [] entropias = new double[numColumnas-1];

			for (int columna = 0; columna < columnaClase; columna++) {
				entropias [columna] = 0;
				for (int k = 0; k < numElemDist[columna]; k++) {
					int sumaValores = 0;
					for (int clase = 0; clase < numClases ; clase++){					
						//Tomamos un valor distinto del atributo.
						int valor = (int) valElemDist[columna].get(k);
						int conteo = (int) modelo[clase][columna].get(valor);
						entropias [columna] -= conteo*log2(conteo);
						sumaValores += conteo;
					}
					entropias [columna] += sumaValores*log2(sumaValores);
				}
				entropias [columna] /= numFilas;
			}	

			//Calculamos la Ganancia de informacion
			int tamañoColum = entropias.length;
			double [] ganancias = new double[tamañoColum];
			for (int i = 0; i < ganancias.length; i++){
				ganancias[i] = cantidadInfo - entropias[i];
			}
			
			//buscamos la clase con mayor ganancia
			int atributoEliminar= 0;
			double mayor= ganancias[0];
			for (int i = 1; i < ganancias.length; i++){
				if(mayor < ganancias[i]){ 
					atributoEliminar= i;
					mayor= ganancias[i];
				}
			}
			
			//Informacion del atributo a eliminar
			int dato = entrenamiento[0][atributoEliminar];
			String tipo = "Atributo";
			ArrayList<Integer> hijos = valElemDist[atributoEliminar];
			
			//Se crea el nodo en el arbol
			Nodo atributo = new Nodo();
			atributo.setDato(dato);
			atributo.setTipo(tipo);
			atributo.setHijos(hijos,"Valor");
			
			//se crean las particiones del atributo
			for (int i = 0; i < numElemDist[atributoEliminar]; i++) {
				int valor = (int) valElemDist[atributoEliminar].get(i);		
				//En la particion solo que consideran las filas con el valor.
				int [][] particion= crearPartion(entrenamiento,valor,atributoEliminar);
				//se borra el atributo de la particion.
				particion = borrarColumna(particion,atributoEliminar);				
				
				//Se genera la recursividad, se realiza lo mismo para la particion.
				Nodo subArbol = iD3(particion);		
				
				//Pegamos el subArbol al nodo atributo
				atributo.getHijos().get(i).setHijo(subArbol);
			}
			//juntamos los arboles
			return atributo;
		}
	}
	
	private int getClasePromedio(int[][] entrenamiento, int numFila, int claseColumna) {
		ArrayList<Integer> valores = dameValorDist(entrenamiento,claseColumna);
		int mayor = 0;
		int clase = 0;
		for (int valor : valores){
			int contar = 0;
			for(int fila = 1; fila <numFila ; fila++){
				if(entrenamiento[fila][claseColumna] == valor) contar++;
			}
			if(contar > mayor){
				mayor = contar;
				clase = valor;
			}
		}
		return clase;
	}

	private int[][] crearPartion(int[][] entrenamiento, int valor, int atributoEliminar) {
		int numfilasValor=0;
		int numFilas= entrenamiento.length;
		int numColumnas= entrenamiento[0].length;
		
		for (int i = 0; i < numFilas; i++)
			if(entrenamiento[i][atributoEliminar] == valor)
				numfilasValor++;
		//Encabezados por eso es el +1
		int [][] particion = new int [++numfilasValor][numColumnas];
		//copiamos los encabezados
		particion[0]= entrenamiento[0];
	
		for (int i = 1; i < numFilas ; i++){			
				if(entrenamiento[i][atributoEliminar] == valor)
					particion[--numfilasValor]= entrenamiento[i];
		}				
		return particion;
	}
	
	private int[][] borrarColumna(int[][] entrenamiento, int atributoEliminar){
		int numFilas= entrenamiento.length;
		int numColumnas= entrenamiento[0].length;
		int [][] subEntrenamiento = new int [numFilas][numColumnas-1];

		for (int fila = 0; fila < numFilas; fila++) {
			for (int columna = 0; columna < numColumnas; columna++) {
				if(columna < atributoEliminar)
					subEntrenamiento[fila][columna] = entrenamiento[fila][columna];
				else{
					if(columna>atributoEliminar){
						subEntrenamiento[fila][columna-1] = entrenamiento[fila][columna];
					}
				}
			}
		}
		return subEntrenamiento;
	}
	
	private void conteo(int [][] entrenamiento, ArrayList[] valElemDist, int[] numElemDist, HashMap[][] modelo){
		int numFilas= entrenamiento.length;
		int numColumnas= entrenamiento[0].length;
		int columnaClase= numColumnas-1;
		int numClases= numElemDist[columnaClase];
		//Comenzamos con el conteo de elementos
		for (int clase = 0; clase < numClases ; clase++){
			for (int columna = 0; columna < numColumnas; columna++) {
				//Creamos un HashMap para cada atributo de cada clase.
				modelo[clase][columna] = new HashMap <Integer, Integer>();
				//Permitira acumular el conteo de valores de cada atributo de cada clase.
				for (int k = 0; k < numElemDist[columna]; k++) {
					//Sacaremos el conteo de cada valor distinto de cada atributo de cada clase.
					//Tomamos un valor distinto del atributo.
					int valor = (int)valElemDist[columna].get(k);
					//Tomamos el valor de clase.
					int valorClase= (int)valElemDist[columnaClase].get(clase);
					//Hacemos el conteo
					int conteo= contar(entrenamiento,columna,valor,valorClase);
					//Agregamos el valor al HashMap con su respectivo conteo.
					modelo[clase][columna].put(valor, conteo);
					
				}
			}
		}
	}

	//Condicion de parada para el metodo recursivo
	private boolean sonClasesIguales(int[][] subEntrenamiento) {
		int numFilas= subEntrenamiento.length;
		int columnaClase= subEntrenamiento[0].length-1;
		//tomamos el primer valor para compararlo con todas las clases restantes
		int tomaClase = subEntrenamiento[1][columnaClase];
		for (int fila = 2; fila < numFilas; fila++){
			if(tomaClase != subEntrenamiento[fila][columnaClase] && columnaClase != 0)
				return false;
		}
		return true;
	}

	@Override
	public int clasificaInstancia(int[] intancia) {
		int respuesta = recorrerArbol(arbol,intancia);
		return respuesta;
	}
	
	private int recorrerArbol(Nodo nodo, int[] intancia){
		int numHijos = nodo.getHijos().size();
		if( numHijos > 0){
			if(nodo.getTipo().equals("Atributo")){
				int indice = buscaIndice(nodo.getDato());
				int valor = intancia[indice];
				for (int i = 0; i < numHijos ; i++)
					if(nodo.getHijos().get(i).getDato() == valor)
						return recorrerArbol(nodo.getHijos().get(i),intancia);
			}else 
				if(nodo.getTipo().equals("Valor"))
					return recorrerArbol(nodo.getHijos().get(0),intancia);
		}else 
			if(nodo.getTipo().equals("Clase")){
				return nodo.getDato();
			}
		return -1;
	}

	private int buscaIndice(int dato) {
		for (int i = 0; i < prueba[0].length; i++) {			
			if(prueba[0][i] == dato) return i;
		}		
		return -1;
	}
}
