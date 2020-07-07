package it.polito.tdp.ufo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.Avvistamento;
import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Avvistamento> boxAnno;

    @FXML
    private ComboBox<String> boxStato;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleAnalizza(ActionEvent event) {
    	txtResult.clear();
    	String stato=this.boxStato.getValue();
    	
    	if(stato==null) {
    		txtResult.clear();
        	txtResult.appendText("Seleziona un anno!\n");;
        	return ;
    	}
    	
    	
    	List<String> pre=model.getPrecedente(stato);
    	List<String> suc=model.getSuccessore(stato);
    	List<String> sList=model.getArchi(stato);
    	
    	txtResult.appendText("Lo stato "+stato+" è in collegamento con "+ model.getNStatiVicini()+" e sono:\n");
    	for(String l:sList) {
    		txtResult.appendText(l.toString()+"\n");
    	}
    	
    	txtResult.appendText("I suoi predecessori sono:\n");
    	for(String l:pre) {
    		txtResult.appendText(l.toString()+"\n");
    	}
    	
    	txtResult.appendText("I suoi successori sono:\n");
    	for(String l:suc) {
    		txtResult.appendText(l.toString()+"\n");
    	}
    	
    }

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	
    	txtResult.clear();
    	this.boxStato.getItems().clear();
    	
    	int anno= this.boxAnno.getValue().getAnno();
    	
    	if(anno==0) {
    		txtResult.clear();
        	txtResult.appendText("Seleziona un anno!\n");
        	return ;
    	}
    	
    	model.creaGrafo(anno);
    	
    	txtResult.appendText("Grafo Creato!\n");
     	txtResult.appendText("# Vertici: " + model.nVertici()+ "\n");
     	txtResult.appendText("# Archi: " + model.nArchi() + "\n");
     	
     	this.boxStato.getItems().addAll(this.model.getStato());
    	
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	txtResult.clear();
    	String stato=this.boxStato.getValue();
    	
    	if(stato==null) {
    		txtResult.clear();
        	txtResult.appendText("Seleziona un anno!\n");;
        	return ;
    	}
    	
        List<String> sList=model.getSequenza(stato);
    	
    	txtResult.appendText("Lo stato "+stato+" è in collegamento con:\n");
    	for(String l:sList) {
    		txtResult.appendText(l.toString()+"\n");
    	}
    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxAnno.getItems().addAll(model.getAnno());
	}
}
