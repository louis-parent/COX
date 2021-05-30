package cox.model.utils;

public class XMLIdentifier
{
	public static final XMLIdentifier IMMUTABLE_EMPTY_IDENTIFIER = new XMLIdentifier("") {
		@Override
		public void setName(String name) {}
		
		@Override
		public void setNamespace(String namespace) {}
	};
	
	private String namespace;
	private String name;
	
	public XMLIdentifier(String name)
	{
		this("", name);
	}
	
	public XMLIdentifier(String namespace, String name)
	{
		this.namespace = namespace;
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getNamespace()
	{
		return this.namespace;
	}
	
	public void setNamespace(String namespace)
	{
		this.namespace = namespace;
	}
	
	public boolean hasNamespace()
	{
		return !this.getNamespace().isBlank();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof XMLIdentifier)
		{
			return this.equals((XMLIdentifier) obj);
		}
		else
		{
			return this.equals(obj.toString());
		}
	}
	
	public boolean equals(XMLIdentifier other)
	{
		return this.getNamespace().equals(other.getNamespace()) && this.getName().equals(other.getName());
	}
	
	public boolean equals(String name)
	{
		return this.toString().equals(name);
	}
	
	@Override
	public String toString()
	{
		if(this.hasNamespace())
		{
			return this.namespace + ":" + this.name;
		}
		else
		{
			return this.name;
		}
	}
	
	public static XMLIdentifier emptyIdentifier()
	{
		return new XMLIdentifier("");
	}
}
