package querying;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuerySPARQLDL {
	private String prefix;
	private String info;
	private boolean distinct;
	private ArrayList<String> vars;
	
	private ArrayList<TypeToken> types;
	private ArrayList<ObjectPropertyToken> objProps;
	private ArrayList<DatatypePropertyToken> dataProps;
	
	public static void main(String [] args) {
		QuerySPARQLDL q = new QuerySPARQLDL();
		System.out.println(q.addObjectPropertyPattern("n1", "nsubj", "n2"));
		System.out.println(q.addObjectPropertyPattern("n2", "nsubj", "n2"));
		
		System.out.println(q.removeObjectPropertyPattern("n1", "nsubj", "n2"));
		System.out.println(q.removeObjectPropertyPattern("n1", "nsubj", "n2"));
		System.out.println(q.addObjectPropertyPattern("n1", "nsubj", "n2"));
		System.out.println(q.addDatatypePropertyPattern("n2", "lemma", "zrf"));
		System.out.println(q.addTypePattern("n3", "VB"));
		System.out.println(q.removeTypePattern("n3", "VB"));
		
		String qs = "Sfin.Dep* PREFIX : <http://www.semanticweb.org/ontologies/2011/10/NLPOntology.owl#> SELECT DISTINCT ?n1 ?n2 ?n3 WHERE { Type(?n1, :VB), Type(?n2, :JJ), Type(?n3, :VB), PropertyValue(?n1, :ccomp, ?n2), PropertyValue(?n2, :cop, ?n3) }";
		QuerySPARQLDL q2 = new QuerySPARQLDL(qs);
		String qs2 = "PPing[for].Dep* PREFIX : <http://www.semanticweb.org/ontologies/2011/10/NLPOntology.owl#> SELECT DISTINCT ?n1 ?n2 ?n3 WHERE { Type(?n1, :VB), Type(?n2, :IN), Type(?n3, :VBG), PropertyValue(?n1, :prep, ?n2), PropertyValue(?n2, :pcomp, ?n3), PropertyValue(?n2, :lemma, \"for\") }";
		System.out.println(qs2);
		QuerySPARQLDL q3 = new QuerySPARQLDL(qs2);
		
		System.out.println(q2);
		System.out.println(q3);
	}
	
	public QuerySPARQLDL() {
		this.vars = new ArrayList<String>();
		this.types = new ArrayList<TypeToken>();
		this.objProps = new ArrayList<ObjectPropertyToken>();
		this.dataProps = new ArrayList<DatatypePropertyToken>();
	}
	
	public QuerySPARQLDL(String queryString) {
		this.vars = new ArrayList<String>();
		this.types = new ArrayList<TypeToken>();
		this.objProps = new ArrayList<ObjectPropertyToken>();
		this.dataProps = new ArrayList<DatatypePropertyToken>();
		
		convert(queryString);
	}
	
	private void convert(String queryString) {
		String idRule [] = queryString.split("\\* ");
		
		this.info = idRule[0];
		
		Pattern p = Pattern.compile("PREFIX : ([^ ]+) ");
		Matcher m = p.matcher(idRule[1]);
		
		while (m.find())
			this.prefix = m.group(1);
		
		if (idRule[1].contains("SELECT DISTINCT"))
			this.distinct = true;
		else
			this.distinct = false;
		
		String varsRule [] = idRule[1].split(" WHERE ");
		
		p = Pattern.compile("(\\?(\\w+\\d+))+");
		m = p.matcher(varsRule[0]);
		
		while (m.find())
			this.vars.add(m.group(2));
		
		p = Pattern.compile("Type\\(\\?(\\w+\\d+), \\:(\\w+)\\)");
		m = p.matcher(varsRule[1]);
		
		while (m.find())
			this.types.add(new TypeToken(m.group(1), m.group(2)));
		
		p = Pattern.compile("PropertyValue\\(\\?(\\w+\\d+), \\:(\\w+), \\?(\\w+\\d+)\\)");
		m = p.matcher(varsRule[1]);
		
		while (m.find())
			this.objProps.add(new ObjectPropertyToken(m.group(1), m.group(2), m.group(3)));
		
		p = Pattern.compile("PropertyValue\\(\\?(\\w+\\d+), \\:(\\w+), \"(\\w+)\"\\)");
		m = p.matcher(varsRule[1]);
		
		while (m.find())
			this.dataProps.add(new DatatypePropertyToken(m.group(1), m.group(2), m.group(3)));
		
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}
	
	public ArrayList<String> getVars() {
		return vars;
	}
	
	public ArrayList<TypeToken> getTypes() {
		return types;
	}

	public ArrayList<ObjectPropertyToken> getObjProps() {
		return objProps;
	}

	public ArrayList<DatatypePropertyToken> getDataProps() {
		return dataProps;
	}

	public void clear() {
		vars.clear();
		types.clear();
		objProps.clear();
		dataProps.clear();
	}
	
	public boolean addTypePattern(String var, String type) {
		TypeToken temp = new TypeToken(var, type);
		
		if (types.contains(temp))
			return false;
		
		if (!vars.contains(var))
			vars.add(var);
		
		types.add(temp);
		
		return true;
	}
	
	public boolean existsTypePattern(String var, String type) {
		TypeToken temp = new TypeToken(var, type);
		
		if (!types.contains(temp))
			return false;
		else
			return true;
	}
	
	public boolean removeTypePattern(String var, String type) {
		if (vars.contains(var)) {
			if (!existsTypePattern(var, type))
				return false;
			
			types.remove(new TypeToken(var, type));
			return true;
		}
		else
			return false;
	}
	
	public boolean addObjectPropertyPattern(String var1, String prop, String var2) {
		ObjectPropertyToken temp = new ObjectPropertyToken(var1, prop, var2); 
		
		if (objProps.contains(temp))
			return false;
		
		if (!vars.contains(var1))
			vars.add(var1);
		
		if (!vars.contains(var2))
			vars.add(var2);
		
		objProps.add(temp);
		
		return true;
	}
	
	public boolean existsObjectPropertyPattern(String var1, String prop, String var2) {
		ObjectPropertyToken temp = new ObjectPropertyToken(var1, prop, var2);
		
		if (!objProps.contains(temp))
			return false;
		else
			return true;
	}
	
	public boolean removeObjectPropertyPattern(String var1, String prop, String var2) {
		if (vars.contains(var1) && vars.contains(var2)) {
			if (!existsObjectPropertyPattern(var1, prop, var2))
				return false;
			
			objProps.remove(new ObjectPropertyToken(var1, prop, var2));
			return true;
		}
		else
			return false;
	}
	
	public boolean addDatatypePropertyPattern(String var1, String prop, String data) {
		DatatypePropertyToken temp = new DatatypePropertyToken(var1, prop, data); 
		
		if (dataProps.contains(temp))
			return false;
		
		if (!vars.contains(var1))
			vars.add(var1);
		
		dataProps.add(temp);
		
		return true;
	}
	
	public boolean existsDatatypePropertyPattern(String var, String prop, String data) {
		DatatypePropertyToken temp = new DatatypePropertyToken(var, prop, data);
		
		if (!dataProps.contains(temp))
			return false;
		else
			return true;
	}
	
	public boolean removeDataPropertyPattern(String var, String prop, String data) {
		if (vars.contains(var)) {
			if (!existsDatatypePropertyPattern(var, prop, data))
				return false;
			
			dataProps.remove(new DatatypePropertyToken(var, prop, data));
			return true;
		}
		else
			return false;
	}

	@Override
	public String toString() {
		String res = "PREFIX : " + prefix + " ";
		
		if (!vars.isEmpty()) {
			res += "SELECT " + (distinct ? "DISTINCT " : "");
			for (String n: vars)
				res += "?" + n + " ";
			
			res += "WHERE { ";
		}
		
		if (!types.isEmpty()) {
			if (types.size() > 1)
				for (int i = 0; i < types.size() - 1; i++)
					res += types.get(i) + " , ";
			
			res += types.get(types.size() - 1) + " ";
			
			if (!objProps.isEmpty() || !dataProps.isEmpty())
				res += ", ";
		}
		
		if (!objProps.isEmpty()) {
			if (objProps.size() > 1)
				for (int i = 0; i < objProps.size() - 1; i++)
					res += objProps.get(i) + " , ";
			
			res += objProps.get(objProps.size() - 1) + " ";
			
			if (!dataProps.isEmpty())
				res += ", ";
		}
		
		if (!dataProps.isEmpty()) {
			if (dataProps.size() > 1)
				for (int i = 0; i < dataProps.size() - 1; i++)
					res += dataProps.get(i) + ", ";
			
			res += dataProps.get(dataProps.size() - 1) + " ";
		}
		
		if (!vars.isEmpty())
			res += "}";
		
		return res;
				
	}
	
}
