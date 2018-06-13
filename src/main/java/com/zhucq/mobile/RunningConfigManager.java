package com.zhucq.mobile;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

public class RunningConfigManager extends ExcelModel {
	private List<RunningConfig> beanList;

	public RunningConfigManager(File f) {
		super(f);
		List<HashMap<String,String>> mpList = getSheetAsMapList(0);
		beanList = mapListToBeans(mpList, RunningConfig.class);
		Iterator<RunningConfig> it = beanList.iterator();
		while (it.hasNext()) {
			RunningConfig config = it.next();
			try {
				if (!BeanUtils.getProperty(config, "toRun").equals("yes")){
					it.remove();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public List<RunningConfig> getBeanList() {
		return beanList;
	}
}
