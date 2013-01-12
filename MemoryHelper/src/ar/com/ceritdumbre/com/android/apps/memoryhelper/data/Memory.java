package ar.com.ceritdumbre.com.android.apps.memoryhelper.data;


public class Memory {

	private int id;
	private String memory;
	private String creationDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "->{id=" + this.getId() + ";memory="
				+ this.getMemory() + ";creationDate=" + this.getCreationDate()
				+ "}";
	}

}
