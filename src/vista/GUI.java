package vista;

import modelo.*;
import controlador.AdministradorBD;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.JMenu;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JTable;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumnModel;
import javax.swing.JScrollPane;
import org.jfree.util.Rotation;
import javax.swing.JOptionPane;
import java.text.DecimalFormat;
import org.jfree.chart.plot.Plot;
import java.awt.event.ActionEvent;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.ChartFactory;
import java.awt.event.ActionListener;
import org.jfree.chart.plot.PiePlot3D;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import org.jfree.data.general.DefaultPieDataset;

public class GUI extends JFrame{
	
	//Variables: Ventana
	ImageIcon imagen;
	ImageIcon imagenGrafico;
	JMenu editarMenu;
	JMenu archivoMenu;
	JMenuBar barraMenu;
	JPanel panelGrafico;
	JPanel PanelInfoClasifi;
	JPanel PanelOpcionTest;
	JButton botonLimpiar;
	JButton botonPermutarDatos;
	JButton botonCargarEntrenamiento;
	JButton botonClasifica;
	JButton botonCargaprueba;
	JButton botonPrepararPorce;
	JButton botonCargarEntPrue;
	JButton menuAbrirEntrena;
	JTextField cajaTiempo;
	JTextField cajaPartiCruza;
	JTextField cajaPartiPorce;
	JTextField cajaKVecinos;
	JTextField cajaExactitud;
	JMenuItem menuSalir;
	JTable tablaPrueba;
	JTable tablaResultados;
	JTable tablaEntrenamiento;
	JTable tablanumeracionEntren;
	JTable tablanumeracionPrueba;
	JLabel tituloPrueba;
	JLabel tituloTiempo;
	JLabel tituloKVecinos;
	JLabel tituloExactitud;
	JLabel contenedorImagen;
	JLabel tituloporcentaje;
	JLabel tituloParticiones;
	JLabel titulosimbolopor;
	JLabel tituloMantenerOrden;
	JLabel contenedorImagenGrafico;
	JLabel tituloClasificador;
	JLabel tituloEntrenamiento;	
	JScrollPane scrollTablaPrue;
	JScrollPane scrollTablaEntre;
	JScrollPane scrollClasificacion; 
	JScrollPane scrollNumeEntre;
	JScrollPane scrollNumePrueba;
	JComboBox <String> comboTestOpcion;
	JComboBox <String> comboClasificador;
	ButtonGroup radioButonGrupo;
	int [][] clase_verdadera_prueba;
	
	//Grafico
	JFreeChart chart;
	PiePlot3D pieplot3d;
	ChartPanel chartPanel;
	DefaultPieDataset defaultpiedataset;
	
	//Variables: Control
	boolean hayPrueba;
	boolean hayEntrenamiento;
	AdministradorBD administradorBD;
	
	//Validacion cruzada	
	int [] respuestaValidacion;
	String [][] entrenamientoValidacion;
	int index;
	int tiempo_total;
	
	public GUI(){
		propiedadesVentana();
		propiedadesMenu();
		propiedadesImagen();
		propiedadesPanel();
		propiedadesBoton();
		propiedadesScroll();
		propiedadesTitulos();
		propiedadesComboBox();
		propiedadesCajasTexto();		
		propiedadesAdministradorBD();
		//Eventos
		añadirEscuchadores();
	}
//----------------------------------------------------------------------------------------------------------//	
//---------------------------------------PROPIEDADES DE INTERFACE--------------------------------------------//
//----------------------------------------------------------------------------------------------------------//

	private void propiedadesVentana(){
		//Titulo
		setTitle("Proyecto Terminal: Clasificadores");
		//Quito el organizador por default(borderLayout)
		setLayout(null);
		//Dimension de la pantalla de la computadora
		Toolkit t = Toolkit.getDefaultToolkit();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = screenSize.width;
		int alto = screenSize.height;
		//tamaño(colums,filas)
		this.setSize(ancho, alto-40); //40: ajuste de mi pantalla
		//Centramos la ventana
		this.setLocationRelativeTo(null);
		//Definimos Color a la ventana
		this.getContentPane().setBackground(new Color(190,199,193));
	}
	
	private void propiedadesMenu(){		
		barraMenu = new JMenuBar();			
		archivoMenu = new JMenu("Archivo");
		menuSalir = new JMenuItem("Salir  ");
		
		setJMenuBar(barraMenu);
		barraMenu.add(archivoMenu);
		archivoMenu.addSeparator();
		archivoMenu.add(menuSalir);
	}
	
	private void propiedadesPanel(){		
		panelGrafico = new JPanel ();
		PanelOpcionTest = new JPanel();
		PanelInfoClasifi = new JPanel();
		//Quito el organizador por default(borderLayout)
		PanelOpcionTest.setLayout(null);
		PanelInfoClasifi.setLayout(null);
		//Defino el color de fondo
		panelGrafico.setBackground(new Color(190,199,193));
		PanelOpcionTest.setBackground(new Color(190,199,193));
		PanelInfoClasifi.setBackground(new Color(190,199,193));
		//Defino Posicion(colums,fila),Tamaño(colum,fila)
		panelGrafico.setBounds(890, 30, 450,400);		
		PanelOpcionTest.setBounds(35, 400, 210,240);
		PanelInfoClasifi.setBounds(1000, 450, 265,190);
		//Borde a los paneles
		//Borde con titulo
		Border bordeJPanel = new TitledBorder(new EtchedBorder(), "INFORMACION DE CLASIFICACION");
		PanelInfoClasifi.setBorder(bordeJPanel);
		Border bordeJPanelOptionTest = new TitledBorder(new EtchedBorder(), "OPCIONES TEST");
		PanelOpcionTest.setBorder(bordeJPanelOptionTest);
		add(panelGrafico);
		add(PanelOpcionTest);
		add(PanelInfoClasifi);
	}
	
	private void propiedadesBoton(){
		botonClasifica = new JButton("CLASIFICA");	
		botonCargarEntPrue = new JButton("Cargar");
		botonCargaprueba = new JButton("Carga prueba");
		botonPermutarDatos = new JButton("Permutar celdas");
		botonCargarEntrenamiento = new JButton("Carga Entrenamientiento");
		botonPrepararPorce = new JButton("Preparar");
		//Defino Posicion(colums,fila),Tamaño(colum,fila)
		botonClasifica.setBounds(60,280,150,40);
		botonCargarEntPrue.setBounds(30,150,150,40);
		botonCargaprueba.setBounds(30,150,150,40);
		botonPermutarDatos.setBounds(750,5,130,30);
		botonCargarEntrenamiento.setBounds(550,5,180,30);
		botonPrepararPorce.setBounds(30,85,150,40);
		add(botonClasifica);
		add(botonPermutarDatos);
		add(botonCargarEntrenamiento);
		PanelOpcionTest.add(botonCargaprueba);	
	}

