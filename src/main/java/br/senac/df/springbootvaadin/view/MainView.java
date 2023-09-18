package br.senac.df.springbootvaadin.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import br.senac.df.springbootvaadin.dao.PessoaRepositorio;
import br.senac.df.springbootvaadin.model.Pessoa;

@Route
public class MainView extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final PessoaRepositorio repo;
	  final Grid<Pessoa> grid;

	  private Button newBtn = new Button("Novo");
	  private Button deleteBtn = new Button("Apagar");
	  private Button saveBtn = new Button("Salvar");
	  private HorizontalLayout btnLayout = new HorizontalLayout();
	  private HorizontalLayout fieldsLayout = new HorizontalLayout();

	  private TextField nome = new TextField("Nome");
	  private TextField sobrenome = new TextField("Sobrenome");
	  private TextField email = new TextField("Email");
	  private IntegerField id = new IntegerField("ID");

	  private String MAX_WIDTH = "400px";
	  private String BUTTON_WIDTH = "123px";

	  public MainView(PessoaRepositorio repo) {
	    this.repo = repo;
	    this.grid = new Grid<>(Pessoa.class, false);
	    grid.addColumn(Pessoa::getId).setHeader("ID").setSortable(true).setWidth("20px");
	    grid.addColumn(Pessoa::getNome).setHeader("Nome").setSortable(true);
	    grid.addColumn(Pessoa::getSobrenome).setHeader("Sobrenome").setSortable(true);
	    grid.addColumn(Pessoa::getEmail).setHeader("Email").setSortable(true);
	    grid.setMaxWidth(MAX_WIDTH);

	    deleteBtn.setEnabled(false);

	    newBtn.setWidth(BUTTON_WIDTH);
	    deleteBtn.setWidth(BUTTON_WIDTH);
	    saveBtn.setWidth(BUTTON_WIDTH);

	    btnLayout.add(newBtn, deleteBtn, saveBtn);
	    btnLayout.setMaxWidth(MAX_WIDTH);
	    fieldsLayout.add(nome, sobrenome);

	    add(btnLayout);
	    add(fieldsLayout);
	    add(grid);
	    refreshTableData();
	    addButtonsActionListeners();
	  }

	  private void addButtonsActionListeners() {

	    grid.addSelectionListener(selected -> {
	      if (selected.getAllSelectedItems().isEmpty()) {
	        deleteBtn.setEnabled(false);
	        clearInputFields();
	      } else {
	        deleteBtn.setEnabled(true);
	        Pessoa selectedCustomer = selected.getFirstSelectedItem().get();
	        nome.setValue(selectedCustomer.getNome());
	        sobrenome.setValue(selectedCustomer.getSobrenome());
	        id.setValue(selectedCustomer.getId());
	      }
	    });

	    newBtn.addClickListener(click -> {
	      clearInputFields();
	      grid.select(null);
	    });

	    deleteBtn.addClickListener(click -> {
	      repo.delete(grid.getSelectedItems().stream().toList().get(0));
	      refreshTableData();
	      clearInputFields();
	    });

	    saveBtn.addClickListener(click -> {
	      Pessoa customer = new Pessoa();
	      customer.setNome(nome.getValue());
	      customer.setSobrenome(sobrenome.getValue());
	      customer.setEmail(email.getValue());
	      customer.setId(id.getValue());
	      repo.save(customer);
	      refreshTableData();
	    });
	  }

	  private void refreshTableData() {
	    grid.setItems(repo.findAll());
	  }

	  private void clearInputFields() {
	    nome.clear();
	    sobrenome.clear();
	    id.clear();
	  }

}
