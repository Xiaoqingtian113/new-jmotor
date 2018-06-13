package com.zhucq.mobile.testcase.cobra;

import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.Test;

import com.zhucq.mobile.TestCaseBase;
import com.zhucq.mobile.appium.JMotorDriver;
import com.zhucq.mobile.util.IdCardGenerator;
import com.zhucq.mobile.util.RandomUtil;

public class CobraLoan extends TestCaseBase{
	@Test(dataProvider="dp")
	public void loan(HashMap<String,String> dmp){
		JMotorDriver<RemoteWebElement> motor = new JMotorDriver<RemoteWebElement>(driver);
		driver.manage().window().maximize();
		driver.get("http://172.16.5.251:10200/#/");
		motor.findElementWhenPresence("登录页面", "用户名").clear();
		motor.findElementWhenPresence("登录页面", "用户名").sendKeys(dmp.get("username"));
		motor.findElementWhenPresence("登录页面", "密码").clear();
		motor.findElementWhenPresence("登录页面", "密码").sendKeys(dmp.get("password"));
		motor.findElementWhenPresence("登录页面", "登录按钮").click();
		
		motor.findElementWhenPresence("我的任务页面", "我的任务").click();
		motor.findElementWhenPresence("我的任务页面", "添加申请").click();
		String jiekuanyongtu = RandomUtil.getRandomOption("经营周转,个人资金周转,消费,创业/投资,其他");
		if(jiekuanyongtu.equals("其他")){
			motor.findElementWhenPresence("添加申请页面", "借款用途-其他").click();
			motor.findElementWhenPresence("添加申请页面", "其他借款用途").sendKeys(RandomUtil.getCNString(3));
		}else
			motor.findElementWhenPresence("添加申请页面", jiekuanyongtu).click();
		
//		String qishu = RandomUtil.getRandomOption("10期,20期,30期,50期");
//		motor.findElementWhenPresence("添加申请页面", qishu).click();
//		String amount = "";
//		switch(qishu){
//			case "10期" :
//				amount= String.valueOf(RandomUtil.getRandomNumber(2000, 5000, 1000));
//				break;
//			case "15期" :
//				amount= String.valueOf(RandomUtil.getRandomNumber(3000, 6000, 1000));
//				break;
//			case "20期" :
//				amount= String.valueOf(RandomUtil.getRandomNumber(3000, 9000, 1000));
//				break;
//			case "25期" :
//				amount= String.valueOf(RandomUtil.getRandomNumber(4000, 10000, 1000));
//				break;
//			case "30期" :
//				amount= String.valueOf(RandomUtil.getRandomNumber(5000, 12000, 1000));
//				break;
//			case "40期" :
//				amount= String.valueOf(RandomUtil.getRandomNumber(6000, 15000, 1000));
//				break;
//			case "50期" :
//				amount= String.valueOf(RandomUtil.getRandomNumber(8000, 20000, 1000));
//				break;
//			default :
//				break;
//		}
		motor.findElementWhenPresence("添加申请页面", "借款金额").sendKeys(RandomUtil.getRandomNumber(2000, 20000, 1000));
		motor.findElementWhenPresence("添加申请页面", "可接受月还款").sendKeys(RandomUtil.getRandomNumber(500,1000,100));
		
		if(dmp.get("city").equals("安康/安康门店")){
			motor.findElementWhenPresence("添加申请页面", "进件城市").click();
			motor.findElementWhenClickable("添加申请页面", "进件城市-安康").click();
			motor.findElementWhenClickable("添加申请页面", "进件城市-安康门店").click();
			
		}else if(dmp.get("city").equals("襄阳/襄阳门店")){
			motor.findElementWhenPresence("添加申请页面", "进件城市").click();
			motor.findElementWhenClickable("添加申请页面", "进件城市-襄阳").click();
			motor.findElementWhenClickable("添加申请页面", "进件城市-襄阳门店").click();
		}
		motor.findElementWhenPresence("添加申请页面", "姓名").sendKeys(RandomUtil.getName());
		motor.findElementWhenPresence("添加申请页面", "身份证号码").sendKeys(IdCardGenerator.generate());
		motor.findElementWhenPresence("添加申请页面", "手机号").sendKeys(RandomUtil.getPhoneNumber());
		
		String hunyinzhuangkuang = RandomUtil.getRandomOption("未婚,已婚,离异,丧偶,其他");
		if(hunyinzhuangkuang.equals("其他")){
			motor.findElementWhenPresence("添加申请页面", "婚姻状况-其他").click();
			motor.findElementWhenPresence("添加申请页面", "其他婚姻状况").sendKeys(RandomUtil.getCNString(3));
		}else
			motor.findElementWhenPresence("添加申请页面", hunyinzhuangkuang).click();
		
		motor.findElementWhenPresence("添加申请页面", "家庭联系人1").click();
		String relative1 = RandomUtil.getRandomOption("父母1,配偶1,子女1,亲属1");
		if(hunyinzhuangkuang.equals("已婚"))
			motor.findElementWhenClickable("添加申请页面", "配偶1").click();
		else if(relative1.equals("亲属1")){
				motor.findElementWhenClickable("添加申请页面", "亲属1").click();
				motor.findElementWhenPresence("添加申请页面", "亲属称呼1").sendKeys(RandomUtil.getCNString(2));
			}else
				motor.findElementWhenClickable("添加申请页面", relative1).click();
		motor.findElementWhenPresence("添加申请页面", "联系人姓名1").sendKeys(RandomUtil.getName());
		motor.findElementWhenPresence("添加申请页面", "联系人手机1").sendKeys(RandomUtil.getPhoneNumber());
		
		motor.findElementWhenPresence("添加申请页面", "家庭联系人2").click();
		String relative2 = RandomUtil.getRandomOption("父母2,配偶2,子女2,亲属2");
		if(relative2.equals("亲属2")){
			motor.findElementWhenClickable("添加申请页面", "亲属2").click();
			motor.findElementWhenPresence("添加申请页面", "亲属称呼2").sendKeys(RandomUtil.getCNString(2));
		}else
			motor.findElementWhenClickable("添加申请页面", relative2).click();
		motor.findElementWhenPresence("添加申请页面", "联系人姓名2").sendKeys(RandomUtil.getName());
		motor.findElementWhenPresence("添加申请页面", "联系人手机2").sendKeys(RandomUtil.getPhoneNumber());
		
		motor.findElementWhenPresence("添加申请页面", "客户经理姓名").sendKeys(dmp.get("mgrName"));
		motor.findElementWhenPresence("添加申请页面", "客户经理员工号").sendKeys(dmp.get("mgrNum"));
		motor.findElementWhenPresence("添加申请页面", "客服专员姓名").sendKeys(dmp.get("韩世伟"));
		motor.findElementWhenPresence("添加申请页面", "客服专员员工号").sendKeys(dmp.get("140800433"));
				
		String qudaokehu = RandomUtil.getRandomOption("是,否");
		if(qudaokehu.equals("是"))
			motor.findElementWhenPresence("添加申请页面", "是否渠道客户-是").click();
		else
			motor.findElementWhenPresence("添加申请页面", "是否渠道客户-否").click();
		motor.findElementWhenPresence("添加申请页面", "渠道进件代码").sendKeys(RandomUtil.getRandomNumber(8));
		motor.findElementWhenPresence("添加申请页面", "确定按钮").click();
		motor.WaitElementUntilInvisibility("添加申请页面", "确定按钮");
		
		
//		findElementWhenPresence(driver,By.partialLinkText("进件列表")).click();
//		String jjbh = findElementWhenPresence(driver,By.xpath("//td[text()='"+ identify +"']/preceding-sibling::td[1]")).getText();
//		System.out.println("进件编号=="+jjbh);
//		
//		findElementWhenPresence(driver,By.partialLinkText("任务列表")).click();
//		Long waitTimeout = 120000L;
//		while(waitTimeout>0){
//			try{
//				driver.findElement(By.xpath("//td[text()='"+ jjbh +"']/following-sibling::td[6]/button")).click();
//				break;
//			}catch(Exception e){
//				driver.navigate().refresh();
//				waitTimeout-=3000;
//				System.out.println("继续等待审核...");
//				Thread.sleep(3000);
//			}
//		}
//		
//		int i=1;
//		findElementWhenPresence(driver,By.partialLinkText("我的任务")).click();
//		findElementWhenPresence(driver,By.xpath("//div[@title='10 条/页']")).click();
//		findElementWhenClickable(driver,By.xpath("//li[text()='40 条/页']")).click();
//		findElementWhenPresence(driver,By.xpath("//td[text()='"+ jjbh +"']/following-sibling::td[5]/a")).click();
//		findElementWhenPresence(driver,By.id("loanData.personalInfo.nameUsedBefore")).sendKeys(Tools.oneword(3));
//		String sex = getRandomOption("男,女");
//		findElementWhenPresence(driver,By.xpath("//span[text()='"+ sex +"']/preceding-sibling::span/input")).click();
//		findElementWhenPresence(driver,By.name("age")).sendKeys(Tools.num(2));
//		String jiaoyu = getRandomOption("硕士及以上,本科,大专,高中及以下");
//		findElementWhenPresence(driver,By.xpath("//span[text()='"+ jiaoyu +"']/preceding-sibling::span/input")).click();
//		findElementWhenPresence(driver,By.id("loanData.personalInfo.residenceAddressCity")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='湖北省'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='武汉市'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='市辖区'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='江夏区'])["+i+"]")).click();
//		i++;
//		findElementWhenPresence(driver,By.id("loanData.personalInfo.residenceAddressDetails")).sendKeys(Tools.oneword(12));
//		findElementWhenPresence(driver,By.id("loanData.personalInfo.permanentAddressCity")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='湖北省'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='武汉市'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='市辖区'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='江夏区'])["+i+"]")).click();
//		i++;
//		findElementWhenPresence(driver,By.id("loanData.personalInfo.permanentAddressDetails")).sendKeys(Tools.oneword(12));
//		String fangchan = getRandomOption("亲属产权,自建房,无按揭房产,按揭房产,单位宿舍,租房");
//		findElementWhenPresence(driver,By.xpath("//span[text()='"+ fangchan +"']/preceding-sibling::span/input")).click();
//		findElementWhenPresence(driver,By.id("loanData.personalInfo.telephone")).sendKeys(Tools.tel2());
//		findElementWhenPresence(driver,By.id("loanData.personalInfo.QQ")).sendKeys(qq(10));
//		findElementWhenPresence(driver,By.id("loanData.personalInfo.WeChat")).sendKeys(Tools.num(10));
//		findElementWhenPresence(driver,By.id("loanData.personalInfo.email")).sendKeys(Tools.email());
//		findElementWhenPresence(driver,By.name("supportNumber")).sendKeys(String.valueOf(getAreaNum(0,5)));
//		findElementWhenPresence(driver,By.name("childernNumber")).sendKeys(String.valueOf(getAreaNum(0,5)));
//		findElementWhenPresence(driver,By.id("loanData.personalInfo.creditBank")).sendKeys(Tools.oneword(4));
//		findElementWhenPresence(driver,By.name("quota")).sendKeys(String.valueOf(getThNum(2000,50000)));
//		findElementWhenPresence(driver,By.name("usedMonth")).sendKeys(String.valueOf(getAreaNum(0,60)));
//		String zhinajin = getRandomOption("是,否");
//		if(zhinajin.equals("是"))
//			findElementWhenPresence(driver,By.xpath("//td[text()='是否缴纳过滞纳金']/following-sibling::td/div/div/div/div/label[1]/span[1]/input")).click();
//		else
//			findElementWhenPresence(driver,By.xpath("//td[text()='是否缴纳过滞纳金']/following-sibling::td/div/div/div/div/label[2]/span[1]/input")).click();
//		findElementWhenPresence(driver,By.id("loanData.employmentInfo.companyName")).sendKeys(Tools.oneword(8));
//		findElementWhenPresence(driver,By.id("loanData.employmentInfo.industry")).sendKeys(Tools.oneword(8));
//		findElementWhenPresence(driver,By.id("loanData.employmentInfo.dept")).sendKeys(Tools.oneword(8));
//		findElementWhenPresence(driver,By.id("loanData.employmentInfo.duty")).sendKeys(Tools.oneword(8));
//		String zhiwei = getRandomOption("高级管理人员,中级管理人员,基层管理人员,正式员工,派遣员工,退休人员,销售人员,其他");
//		if(zhiwei.equals("其他")){
//			findElementWhenPresence(driver,By.xpath("//td[text()='职位']/following-sibling::td/div/div/div/div/label[8]/span[1]/input")).click();
//			findElementWhenPresence(driver,By.id("loanData.employmentInfo.positionOther")).sendKeys(Tools.oneword(8));
//		}else
//			findElementWhenPresence(driver,By.xpath("//span[text()='"+ zhiwei +"']/preceding-sibling::span/input")).click();
//		findElementWhenPresence(driver,By.id("loanData.employmentInfo.companyAddressCity")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='湖北省'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='武汉市'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='市辖区'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='江夏区'])["+i+"]")).click();
//		i++;
//		findElementWhenPresence(driver,By.id("loanData.employmentInfo.companyAddressDetails")).sendKeys(Tools.oneword(12));
//		String gongsi = getRandomOption("机关事业,国有企业,外资,合资,民营,个体,其他");
//		if(gongsi.equals("其他")){
//			findElementWhenPresence(driver,By.xpath("//td[text()='公司类型']/following-sibling::td/div/div/div/div/label[7]/span[1]/input")).click();
//			findElementWhenPresence(driver,By.id("loanData.employmentInfo.companyTypeOther")).sendKeys(Tools.oneword(8));
//		}else
//			findElementWhenPresence(driver,By.xpath("//span[text()='"+ gongsi +"']/preceding-sibling::span/input")).click();
//		findElementWhenPresence(driver,By.id("loanData.employmentInfo.tel")).sendKeys(Tools.tel2());;
//		findElementWhenPresence(driver,By.xpath("//input[@class='ant-calendar-picker-input ant-input ant-input-lg']")).click();
//		Thread.sleep(500);
//		findElementWhenPresence(driver,By.xpath("//a[@title='上一年 (Control键加左方向键)']")).click();
//		findElementWhenPresence(driver,By.xpath("//a[@title='上一年 (Control键加左方向键)']")).click();
//		findElementWhenPresence(driver,By.xpath("//a[text()='一月']")).click();
//		findElementWhenPresence(driver,By.id("loanData.employmentInfo.commEmail")).sendKeys(Tools.email());
//		findElementWhenPresence(driver,By.name("monthlyIncome")).sendKeys(String.valueOf(getThNum(3000, 30000)));
//		String gongzi = getRandomOption("打卡,现金");
//		findElementWhenPresence(driver,By.xpath("//span[text()='"+ gongzi +"']/preceding-sibling::span/input")).click();
//		findElementWhenPresence(driver,By.name("monthlyPayDay")).sendKeys(String.valueOf(getAreaNum(1,30)));
//		String zhixiao = getRandomOption("是,否");
//		if(zhixiao.equals("是"))
//			findElementWhenPresence(driver,By.xpath("//td[text()='家人是否知晓借款']/following-sibling::td/div/div/div/div/label[1]/span[1]/input")).click();
//		else
//			findElementWhenPresence(driver,By.xpath("//td[text()='家人是否知晓借款']/following-sibling::td/div/div/div/div/label[2]/span[1]/input")).click();
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[0].jobTitle")).sendKeys(Tools.oneword(8));
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[0].telephone")).sendKeys(Tools.tel2());
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[0].familyAddressCity")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='湖北省'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='武汉市'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='市辖区'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='江夏区'])["+i+"]")).click();
//		i++;
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[0].familyAddressDetails")).sendKeys(Tools.oneword(12));
//		
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[0].companyAddressCity")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='湖北省'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='武汉市'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='市辖区'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='江夏区'])["+i+"]")).click();
//		i++;
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[0].companyAddressDetails")).sendKeys(Tools.oneword(12));
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[1].jobTitle")).sendKeys(Tools.oneword(8));
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[1].telephone")).sendKeys(Tools.tel2());
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[1].familyAddressCity")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='湖北省'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='武汉市'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='市辖区'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='江夏区'])["+i+"]")).click();
//		i++;
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[1].familyAddressDetails")).sendKeys(Tools.oneword(12));
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[1].companyAddressCity")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='湖北省'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='武汉市'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='市辖区'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='江夏区'])["+i+"]")).click();
//		i++;
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[1].companyAddressDetails")).sendKeys(Tools.oneword(12));
//		findElementWhenPresence(driver,By.xpath("(//div[@class='ant-select-selection__rendered'])[3]")).click();
//		String relate3 = getRandomOption("亲属,朋友,同事,其他");
//		findElementWhenClickable(driver,By.xpath("(//li[text()='"+ relate3 +"'])")).click();
//		if(relate3.equals("其他") || relate3.equals("亲属"))
//			findElementWhenPresence(driver,By.id("loanData.contactInfo[2].relativesName")).sendKeys(Tools.oneword(6));
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[2].name")).sendKeys(Tools.oneword(3));
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[2].phone")).sendKeys(Tools.tel());
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[2].jobTitle")).sendKeys(Tools.oneword(8));
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[2].telephone")).sendKeys(Tools.tel2());
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[2].familyAddressCity")).click();
//		Thread.sleep(500);
//		findElementWhenClickable(driver,By.xpath("(//li[@title='湖北省'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='武汉市'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='市辖区'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='江夏区'])["+i+"]")).click();
//		i++;
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[2].familyAddressDetails")).sendKeys(Tools.oneword(12));
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[2].companyAddressCity")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='湖北省'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='武汉市'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='市辖区'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='江夏区'])["+i+"]")).click();
//		i++;
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[2].companyAddressDetails")).sendKeys(Tools.oneword(12));
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[3].name")).sendKeys(Tools.oneword(3));
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[3].dept")).sendKeys(Tools.oneword(5));
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[3].job")).sendKeys(Tools.oneword(5));
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[3].phone")).sendKeys(Tools.tel());
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[3].telephone")).sendKeys(Tools.tel2());
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[3].familyAddressCity")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='湖北省'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='武汉市'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='市辖区'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='江夏区'])["+i+"]")).click();
//		i++;
//		findElementWhenPresence(driver,By.id("loanData.contactInfo[3].familyAddressDetails")).sendKeys(Tools.oneword(12));
//		findElementWhenPresence(driver,By.xpath("(//div[@class='ant-select-selection__rendered'])[4]")).click();
//		String bank = getRandomOption("农业银行,中国银行,建设银行,光大银行,兴业银行,中信银行,招商银行,民生银行,交通银行,广发银行,工商银行,邮储银行,平安银行");
//		findElementWhenClickable(driver,By.xpath("(//li[text()='"+ bank +"'])")).click();
//		findElementWhenPresence(driver,By.id("loanData.bankInfo.accountNum")).sendKeys(getBankCard(bank));
//		findElementWhenPresence(driver,By.id("loanData.bankInfo.branchCity")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='湖北省'])["+i+"]")).click();
//		findElementWhenClickable(driver,By.xpath("(//li[@title='武汉市'])["+i+"]")).click();
//		i++;
//		findElementWhenPresence(driver,By.id("loanData.bankInfo.branchDetails")).sendKeys(Tools.oneword(6));
//		findElementWhenPresence(driver,By.id("loanData.bankInfo.mobile")).sendKeys(Tools.tel());
//		findElementWhenPresence(driver,By.xpath("//span[text()='验 卡']/parent::button")).click();
//		Thread.sleep(3000);
//		String qudao = getRandomOption("是,否");
//		if(qudao.equals("是"))
//			findElementWhenPresence(driver,By.xpath("//td[text()='是否渠道客户：']/following-sibling::td/div/div/div/div/label[1]/span[1]/input")).click();
//		else
//			findElementWhenPresence(driver,By.xpath("//td[text()='是否渠道客户：']/following-sibling::td/div/div/div/div/label[2]/span[1]/input")).click();
//		
//		findElementWhenPresence(driver,By.id("loanData.salesInfo.channelCode")).sendKeys(Tools.num(10));
//		findElementWhenPresence(driver,By.id("loanData.remark")).sendKeys(Tools.oneword(200+(int)Math.random()*300));
//		findElementWhenPresence(driver,By.xpath("(//div[@class='ant-upload ant-upload-select ant-upload-select-picture-card'])[1]")).click();
//		int count =0;
//		JNAUtils.uploadImg();
//		count = waitAndCheck(driver, count);
//		findElementWhenPresence(driver,By.xpath("(//div[@class='ant-upload ant-upload-select ant-upload-select-picture-card'])[2]")).click();
//		JNAUtils.uploadImg();
//		count = waitAndCheck(driver, count);
//		findElementWhenPresence(driver,By.xpath("(//div[@class='ant-upload ant-upload-select ant-upload-select-picture-card'])[3]")).click();
//		JNAUtils.uploadImg();
//		count = waitAndCheck(driver, count);
//		findElementWhenPresence(driver,By.xpath("(//div[@class='ant-upload ant-upload-select ant-upload-select-picture-card'])[4]")).click();
//		JNAUtils.uploadImg();
//		count = waitAndCheck(driver, count);
//		WebElement ele2 = findElementWhenPresence(driver,By.xpath("//span[text()='保 存']/parent::button"));
//		ele2.click();
//		new WebDriverWait(driver,20).until(ExpectedConditions.invisibilityOf(ele2));
	}
}
