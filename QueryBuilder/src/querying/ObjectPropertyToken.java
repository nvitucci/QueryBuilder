package querying;

public class ObjectPropertyToken {
	private String first;
	private String prop;
	private String second;
	
	public ObjectPropertyToken(String first, String prop, String second) {
		super();
		this.first = first;
		this.prop = prop;
		this.second = second;
	}
	
	public String getFirst() {
		return first;
	}

	public String getProp() {
		return prop;
	}

	public String getSecond() {
		return second;
	}

	@Override
	public String toString() {
		return "PropertyValue(?" + first + ", :" + prop +
				", ?" + second + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((prop == null) ? 0 : prop.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
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
		ObjectPropertyToken other = (ObjectPropertyToken) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (prop == null) {
			if (other.prop != null)
				return false;
		} else if (!prop.equals(other.prop))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}
	
}