	private void propiedadesImagen() {
		//Escala de Imagen deseada.
		int largo= 70;
		int Ancho= 180;		
		//Buscamos la imagen
		imagen= new ImageIcon(getClass().getResource("uamk.png"));		
		//Ajustar tamaño de imagen al contenedor.
		Image conversion = imagen.getImage();
		Image tamaño = conversion.getScaledInstance(Ancho, largo,Image.SCALE_SMOOTH);
		imagen = new ImageIcon(tamaño);
		//Creamos y agregamos la imagen al contenedor.
		contenedorImagen = new JLabel(imagen);		
		//Defino Posicion(colums,fila),Tamaño(colums,fila).
		contenedorImagen.setBounds(45, 40, Ancho,largo);		
		add(contenedorImagen);
		
		//Imagen de Grafico
		//Escala de Imagen deseada.
		int largoGrafico= 250;
		int AnchoGrafico= 250;	
		//Buscamos la imagen
		imagenGrafico= new ImageIcon(getClass().getResource("graficos.png"));		
		//Ajustar tamaño de imagen al contenedor.
		Image conversionGrafico = imagenGrafico.getImage();
		Image tamañoGrafico = conversionGrafico.getScaledInstance(AnchoGrafico, largoGrafico,Image.SCALE_SMOOTH);
		imagenGrafico = new ImageIcon(tamañoGrafico);
		//Creamos y agregamos la imagen al contenedor.
		contenedorImagenGrafico = new JLabel(imagenGrafico);		
		//Defino Posicion(colums,fila),Tamaño(colums,fila).
		contenedorImagenGrafico.setBounds(1000, 100, AnchoGrafico,largoGrafico);
		add(contenedorImagenGrafico);
	}
	
	private void propiedadesScroll(){
		//creamos el panel Scroll
		scrollTablaPrue = new JScrollPane();
		scrollTablaEntre = new JScrollPane();
		scrollClasificacion = new JScrollPane();
		scrollNumeEntre = new JScrollPane();
		scrollNumePrueba = new JScrollPane();
		
		//posicion(colms,fila) y tamaño(colums,fila)
		scrollTablaEntre.setBounds(new Rectangle(323,40,563,280));
		scrollTablaPrue.setBounds(new Rectangle(323,360,475,280));
		scrollClasificacion.setBounds(new Rectangle(800,360,90,280));
		scrollNumeEntre.setBounds(new Rectangle(270,40,50,280));
		scrollNumePrueba.setBounds(new Rectangle(270,360,50,280));
		
		//añadiendo a la ventana.
		add(scrollTablaPrue);
		add(scrollTablaEntre);
		add(scrollClasificacion);
		add(scrollNumeEntre);
		add(scrollNumePrueba);
	}
	

	
	private void propiedadesTitulos(){		
		titulosimbolopor = new JLabel("%");
		tituloTiempo = new JLabel("Tiempo:");
		tituloExactitud = new JLabel("Exactitud:");
		tituloPrueba = new JLabel("CONJUNTO PRUEBA");
		tituloporcentaje = new JLabel("Porcentaje:");
		tituloParticiones = new JLabel("Particiones:");
		tituloClasificador = new JLabel("CLASIFICADORES");
		tituloMantenerOrden = new JLabel("");
		tituloEntrenamiento = new JLabel("CONJUNTO ENTRENAMIENTO");
		tituloKVecinos = new JLabel("¿Cuantos K-vecinos desea considerar?");
		//Defino Posicion(colums,fila),Tamaño(colums,fila)
		tituloTiempo.setBounds(30,35,300,30);
		tituloPrueba.setBounds(270,332,200,30);
		tituloKVecinos.setBounds(20,240,500,30);
		tituloExactitud.setBounds(30,105,300,30);
		tituloporcentaje.setBounds(25,130,150,40);
		tituloParticiones.setBounds(25,145,150,40);
		tituloClasificador.setBounds(85,150,200,30);
		tituloEntrenamiento.setBounds(270,10,200,30);
		titulosimbolopor.setBounds(160,130,50,40);
		tituloMantenerOrden.setBounds(25,168,150,40);
		add(tituloPrueba);	
		add(tituloClasificador);
		add(tituloEntrenamiento);
		//Agregamos a su respectivo panel
		PanelInfoClasifi.add(tituloTiempo);
		PanelInfoClasifi.add(tituloExactitud);
	}
	
	private void propiedadesComboBox(){
		//Datos
		DefaultComboBoxModel <String> datos = new DefaultComboBoxModel<String>();
		datos.addElement("Naive Bayes");
		datos.addElement("K Nearest Neighbors");
		datos.addElement("Iterative Dichotomiser 3");
		//Creamos la Lista JComboBox
		comboClasificador = new JComboBox <String> (datos);
		//Defino Posicion(colums,fila),Tamaño(colums,fila).
		comboClasificador.setBounds(37,180,200,30);	
		//Agregamos a la ventana
		add(comboClasificador);
		
		//ComboBox de test option
		//Datos
		DefaultComboBoxModel <String> datosTestOption = new DefaultComboBoxModel<String>();
		datosTestOption.addElement("Archivo prueba");
		datosTestOption.addElement("Validacion Cruzada");
		datosTestOption.addElement("Usar Entrenamiento");
		datosTestOption.addElement("Division en porcentaje");
		//Creamos la Lista JComboBox
		comboTestOpcion = new JComboBox <String> (datosTestOption);
		//Defino Posicion(colums,fila),Tamaño(colums,fila).
		comboTestOpcion.setBounds(20,40,170,30);	
		//Agregamos a la ventana
		PanelOpcionTest.add(comboTestOpcion);
		
	}
	
	public void propiedadesCajasTexto(){		
		 cajaTiempo = new JTextField(40);
		 cajaKVecinos = new JTextField(40);
		 cajaExactitud = new JTextField(40);
		 cajaPartiCruza = new JTextField(40);
		 cajaPartiPorce = new JTextField(40);
		 //Defino Posicion(colums,fila),Tamaño(colums,fila).
		 cajaTiempo.setBounds(50,65,150,30);
		 cajaKVecinos.setBounds(105,280,60,30);
		 cajaExactitud.setBounds(50,135,150,30);
		 cajaPartiCruza.setBounds(100,150,50,30);
		 cajaPartiPorce.setBounds(100,135,50,30);
		 //Inmutable por teclado
		 cajaTiempo.setEditable(false);
		 cajaExactitud.setEditable(false);
		 //Las agregamos a los paneles correspondiente
		 PanelInfoClasifi.add(cajaTiempo);		 
		 PanelInfoClasifi.add(cajaExactitud);
	}
	
