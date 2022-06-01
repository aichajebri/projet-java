package entreprise;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


import connexion.Connexion;
public class Controller implements Initializable {
    @FXML
	private Button delete;
	@FXML
	private Button add;
	@FXML
	private Button edit;
	@FXML
	private Button get;

	@FXML
	private Button listbutton;
    @FXML
    private TableView <Salarie> table;
    @FXML
    private TableColumn<Salarie,Integer> matriculeCol;
    @FXML
    private TableColumn<Salarie,String> nomCol;
    @FXML
    private TableColumn <Salarie,String> emailCol;
    @FXML
    private TableColumn<Salarie,Double> dateCol;
    @FXML
    private TableColumn <Salarie,Double> SalCol;

    @FXML
    private TableColumn <Salarie,String> Cat;
    @FXML
	private TextField matriculeField;
	@FXML
	private TextField nomField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField DateField;
	@FXML
	private TextField SalaireField;
	@FXML
	private TextField CategorieField;

	
	private ListSal listSal;
	Connection con = null;
    PreparedStatement ps  =null;
	Service ss = new Service();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	       this.listSal=new ListSal() ;		
	       matriculeCol.setCellValueFactory(new PropertyValueFactory<Salarie,Integer>("matricule"));
	       nomCol.setCellValueFactory(new PropertyValueFactory<Salarie,String>("nom"));
	       emailCol.setCellValueFactory(new PropertyValueFactory<Salarie,String>("email"));
	       dateCol.setCellValueFactory(new PropertyValueFactory<Salarie,Double>("DateEmbauche"));
	       SalCol.setCellValueFactory(new PropertyValueFactory<Salarie,Double>("salaire"));
	       Cat.setCellValueFactory(new PropertyValueFactory<Salarie,String>("categorie"));
		 Connexion.getCon();
	}

	@FXML
	protected void Ajouter()
	{ 
	            String req = "insert into salariers (matricule,nom,email,dateEmbauche,salaire,categorie) values ('" + matriculeField.getText() + "', '" + nomField.getText()+ "', '" + emailField.getText() + "', '" + Double.parseDouble(SalaireField.getText())+  "', '" + Double.parseDouble(DateField.getText())+  "', '" + CategorieField.getText()+"')";

	            Statement st;
				try {
					st = Connexion.getCon().createStatement();
					 st.executeUpdate(req);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				 Salarie s = new Salarie (Integer.parseInt(matriculeField.getText()),nomField.getText(),emailField.getText(),Double.parseDouble(SalaireField.getText()),Double.parseDouble(DateField.getText()),CategorieField.getText());
			   		listSal.getImportList().add(s);
			   		table.getItems().add(s);
	}
	
	@FXML
	protected void Supprimer()
{ 
		Salarie s = table.getSelectionModel().getSelectedItem();
		table.getItems().remove(s);
	        ss.delete(ss.findByMat(s.getMatricule()));
}
	
	@FXML
	protected void ImportSalarie()
	{ 
		   table.getItems().clear();
		  
	 table.getItems().addAll(listSal.getImportList());

	}

	
	public void select() {
	
	    Salarie s = table.getSelectionModel().getSelectedItem();
        matriculeField.setText(String.valueOf(s.getMatricule()));
        nomField.setText(s.getNom());
        emailField.setText(s.getEmail());
        DateField.setText(String.valueOf(s.getSalaire()));
        SalaireField.setText(String.valueOf(s.getDateEmbauche()));
        CategorieField.setText(s.getCategorie());
        table.getItems().remove(s);
	}
	public void update() {  
		
	    String req = "update salariers set nom ='"+nomField.getText()+"', email ='"+emailField.getText()+"',salaire ='"+ Double.parseDouble(DateField.getText())+"' , dateEmbauche ='"+Double.parseDouble(SalaireField.getText())+"',categorie ='"+CategorieField.getText()+"' where matricule = "+matriculeField.getText();
        try {
			Statement st = Connexion.getCon().createStatement();
			 st.executeUpdate(req);
		} catch (SQLException e) {
	
			e.printStackTrace();
		}
	 
        Salarie s = new Salarie (Integer.parseInt(matriculeField.getText()),nomField.getText(),emailField.getText(),Double.parseDouble(SalaireField.getText()),Double.parseDouble(DateField.getText()),CategorieField.getText());
   		listSal.getImportList().add(s);
   		
   		table.getItems().add(s);
	}
	
	

}