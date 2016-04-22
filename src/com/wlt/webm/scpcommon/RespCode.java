/**
 * 
 */
package com.wlt.webm.scpcommon;

/**
 * ��������Ϣ
 * �������ڣ�2013-11-18
 * caiSJ-12-27
 * Company�����������Ƽ����޹�˾
 * Copyright: Copyright (c) 2008
 * @author caiSJ
 */
public class RespCode {
	
	/**
	 * ���׳ɹ�
	 */
	public final static String RC_SUCCESS = "000";
	/**
	 * ���׳ɹ�
	 */
	public final static String TY = "0000";
	
	/**
	 * ����ʧ��--���װ���ʽ����
	 */
	public final static String RC_PKGERR = "001";
	
	/**
	 * ����ʧ��--δ֪ԭ��
	 */
	public final static String RC_FAIL = "002";

	/**
	 * ��ʱ,���ױ�ȡ��
	 */
	public final static String RC_TIMEOUT = "003";
	
	/**
	 * ���˻����ڽ���
	 */
	public final static String RC_DOINGNOW = "004";
	
	/**
	 * MACУ�����
	 */
	public final static String RC_MACERR = "005";
	
	/**
	 * ���ݿ�����쳣
	 */
	public final static String RC_DATEBASE_ERR = "006";
	
	/**
	 * ͳ�����ڿ�ȱ���С��2����
	 */
	public final static String RC_COUNTDATE_ERR = "007";
	
	/**
	 * ϵͳ����,�޷�����
	 */
	public final static String RC_SYSLOCK = "010";
	
	/**
	 * δ����Ľ�����
	 */
	public final static String RC_NOSCODE = "011";

	/**
	 * �ʽ��ʻ�������
	 */
	public final static String RC_FACCT_NOFIND = "020";

	/**
	 * ͳһ֧���ʻ��������
	 */
	public final static String RC_FACCT_PWDERR = "021";

	/**
	 * �ʽ��ʻ��Ѿ�����
	 */
	public final static String RC_FACCT_DROP = "022";

	/**
	 * �ʽ��ʻ�����
	 */
	public final static String RC_FACCT_NOLEFT = "023";
	
	/**
	 * �˱ʽ��׽����ʽ��˻�����������߽��
	 */
	public final static String RC_FACCT_OVERONCE = "024";

	/**
	 * �˴ν��׽���ѳ����ʽ��˻������Ѷ��
	 */
	public final static String RC_FACCT_OVERDAYCOST = "025";
	
	/**
	 * �ʽ��ʻ���δ���ö������
	 */
	public final static String RC_FACCT_NOLIMIT = "026";
	
	/**
	 * �ʽ��˻����С��Ԥ��ֵ
	 */
	public final static String RC_FACCT_LESSWARN = "027";
	
	/**
	 * ����ת�˹����Ѿ��ر�
	 */
	public final static String RC_SWITCH_OFF = "028";
	/**
	 * �ն���Ϣ��֤ʧ��
	 */
	public final static String RC_TERMERR ="030";
	
	/**
	 * �ն��Ѿ�����
	 */
	public final static String RC_TERMDROP="031";
	
	/**
	 * ������벻�ǰ󶨵绰
	 */
	public final static String RC_TERMBIND="032";
	
    /**
     * �ն˽���������
     */
	public final static String RC_TERMACCESSERR="033";
	
	/**
	 * PSAM���Ų�����
	 */
	public final static String RC_NOPSAMCARD="034";
	
	/**
	 * ��Ʒѷ�����Ϣʧ��
	 */
	public final static String RC_SENDBILL_ERR="035";
	
	/**
	 * ���࿨������
	 */
	public final static String RC_NOCARD="036";
	/**
	 * ƽ̨�޿�����
	 */
	public final static String RC_NOCARDDATA = "038";
	/**
	 * ���ش�ӡ����
	 */
	public final static String RC_NOREPRINT_DATA = "039";
	
	/**
	 * �����ѹ���Ч��
	 */
	public final static String RC_ORDER_OVERTIME="040";
	
	/**
	 * ����������
	 */
	public final static String RC_NOFIND_ORDER="041";
	
	/**
	 * ������֧��
	 */
	public final static String RC_ISPAY_ORDER="042";
	