	private void propiedadesAdministradorBD(){
		 //Se crea el Administrador de Base de datos
		 administradorBD = new AdministradorBD();
	}
	
	
//----------------------------------------------------------------------------------------------------------//	
//---------------------------------------GESTION DE EVENTOS--------------------------------------------//
//----------------------------------------------------------------------------------------------------------//
	private void añadirEscuchadores(){
		//Evento de ComboBox
		comboClasificador.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){	
				if (comboClasificador.getSelectedIndex() == 1) {
					add(cajaKVecinos);
					add(tituloKVecinos);
					botonClasifica.setBounds(60,330,150,40);
					add(botonClasifica);
					repaint();
				}else{
					remove(cajaKVecinos);
					remove(tituloKVecinos);
					//Lo dejamos como estaba
					botonClasifica.setBounds(60,270,150,40);
					add(botonClasifica);
					repaint();
				}
			}
		});
		
		//Evento de ComboBox de option test
				comboTestOpcion.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e){	
						if (comboTestOpcion.getSelectedIndex() == 0) {
							//cargar prueba
							PanelOpcionTest.add(botonCargaprueba);
							repaint();
						}else{
							PanelOpcionTest.remove(botonCargaprueba);
							repaint();
						}
						if (comboTestOpcion.getSelectedIndex() == 1) {
							//validacion cruzada
							PanelOpcionTest.add(cajaPartiCruza);
							PanelOpcionTest.add(tituloParticiones);
							repaint();
						}else{
							PanelOpcionTest.remove(cajaPartiCruza);
							PanelOpcionTest.remove(tituloParticiones);
							repaint();
						}
						if (comboTestOpcion.getSelectedIndex() == 2) {
							//Usar entrenamiento
							PanelOpcionTest.add(botonCargarEntPrue);
							repaint();
						}else{
							PanelOpcionTest.remove(botonCargarEntPrue);
							repaint();
						}
						if (comboTestOpcion.getSelectedIndex() == 3) {
							//Division de porcentaje
							PanelOpcionTest.add(cajaPartiPorce);
							PanelOpcionTest.add(tituloporcentaje);
							PanelOpcionTest.add(titulosimbolopor);
							PanelOpcionTest.add(tituloMantenerOrden);
							PanelOpcionTest.add(botonPrepararPorce);
							repaint();
						}else{
							PanelOpcionTest.remove(cajaPartiPorce);
							PanelOpcionTest.remove(tituloporcentaje);
							PanelOpcionTest.remove(titulosimbolopor);
							PanelOpcionTest.remove(tituloMantenerOrden);
							PanelOpcionTest.remove(botonPrepararPorce);
							repaint();
						}
					}
				});
		
