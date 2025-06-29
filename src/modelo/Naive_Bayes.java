package modelo;

import java.util.HashMap;
import java.util.ArrayList;

public class Naive_Bayes extends Clasificador{
	//Variables Globales
	boolean laplace;
	int numElemDist[];
	int denominador [][];
	ArrayList<Integer> []valElemDist;
	HashMap <Integer,Double> modelo [][];

	public void inicializarDatos(int [][] entrenamiento){
		//Comenzamos sacando los datos necesarios
		preparacionDatos(entrenamiento);
		//Crear el modelo Probabilistico.
		construirClasificador(entrenamiento);
	}
	
	private void preparacionDatos(int [][] entrenamiento){
		//Declaracion e Inicializacion de datos.
		//Dimensiones
		numFilas= entrenamiento.length;
		numColumnas= entrenamiento[0].length;
		//Variable que indica el indice del atributo clase.
		columnaClase= numColumnas-1;
		//Arreglo que guardara el numero de elementos distintos de cada columna.
		numElemDist = new int [numColumnas];
		//Arreglo de listas que guardara todos los valores distintos de cada columna.
		valElemDist = new ArrayList[numColumnas];
		//Obtenemos los elementos diferentes de cada atributo.
		for (int columna = 0; columna < numColumnas; columna++){
			valElemDist[columna] = dameValorDist(entrenamiento,columna);
			numElemDist[columna] = valElemDist[columna].size();
		}
		//Obtenemos el numero de clases
		numClases= numElemDist[columnaClase];
		//HashMap que representara el modelo probabilistico.
		modelo = new HashMap [numClases][numColumnas];
		//Denominador para sacar las probabilidades de la formula de Naive bayes.
		denominador= new int [numClases][numColumnas];
	}

	public void construirClasificador(int [][] entrenamiento){
		//Calculamos el conteo de elementos.
		conteo(entrenamiento);
		//Realizamos la correccion de laplace, si es necesario.
		if(laplace) correccionLaplace();
		//Calculamos las probabilidades. 
		calculaProbabilidad();
	}

	private void conteo(int [][] entrenamiento){
		//Comenzamos con el conteo de elementos
		for (int clase = 0; clase < numClases ; clase++){
			for (int columna = 0; columna < numColumnas; columna++) {
				//Creamos un HashMap para cada atributo de cada clase.
				modelo[clase][columna] = new HashMap <Integer, Double>();
				//Permitira acumular el conteo de valores de cada atributo de cada clase.
				int suma= 0;				
				for (int k = 0; k < numElemDist[columna]; k++) {
					//Sacaremos el conteo de cada valor distinto de cada atributo de cada clase.
					//Tomamos un valor distinto del atributo.
					int valor = valElemDist[columna].get(k);
					//Tomamos el valor de clase.
					int valorClase= valElemDist[columnaClase].get(clase);
					//Hacemos el conteo
					double conteo= contar(entrenamiento,columna,valor,valorClase);
					//Agregamos el valor al HashMap con su respectivo conteo.
					modelo[clase][columna].put(valor, conteo);
					//Suma parcial de conteo que cumple con el valor y clase especificada.
					suma += conteo;
				}
				//Distincion entre el conteo a priori y el conteo parcial de los atributo clase.
				if (columna == columnaClase)
					 denominador[clase][columna] = numFilas-1; 
				else denominador[clase][columna] = suma;
			}
		}
	}
	
	private void correccionLaplace(){
		//Incrementamos uno a todos los valores del HashMap, es la correccion suave de laplace.
		for (int clase = 0; clase < numClases ; clase++){
			//No se incrementan las Probabilidades a priori.
			for (int columna = 0; columna < columnaClase; columna++){
				int suma= 0;
				for (int k = 0; k < numElemDist[columna]; k++) {
					int llave= valElemDist[columna].get(k);
					double valorActual= modelo[clase][columna].get(llave);
					//Sumamos y actualizamos los valores del HashMap
					modelo[clase][columna].replace(llave, ++valorActual);	
					suma += valorActual;
				}
				//Actualizamos el denominador
				denominador[clase][columna] = suma;
			}
		}			
	}

	public void calculaProbabilidad(){
		for (int clase = 0; clase < numClases ; clase++){
			for (int columna = 0; columna < numColumnas; columna++) {
				for (int k = 0; k < numElemDist[columna]; k++){
					int llave= valElemDist[columna].get(k);
					double valorActual= modelo[clase][columna].get(llave);
					//Aplicacion de Logaritmo Natural, en lugar de la division.
					double probabilidad= Math.log(valorActual)-Math.log(denominador[clase][columna]);
					modelo[clase][columna].replace(llave, probabilidad);
				}
			}
		}
	}

	public int clasificaInstancia(int[] prueba){
		int numAtributos= prueba.length;
		//Arreglo; Guarda las probabilidades de cada clase.
		double probabilidades []= new double [numClases];
		//Suministramos las probabilidades del modelo probabilistico.
		for(int clase= 0; clase< numClases; clase++){
			//comenzamos con 1 para no tener problemas en la multiplicacion.
			probabilidades[clase]= 0.0;
			for(int columna= 0; columna<numAtributos; columna++){
				//sacamos los valores de la instancia
				int llave= prueba[columna];
				//Se controla cuando el valor de la instancia no se encuentra en el modelo.
				if(modelo[clase][columna].containsKey(llave)){
					//Con ayuda del Logaritmo Natural sumamos las probabilidades condicionales P(a|C) , P(b|C) ... 
					probabilidades[clase] += modelo[clase][columna].get(llave);
				}else{
					//Si no se encuentra el valor en el modelo, se calcula su probabilidad; 1/(total+1)
					//Ln(1)-Ln(total+1) = 0-Ln(total+1) = -Ln(total+1)
					probabilidades[clase] -= Math.log((denominador[clase][columna]+1));
				}
			}
			//Con ayuda del Logaritmo Natural sumamos las probabilidades a priori de la clase P(C)					
			int valor = valElemDist[columnaClase].get(clase);
			probabilidades[clase] += modelo[clase][columnaClase].get(valor);
		}

		//Pedimos la clase con mayor probabilidad.
		int indiceMAX= maxProbabilidad(probabilidades);

		//con el indice de la Clase mas Probable sacamos la clase.	
		int ganadora = valElemDist[columnaClase].get(indiceMAX);

		return ganadora;
	}

	public int maxProbabilidad(double [] probabilidades){
		//indicamos por defaul, el primero es el mayor.
		int indiceMAX= 0;
		double mayor= probabilidades[0];
		//Comenzamos a buscar la clase con mayor probabilidad
		for (int i = 1; i < probabilidades.length; i++){
			if(mayor < probabilidades[i]){ 
				indiceMAX= i;
				mayor= probabilidades[i];
			}
		}
		return indiceMAX;
	}
}
