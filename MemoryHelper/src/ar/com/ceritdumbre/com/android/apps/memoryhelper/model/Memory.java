package ar.com.ceritdumbre.com.android.apps.memoryhelper.model;

import java.util.Date;

public class Memory {

	private int id;
	private String memory;
	private Long creationDate;

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

	public Long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Long creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "->{id=" + this.getId() + ";memory="
				+ this.getMemory() + ";creationDate=" + this.getCreationDate()
				+ "}";
	}

}
