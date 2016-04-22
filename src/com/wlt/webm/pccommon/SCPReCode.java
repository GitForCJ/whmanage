package com.wlt.webm.pccommon;

import java.util.HashMap;
import java.util.Map;

/**
 * SMP��SCPͨѶ����<br>
 */
public class SCPReCode {
    /**
     * ����Map
     */
	private static Map scprecode = new HashMap();
	/**
	 * ����scp��Ӧ��Ϣ
	 *
	 */
	static {
		scprecode.put("000", "���׳ɹ�");
		scprecode.put("001", "���װ���ʽ����");
		scprecode.put("002", "��������");
		scprecode.put("003", "������,���ױ�ȡ��");
		scprecode.put("004", "���˻����ڽ���");
		scprecode.put("005", "MACУ�����");
		scprecode.put("006", "ϵͳ����ʧ��");
		scprecode.put("010", "ϵͳ����,�޷�����");
		scprecode.put("011", "δ����Ľ�����");
		scprecode.put("020", "�ʽ��˻�������");
		scprecode.put("021", "�ʽ��˻��������");
		scprecode.put("022", "�ʽ��˻��Ѿ�����");
		scprecode.put("023", "�ʽ��˻�����");
		scprecode.put("024", "�˱ʽ��׽����ʽ��˻�����������߽��");
		scprecode.put("025", "�˴ν��׽���ѳ����ʽ��˻������Ѷ��");
		scprecode.put("026", "�ʽ��˻���δ���ö������");
		scprecode.put("027", "�ʽ��˻�������Ԥ��ֵ");
		scprecode.put("030", "�ն���Ϣ��֤ʧ��");
		scprecode.put("031", "�ն��Ѿ�����");
		scprecode.put("032", "������벻�ǰ󶨵绰");		
		scprecode.put("033", "�ն˽���������");
		scprecode.put("034", "PSAM���Ų�����");	
		scprecode.put("035", "��Ʒѷ�����Ϣʧ��");	
		scprecode.put("036", "���࿨������");		
		scprecode.put("045", "�ɷѹ���Ƶ��,���Ժ��ٽ�");		
		scprecode.put("047", "���ڴ����У������ظ��ɷ�");	
		scprecode.put("059", "���컧δ�������˻�");
		scprecode.put("070", "�޿��ü�¼");	
		scprecode.put("071", "�Ǳ����û�");	
		scprecode.put("072", "�쳣���");	
		scprecode.put("073", "���û��ʵ�����7����,�������޷������ɷ�,��֪ͨ�û�������Ӫҵ���ɷ�");	
		scprecode.put("074", "������δǩ�����շ�Э��,��������յ��Ż���");	
		scprecode.put("075", "���·�Ʊ�Ѵ�ӡ");	
		scprecode.put("076", "���ʲ�ƽ");			
		scprecode.put("077", "����ƽ");			
		scprecode.put("102", "û��Ƿ��");
		scprecode.put("110", "������벻����");
		scprecode.put("111", "�û��������");
		scprecode.put("112", "���ܷ���");
		scprecode.put("113", "����ָ���Ŀͻ���");
		scprecode.put("114", "�ж�ֱ��˺�");
		scprecode.put("115", "������ˮ������");
		scprecode.put("116", "û��Ҫ��ӡ�ĵ���");
		scprecode.put("117", "�ñ���ˮ�ѷ���");
		scprecode.put("118", "�ñ���ˮ������");
		scprecode.put("200", "����ʱ�ܶ�˶Բ�ƥ��");
		scprecode.put("222", "������");
		scprecode.put("666", "�Ʒ�ϵͳ����");
		scprecode.put("777", "Ҫ����˵���ϸ̫��");
		
//		******************** ʡ��ϵͳ����������� *********************
		scprecode.put("500", "���Ų�ѯʧ�ܣ����Ժ�����");
		scprecode.put("501", "���û���ֵ���ޣ�����ѯ10000");
		scprecode.put("502", "���緱æ�����Ժ�����");
		scprecode.put("503", "ϵͳ��æ�����Ժ�����");
		scprecode.put("504", "���˻���֤������");
		scprecode.put("505", "ϵͳ�ս��У���10���Ӻ�����");
		
		scprecode.put("506", "�û������䶳�ڣ�����ѯ10000");
		scprecode.put("507", "�û�δʹ��");
		scprecode.put("508", "�û����ڹ�ʧ״̬");
		scprecode.put("509", "��ʡ�󸶷��û��ݲ��ṩ����ѯ");
		scprecode.put("510", "�û�������տ�������������ѯ10000");
		scprecode.put("511", "�û�����������ƥ��");
		scprecode.put("512", "���й������û������ܳ�ֵ");
		scprecode.put("513", "�Ҳ����û�������");
		scprecode.put("514", "�û����ϴ���");
		scprecode.put("515", "�û���������ֵ������ѯ10000");
		scprecode.put("516", "VCƽ̨�����У����Ժ�����");
		scprecode.put("517", "VCƽ̨�ڲ�����");
		scprecode.put("518", "VCƽ̨���ݿ����ʧ��");
		scprecode.put("519", "VCƽ̨�����������ݲ��ܳ�ֵ");
		scprecode.put("520", "��Ʒ�ϵͳ�����쳣�����Ժ�����");
		scprecode.put("521", "VCϵͳ��ֵʧ��");
		scprecode.put("522", "��ֵʧ�ܣ�����ֵ�û����ޣ�����ѯ10000");
		scprecode.put("523", "�˻��¸����û����࣬���ܳ�ֵ");
		scprecode.put("524", "��ֵʧ�ܣ���������ֵ�û�������޶�");
		scprecode.put("525", "��ֵʧ�ܣ�������ֵ���ɲ�ֳ�ֵ");
		scprecode.put("526", "��ֵʧ�ܣ���ֳ�ֵ��ĳ��ʧ�ܵ�������ع�");
	}

	/**
	 * ������Ӧ������Ӧ��Ϣ
	 * @param scpReCode
	 * @return ��Ӧ��Ϣ
	 */
	public static String getSCPreMsg(String scpReCode) {
		String mess=(String) scprecode.get(scpReCode);
		if(mess==null){
			mess="��������";
		}
		return mess;
	}
}
