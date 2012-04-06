package querying;

public class TypeToken {
	private String obj;
	private String type;
	
	public TypeToken(String obj, String type) {
		super();
		this.obj = obj;
		this.type = type;
	}

	public String getObj() {
		return obj;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Type(?" + obj + ", :" + type + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((obj == null) ? 0 : obj.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		TypeToken other = (TypeToken) obj;
		if (this.obj == null) {
			if (other.obj != null)
				return false;
		} else if (!this.obj.equals(other.obj))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
}