	/**
	 * ƽ̨�޴�����Ϣ
	 */
	public final static String RC_QRYBUSI_ORDER="043";
	
	/**
	 * ��ˮ�Ų�ѯ �޴���ˮ��
	 */
	public final static String RC_SERIALQRY_ORDER="044";

	/**
	 * �ɷѹ���Ƶ��
	 */
	public final static String RC_PAYBUSI="045";
	
	/**
	 * Ӷ��ͳ�ƴ��� ��Ӷ��
	 */
	public final static String RC_COMMSETTERROR="046";
	
	/**
	 * �ظ��ɷ�
	 */
	public final static String RC_PAYAGAIN="047";
	
	/**
	 * 
	 */
	public final static String RC_NO_TRADE="050";
	/**
	 * 
	 */
	public final static String RC_BANK_ERR="051";
	/**
	 * 
	 */
	public final static String RC_PAYFEE_ERR="052";
	/**
	 * 
	 */
	public final static String RC_BANKCARD_ERR="053";
	/**
	 * 
	 */
	public final static String RC_BANKCARD_OVER="054";
	/**
	 * 
	 */
	public final static String RC_BANKCARD_LOCK="055";
	/**
	 * ͳһ֧���˻�����
	 */
	public final static String RC_BANK_NOLEFT="056";
	/**
	 * û�й�����Ϣ
	 */
	public final static String NOPOSNOTE="057";
	/**
	 * ƽ̨�޽ɷ���ֵ����
	 */
	public final static String RC_NOFILLFEEDATA = "058";
	/**
	 * ���컧δ�������˻�
	 */
	public final static String RC_NOBANDBANK = "059";
	/**
	 * û��Ƿ��
	 */
	public final static String RC_NOAMT="102";
	
	/**
	 * ������벻����
	 */
	public final static String RC_NONBR="110";
	
	/**
	 * �û��������
	 */
	public final static String RC_ERRPASS="111";
	
	/**
	 * ���ܷ���
	 */
	public final static String RC_NOTCANCEL="112";
	
	/**
	 * ����ָ���Ŀͻ���
	 */
	public final static String RC_ERRCLIENT="113";
	
	/**
	 * �ж�ֱ��˺�
	 */
	public final static String RC_ERRACCT="114";
	
	/**
	 * ������ˮ������
	 */
	public final static String RC_NOCANCELSERIAL="115";
	
	/**
	 * û��Ҫ��ӡ�ĵ���
	 */
	public final static String RC_NOPRN="116";
	
	/**
	 * �ñ���ˮ�ѷ���
	 */
	public final static String RC_HASCANCEL="117";
	
	/**
	 * �ñ���ˮ������
	 */
	public final static String RC_HASCOMIT="118";
	
	/**
	 * ����ʱ�ܶ�˶Բ�ƥ��
	 */
	public final static String RC_CHECK_ERROR="200";
	
	/**
	 * ������
	 */
	public final static String RC_AMTERR="222";
	
	/**
	 * ϵͳ����
	 */
	public final static String RC_SYSERR="666";
	
	/**
	 * Ҫ����˵���ϸ̫��
	 */
	public final static String RC_TOOMUCH="777";
	
	/**
	 * �۷�����ȡ���ɹ�
	 */
	public final static String RC_CANCLE_CUSTORDER="999";
	
	
	//******************** ʡ��ϵͳ����������� *********************
	/**
	 * ���Ų�ѯʧ�ܣ����Ժ�����
	 */
	public final static String RC_QUERY_FAIL = "500";
	
	/**
	 * ���û���ֵ���ޣ�����ѯ10000
	 */
	public final static String RC_FEE_FAIL = "501";
	
	/**
	 * ���緱æ�����Ժ�����
	 */
	public final static String RC_NET_BUSY = "502";
	
	/**
	 * ϵͳ��æ�����Ժ�����
	 */
	public final static String RC_SYSTEM_BUSY ="503";
	
	/**
	 * ��֤������
	 */
	public final static String RC_LEFTFEE = "504";
	
	/**
	 * ϵͳ�ս��У���10���Ӻ�����
	 */
	public final static String RC_SYSTEM_CHECK = "505";

}
