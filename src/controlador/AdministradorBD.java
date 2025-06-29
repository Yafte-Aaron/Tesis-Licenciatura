package controlador;

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.JFileChooser;

import modelo.Clasificador;

public class AdministradorBD {
	
	//Conjunto Prueba
	public int[][] pruebaNum;
	public String [][] prueba;
	
	//Conjunto Entrenamiento
	public int [][] entrenamientoNum;
	public String [][] entrenamiento;
	//Datos
	private int columnas;
	private int renglones;
	String actual[][];

//Metodo que creara la BD, la bandera es para hacer distincion si es prueba(false) o entrenamiento(true).
public  boolean cargaArchivo(String ruta, boolean bandera){
		
		try{
			File cargaArchivo = new File(ruta);
			
			//Leemos 3 veces el archivo.
			Scanner archivo = new Scanner(cargaArchivo);
			Scanner scannerRenglones = new Scanner(cargaArchivo);
			Scanner scannerColumnas = new Scanner(cargaArchivo);
			
			//Obtenemos el numero de columnas y renglones del archivo.
			getColumnas(scannerColumnas);
			getRenglones(scannerRenglones);
			
			//Creamos las dos matricez necesarias; String y entera
			String [][] matriz =  new String [renglones][columnas];
			int [][] matrizNum = new int [renglones][columnas];
			
			//Llenamos la matriz con los datos del archivo.
			llenaMatriz(archivo,matriz);
						
			//Sacamos la matriz String a una matriz entera.
			convertirASCII(matriz,matrizNum);
			
			//Actualizamos base de datos
			actualizarBaseDatos(matriz,matrizNum,bandera);			
			//Devolvemos true si; se cargo el archivo correctamente. 
			return true;
		}
		catch(Exception e){
			System.err.println("ERROR: Ocurrio un problema con el archivo");
			return false;
		}	
	}


	public void actualizarBaseDatos(String [][] matriz,int [][] matrizNum ,boolean bandera) {
		//Guardamos la matriz String para mostrarla en la GUI.
		actual= matriz;
		//Con ayuda de la bandera, guardamos la matriz creada en su lugar  correspondiente
		if (bandera){
			entrenamiento = matriz;
			entrenamientoNum = matrizNum;
		} else{
			prueba = matriz;
			pruebaNum = matrizNum;
		}	
	}

//----------------------------------------------------------------------------------------------------------//	
//---------------------------------------DE ARCHIVO A MATRIZ------------------------------------------------//
//----------------------------------------------------------------------------------------------------------//
	public void getRenglones(Scanner ScannerRenglones){
		renglones=0;
		while(ScannerRenglones.hasNextLine()){
			renglones++;
			ScannerRenglones.nextLine();
		}
		ScannerRenglones.close();
	}
	
	public void getColumnas(Scanner ScannerColumnas){
		columnas=0;
		if(ScannerColumnas.hasNextLine()){
			String cadena = ScannerColumnas.nextLine();
			Scanner sl = new Scanner(cadena);
			
			while(sl.hasNext()){
				sl.next();
				columnas++;
			}
			sl.close();
			ScannerColumnas.close();
		}
	}
	
	public void llenaMatriz(Scanner Archivo,String [][]matriz){
		int linea=-1;
		String contenido = "";
		
		while(Archivo.hasNextLine()){
			linea++;
			contenido = Archivo.nextLine();
			Scanner sl = new Scanner(contenido);
			for(int j=0; j<columnas; j++)
				matriz[linea][j]=sl.next();			
			sl.close();
		}		
		Archivo.close();
	}
	
//----------------------------------------------------------------------------------------------------------//	
//---------------------------------------conversiones ASCII O STRING--------------------------------------------------------------//
//----------------------------------------------------------------------------------------------------------//
	public void convertirASCII(String matriz[][], int matrizNum[][]){
		String cadena = "";
		int suma;
		int numFilas = matriz.length;
		int numColum = matriz[0].length;
		
		for(int i=0;i<numFilas;i++){
			for(int j=0;j<numColum;j++){
				suma=0;
				cadena = matriz[i][j];				
				for (int x=0;x<cadena.length();x++){
					suma +=cadena.codePointAt(x);
				}
				matrizNum[i][j]=suma;
			}
		}
	}
	
	public String[][] convertirString(int [] respuesta){
		int valorResp;
		int valorEntren;
		int filas= entrenamientoNum.length;
		int columnaClase= entrenamientoNum[0].length-1;
		String  [][] conversion= new String [respuesta.length][1];		
		//Llenamos toda la matriz con ""
		for (int i = 0; i < respuesta.length; i++) {
			conversion[i][0] = "";
		}
		for (int i = 0; i < respuesta.length; i++){
			//Tomo uno a uno los enteros respuesta.
			valorResp= respuesta[i];
			for (int j = 1; j < filas; j++) {
				valorEntren= entrenamientoNum[j][columnaClase];
				if(valorResp == valorEntren){
					conversion[i][0] = entrenamiento[j][columnaClase];
					//Nos salimos del for.
					j=filas;
				}
			}
		}
		return conversion;
	}
	
	public int [][] getEntrenamientoNum(){
		return entrenamientoNum;
	}
	
	public int [][] getPruebaNum(){
		return pruebaNum;
	}
	
	public String [] getEncabezado(){
		String [] encabezado= new String[columnas];
		for(int i=0; i<columnas; i++)
			encabezado[i]= actual[0][i];
		return encabezado;		
	}
	
	public String [][] getDatos(){		
		String [][] datos= new String[renglones-1][columnas];
			for (int i = 0; i < renglones-1; i++)
				for(int k= 0; k<columnas; k++)
					datos[i][k]= actual[i+1][k];
		return datos;		
	}
	
	public String buscarRuta(){
		//crea el Buscador, para buscar el archivo
		JFileChooser buscador = new JFileChooser();
		buscador.showOpenDialog(buscador);
		String path = buscador.getSelectedFile().getAbsolutePath();
		return path;		
	}

	public ArrayList<String> getclases(String[][] datos) {
		int numFilas= datos.length;
		int numColumas= datos[0].length;
		int columnaClase= numColumas-1;
		//Lista que guardara los valores de cada Atributo.
		ArrayList<String> buffer = new ArrayList<String>();
		//Agregamos el primero para comenzar a comparar.
		buffer.add(datos[0][columnaClase]);		
		for (int fila = 1; fila < numFilas; fila++)				
			if(!estaElemento(buffer,datos[fila][columnaClase]))
				buffer.add(datos[fila][columnaClase]);
		return buffer;
	}	
	
	public static boolean estaElemento(ArrayList<String> lista, String valor){
		for (String cadena : lista) 
			if(valor.equals(cadena) )
				return true;
		return false;
	}


	public double[] getporcentajes(String[][] datos, ArrayList<String> clases) {
		int contador;
		int numFilas= datos.length;
		int numClases= clases.size();
		int numColumas= datos[0].length;
		int columnaClase= numColumas-1;
		double [] porcentajes = new double [numClases];
		
		for (int clase = 0; clase < numClases; clase++) {
			contador=0;
			for (int fila = 0; fila < numFilas; fila++) {
				if(clases.get(clase).equals(datos[fila][columnaClase]))
					contador++;
			}
			porcentajes[clase] = contador*100.0/numFilas;
		}
		return porcentajes;
	}
	
	public void setColumnas(int numcolumnas){
		columnas = numcolumnas;
	}
	public void setFilas(int numfilas){
		renglones = numfilas;
	}	
}
