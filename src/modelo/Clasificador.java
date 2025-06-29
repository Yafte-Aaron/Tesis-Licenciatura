package modelo;

import java.util.ArrayList;
import java.text.DecimalFormat;

public abstract class Clasificador {
	//Variables 
	int numClases;
	int numFilas;
	int numColumnas;
	int columnaClase;
	int [][] prueba;
	
	//Metodos Abstractos
	public abstract int clasificaInstancia(int[] prueba);
	public abstract void inicializarDatos (int [][] entrenamiento);
	
	//Metodos Reutilizables
	public int [] clasificar(int [][] prueba){
		this.prueba = prueba;
		//El -1, para no contar los encabezados.
		int numInstancias= prueba.length-1;
		//Arreglo; guardara la clase predicha por el clasificador de cada instancia.
		int [] respuestaClase = new int [numInstancias];
		//Envia una por una las instancias(Filas) del conjunto prueba.
		for (int fila = 0; fila < numInstancias; fila++)
			respuestaClase[fila] = clasificaInstancia(prueba[fila+1]);

		//Retornamos la respuesta. 
		return respuestaClase;	
	}
	
	public static boolean estaElemento(ArrayList<Integer> lista, int valor){
		for (Integer integer : lista) 
			if(valor == integer) 
				return true;
		return false;
	}
	
	public static ArrayList<Integer> dameValorDist(int entrenamiento [][],int columna){
		int numFilas= entrenamiento.length;
		//Lista que guardara los valores de cada Atributo.
		ArrayList<Integer> buffer = new ArrayList<Integer>();
		//Agregamos el primero para comenzar a comparar.
		buffer.add(entrenamiento[1][columna]);		
		for (int fila = 2; fila < numFilas; fila++)				
			if(!estaElemento(buffer,entrenamiento[fila][columna]))
				buffer.add(entrenamiento[fila][columna]);
		return buffer;
	}
	
	public String exactitud(int[][] numericaBD, int[] resultado){
		int valorRes;
		int valorEntren;
		double correctos=0;
		int total= resultado.length;
		int columnaClase= numericaBD[0].length-1;
		//comenzamos a comparar. 
		for (int i = 0; i < total; i++) {
			valorRes= resultado[i];
			valorEntren= numericaBD[i+1][columnaClase];
			if(valorRes == valorEntren)
				correctos++;
		}
		correctos /= total;
		DecimalFormat decimales = new DecimalFormat("0.00");
		
		return decimales.format(correctos*100);
	}
	
	protected void quickSort(double [][] a,int ini, int fin){
        double pivote = a[0][ini];
        double pivoteClase= a[1][ini];
        int i=ini;
        int j=fin;
        double aux;
        double auxClase;
        
        while(i<j){
           while(a[0][i] <= pivote && i<j) i++;
           while(a[0][j] > pivote) j--;
           if(i<j){
        	   //Cambiamos valores
               aux = a[0][i];
               auxClase = a[1][i];
               a[0][i]=a[0][j];
               a[1][i]=a[1][j];
               a[0][j]=aux;
               a[1][j]=auxClase;
           }
        }
        a[0][ini]=a[0][j];
        a[1][ini]=a[1][j];
        a[0][j]=pivote;
        a[1][j]=pivoteClase;
        if(ini<j-1){
            quickSort(a,ini,j-1);
        }
        if(j+1<fin){
            quickSort(a,j+1,fin);
        }
    }
	
	protected double log2(int argumento){
		double log;
		if(argumento>0){
			log = Math.log(argumento) / Math.log(2);
		}else 
			log = 0;
		
		return log;
	}
	
	protected int contar(int [][] entrenamiento, int columna, int valor, int valorClase) {
		int numFilas= entrenamiento.length;
		int numColumnas= entrenamiento[0].length;
		int columnaClase= numColumnas-1;
		int contador= 0;
		for (int fila = 0; fila < numFilas; fila++) {
			//Cuenta si encuentra el valor, dependiendo de la clase que se encuentra.
			if(entrenamiento[fila][columna] == valor && valorClase == entrenamiento[fila][columnaClase]) contador++;			
		}
		return contador;
	}
}
