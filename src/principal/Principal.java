/** 
 * Proyecto Terminal 1: Miner�a de Datos para el an�lisis de datos obtenidos en
 * 						reactor de Microactividad de Craking Catal�tico.
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