////////////////////////////////////////////////////////////////////////////
		//Evento de Boton		
		botonClasifica.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){	
				//Si la opcion de validacion cruzada esta activa				
				if(comboTestOpcion.getSelectedIndex() != 1){
					accionClasificar();
				}
				else{// Accion de validacion cruzada
					if(hayEntrenamiento){
						String cadena = cajaPartiCruza.getText();
						if(!cadena.equals("")){			
							//Pedimos el entrenamiento
							entrenamientoValidacion = copiarArreglo(administradorBD.entrenamiento);							
							respuestaValidacion = new int [administradorBD.entrenamiento.length-1];

							//Vamos a mostrar el conjunto prueba y conjunto entrenamiento
							//mostramos Prueba

							String [][] prueba = administradorBD.entrenamiento;
							prueba = quitarColumaClase(prueba);
							//Creamos y actualizamos la Base de Datos Numerica
							int numFilas = prueba.length;
							int numColum = prueba[0].length;				
							int [][] pruebaNumer = new int [numFilas][numColum];
							administradorBD.convertirASCII(prueba,pruebaNumer);
							//Actualizamos base de datos
							administradorBD.actualizarBaseDatos(prueba,pruebaNumer,false);
							hayPrueba = true;
							//mostramos los resultados
							//Creamos la tabla
							//Actualizamo los campos filas y renglones
							administradorBD.setFilas(numFilas);
							administradorBD.setColumnas(numColum);
							//Comenzamos a pedir datos de la matriz actual iniciar lo datos en necesario
							String [][]datos= administradorBD.getDatos();
							String []encabezado= administradorBD.getEncabezado();
							tablaPrueba = new JTable(datos,encabezado);					
							//Le ponemos la tabla adentro.		
							scrollTablaPrue.setViewportView(tablaPrueba);
							//Actualizar Numeracion
							actualizarNum(datos.length,tablanumeracionPrueba,scrollNumePrueba,scrollTablaPrue);	

							///////////////////////////////////////////////////////////////////////////
							//Tomamos la constante de particiones
							int numParticiones = Integer.parseInt(cajaPartiCruza.getText());
							//Arreglo que guardara los tamaños de las particiones
							int tamañosParticiones [] = new int [numParticiones];
							tamañosParticiones = dameTamParticiones(tamañosParticiones,numParticiones,administradorBD.entrenamiento);
							//Se permutan aleatoriamente							
							administradorBD.entrenamiento = filasAleatoria(administradorBD.entrenamiento);//hace 100000 cambios

							//Tabla que contendra las particiones
							String particiones [][][] = new String [numParticiones][][];
							particiones = crearparticiones(particiones,tamañosParticiones,administradorBD.entrenamiento);

							//Guardamos lo titulos
							String [] titulos = administradorBD.entrenamiento[0];

							//Datos del clasificador
							String entrenamientoFinal [][];

							int numFilasPrueba;
							//Comenzamos clasificar cada particion					
							for(int turno = 0; turno < numParticiones ; turno++  ){
								//Llenar Conjunto prueba
								numFilasPrueba = tamañosParticiones[turno]+1;
								prueba = new String [numFilasPrueba][];
								//ponemos los titulos
								prueba[0] = titulos;
								for (int fila = 1; fila < numFilasPrueba; fila++) {
									prueba[fila] = particiones[turno][fila-1];
								}

								//Crear el tamaño del conjunto entrenamiento
								int numFilasEntren = 1;//+1 por los titulos
								for (int i = 0; i < numParticiones; i++) {
									numFilasEntren += tamañosParticiones[i];
								}
								numFilasEntren -= tamañosParticiones[turno];

								//creamos el conjunto entrenamiento
								entrenamientoFinal = new String [numFilasEntren][];
								//ponemos los titulos
								entrenamientoFinal[0] = titulos;
								int indice = 1;
								//Llenamos el conjunto entrenamiento
								for(int j = 0; j < numParticiones ; j++){
									if(j != turno){
										for(int k = 0; k < tamañosParticiones[j] ; k++){
											entrenamientoFinal[indice++] = particiones[j][k];
										}
									}
								}

								//Listo ya tenemos el conjuto prueba y entrenamiento para clasificar
								numFilas = entrenamientoFinal.length;
								numColum = entrenamientoFinal[0].length;
								administradorBD.setFilas(numFilas);
								administradorBD.setColumnas(numColum);
								int [][] matrizNum = new int [numFilas][numColum];
								administradorBD.convertirASCII(entrenamientoFinal,matrizNum);
								//Actualizamos
								administradorBD.actualizarBaseDatos(entrenamientoFinal,matrizNum,true);								

								//Ahora vamos con la prueba								
								prueba = quitarColumaClase(prueba);
								//Creamos y actualizamos la Base de Datos Numerica
								int numFilasPrue = prueba.length;
								int numColumPrue = prueba[0].length;
								int [][] pruebaNum = new int [numFilasPrue][numColumPrue];
								administradorBD.convertirASCII(prueba,pruebaNum);
								//Actualizamos base de datos
								administradorBD.actualizarBaseDatos(prueba,pruebaNum,false);
								administradorBD.setFilas(numFilasPrue);
								administradorBD.setColumnas(numColumPrue);				
								hayPrueba = true;
								//Comenzamos a Clasificar
								accionClasificar();
							}							
							mostrarResultados(respuestaValidacion);
							cajaTiempo.setText(tiempo_total + " milisegundos");							
							//Ya todo acabo, restauramos todo
							tiempo_total = 0;
							index = 0;
							//Actualizamos la base de datos a como estaba antes
							int entrenamientoValidadcionNum[][] = new int[entrenamientoValidacion.length][entrenamientoValidacion[0].length];
							administradorBD.convertirASCII(entrenamientoValidacion, entrenamientoValidadcionNum);
							administradorBD.actualizarBaseDatos(entrenamientoValidacion,entrenamientoValidadcionNum,true);	
							///////////////////////////////////////////////////////////
							String [][] respuestaGeneral = administradorBD.convertirString(respuestaValidacion);
							String porcentaje= exactitud(entrenamientoValidacion,respuestaGeneral);
							
							cajaExactitud.setText(porcentaje + " %");							
							//////////////////////////////////////////////////////////
						}else{
							JOptionPane.showMessageDialog(null,
									"- No Se ha definido las divisiones para la validacion cruzada",
									"ESPERE",JOptionPane.WARNING_MESSAGE);	
						}					
					}else{
						mensajesProteccionSistema();
					}
				}
			}
			private String[][] copiarArreglo(String[][] entrenamiento) {
				int numFilas = entrenamiento.length;
				int numColumnas = entrenamiento[0].length;
				
				String [][] copia = new String[numFilas][numColumnas];				
				for (int i = 0; i < numFilas; i++) {
					for (int j = 0; j < numColumnas; j++) {
						copia[i][j] = entrenamiento [i][j];
					}
				}
				return copia;
			}
			private String exactitud(String[][] entrenamientoValidacion, String[][] respuetaGeneral) {
				String valorRes;
				String valorEntren;
				double correctos=0;
				int total= respuetaGeneral.length;
				int columnaClase= entrenamientoValidacion[0].length-1;
				//comenzamos a comparar. 
				for (int i = 0; i < total; i++){
					valorRes = respuetaGeneral[i][0];
					valorEntren= entrenamientoValidacion[i+1][columnaClase];
					if(valorRes.equals(valorEntren))
						correctos++;
				}
				correctos /= total;
				DecimalFormat decimales = new DecimalFormat("0.00");
				
				return decimales.format(correctos*100);
			}
			private void imprimirCubo(String[][][] particiones) {
				int numParticiones = particiones.length;
				int numFilas;
				int numColumnas;
				for (int i = 0; i < numParticiones; i++) {
					numFilas = particiones[i].length;
					System.out.println("Particion: "+ i);
					for (int j = 0; j < numFilas; j++) {
						System.out.print("        ");
						for (int k = 0; k < particiones[i][0].length; k++) {
							System.out.print(" "+particiones[i][j][k]);
						}
						System.out.println("\n");
					}
				}
				
			}

			private String[][][] crearparticiones(String[][][] particiones,int [] tamañosParticiones , String[][] numEntrenamiento) {
				int limiteAcumulation = tamañosParticiones[0];
				int numColumnas = numEntrenamiento[0].length;
				int numParticiones = tamañosParticiones.length;
				int cont = 1;
				String filaActual [] ;
				//Creamos los espacios
				for(int particion = 0; particion < numParticiones ; particion++ ){
					int numFilas = tamañosParticiones[particion];
					particiones [particion] = new String [numFilas][];
					//LLenado de la particioon creada.
					for(int j = 0 ; j < numFilas ; j++){						
						filaActual = numEntrenamiento[cont++];//Tomamos una fila para colocarla a en las particiones
						particiones[particion][j] = filaActual;
					}
				}			
				return particiones;
			}

			private int[] dameTamParticiones(int[] tamañosParticiones, int numParticiones, String[][] entrenamiento) {
				int numFilas = entrenamiento.length-1;
				int numFilasRestantes = numFilas % numParticiones;
				int divisionEntera = numFilas / numParticiones;
				
				for(int i = 0; i < numParticiones; i++){					
					tamañosParticiones[i] = (i < numFilasRestantes)? divisionEntera+1 : divisionEntera;
				}				
				return tamañosParticiones;
			}				
		});
		
		botonCargaprueba.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){	
				//Bucamos la ruta
				String ruta= administradorBD.buscarRuta();
				//El administrador se encargara generar la Base Prueba
				hayPrueba = administradorBD.cargaArchivo(ruta,false);
				//Comenzamos a pedir datos
				String [][]datos= administradorBD.getDatos();
				String []encabezado= administradorBD.getEncabezado();
				
				//Creamos la tabla.				
				tablaPrueba = new JTable(datos,encabezado);
				
				//le ponemos la tabla adentro.
				scrollTablaPrue.setViewportView(tablaPrueba);
				scrollClasificacion.setViewportView(null);
				//Actualizar Numeracion
				actualizarNum(datos.length,tablanumeracionPrueba,scrollNumePrueba,scrollTablaPrue);	
				
				//Ocultar la barra vertical
				scrollTablaPrue.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			}			
		});
		
		botonCargarEntPrue.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){	
				if(hayEntrenamiento){										
					String [][] prueba = administradorBD.entrenamiento;
					clase_verdadera_prueba = new int [prueba.length][1];
					administradorBD.convertirASCII(dameColumaClase(prueba),clase_verdadera_prueba);
					prueba = quitarColumaClase(prueba);
					//Creamos y actualizamos la Base de Datos Numerica
					int numFilas = prueba.length;
					int numColum = prueba[0].length;
					administradorBD.setFilas(numFilas);
					administradorBD.setColumnas(numColum);					
					int [][] pruebaNum = new int [numFilas][numColum];
					administradorBD.convertirASCII(prueba,pruebaNum);
					//Actualizamos base de datos
					administradorBD.actualizarBaseDatos(prueba,pruebaNum,false);
					hayPrueba = true;
					//mostramos los resultados
					//Creamos la tabla
					//Comenzamos a pedir datos
					String [][]datos= administradorBD.getDatos();
					String []encabezado= administradorBD.getEncabezado();
					tablaPrueba = new JTable(datos,encabezado);					
					//Le ponemos la tabla adentro.		
					scrollTablaPrue.setViewportView(tablaPrueba);
					//Actualizar Numeracion
					actualizarNum(datos.length,tablanumeracionPrueba,scrollNumePrueba,scrollTablaPrue);	
					
				}else{
					mensajesProteccionSistema();
				}
			}			
		});
		
		botonPrepararPorce.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){	
				if(hayEntrenamiento){
					String cadena = cajaPartiPorce.getText();
					if(!cadena.equals("")){
						String [][] entrenamiento = administradorBD.entrenamiento;						
						entrenamiento = filasAleatoria(entrenamiento);						
						////////////////////////////////////////////////////////////////
						//Se recorto el entrenamiento
						int porcentaje = Integer.parseInt(cajaPartiPorce.getText());
						String tablas [][][]= dameTablasEntreYPrueba(entrenamiento,porcentaje);	
						entrenamiento = tablas [0];
						//Creamos y actualizamos la Base de Datos Numerica
						int numFilas = entrenamiento.length;
						int numColum = entrenamiento[0].length;
						administradorBD.setFilas(numFilas);
						administradorBD.setColumnas(numColum);					
						int [][] entrenamientoNum = new int [numFilas][numColum];
						administradorBD.convertirASCII(entrenamiento,entrenamientoNum);
						//Actualizamos base de datos
						administradorBD.actualizarBaseDatos(entrenamiento,entrenamientoNum,true);
						hayEntrenamiento = true;
						//mostramos los resultados
						//Creamos la tabla
						//Comenzamos a pedir datos
						String [][]datos= administradorBD.getDatos();
						String []encabezado= administradorBD.getEncabezado();
						tablaEntrenamiento = new JTable(datos,encabezado);					
						//Le ponemos la tabla adentro.		
						scrollTablaEntre.setViewportView(tablaEntrenamiento);
						//Mostramos la distribucion de la base de datos
						mostrarGrafico(datos);	
						//////////////////////////////////////////////////////////////
						String [][] prueba = tablas[1];
						clase_verdadera_prueba = new int [prueba.length][1];
						administradorBD.convertirASCII(dameColumaClase(prueba),clase_verdadera_prueba);
						prueba = quitarColumaClase(prueba);
						//Creamos y actualizamos la Base de Datos Numerica
						int numFilasP = prueba.length;
						int numColumP = prueba[0].length;
						administradorBD.setFilas(numFilasP);
						administradorBD.setColumnas(numColumP);					
						int [][] pruebaNum = new int [numFilasP][numColumP];
						administradorBD.convertirASCII(prueba,pruebaNum);
						//Actualizamos base de datos
						administradorBD.actualizarBaseDatos(prueba,pruebaNum,false);
						hayPrueba = true;
						//mostramos los resultados
						//Creamos la tabla
						//Comenzamos a pedir datos
						String [][]datosP= administradorBD.getDatos();
						String []encabezadoP= administradorBD.getEncabezado();
						tablaPrueba = new JTable(datosP,encabezadoP);					
						//Le ponemos la tabla adentro.		
						scrollTablaPrue.setViewportView(tablaPrueba);
						//Actualizar Numeracion
						actualizarNum(datosP.length,tablanumeracionPrueba,scrollNumePrueba,scrollTablaPrue);				
						
						//////////////////////////////////////////////////////////////////					
						
					}else{
						JOptionPane.showMessageDialog(botonPrepararPorce,
								"- No Se ha definido el porcentaje",
								"ESPERE",JOptionPane.WARNING_MESSAGE);	
					}					
				}else{
					JOptionPane.showMessageDialog(botonPrepararPorce,
							"- No Existe Conjuto Entrenamiento",
							"ESPERE",JOptionPane.WARNING_MESSAGE);			
				}
			}

								
		});
		
		botonCargarEntrenamiento.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){	
				//Bucamos la ruta
				String ruta= administradorBD.buscarRuta();
				//El administrador se encargara de generar la BD
				hayEntrenamiento = administradorBD.cargaArchivo(ruta,true);
				//Comenzamos a pedir datos
				String [][]datos= administradorBD.getDatos();
				String []encabezado= administradorBD.getEncabezado();
				
				//Creamos la tabla
				tablaEntrenamiento = new JTable(datos,encabezado);
								
				//le ponemos la tabla adentro.		
				scrollTablaEntre.setViewportView(tablaEntrenamiento);
				//Actualizar Numeracion
				actualizarNum(datos.length,tablanumeracionEntren,scrollNumeEntre,scrollTablaEntre);
				
				
				//Mostramos la distribucion de la base de datos
				mostrarGrafico(datos);						
			}
		});
		
		botonPermutarDatos.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){	
				if(hayEntrenamiento){
					String [][] tabla = administradorBD.entrenamiento;
					String [][]tablaPermutada = GenerarPermutacionMatriz(tabla);					
					//Creamos y actualizamos la Base de Datos Numerica
					int numFilas = tablaPermutada.length;
					int numColum = tablaPermutada[0].length;
					administradorBD.setFilas(numFilas);
					administradorBD.setColumnas(numColum);
					int [][] matrizNum = new int [numFilas][numColum];
					administradorBD.convertirASCII(tablaPermutada,matrizNum);
					//Actualizamos
					administradorBD.actualizarBaseDatos(tablaPermutada,matrizNum,true);
					//mostramos los resultados
					//Comenzamos a pedir datos
					String [][]datos= administradorBD.getDatos();
					String []encabezado= administradorBD.getEncabezado();
					
					//Creamos la tabla
					tablaEntrenamiento = new JTable(datos,encabezado);
					
					//Le ponemos la tabla adentro.		
					scrollTablaEntre.setViewportView(tablaEntrenamiento);
					//Actualizar Numeracion
					actualizarNum(datos.length,tablanumeracionEntren,scrollNumeEntre,scrollTablaEntre);
					
					//Mostramos la distribucion de la base de datos
					mostrarGrafico(datos);	
					
				}else
					JOptionPane.showMessageDialog(botonClasifica,
							"- No Existe Conjuto Entrenamiento",
							"ESPERE",JOptionPane.WARNING_MESSAGE);				
			}

			
		});
		
		//Eventos de Menus
		menuSalir.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});		
	}
	
	private String[][] dameColumaClase(String[][] prueba) {
		int numcolum = prueba[0].length;
		int numFilas = prueba.length;
		int indexcolumClase = numcolum-1;
		String [][] clase_verdadera_prueba = new String [prueba.length][1];
		
		for (int i = 0; i < numFilas; i++){
			clase_verdadera_prueba[i][0] = prueba[i][indexcolumClase];
		}				
		return clase_verdadera_prueba;
	}
	
	//Ejecuta la accion a clasificar
	private void accionClasificar() {
		switch (comboClasificador.getSelectedIndex()){
		case 0:
			//Clasificador bayes ingenuo.
			Clasificador bayesIngenuo = new Naive_Bayes();
			ejecutarClasificador(bayesIngenuo);
			break;
		case 1:
			//Clasificador K-Vecinos.
			//Gestionamos el Valor K.
			int k;
			try{
				k= Integer.parseInt(cajaKVecinos.getText());
			}catch(Exception a){
				JOptionPane.showMessageDialog(null,
				"El valor de K-Vecinos es INVALIDO;\n"
				+ "-O No es un numero entero\n"
				+ "-O No hay nada.\nIntente de nuevo",
				"ERROR",JOptionPane.ERROR_MESSAGE);
				break;
			}			
			Clasificador kVecinosCercanos = new Nearest_Neightbor(k);
			ejecutarClasificador(kVecinosCercanos);
			break;
		case 2:
			Clasificador iterativeD3 = new Iterative_Dichotomiser();
			ejecutarClasificador(iterativeD3);
			break;
		}
		
	}	
	//----------------------------------------------------------------------------------------------------------
	//----------------------------------------Actualizar numeracion-----------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------
	private void actualizarNum(int tamFilas,JTable tablaNum,JScrollPane scrollNume,JScrollPane scrollpadre ){
		//Creamos la numeracion
		String [][]datosNum= dameNumeracion(tamFilas);
		String [][]encabezadoNum = new String [1][1];
		encabezadoNum[0][0]="#";
		tablaNum = new JTable(datosNum,encabezadoNum);
		scrollNume.setViewportView(tablaNum);
		//sincronizar dos JScrollPane
		scrollpadre.getVerticalScrollBar().setModel(scrollNume.getVerticalScrollBar().getModel());
		//Ocultar la barra vertical
		scrollNume.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
	}
	
	private String[][] dameNumeracion(int tamaño) {
		String numeracion [][] = new String [tamaño][1];
		for (int i = 0; i < tamaño; i++) {
			numeracion[i][0] = Integer.toString(i+1);
		}
	return numeracion;
}
//----------------------------------------------------------------------------------------------------------
//----------------------------------------OPCIONES DE TEST-----------------------------------------------------------
//----------------------------------------------------------------------------------------------------------
	private String[][] quitarColumaClase(String[][] prueba) {
		int numFilas = prueba.length;
		int numColum = prueba[0].length;
		String [][] pruebaSinClase = new String [numFilas][numColum-1];
		for (int i = 0; i < numFilas; i++) {
			for (int j = 0; j < numColum-1; j++) {
				pruebaSinClase[i][j] = prueba[i][j];
			}
		}
		return pruebaSinClase;
	}
	private String[][] filasAleatoria(String[][] prueba) {
		int numFilas = prueba.length;
		String [] aux;
		Random  rnd = new Random();
		int filaX;
		int filaY;
		for (int veces = 0; veces < 100000; veces++) {
			filaX = (int) (rnd.nextFloat()*(numFilas-1)+1);
			filaY = (int) (rnd.nextFloat()*(numFilas-1)+1);
			aux = prueba[filaX];
			prueba[filaX] = prueba[filaY];
			prueba[filaY] = aux;
		}	
		return prueba;
	}
	
	private String[][][] dameTablasEntreYPrueba(String[][] entrenamiento, int porcentaje) {
		int numFilas = entrenamiento.length-1;//menos titulos
		int numColum = entrenamiento[0].length;
		String [] titulos = entrenamiento[0];
		int filasPrueba = (int) (numFilas*(porcentaje/100.0));
		int filasEntre = numFilas - filasPrueba;
		String[][] prueba = new String [filasPrueba+1][numColum];
		String[][] entrena = new String [filasEntre+1][numColum];
		prueba [0] = titulos;
		entrena [0] = titulos;
		int indiceK = 1;
		for (int i = 1; i < entrena.length ; i++){			
			entrena[i] = entrenamiento[indiceK++];			
		}
		for (int i = 1; i < prueba.length; i++){			
			prueba[i] = entrenamiento[indiceK++];		
		}
		
		String [][][] tablas= new String[2][][];
		tablas[0] = entrena;
		tablas[1] = prueba;
		
		return tablas;
	}
	private static void imprimirTabla(String [][] tabla, int numFilas, int numColumnas) {
		System.out.println("\n-------TABLA-------\n");
		for (int i = 0; i < numFilas; i++) {
			System.out.print(i+" :   ");
			for (int j = 0; j < numColumnas; j++) {
				System.out.print(" "+ tabla[i][j]);
			}	
			System.out.println("\n");
		}
	}
	
	private static void imprimirArreglo(int[] respuestaValidacion2) {
		int numFilas = respuestaValidacion2.length;
		System.out.println("\n-------ARREGLO-------\n");
		for (int i = 0; i < numFilas; i++) {
			System.out.print(" "+ respuestaValidacion2[i] );			
		}
		System.out.println("\n");
	}
