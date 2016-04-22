package com.wlt.webm.util;

import java.util.Hashtable;
import java.io.*;

/**
 * ���ֽ��ͺ��ֽ���ת����<br>
 */
public class ConvertMoney {
  /**
   * ���ֱ�ʾ
   */
  private String chinenseMoney;

  /**
   * ���ֱ�ʾ
   */
  private String numberMoney;

  /**
   * ����������������λ��
   */
  private int longPartMaxLength = 13;

  /**
   * �ֵ�����(���֡����ֶ��ձ�)
   */
  private Hashtable tab = new Hashtable();

  /**
   * �޲ι��캯��
   */
  public ConvertMoney() {
    init();
  }

  /**
   * ���ι��캯��
   * @param numberMoney ���ֽ��
   * @param chineseMoney ���ֽ��
   */
  public ConvertMoney(String numberMoney, String chineseMoney) {
    this();
    this.chinenseMoney = chineseMoney;
    this.numberMoney = numberMoney;
  }

  /**
   * ��ʼ��
   */
  private void init() {
    tab.put("0", new String("��"));
    tab.put("��", new String("0"));
    tab.put("1", new String("Ҽ"));
    tab.put("Ҽ", new String("1"));
    tab.put("2", new String("��"));
    tab.put("��", new String("2"));
    tab.put("3", new String("��"));
    tab.put("��", new String("3"));
    tab.put("4", new String("��"));
    tab.put("��", new String("4"));
    tab.put("5", new String("��"));
    tab.put("��", new String("5"));
    tab.put("6", new String("½"));
    tab.put("½", new String("6"));
    tab.put("7", new String("��"));
    tab.put("��", new String("7"));
    tab.put("8", new String("��"));
    tab.put("��", new String("8"));
    tab.put("9", new String("��"));
    tab.put("��", new String("9"));
    tab.put("10", new String("ʰ"));
    tab.put("ʰ", new String("10"));
    tab.put("100", new String("��"));
    tab.put("��", new String("100"));
    tab.put("1000", new String("Ǫ"));
    tab.put("Ǫ", new String("1000"));
    tab.put("10000", new String("��"));
    tab.put("��", new String("10000"));
    tab.put("100000", new String("ʰ")); //tab.put("ʰ",new String("100000"));
    tab.put("1000000", new String("��")); //tab.put("��",new String("1000000"));
    tab.put("10000000", new String("Ǫ")); //tab.put("Ǫ",new String("10000000"));
    tab.put("100000000", new String("��"));
    tab.put("��", new String("100000000"));
    tab.put("1000000000", new String("ʰ")); //tab.put("ʰ",new String("1000000000"));
    tab.put("10000000000", new String("��")); //tab.put("��",new String("10000000000"));
    tab.put("100000000000", new String("Ǫ")); //tab.put("Ǫ",new String("100000000000"));
    tab.put("1000000000000", new String("��")); //tab.put("��",new String("1000000000000"));
    tab.put("10000000000000", new String("ʰ")); //tab.put("ʰ",new String("10000000000000"));
  }

  /**
   * �����ֱ�ʾ��Ǯת��Ϊ���ֱ�ʾ��ʽ
   * @return ���ָ�ʽ�ַ���
   */
  public String toChinese() {
    String result = "";
    String strNum = this.numberMoney;
    
	String[] feeArr = strNum.split("\\Ԫ",-1);   
	strNum = feeArr[0];
	
    if(Tools.formatE(Float.parseFloat(strNum)).equals("0.00"))
      return "��Ԫ��";

    if (strNum.indexOf(".") < 0) { //ֻ������ʱ
      return this.toChineseLongPart(Long.parseLong(strNum)) + "Ԫ��";
    }

    long lngPart = Long.parseLong(strNum.substring(0, strNum.indexOf(".")));
    String fltPart = strNum.substring(strNum.indexOf(".") + 1);

    if (lngPart == 0) { //������Ϊ0,��ֻ��ʾС��,������ʾ
      result = this.toChineseDecimalPart(fltPart);
    }
    else {
      result = this.toChineseLongPart(lngPart) + "Ԫ" +
          this.toChineseDecimalPart(fltPart);
    }

    return result+"��";
  }

  /**
   * �����ֱ�ʾ��Ǯת��Ϊ���ֱ�ʾ��ʽ
   * @param numberMoney ������ʽ
   * @return ������ʽ
   */
  public String toChinese(String numberMoney) {
    this.numberMoney = numberMoney;
    return this.toChinese();
  }

