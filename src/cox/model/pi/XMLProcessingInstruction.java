package cox.model.pi;

import java.util.Collection;

public interface XMLProcessingInstruction
{
	public abstract String getKey();
	public abstract Collection<String> getValues();
	
	public default boolean equals(XMLProcessingInstruction other)
	{
		return this.getKey().equals(other.getKey()) && this.getValues().equals(other.getValues());
	}
}