//----------------------------------------------------------------------------------------------------------
//----------------------------------------PERMUTAR MATRIZ-----------------------------------------------------------
//----------------------------------------------------------------------------------------------------------
	public static ArrayList<String> dameValorDist(String matriz [][],int columna){
		int numFilas= matriz.length;
		//Lista que guardara los valores de cada Atributo.
		ArrayList<String> buffer = new ArrayList<String>();
		//Agregamos el primero para comenzar a comparar.
		buffer.add(matriz[1][columna]);		
		for (int fila = 2; fila < numFilas; fila++)				
			if(!estaElemento(buffer,matriz[fila][columna]))
				buffer.add(matriz[fila][columna]);
		return buffer;
	}
	public static boolean estaElemento(ArrayList<String> lista, String valor){
		for (String cadena : lista) 
			if(valor.equals(cadena) )
				return true;
		return false;
	}
	private static int[] dameConteoValoresPorColumna(String[][] tabla, int columna, ArrayList valores){
		int numValores = valores.size();
		int numFilas = tabla.length;
		int [] conteoValoresPorClase = new int [numValores];
		
		String valorActual;
		for (int valor = 0; valor < numValores; valor++) {
			valorActual = (String) valores.get(valor);
			for (int fila = 0; fila < numFilas; fila++) {
				if(valores.get(valor).equals(tabla[fila][columna]))
					conteoValoresPorClase[valor]++;
			}
		}
		
		return conteoValoresPorClase;
	}
	private static String[][] crearParticion(String[][] particion,String[][] tabla, int columnaPivote, String valorPivote,String [] titulos ) {
		int indiceFilaParticion=1;
		for (int fila = 0; fila < tabla.length; fila++) {
			if(tabla[fila][columnaPivote].equals(valorPivote))
				particion[indiceFilaParticion++] = tabla[fila];
		}
		particion[0] = titulos;
		return particion;
	}
	private String[][] GenerarPermutacionMatriz(String[][] tabla) {
		//Datos Importantes
		int numFilas = tabla.length;
		int numColum = tabla[0].length;		
		int indiceClase = numColum-1;
		String [] titulos = tabla[0];
		//Conteo correspondiente
		ArrayList valClases = dameValorDist(tabla,indiceClase);
		int numeroDeClases = valClases.size();		

		//contaremos el numero de casillas en las que aparace cada clase en la columnaclase		
		int [] conteoValoresPorClase = dameConteoValoresPorColumna(tabla,indiceClase,valClases);//con titulos

		//Creacion de las matricez particionadas
		String [][][] particiones = new String [numeroDeClases][][];
		int numFilasParticiones=0;
		for (int matriz = 0; matriz < numeroDeClases; matriz++) {
			numFilasParticiones = ++conteoValoresPorClase[matriz];//el ++ es de los titulos
			particiones[matriz] = new String[numFilasParticiones][numColum];
		}

		//Comenzamos a llenar las particiones 
		for (int particion = 0; particion < numeroDeClases; particion++) {
			//crearParticion(tabla,columna pivote, "agua", filas,columnas)
			particiones[particion] = crearParticion(particiones[particion],tabla,indiceClase,(String)valClases.get(particion),titulos);
		}
		
		//Ahora vamos a generar las permutaciones de cada una de estas matricez
		for (int particion = 0; particion < numeroDeClases; particion++) {
			//Algoritmo para permutar los datos de una columna.
			String [][] Matriz = particiones[particion];
			//Ya tenemos los titulos []; 
			//Dimensiones
			int numeroFilas = Matriz.length;
			int numColumnas = Matriz[0].length;
			//Arreglo que guardara el numero de elementos distintos de cada columna.
			int numElemDist [] = new int [numColumnas];
			//Arreglo de listas que guardara todos los valores distintos de cada columna.
			ArrayList<String> [] valElemDist = new ArrayList[numColumnas];
			//Pedimos tabla sin datos repetidos de cada atributo.
			for (int columna = 0; columna < numColumnas; columna++){
				valElemDist[columna] = dameValorDist(Matriz,columna);
				numElemDist[columna] = valElemDist[columna].size();
			}
			// Calculamos el numero de fila de la tabla final
			// permutaciones de filas
			int numFilaTotal = 1;// Numero final de la tabla final
			for (int numFila : numElemDist){
				numFilaTotal *= numFila;
			}
			// Creamos la matriz final
			String [][] tablaFinal = new String [numFilaTotal+1][numColumnas];//mas uno por los titulo
			String cadena;// variable para guardar el valor que tenemos que insertar
			// Ahora vamos a llenar la tabla con todas las combinaciones
			int limite = numFilaTotal;
			for (int columna = 0; columna < numColumnas; columna++) {
				int indiceTablaFuente = 0;
				limite /= numElemDist[columna];
				for (int fila = 0; fila < numFilaTotal; fila++) {
					// Pedimos los datos de la tabla
					cadena = valElemDist[columna].get(indiceTablaFuente);
					tablaFinal[fila+1][columna] = cadena;
					if (fila%limite == limite-1)
						indiceTablaFuente = (indiceTablaFuente + 1) % numElemDist[columna];
				}
			}
			//ponemos los titulo
			for (int columna = 0; columna < numColumnas; columna++) {
				tablaFinal[0][columna] = titulos[columna];
			}
			conteoValoresPorClase[particion] = tablaFinal.length;//Extra
			particiones[particion] = tablaFinal;
			
		}

		//Ahora solo vamos a crear una sola matriz final permutada , que no es mas que la combinacion de las particiones
		int numFilaTotal = 1;
		for (int particion = 0; particion < numeroDeClases; particion++) {
			numFilaTotal += (particiones[particion].length-1);				
		}
		
		String [][] tablaFinalPermutada = new String [numFilaTotal][numColum];
		//ponemos los titulos
		tablaFinalPermutada[0] = titulos;
		
		int indiceMatrizPermutada = 1;
		for (int particion = 0; particion < numeroDeClases; particion++) {
			for (int filas = 1; filas < particiones[particion].length; filas++) {
				tablaFinalPermutada[indiceMatrizPermutada++] = particiones[particion][filas];
			}
		}

		return tablaFinalPermutada;
	}
