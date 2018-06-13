package com.zhucq.mobile;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DataDriver extends ExcelModel {
	List<HashMap<String,String>> mpList;

	public DataDriver(File f, String sheetName) {
		super(f);
		mpList = getSheetAsMapList(sheetName);
		Iterator<HashMap<String,String>> it = mpList.iterator();
		while (it.hasNext()) {
			HashMap<String,String> mp = it.next();
			if (!mp.get("toRun").equals("yes"))
				it.remove();
		}
	}

	public Object[][] getData() {
		Object[][] data = new Object[mpList.size()][1];
		for (int i = 0; i < mpList.size(); i++) {
			data[i][0] = mpList.get(i);
		}
		return data;
	}
}
