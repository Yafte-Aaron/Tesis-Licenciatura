/** 
 * Proyecto Terminal 1: Minería de Datos para el análisis de datos obtenidos en
 * 						reactor de Microactividad de Craking Catalítico.
 * 		Mandujano Lopez Karla
 * 		Flores Morales Yafte Aaron 
 */

package principal;

import javax.swing.JFrame;

import vista.GUI;

public class Principal {
	
	public static void main(String[] args) {
		GUI ventana = new GUI();
		ventana.setVisible(true);
		ventana.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}

}