//----------------------------------------------------------------------------------------------------------//	
//---------------------------------------LOGICA DEL PROGRAMA------------------------------------------------//
//----------------------------------------------------------------------------------------------------------//

	private void ejecutarClasificador(Clasificador clasificador) {

		if(hayPrueba && hayEntrenamiento){
			//Pido la base de datos al administrador.
			int [][] numericaBD = administradorBD.getEntrenamientoNum();
			//Pido la base de prueba al administrador.
			int [][] prueba= administradorBD.getPruebaNum();

			//Inicializamos los datos
			clasificador.inicializarDatos(numericaBD);
			
			//medimos el tiempo de inicio 
			long tiempoI = System.nanoTime();
					int [] resultado= clasificador.clasificar(prueba);					
			//medimos el tiempo final 
			long tiempoF = System.nanoTime();
				//Calculamos el tiempo total
				double tiempo =  tiempoF - tiempoI ;
				tiempo_total += tiempo;
			tiempo /= Math.pow(10, 3);
			cajaTiempo.setText(tiempo + " milisegundos");
			
			//Medimos la exactitud de clasificacion 
			String porcentaje = "";
			if(comboTestOpcion.getSelectedIndex() == 1){//Si validacion cruzada esta seleccionada
				AgregarResultadoFinal(respuestaValidacion,resultado);				
			}else{			
				if(comboTestOpcion.getSelectedIndex() == 3){//Si division en porcentaje
					porcentaje= clasificador.exactitud(clase_verdadera_prueba,resultado);
					cajaExactitud.setText(porcentaje + " %");
					//Mostramos resultados.
					mostrarResultados(resultado);				
				}else{
					porcentaje= clasificador.exactitud(numericaBD,resultado);
					cajaExactitud.setText(porcentaje + " %");
					//Mostramos resultados.
					mostrarResultados(resultado);
				}				
			}
			
		}
		else
			mensajesProteccionSistema();
	}
	
