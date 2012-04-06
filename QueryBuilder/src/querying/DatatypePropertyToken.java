package querying;

public class DatatypePropertyToken {
	private String var;
	private String prop;
	private String data;
	
	public DatatypePropertyToken(String var, String prop, String data) {
		super();
		this.var = var;
		this.prop = prop;
		this.data = data;
	}

	public String getVar() {
		return var;
	}

	public String getProp() {
		return prop;
	}

	public String getData() {
		return data;
	}

	@Override
	public String toString() {
		return "PropertyValue(?" + var + ", :" + prop +
				", \"" + data + "\")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((prop == null) ? 0 : prop.hashCode());
		result = prime * result + ((var == null) ? 0 : var.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatatypePropertyToken other = (DatatypePropertyToken) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (prop == null) {
			if (other.prop != null)
				return false;
		} else if (!prop.equals(other.prop))
			return false;
		if (var == null) {
			if (other.var != null)
				return false;
		} else if (!var.equals(other.var))
			return false;
		return true;
	}
}