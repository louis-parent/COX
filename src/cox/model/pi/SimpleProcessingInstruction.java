package cox.model.pi;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleProcessingInstruction implements ProcessingInstruction
{
	private String key;
	private Collection<String> values;
	
	public SimpleProcessingInstruction(String key)
	{
		this(key, new ArrayList<String>());
	}
		
	public SimpleProcessingInstruction(String key, Collection<String> values)
	{
		this.key = key;
		this.values = values;
	}

	@Override
	public String getKey()
	{
		return this.key;
	}
	
	public void addValue(String value)
	{
		this.values.add(value);
	}
	
	public void addAllValues(Collection<String> values)
	{
		this.values.addAll(values);
	}
	
	public void removeValue(String value)
	{
		this.values.remove(value);
	}

	@Override
	public Collection<String> getValues()
	{
		return this.values;
	}
	
	@Override
	public String toString()
	{
		return this.key + " [" + String.join(", ", this.values) + "]";
	}
}