//---------------------------------------------------------------------------------------------------------//	
//---------------------------------------Visualizacion de informacion--------------------------------------------//
//---------------------------------------------------------------------------------------------------------//	
	
	private void AgregarResultadoFinal(int[] respuestaValidacion, int[] resultado){
		for (int i = 0; i < resultado.length; i++){
			respuestaValidacion[index++] = resultado[i];
		}
	}

	public void mostrarResultados(int [] resultado){
		//Datos de tabla a mostrar.
		String [] encabezado={"Clase"};
		String [][] datos = administradorBD.convertirString(resultado);

		tablaResultados = new JTable(datos,encabezado);
		scrollClasificacion.setViewportView(tablaResultados);

		//sincronizar dos JScrollPane
		scrollClasificacion.getVerticalScrollBar().setModel(scrollTablaPrue.getVerticalScrollBar().getModel());
		//Ocultar la barra vertical
		scrollTablaPrue.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
	}
	
	public void mensajesProteccionSistema(){
		//Mensajes de información: Para que avisar que faltan datos.
		if(hayPrueba == false && hayEntrenamiento == false) 
			JOptionPane.showMessageDialog(botonClasifica,
					"- No Existe Conjuto prueba \n- No Existe Conjunto entrenamiento",
					"ESPERE",JOptionPane.WARNING_MESSAGE);
		else{
			if(hayPrueba == false)
				JOptionPane.showMessageDialog(botonClasifica,
					"- No Existe Conjuto prueba",
					"ESPERE",JOptionPane.WARNING_MESSAGE);
			if(hayEntrenamiento == false) 
				JOptionPane.showMessageDialog(botonClasifica,
						"- No Existe Conjuto Entrenamiento",
						"ESPERE",JOptionPane.WARNING_MESSAGE);
		}
	}
