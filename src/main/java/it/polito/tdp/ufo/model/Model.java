package it.polito.tdp.ufo.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {

	SightingsDAO dao;
	List<String> stati=new ArrayList<>();
	private Graph<String,DefaultEdge> graph;
	List<String> sList;
	
	List<String> bestCammino;
	
	
	public Model() {
		dao= new SightingsDAO();
	}
	
	
	//getAnno
	public List<Avvistamento> getAnno(){
		return dao.getAnno();
	}
	
	//creaGrafo
	public void creaGrafo(int anno) {
		this.graph=new SimpleDirectedGraph<>(DefaultEdge.class);
		
		stati=dao.getStati(anno);
		Graphs.addAllVertices(graph, stati);
		
		for(Arco a: dao.getArco(anno)) {
			if(!this.graph.containsEdge(a.getS1(), a.getS2())) {
				Graphs.addEdgeWithVertices(graph, a.getS1(), a.getS2());
			}
		}
	}
	
	
	public int nVertici() {
		return this.graph.vertexSet().size();
	}

//NUMERO ARCHI:

	public int nArchi() {
		return this.graph.edgeSet().size();
	}
	
	//getStato
	
	public List<String> getStato(){
		return stati;
	}
	
	//getPrecedente
	public List<String> getPrecedente(String s){
		List<String> precedente=Graphs.predecessorListOf(graph, s);
		return precedente;
	}
	
	public List<String> getSuccessore(String s){
		List<String> successore=Graphs.successorListOf(graph, s);
		return successore;
	}
	//getArchi
	public List<String> getArchi(String s){
		sList=new ArrayList<>();
		
		List<String> vicini=Graphs.neighborListOf(graph, s);
		
		for(String v: vicini) {
			sList.add(v);
		}
	
		return sList;
	}
	

	public int getNStatiVicini() {
		return sList.size();
	}
	
	
	
	//ricorsione 
	
	public List<String> getSequenza(String stato) {
		
		bestCammino=new ArrayList<>();
		
		List<String> parziale=new ArrayList<>();
		parziale.add(stato);
		
		ricorsione(parziale);
		return bestCammino;
	}


	private void ricorsione(List<String> parziale) {
      
		String last=parziale.get(parziale.size() -1 );
		
		//ottengo i vicini
		List<String> vicini= Graphs.neighborListOf(this.graph, last);
		
		for(String v: vicini) {
			if(!parziale.contains(v)) {
				parziale.add(v);
				ricorsione(parziale);
				parziale.remove(parziale.size()-1);
			}
		}
		
		if(this.bestCammino.size()<parziale.size()) {
			bestCammino=new ArrayList<>(parziale);
		}
	}
}
