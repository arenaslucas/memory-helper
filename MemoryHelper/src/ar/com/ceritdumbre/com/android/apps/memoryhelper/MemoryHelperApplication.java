package ar.com.ceritdumbre.com.android.apps.memoryhelper;

import android.app.Application;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.data.MemoryData;

public class MemoryHelperApplication extends Application {

	private MemoryData memoryData;

	@Override
	public void onCreate() {
		super.onCreate();
		memoryData = new MemoryData(this);
	}

	public MemoryData getMemoryData() {
		return memoryData;
	}

	public void setMemoryData(MemoryData memoryData) {
		this.memoryData = memoryData;
	}

}