  /**
   * ������������==>����
   * @param longPart ������������
   * @return ����
   */
  private String toChineseLongPart(long longPart) {
    String result = ""; //����ֵ

    if (longPart == 0) {
      return (String) tab.get(Long.toString(longPart));
    }

    String strPart = Long.toString(longPart); //ת��Ϊ�ַ�������
    if (strPart.length() > this.longPartMaxLength) {
      return "��������ֻ֧��" + this.longPartMaxLength + "λ!";
    }
    long base = 1; //����
    char bit; //ÿλ����ֵ

    for (int i = strPart.length() - 1; i >= 0; i--, base *= 10) {
      bit = strPart.charAt(i);

      if (bit == '0') { //����0�Ĵ���
        String strZero = "", strStep = "";

        if (base != 1) { //���Ǹ�λ(�м�?�����"��"
          strZero = (String) tab.get(Long.toString(0L));
        }
        else {
          strZero = "";
        }
        for (; bit == '0'; i--, base *= 10, bit = strPart.charAt(i)) { //������������0��
          if ( (base == 10000L || base == 100000000L || base == 1000000000000L)) {
            strStep = (String) tab.get(Long.toString(base));
          }
        }

        i++;
        base /= 10; //�����һλ,
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
   * С����������==>����
   * @param floatPart С����������
   * @return ����
   */
  private String toChineseDecimalPart(String floatPart) {
    String result = "";
   // String fltPart = Long.toString(floatPart);
    String fltPart = (floatPart+"00").substring(0,2);
    if (floatPart.length() == 0) { //С��Ϊ0
      return result;
    }

    char bit;
    if ( (bit = fltPart.charAt(0)) != '0') {
//   result += (String)tab.get(Character.toString(bit));
//  else
      result += (String) tab.get(Character.toString(bit)) + "��";
    }

    if ( (bit = fltPart.charAt(1)) != '0') {
//   result += (String)tab.get(Character.toString(bit));
//  else
      result += (String) tab.get(Character.toString(bit)) + "��";
    }

    return result;
  }

  /**
   * �����ֱ�ʾ��Ǯת��Ϊ���ֱ�ʾ��ʽ
   * @return ������ʽ
   */
  public String toNumber() {
    String result = "";
    String strChinese = this.chinenseMoney; //this.ChinenseMoney;
    String decimalPart = "";

    if (strChinese.indexOf("Ԫ") > 0) { //������������
      String longPart = strChinese.substring(0, strChinese.indexOf("Ԫ"));
      result += this.toNumberLongPart(longPart);
    }

    if (strChinese.indexOf("��") > 0 || strChinese.indexOf("��") > 0) { //����С������
      decimalPart = strChinese.substring(strChinese.indexOf("Ԫ") + 1);
      result += this.toNumberDecimalPart(decimalPart);
    }

    return result;
  }

  /**
   * �����ֱ�ʾ��Ǯת��Ϊ���ֱ�ʾ��ʽ
   * @param chinese ������ʽ
   * @return ������ʽ
   */
  public String toNumber(String chinese) {
    this.chinenseMoney = chinese;
    return this.toNumber();
  }

  /**
   * �������ֵĺ���==>����
   * @param lngPart ��������
   * @return ����
   */
  public String toNumberLongPart(String lngPart) {
    int i = 0;
    long result = 0, tmpResult = 0; //���ؽ��
    long bit; //����λ
    long base; //����ֵ
    long maxBase;

    String longPart = this.deleteZero(lngPart); //ȥ�����е�"��"

    int len = longPart.length();

    while (i < len) {
      tmpResult = 0;

      //��ȡǰ��λ
      bit = this.getDigit(longPart.charAt(i));
      try {
        maxBase = this.getDigit(longPart.charAt(i + 1));
      }
      catch (Exception ex) { //��ֻ��һλ
        result += bit;
        break;
      }
      tmpResult += bit * maxBase; //����λ
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
        else if (bit < 10 && base > maxBase) { //bit<10  1ǧ3��
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
   * С�����ֵĺ���==>����
   * @param decimalPart С�����ֵĺ���
   * @return ����
   */
  private String toNumberDecimalPart(String decimalPart) {
    String result = ".";
    char jiao;
    char cent;
    int index;

    if ( (index = decimalPart.indexOf("��")) >= 0) { //�Ƿ��н�
      jiao = decimalPart.charAt(index - 1);
      result += (String) tab.get(Character.toString(jiao));
    }
    else {
      result += "0";
    }

    if ( (index = decimalPart.indexOf("��")) >= 0) { //�Ƿ��з�
      cent = decimalPart.charAt(index - 1);
      result += (String) tab.get(Character.toString(cent));
    }
    else {
      result += "0";
    }

    return result;
  }

  /**
   * ȥ������������"��"�ַ�
   * @param str ����
   * @return ȥ������"��"�ĺ���
   */
  private String deleteZero(String str) {
    String noZeroStr = "";
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) != '��') {
        noZeroStr += Character.toString(str.charAt(i));
      }
    }

    return noZeroStr;
  }

  /**
   * ����һ�����ֶ�Ӧ������
   * @param charBit �����ַ�
   * @return ����
   */
  private long getDigit(char charBit) {
    String strbit = (String) tab.get(Character.toString(charBit));
    return Long.parseLong(strbit);
  }

  /**
   * ���в���
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
            //System.out.print("���������֣�");
          String inputNum = stdin.readLine(); //"1031";
          ConvertMoney money = new ConvertMoney(inputNum, ""); //����һ��ʵ��
          String chin = money.toChinese();
          break;
        case 2:
            //System.out.print("�����뺺�֣�");
          String inputChin = stdin.readLine(); //"1031";
          ConvertMoney money2 = new ConvertMoney("", inputChin); //����һ��ʵ��
          String number = money2.toNumber();
          break;
        case 0:
          System.exit(0);
          break;
      }

    } //while
  }

}
