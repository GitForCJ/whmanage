package com.wlt.webm.util;

import java.util.Hashtable;
import java.io.*;

/**
 * 数字金额和汉字金额的转换类<br>
 */
public class ConvertMoney {
  /**
   * 汉字表示
   */
  private String chinenseMoney;

  /**
   * 数字表示
   */
  private String numberMoney;

  /**
   * 整数部分允许的最大位数
   */
  private int longPartMaxLength = 13;

  /**
   * 字典数据(数字、汉字对照表)
   */
  private Hashtable tab = new Hashtable();

  /**
   * 无参构造函数
   */
  public ConvertMoney() {
    init();
  }

  /**
   * 带参构造函数
   * @param numberMoney 数字金额
   * @param chineseMoney 汉字金额
   */
  public ConvertMoney(String numberMoney, String chineseMoney) {
    this();
    this.chinenseMoney = chineseMoney;
    this.numberMoney = numberMoney;
  }

  /**
   * 初始化
   */
  private void init() {
    tab.put("0", new String("零"));
    tab.put("零", new String("0"));
    tab.put("1", new String("壹"));
    tab.put("壹", new String("1"));
    tab.put("2", new String("贰"));
    tab.put("贰", new String("2"));
    tab.put("3", new String("叁"));
    tab.put("叁", new String("3"));
    tab.put("4", new String("肆"));
    tab.put("肆", new String("4"));
    tab.put("5", new String("伍"));
    tab.put("伍", new String("5"));
    tab.put("6", new String("陆"));
    tab.put("陆", new String("6"));
    tab.put("7", new String("柒"));
    tab.put("柒", new String("7"));
    tab.put("8", new String("捌"));
    tab.put("捌", new String("8"));
    tab.put("9", new String("玖"));
    tab.put("玖", new String("9"));
    tab.put("10", new String("拾"));
    tab.put("拾", new String("10"));
    tab.put("100", new String("佰"));
    tab.put("佰", new String("100"));
    tab.put("1000", new String("仟"));
    tab.put("仟", new String("1000"));
    tab.put("10000", new String("万"));
    tab.put("万", new String("10000"));
    tab.put("100000", new String("拾")); //tab.put("拾",new String("100000"));
    tab.put("1000000", new String("佰")); //tab.put("佰",new String("1000000"));
    tab.put("10000000", new String("仟")); //tab.put("仟",new String("10000000"));
    tab.put("100000000", new String("亿"));
    tab.put("亿", new String("100000000"));
    tab.put("1000000000", new String("拾")); //tab.put("拾",new String("1000000000"));
    tab.put("10000000000", new String("佰")); //tab.put("佰",new String("10000000000"));
    tab.put("100000000000", new String("仟")); //tab.put("仟",new String("100000000000"));
    tab.put("1000000000000", new String("万")); //tab.put("万",new String("1000000000000"));
    tab.put("10000000000000", new String("拾")); //tab.put("拾",new String("10000000000000"));
  }

  /**
   * 将数字表示的钱转化为汉字表示形式
   * @return 汉字格式字符串
   */
  public String toChinese() {
    String result = "";
    String strNum = this.numberMoney;
    
	String[] feeArr = strNum.split("\\元",-1);   
	strNum = feeArr[0];
	
    if(Tools.formatE(Float.parseFloat(strNum)).equals("0.00"))
      return "零元整";

    if (strNum.indexOf(".") < 0) { //只有整数时
      return this.toChineseLongPart(Long.parseLong(strNum)) + "元整";
    }

    long lngPart = Long.parseLong(strNum.substring(0, strNum.indexOf(".")));
    String fltPart = strNum.substring(strNum.indexOf(".") + 1);

    if (lngPart == 0) { //若整数为0,则只显示小数,否则都显示
      result = this.toChineseDecimalPart(fltPart);
    }
    else {
      result = this.toChineseLongPart(lngPart) + "元" +
          this.toChineseDecimalPart(fltPart);
    }

    return result+"整";
  }

  /**
   * 将数字表示的钱转化为汉字表示形式
   * @param numberMoney 数字形式
   * @return 汉字形式
   */
  public String toChinese(String numberMoney) {
    this.numberMoney = numberMoney;
    return this.toChinese();
  }

  /**
   * 整数部分数字==>汉字
   * @param longPart 整数部分数字
   * @return 汉字
   */
  private String toChineseLongPart(long longPart) {
    String result = ""; //返回值

    if (longPart == 0) {
      return (String) tab.get(Long.toString(longPart));
    }

    String strPart = Long.toString(longPart); //转化为字符串处理
    if (strPart.length() > this.longPartMaxLength) {
      return "整数部分只支持" + this.longPartMaxLength + "位!";
    }
    long base = 1; //基数
    char bit; //每位的数值

    for (int i = strPart.length() - 1; i >= 0; i--, base *= 10) {
      bit = strPart.charAt(i);

      if (bit == '0') { //遇到0的处理
        String strZero = "", strStep = "";

        if (base != 1) { //不是个位(中间?则需加"零"
          strZero = (String) tab.get(Long.toString(0L));
        }
        else {
          strZero = "";
        }
        for (; bit == '0'; i--, base *= 10, bit = strPart.charAt(i)) { //（跳过连续的0）
          if ( (base == 10000L || base == 100000000L || base == 1000000000000L)) {
            strStep = (String) tab.get(Long.toString(base));
          }
        }

        i++;
        base /= 10; //向后退一位,
        result = strStep + strZero + result;
      }
      else {
        if (i != strPart.length() - 1) {
          result = (String) tab.get(Character.toString(bit)) +
              (String) tab.get(Long.toString(base)) + result;
        }
        else {
          result = (String) tab.get(Character.toString(bit)) + result;
        }
      }
    }

    return result;
  }
  /**
   * 小数部分数字==>汉字
   * @param floatPart 小数部分数字
   * @return 汉字
   */
  private String toChineseDecimalPart(String floatPart) {
    String result = "";
   // String fltPart = Long.toString(floatPart);
    String fltPart = (floatPart+"00").substring(0,2);
    if (floatPart.length() == 0) { //小数为0
      return result;
    }

    char bit;
    if ( (bit = fltPart.charAt(0)) != '0') {
//   result += (String)tab.get(Character.toString(bit));
//  else
      result += (String) tab.get(Character.toString(bit)) + "角";
    }

    if ( (bit = fltPart.charAt(1)) != '0') {
//   result += (String)tab.get(Character.toString(bit));
//  else
      result += (String) tab.get(Character.toString(bit)) + "分";
    }

    return result;
  }

