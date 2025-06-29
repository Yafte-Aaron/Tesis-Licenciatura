package modelo;

import javax.swing.JOptionPane;

public class Nearest_Neightbor extends Clasificador{
	
	private int kVecinos;
	int [][] entrenamiento;
		
	public Nearest_Neightbor(int k){
		kVecinos =  k;
	}
	
	public void inicializarDatos(int [][] base){
		entrenamiento= base;
		//Declaracion e Inicializacion de datos.
		//Dimensiones
		numFilas = entrenamiento.length;
		numColumnas = entrenamiento[0].length;
		//Variable que indica el indice del atributo clase.
		columnaClase = numColumnas-1;
		numClases = dameValorDist(entrenamiento,columnaClase).size();
		//K-Vecinos que deseamos considerar.
		
		setKVecinos(kVecinos);
	}

	public int clasificaInstancia(int[] instancia){
		//Calculamos las distancias.
		double [][] distancias = calculaDistancias(instancia);
		//Ordenamos las distancias.
		quickSort(distancias,0,numFilas-2);
		//Se Vota por una clase.
		int ganadora = votacion(distancias,kVecinos);
		return ganadora;
	}

	private double [][] calculaDistancias(int[] instancia){
		int resta=0;
		double radicando;
		//distancia[0][...]: distancia de la instancia prueba.
		//distancia[1][...]: valor de clase de la instancia.
		double [][] distancia = new double [2][numFilas-1];
		for (int fila = 1; fila < numFilas; fila++){
			radicando = 0.0;
			for (int columna = 0; columna < columnaClase; columna++) {
				resta = instancia[columna] - entrenamiento[fila][columna];
				radicando += Math.pow(resta, 2);
			}
			distancia[0][fila-1] = Math.sqrt(radicando);
			distancia[1][fila-1] = entrenamiento[fila][columnaClase];
		}
		return distancia;
	}
	
	private int votacion(double[][] distancia, int kVecinos){
		int conteo;
		double valorClase;
		//Valores minimos
		int conteMayor = 0;
		double claseMayor = -1.0;

		for (int i = 0; i < kVecinos; i++) {
			conteo = 0;
			valorClase = distancia[1][i];
			if(valorClase == -1.0) continue;
			for (int j = i; j < kVecinos; j++) 
				if (valorClase == distancia[1][j]) {
					conteo ++;
					distancia[1][j] = -1;
				}
			
			if (conteMayor <= conteo) {
				claseMayor = valorClase;
				conteMayor = conteo;
			}
		}
		return (int)claseMayor;
	}
	
	public void setKVecinos(int vecinos){
		if(vecinos>0 && vecinos < numFilas-1)
			kVecinos = vecinos;
		else{
			JOptionPane.showMessageDialog(null,
			"El valor de K-Vecinos no esta en intervalo adecuado."
			+ "\nTiene que ser: \n"
			+"- mayor a 0\n- menor a "+(numFilas-1)+
			".\nNOTA: Se pondra el valor por default: K= 1",
			"ERROR",JOptionPane.ERROR_MESSAGE);
			kVecinos=1;
		}
	}
}
