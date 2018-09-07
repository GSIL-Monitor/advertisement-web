package com.yuanshanbao.dsp.quota.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.yuanshanbao.dsp.quota.service.operation.CPCOperationFactory;
import com.yuanshanbao.dsp.quota.service.operation.CPMOperationFactory;
import com.yuanshanbao.dsp.quota.service.operation.CPTOperationFactory;
import com.yuanshanbao.dsp.quota.service.operation.QuotaOperationFactory;

public class QuotaType {
	protected static Map<Integer, String> codeDescriptionMap = new LinkedHashMap<Integer, String>();
	protected static Map<Integer, QuotaOperationFactory> codeCountFactoryMap = new LinkedHashMap<Integer, QuotaOperationFactory>();

	public static final Integer CPC = 1;
	public static final String CPC_DESCRIPTION = "CPC";
	public static final Integer CPM = 2;
	public static final String CPM_DESCRIPTION = "CPM";
	public static final Integer CPT = 3;
	public static final String CPT_DESCRIPTION = "CPT";

	static {
		initCodeDescriptionMap();
		initCodeCountFactoryMap();
	}

	public static void initCodeDescriptionMap() {
		codeDescriptionMap.put(CPC, CPC_DESCRIPTION);
		codeDescriptionMap.put(CPM, CPM_DESCRIPTION);
		codeDescriptionMap.put(CPT, CPT_DESCRIPTION);
	}

	public static void initCodeCountFactoryMap() {
		codeCountFactoryMap.put(CPC, new CPCOperationFactory());
		codeCountFactoryMap.put(CPM, new CPMOperationFactory());
		codeCountFactoryMap.put(CPT, new CPTOperationFactory());
	}

	public static String getDescription(Integer code) {
		return codeDescriptionMap.get(code);
	}

	public static QuotaOperationFactory getCountFactory(Integer code) {
		return codeCountFactoryMap.get(code);
	}

	public static Map<Integer, String> getCodeDescriptionMap() {
		return codeDescriptionMap;
	}

}
