package cox.model.pi;

import java.util.Collection;

public interface ProcessingInstruction
{
	public abstract String getKey();
	public abstract Collection<String> getValues();
	
	public default boolean equals(ProcessingInstruction other)
	{
		return this.getKey().equals(other.getKey()) && this.getValues().equals(other.getValues());
	}
}