//----------------------------------------------------------------------------------------------------------//	
//---------------------------------------PROPIEDADES DE GRAFICO--------------------------------------------//
//----------------------------------------------------------------------------------------------------------//	
	
		/**
         * Set radius of a pie plot.
         * @param chart JFreeChart.
         * @param radius Radius to set (between 0.0 and 1.0)
         */
        public void setPieRadius(JFreeChart chart, double radius) {
            if (chart != null) {
                Plot plot = chart.getPlot();
                if (plot instanceof PiePlot) {
                    PiePlot piePlot = (PiePlot) plot;
                    double ig = 1.0 - radius;
                    if (ig > PiePlot.MAX_INTERIOR_GAP) {
                        ig = PiePlot.MAX_INTERIOR_GAP;
                    }
                    piePlot.setInteriorGap(ig);
                }
            }
        }
        
        private void mostrarGrafico(String[][] datos) {
    		//Quitamos la imagen por default 
    		this.remove(contenedorImagenGrafico);
    		
    		//Quitamos la grafica anterior.
    		if(panelGrafico.getComponentCount() != 0)
    			panelGrafico.remove(chartPanel);
    		
    		//Requerimos las diferentes clases en un arreglo.
    		ArrayList<String> clases = administradorBD.getclases(datos);
    		//Requerimos el porcentaje de clases en la base de datos.
    		double [] porcentajeClases = administradorBD.getporcentajes(datos,clases);
    		//Damos formato a los decimales de los double
    		DecimalFormat decimales = new DecimalFormat("0");
    		// Fuente de Datos
            defaultpiedataset = new DefaultPieDataset(); 
            double porcentaje;
            String cadenaMostrar; 
            for (int clase = 0; clase < clases.size(); clase++) {
            	porcentaje = porcentajeClases[clase];
            	cadenaMostrar = clases.get(clase)+" "+decimales.format(porcentaje)+"%";
            	defaultpiedataset.setValue(cadenaMostrar.toUpperCase(), porcentaje);
    		}
            // Creando el Grafico
            chart = ChartFactory.createPieChart3D("Distribucion de Base de Datos",defaultpiedataset, false, true, false); 
            //Cambiamos el tamaño
            setPieRadius(chart,0.85);
            //Cambiamos algunas propiedades
            pieplot3d = (PiePlot3D)chart.getPlot(); 
            //profundidad de la grafica de pastel
            pieplot3d.setDepthFactor(0.2); 
            //Propiedades de direccion y rotacion
            pieplot3d.setStartAngle(290D); 
            pieplot3d.setDirection(Rotation.CLOCKWISE); 
            //Trasparencia
            pieplot3d.setForegroundAlpha(0.8F);
            //Color de fondo
            pieplot3d.setBackgroundPaint(new Color(190,199,193));       
            //Color del fondo
            chart.setBackgroundPaint(new Color(190,199,193));
            //Bordes no visibles
            chart.setBorderVisible(false);
         // Mostrar Grafico
            chartPanel = new ChartPanel(chart);
            panelGrafico.add(chartPanel);
            
         //Agregamos el grafico a la ventana
            add(panelGrafico);
            panelGrafico.updateUI();
    	}
//----------------------------------------------------------------------------------------------------------// 
}