  /**
   * 将汉字表示的钱转化为数字表示形式
   * @return 数字形式
   */
  public String toNumber() {
    String result = "";
    String strChinese = this.chinenseMoney; //this.ChinenseMoney;
    String decimalPart = "";

    if (strChinese.indexOf("元") > 0) { //若有整数部分
      String longPart = strChinese.substring(0, strChinese.indexOf("元"));
      result += this.toNumberLongPart(longPart);
    }

    if (strChinese.indexOf("角") > 0 || strChinese.indexOf("分") > 0) { //若有小数部分
      decimalPart = strChinese.substring(strChinese.indexOf("元") + 1);
      result += this.toNumberDecimalPart(decimalPart);
    }

    return result;
  }

  /**
   * 将汉字表示的钱转化为数字表示形式
   * @param chinese 汉字形式
   * @return 数字形式
   */
  public String toNumber(String chinese) {
    this.chinenseMoney = chinese;
    return this.toNumber();
  }

  /**
   * 整数部分的汉字==>数字
   * @param lngPart 整数汉字
   * @return 数字
   */
  public String toNumberLongPart(String lngPart) {
    int i = 0;
    long result = 0, tmpResult = 0; //返回结果
    long bit; //数字位
    long base; //基数值
    long maxBase;

    String longPart = this.deleteZero(lngPart); //去掉所有的"零"

    int len = longPart.length();

    while (i < len) {
      tmpResult = 0;

      //先取前两位
      bit = this.getDigit(longPart.charAt(i));
      try {
        maxBase = this.getDigit(longPart.charAt(i + 1));
      }
      catch (Exception ex) { //若只有一位
        result += bit;
        break;
      }
      tmpResult += bit * maxBase; //有两位
      i += 2;

      if (i >= len) {
        result += tmpResult;
        break;
      }

      for (; i < len; i += 2) {
        bit = this.getDigit(longPart.charAt(i));
        try {
          base = this.getDigit(longPart.charAt(i + 1));
        }
        catch (Exception ex) {
          tmpResult += bit;
          i++;
          break;
        }

        if (bit > maxBase) {
          tmpResult *= bit;
          i++;
          break;
        }
        else if (bit < 10 && base > maxBase) { //bit<10  1千3万
          tmpResult = (tmpResult + bit) * base;
          i += 2;
          break;
        }

        tmpResult += bit * base;

      }

      result += tmpResult;

    }

    return Long.toString(result);
  }

  /**
   * 小数部分的汉字==>数字
   * @param decimalPart 小数部分的汉字
   * @return 数字
   */
  private String toNumberDecimalPart(String decimalPart) {
    String result = ".";
    char jiao;
    char cent;
    int index;

    if ( (index = decimalPart.indexOf("角")) >= 0) { //是否有角
      jiao = decimalPart.charAt(index - 1);
      result += (String) tab.get(Character.toString(jiao));
    }
    else {
      result += "0";
    }

    if ( (index = decimalPart.indexOf("分")) >= 0) { //是否有分
      cent = decimalPart.charAt(index - 1);
      result += (String) tab.get(Character.toString(cent));
    }
    else {
      result += "0";
    }

    return result;
  }

  /**
   * 去掉汉字中所有"零"字符
   * @param str 汉字
   * @return 去掉所有"零"的汉字
   */
  private String deleteZero(String str) {
    String noZeroStr = "";
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) != '零') {
        noZeroStr += Character.toString(str.charAt(i));
      }
    }

    return noZeroStr;
  }

  /**
   * 返回一个汉字对应的数字
   * @param charBit 汉字字符
   * @return 数字
   */
  private long getDigit(char charBit) {
    String strbit = (String) tab.get(Character.toString(charBit));
    return Long.parseLong(strbit);
  }

  /**
   * 运行测试
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {

    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

    while (true) {

      int item;
      while (true) {
        try {
          item = Integer.parseInt(stdin.readLine());
        }
        catch (Exception ex) {
          continue;
        }

        break ;
      }

      switch (item) {
        case 1:
            //System.out.print("请输入数字：");
          String inputNum = stdin.readLine(); //"1031";
          ConvertMoney money = new ConvertMoney(inputNum, ""); //创建一个实例
          String chin = money.toChinese();
          break;
        case 2:
            //System.out.print("请输入汉字：");
          String inputChin = stdin.readLine(); //"1031";
          ConvertMoney money2 = new ConvertMoney("", inputChin); //创建一个实例
          String number = money2.toNumber();
          break;
        case 0:
          System.exit(0);
          break;
      }

    } //while
  }

}
