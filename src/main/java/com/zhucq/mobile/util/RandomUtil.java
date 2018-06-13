package com.zhucq.mobile.util;

public class RandomUtil {

	public static String getFromBase(String baseStr, int length){
		StringBuffer str = new StringBuffer();
		for(int i=0; i<length; i++){
			int index = (int) (Math.random()*baseStr.length());
			str.append(baseStr.charAt(index));
		}
		return str.toString();
	}
	
	public static String getCNString(int length) {
		String baseStr = "老适调各者理将标合选同而名后小民向少气研候通思速造性听就水样根总值格局求层行道联展员做属江象表周火确山米类须命领和题增没那被育点"
				+"油治精品铁情装响风法部热声想系能都处备复意外多生感素大用质天太支由头电然收改示活社放派政儿流元资商先效光克界济教党入照全八公六数走共关整"
				+"其起海具酸她养好长西要如内文消越再斗料写军农断斯新决况方离见观规准采始里种重量族金科角委无几解工日门自问路积称己深已至间时出一市七布清分"
				+"切万三上下程不与明成我划易专且或世列战则业东队两严带利温个别般中是到制阶常为主所际片么义之手前才打九也习院除书器物马干色平年并究特空白百"
				+"广技节的了争把事二验于应五力些府办加务红交报度约产级动身京花亲劳状人难线什组集细织拉今立从经结他高给四回统因满团代以们目直更维件价相建任"
				+"国图开最需持省包圆指月有按看式引化北众会期真土张传次在本第术地机区强场眼着十权千但等半位华低住子青体当单何南存作非你条面形正此来步学车革"
				+"转据置影即却使往极厂它较构历率很安压例律型王完美得林算定果实原管计认群接议现记段家许边容论去设达便县证参心必识过又及反每提运发近比取志受"
				+"还变这进毛保话连信口知查该只叫快可史石音说号对基导矿";
		return getFromBase(baseStr, length);
	}

	public static String getEnString(int length){
		return getEnString(length,true);
	}
	
	public static String getEnString(int length,boolean isLowerCase){
		String baseStrUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String baseStrLower = "abcdefghijklmnopqrstuvwxyz";
		return isLowerCase ? getFromBase(baseStrLower, length) : getFromBase(baseStrUpper,length);
	}
	
	public static String getRandomNumber(int length) {
		return getRandomNumber(length, false);
	}
	
	public static String getRandomNumber(int length, boolean noFirstZero) {
		String baseStrNoFirstZero = "123456789";
		String baseStr = "0123456789";
		return noFirstZero ? getFromBase(baseStrNoFirstZero, 1) + getFromBase(baseStr, length-1) : getFromBase(baseStr, length);
	}
	
	public static String getRandomNumber(int mix, int max, int multi) {
		int minus = max - mix;
		int delta =(int) Math.round(minus/multi*Math.random())*multi;
		return String.valueOf(mix + delta);
	}
	
	public static String getQQ(){
		String baseStr = "123456789";
		return getFromBase(baseStr, 1) + getRandomNumber(8);
	}
	
	public static String getPhoneNumber() {
		return "140" + getRandomNumber(8);
	}

	public static String getWuHanTel() {
		return "027-" + getRandomNumber(8);
	}
	
	public static String email(int preLength,String domain){
		 return getEnString(preLength) + "@" + domain + ".com";
	 }
	
	public static String getShortAddress(){
		return getCNString(3) + "路" + getRandomNumber(2,true) + "号" + getCNString(4) + getRandomNumber(1,true) + "栋" + getRandomNumber(1,true) + "单元" + getRandomNumber(2,true) + "02";
	}
	
	public static String getRandomOption(String str){
		String[] array = str.split(",");
		int i = (int) (array.length * Math.random());
		return array[i];
	}
	
	public static String getBankCard(String bank) {
		if(bank.matches("(ICBC|工商银行|中国工商银行)"))// 中国工商银行
			return "621300" + getRandomNumber(10);
		if(bank.matches("(CCB|建设银行|中国建设银行)"))// 中国建设银行
			return "621700" + getRandomNumber(13);
		if(bank.matches("(ABC|农业银行|中国农业银行)"))// 中国农业银行
			return "955989160" + getRandomNumber(10);
		if(bank.matches("(CEB|光大银行|中国光大银行)"))// 中国光大银行
			return "900302" + getRandomNumber(10);
		if(bank.matches("(BOC|中国银行)"))// 中国银行
			return "621661" + getRandomNumber(13);
		if(bank.matches("(CIB|兴业银行)"))// 兴业银行
			return "622909" + getRandomNumber(12);
		if(bank.matches("(CITIC|中信银行)"))// 中信银行
			return "433670" + getRandomNumber(10);
		if(bank.matches("(SZPAB|平安银行)"))// 平安银行
			return "622298" + getRandomNumber(10);
		if(bank.matches("(COMM|交通银行|中国交通银行)"))// 交通银行
			return "622258" + getRandomNumber(11);
		if(bank.matches("(CMBC|民生银行|中国民生银行)"))// 民生银行
			return "415599" + getRandomNumber(10);
		if(bank.matches("(GDB|广发银行)"))// 广发银行
			return "623259" + getRandomNumber(13);
		if(bank.matches("(CMB|招商银行)"))// 招商银行
			return "690755" + getRandomNumber(9);
		if(bank.matches("(PSBC|邮政储蓄|邮政储蓄银行|中国邮政储蓄银行|中国邮政储蓄)"))// 中国邮政储蓄银行
			return "620062" + getRandomNumber(13);
		return null;
	}
	
