package graphics;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.ReasonerInternalException;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;

import querying.QuerySPARQLDL;

import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;

public class Window {
	private JFrame frame;
	private QuerySPARQLDL query;
	private OWLOntology ont;
	private ArrayList<QuerySPARQLDL> queries;
	private JTextField textStatus;
	private JTable table3;
	private JTable table2;
	private JTable table1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// System.out.println(System.getProperty("java.class.path"));
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		String path = null;
		File currDir = null;
		
		try {
			currDir = new java.io.File(".");
			path = currDir.getCanonicalPath();
			// String ontFile = "file://" + path + "/NLPOntology.owl";
			String queryFile = "/data/code/eclipse-workspace/suprantu/files/dep_queries.txt";
			
			query = new QuerySPARQLDL();
			
			initializeQueries(queryFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setCurrentDirectory(currDir);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.getContentPane().add(panel, BorderLayout.WEST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		final JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{794, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblQuery = new JLabel("Query");
		lblQuery.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblQuery = new GridBagConstraints();
		gbc_lblQuery.fill = GridBagConstraints.BOTH;
		gbc_lblQuery.insets = new Insets(0, 0, 5, 0);
		gbc_lblQuery.gridx = 0;
		gbc_lblQuery.gridy = 0;
		panel_1.add(lblQuery, gbc_lblQuery);
		
		final JTextArea txtrTextquery = new JTextArea();
		txtrTextquery.setText(" ");
		txtrTextquery.setRows(4);
		txtrTextquery.setWrapStyleWord(true);
		txtrTextquery.setLineWrap(true);
		txtrTextquery.setEditable(false);
		GridBagConstraints gbc_txtrTextquery = new GridBagConstraints();
		gbc_txtrTextquery.fill = GridBagConstraints.BOTH;
		gbc_txtrTextquery.gridx = 0;
		gbc_txtrTextquery.gridy = 1;
		panel_1.add(txtrTextquery, gbc_txtrTextquery);
		
		final JComboBox comboVarsForType = new JComboBox();
		comboVarsForType.setModel(new DefaultComboBoxModel(new String[] {"n1", "n2"}));
		final JComboBox comboTypes = new JComboBox();
		final JComboBox comboRules = new JComboBox();
		
		JButton openOntButton = new JButton("Open ontology");
		openOntButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        int returnVal = fc.showOpenDialog(panel_1);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            // This is where a real application would open the file.
		            
		            textStatus.setText("Opening: " + file.getAbsolutePath() + "...");
		            
		            try {
						initializeOntology(file.getAbsolutePath());
						
						comboTypes.setModel(new DefaultComboBoxModel(getTypes()));
						comboRules.setModel(new DefaultComboBoxModel(getObjectProperties()));
					} catch (OWLOntologyCreationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        } else {
		        	textStatus.setText("Open command cancelled by user");
		        }
			}
		});
		GridBagConstraints gbc_openOntButton = new GridBagConstraints();
		gbc_openOntButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_openOntButton.gridwidth = 2;
		gbc_openOntButton.insets = new Insets(0, 0, 5, 5);
		gbc_openOntButton.gridx = 0;
		gbc_openOntButton.gridy = 1;
		panel.add(openOntButton, gbc_openOntButton);
		
		JLabel lblTypeForEvery = new JLabel("Type for every variable");
		GridBagConstraints gbc_lblTypeForEvery = new GridBagConstraints();
		gbc_lblTypeForEvery.gridwidth = 2;
		gbc_lblTypeForEvery.insets = new Insets(0, 0, 5, 0);
		gbc_lblTypeForEvery.gridx = 0;
		gbc_lblTypeForEvery.gridy = 2;
		panel.add(lblTypeForEvery, gbc_lblTypeForEvery);
		
		GridBagConstraints gbc_comboVarsForType = new GridBagConstraints();
		gbc_comboVarsForType.fill = GridBagConstraints.BOTH;
		gbc_comboVarsForType.insets = new Insets(0, 0, 5, 5);
		gbc_comboVarsForType.gridx = 0;
		gbc_comboVarsForType.gridy = 3;
		panel.add(comboVarsForType, gbc_comboVarsForType);
		
		JButton btnAddType = new JButton("Add type");
		btnAddType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String v = comboVarsForType.getSelectedItem().toString();
				String t = comboTypes.getSelectedItem().toString();
				
				if (!query.existsTypePattern(v, t)) {
					query.addTypePattern(v, t);
					txtrTextquery.setText(query.toString());
					
					// System.out.println(query.toString());
					
					textStatus.setText(v + " " + t);
				}
				else
					textStatus.setText("Already existing pattern!");
				
				/* if (comboVarsForType.getSelectedIndex() != -1) {
					textStatus.setText(v + " " + t);
					query.addTypePattern(v, t);
					
					comboVarsForType.removeItemAt(comboVarsForType.getSelectedIndex());
					comboVarsForType.setSelectedIndex(-1);
					
					textField.setText(query.toString());
				} */
			}
		});
		
		GridBagConstraints gbc_comboTypes = new GridBagConstraints();
		gbc_comboTypes.fill = GridBagConstraints.BOTH;
		gbc_comboTypes.insets = new Insets(0, 0, 5, 0);
		gbc_comboTypes.gridx = 1;
		gbc_comboTypes.gridy = 3;
		panel.add(comboTypes, gbc_comboTypes);
		GridBagConstraints gbc_btnAddType = new GridBagConstraints();
		gbc_btnAddType.gridwidth = 2;
		gbc_btnAddType.fill = GridBagConstraints.BOTH;
		gbc_btnAddType.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddType.gridx = 0;
		gbc_btnAddType.gridy = 4;
		panel.add(btnAddType, gbc_btnAddType);
		
		JLabel lblRelationsBetweenVariables = new JLabel("Relations between variables");
		GridBagConstraints gbc_lblRelationsBetweenVariables = new GridBagConstraints();
		gbc_lblRelationsBetweenVariables.gridwidth = 2;
		gbc_lblRelationsBetweenVariables.insets = new Insets(0, 0, 5, 0);
		gbc_lblRelationsBetweenVariables.gridx = 0;
		gbc_lblRelationsBetweenVariables.gridy = 5;
		panel.add(lblRelationsBetweenVariables, gbc_lblRelationsBetweenVariables);
		
		final JComboBox comboVarsForRule1 = new JComboBox();
		comboVarsForRule1.setModel(new DefaultComboBoxModel(new String[] {"n1", "n2"}));
		GridBagConstraints gbc_comboVarsForRule1 = new GridBagConstraints();
		gbc_comboVarsForRule1.fill = GridBagConstraints.BOTH;
		gbc_comboVarsForRule1.insets = new Insets(0, 0, 5, 5);
		gbc_comboVarsForRule1.gridx = 0;
		gbc_comboVarsForRule1.gridy = 6;
		panel.add(comboVarsForRule1, gbc_comboVarsForRule1);
		final JComboBox comboVarsForRule2 = new JComboBox();
		comboVarsForRule2.setModel(new DefaultComboBoxModel(new String[] {"n1", "n2"}));
		GridBagConstraints gbc_comboVarsForRule2 = new GridBagConstraints();
		gbc_comboVarsForRule2.fill = GridBagConstraints.BOTH;
		gbc_comboVarsForRule2.insets = new Insets(0, 0, 5, 0);
		gbc_comboVarsForRule2.gridx = 1;
		gbc_comboVarsForRule2.gridy = 6;
		panel.add(comboVarsForRule2, gbc_comboVarsForRule2);
		
		GridBagConstraints gbc_comboRules = new GridBagConstraints();
		gbc_comboRules.gridwidth = 2;
		gbc_comboRules.fill = GridBagConstraints.BOTH;
		gbc_comboRules.insets = new Insets(0, 0, 5, 0);
		gbc_comboRules.gridx = 0;
		gbc_comboRules.gridy = 7;
		panel.add(comboRules, gbc_comboRules);
		
		final JSpinner spinner = new JSpinner();
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				textStatus.setText(spinner.getValue().toString() + " variables");
				
				int numVariables = ((Integer) spinner.getValue()).intValue();
				
				comboVarsForRule1.removeAllItems();
				comboVarsForRule2.removeAllItems();
				comboVarsForType.removeAllItems();
				for (int i = 1; i <= numVariables; i++) {
					comboVarsForRule1.addItem("n" + i);
					comboVarsForRule2.addItem("n" + i);
					comboVarsForType.addItem("n" + i);
				}
			}
		});
		JButton btnAddRule = new JButton("Add rule");
		btnAddRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String e1 = comboVarsForRule1.getSelectedItem().toString();
				String e2 = comboVarsForRule2.getSelectedItem().toString();
				String r = comboRules.getSelectedItem().toString();
				
				if (e1.equals(e2))
					textStatus.setText("Loops not allowed!");
				else {
					if (query.existsObjectPropertyPattern(e1, r, e2))
						textStatus.setText("Rule already present!");
					else {
						query.addObjectPropertyPattern(e1, r, e2);
						txtrTextquery.setText(query.toString());
						
						// System.out.println(query.toString());
						
						textStatus.setText(e1 + " " + r + " " + e2);
					}
				}
			}
		});
		GridBagConstraints gbc_btnAddRule = new GridBagConstraints();
		gbc_btnAddRule.gridwidth = 2;
		gbc_btnAddRule.fill = GridBagConstraints.BOTH;
		gbc_btnAddRule.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddRule.gridx = 0;
		gbc_btnAddRule.gridy = 8;
		panel.add(btnAddRule, gbc_btnAddRule);
		
		JLabel lblNumberOfVariables = new JLabel("Number of variables");
		GridBagConstraints gbc_lblNumberOfVariables = new GridBagConstraints();
		gbc_lblNumberOfVariables.gridwidth = 2;
		gbc_lblNumberOfVariables.insets = new Insets(0, 0, 5, 0);
		gbc_lblNumberOfVariables.gridx = 0;
		gbc_lblNumberOfVariables.gridy = 9;
		panel.add(lblNumberOfVariables, gbc_lblNumberOfVariables);
		spinner.setModel(new SpinnerNumberModel(2, 1, 10, 1));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.insets = new Insets(0, 0, 5, 0);
		gbc_spinner.gridwidth = 2;
		gbc_spinner.fill = GridBagConstraints.BOTH;
		gbc_spinner.gridx = 0;
		gbc_spinner.gridy = 10;
		panel.add(spinner, gbc_spinner);
		
		JButton btnNewButton = new JButton("Clear query");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				query.clear();
				txtrTextquery.setText("");
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.gridwidth = 2;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 11;
		panel.add(btnNewButton, gbc_btnNewButton);
		
		textStatus = new JTextField();
		textStatus.setEditable(false);
		frame.getContentPane().add(textStatus, BorderLayout.SOUTH);
		textStatus.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.EAST);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		table1 = new JTable();
		table1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		GridBagConstraints gbc_table1 = new GridBagConstraints();
		gbc_table1.insets = new Insets(0, 0, 5, 0);
		gbc_table1.fill = GridBagConstraints.BOTH;
		gbc_table1.gridx = 0;
		gbc_table1.gridy = 0;
		panel_2.add(table1, gbc_table1);
		
		table2 = new JTable();
		table2.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		GridBagConstraints gbc_table2 = new GridBagConstraints();
		gbc_table2.insets = new Insets(0, 0, 5, 0);
		gbc_table2.fill = GridBagConstraints.BOTH;
		gbc_table2.gridx = 0;
		gbc_table2.gridy = 1;
		panel_2.add(table2, gbc_table2);
		
		table3 = new JTable();
		table3.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		GridBagConstraints gbc_table3 = new GridBagConstraints();
		gbc_table3.insets = new Insets(0, 0, 5, 0);
		gbc_table3.fill = GridBagConstraints.BOTH;
		gbc_table3.gridx = 0;
		gbc_table3.gridy = 2;
		panel_2.add(table3, gbc_table3);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 3;
		panel_2.add(panel_3, gbc_panel_3);
		
		final JList list_1 = new JList();
		list_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list_1.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				query = queries.get(list_1.getSelectedIndex());
				
				// Clear tables
				for (int r = 0; r < table1.getModel().getRowCount(); r++)
					for (int c = 0; c < table1.getModel().getColumnCount(); c++)
						((DefaultTableModel) table1.getModel()).setValueAt(null, r, c);
				
				for (int r = 0; r < table2.getModel().getRowCount(); r++)
					for (int c = 0; c < table2.getModel().getColumnCount(); c++)
						((DefaultTableModel) table2.getModel()).setValueAt(null, r, c);
				
				for (int r = 0; r < table2.getModel().getRowCount(); r++)
					for (int c = 0; c < table2.getModel().getColumnCount(); c++)
						((DefaultTableModel) table2.getModel()).setValueAt(null, r, c);
				
				// TODO: Really like this???
				// Update tables
				for (int r = 0; r < query.getTypes().size(); r++) {
					((DefaultTableModel) table1.getModel()).setValueAt(query.getTypes().get(r).getObj(), r, 0);
					((DefaultTableModel) table1.getModel()).setValueAt(query.getTypes().get(r).getType(), r, 1);
				}
				
				for (int r = 0; r < query.getObjProps().size(); r++) {
					((DefaultTableModel) table2.getModel()).setValueAt(query.getObjProps().get(r).getFirst(), r, 0);
					((DefaultTableModel) table2.getModel()).setValueAt(query.getObjProps().get(r).getProp(), r, 1);
					((DefaultTableModel) table2.getModel()).setValueAt(query.getObjProps().get(r).getSecond(), r, 2);
				}
				
				for (int r = 0; r < query.getDataProps().size(); r++) {
					((DefaultTableModel) table3.getModel()).setValueAt(query.getDataProps().get(r).getVar(), r, 0);
					((DefaultTableModel) table3.getModel()).setValueAt(query.getDataProps().get(r).getProp(), r, 1);
					((DefaultTableModel) table3.getModel()).setValueAt(query.getDataProps().get(r).getData(), r, 2);
				}
				
				txtrTextquery.setText(query.toString());
			}
		});
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultListModel listModel2 = new DefaultListModel();
		
		for (QuerySPARQLDL q: queries) {
			listModel2.addElement(q.getInfo());
		}
		
		list_1.setModel(listModel2);
		scrollPane.setViewportView(list_1);
	}
	
	public void initializeOntology(String ontPath) throws OWLOntologyCreationException, IOException {
		// String namespace = "http://www.semanticweb.org/ontologies/2011/10/NLPOntology.owl#";
		String ontFile = "file://" + ontPath;
		IRI ontIRI = IRI.create(ontFile);
		
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		this.ont = manager.loadOntologyFromOntologyDocument(ontIRI);
		
		OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
		OWLReasonerConfiguration config = new SimpleConfiguration();
		OWLReasoner reasoner = reasonerFactory.createReasoner(ont, config);
		
		reasoner.precomputeInferences();
		
		if (!reasoner.isConsistent())
			throw new ReasonerInternalException("Ontology not consistent!");
	}
	
	public String [] getTypes() {
		ArrayList<String> types = new ArrayList<String>(); 
		for (OWLClass c: ont.getClassesInSignature())
			types.add(c.getIRI().getFragment());
		
		String [] array = new String[types.size()];
		Arrays.sort(types.toArray(array));
		
		return array;
	}
	
	public String [] getObjectProperties() {
		ArrayList<String> obj = new ArrayList<String>(); 
		for (OWLObjectProperty o: ont.getObjectPropertiesInSignature())
			obj.add(o.getIRI().getFragment());
		
		String [] array = new String[obj.size()];
		Arrays.sort(obj.toArray(array));
		
		return array;
	}
	
	public String [] getDatatypeProperties() {
		ArrayList<String> data = new ArrayList<String>(); 
		for (OWLDataProperty d: ont.getDataPropertiesInSignature())
			data.add(d.getIRI().getFragment());
		
		String [] array = new String[data.size()];
		Arrays.sort(data.toArray(array));
		// Arrays.sort(array);
		
		return array;
	}
	
	public void initializeQueries(String queryFile) throws IOException {
		queries = new ArrayList<QuerySPARQLDL>();
		
		BufferedReader in = new BufferedReader(new FileReader(queryFile));
		
		String queryString = null;
		
		while ((queryString = in.readLine()) != null)
			queries.add(new QuerySPARQLDL(queryString));
		
		in.close();
	}

}
