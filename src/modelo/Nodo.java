package modelo;

import java.util.ArrayList;

public class Nodo {	
	//Variables Globales.
	private int dato;
	private String tipo;
	private ArrayList<Nodo> hijos;
	
	public Nodo() {
		hijos= new ArrayList<Nodo>();
	}
	
	//Get
	public int getDato(){
		return dato;
	}
	
	public String getTipo(){
		return tipo;
	}
	
	public ArrayList<Nodo> getHijos(){
		return hijos;
	}
	
	//Set
	public void setDato(int nuevoDato){
		dato = nuevoDato;
	}
	
	public void setTipo(String nuevoTipo){
		tipo = nuevoTipo;
	}
	
	public void setHijos(ArrayList<Integer> nuevosHijos,String tipo){
		Nodo hijo;
		for (int i = 0; i < nuevosHijos.size(); i++) {			
			hijo = new Nodo(); 
			hijo.setDato(nuevosHijos.get(i));
			hijo.setTipo(tipo);
			hijos.add(hijo);
		}
	}
	
	//get and set especificos
	public Nodo getHijo(int indiceHijo){
		return hijos.get(indiceHijo);
	}
	
	public void setHijo(Nodo hijo){
		hijos.add(hijo);
	}	
}