	public static String getName() {
		String[] firsname = { "赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "楮", "卫", "蒋", "沈", "韩", "杨", "朱", "秦",
				"尤", "许", "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜", "戚", "谢", "邹", "喻", "柏", "水", "窦",
				"章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎", "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳",
				"酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷", "罗", "毕", "郝", "邬", "安", "常", "乐",
				"于", "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄", "和", "穆", "萧", "尹", "姚", "邵",
				"湛", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧", "计", "伏", "成", "戴", "谈", "宋", "茅", "庞", "熊", "纪", "舒",
				"屈", "项", "祝", "董", "梁", "杜", "阮", "蓝", "闽", "席", "季", "麻", "强", "贾", "路", "娄", "危", "江", "童", "颜", "郭",
				"梅", "盛", "林", "刁", "锺", "徐", "丘", "骆", "高", "夏", "蔡", "田", "樊", "胡", "凌", "霍", "虞", "万", "支", "柯", "昝",
				"管", "卢", "莫", "经", "房", "裘", "缪", "干", "解", "应", "宗", "丁", "宣", "贲", "邓", "郁", "单", "杭", "洪", "包", "诸",
				"左", "石", "崔", "吉", "钮", "龚", "程", "嵇", "邢", "滑", "裴", "陆", "荣", "翁", "荀", "司马", "上官", "欧阳", "夏侯", "诸葛",
				"东方", "赫连", "皇甫", "尉迟", "公羊", "澹台", "公冶", "宗政", "濮阳", "淳于", "单于", "太叔", "申屠", "公孙", "仲孙", "轩辕", "令狐",
				"锺离", "宇文", "长孙", "慕容", "鲜于", "拓拔", "夹谷", "宰父", "谷梁", "东郭", "南门", "呼延" };
		String[] namelist = { "伟", "伟", "芳", "伟", "秀英", "秀英", "娜", "秀英", "伟", "敏", "静", "丽", "静", "丽", "强", "静", "敏",
				"敏", "磊", "军", "洋", "勇", "勇", "艳", "杰", "磊", "强", "军", "杰", "娟", "艳", "涛", "涛", "明", "艳", "超", "勇", "娟",
				"杰", "秀兰", "霞", "敏", "军", "丽", "强", "平", "刚", "杰", "桂英", "芳", "　嘉懿", "煜城", "懿轩", "烨伟", "苑博", "伟泽", "熠彤",
				"天磊", "绍辉", "泽洋", "明轩", "健柏", "鹏煊", "昊强", "伟宸", "博超", "君浩", "子骞", "明辉", "鹏涛", "炎彬", "鹤轩", "越彬", "风华",
				"靖琪", "明诚", "高格", "光华", "国源", "冠宇", "晗昱", "涵润", "翰飞", "翰海", "昊乾", "浩博", "和安", "弘博", "宏恺", "鸿朗", "华奥",
				"华灿", "嘉慕", "坚秉", "建明", "金鑫", "锦程", "瑾瑜", "晋鹏", "经赋", "景同", "靖琪", "君昊", "俊明", "季同", "开济", "凯安", "康成",
				"乐语", "力勤", "良哲", "理群", "茂彦", "敏博", "明达", "朋义", "彭泽", "鹏举", "濮存", "溥心", "璞瑜", "浦泽", "奇邃", "祺祥", "荣轩",
				"锐达", "睿慈", "绍祺", "圣杰", "晟睿", "思源", "斯年", "泰宁", "天佑", "同巍", "奕伟", "祺温", "文虹", "向笛", "心远", "欣德", "新翰",
				"兴言", "星阑", "修为", "旭尧", "炫明", "学真", "雪风", "雅昶", "阳曦", "烨熠", "英韶", "永贞", "咏德", "宇寰", "雨泽", "玉韵", "越彬",
				"蕴和", "哲彦", "振海", "正志", "子晋", "自怡", "君平" };
		int a = (int) (firsname.length * Math.random());
		int b = (int) (namelist.length * Math.random());
		String name = firsname[a] + namelist[b];
		return name;
	}

}