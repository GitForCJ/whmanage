<%@ page contentType="text/html; charset=gb2312" language="java"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
<%@ page import="java.util.*" %>
<?xml version="1.0" encoding="GB2312"?> 
<tree>
<%
  String userole=userSession.getUser_role();
  List menuList = user.getMenuList(userole);  //���ݽ�ɫid��ò˵��� 
  List firstMenuList = user.getChildMenuList(menuList,"0");   //һ���˵����ڵ�Ϊ"0"
%>

<% for(int i=0;i<firstMenuList.size();i++){ 
    String[] firstMenu = (String[]) firstMenuList.get(i);%>
  <entity>
    <eid><%=firstMenu[0]%></eid>
    <description><%=firstMenu[1]%></description>
    <oncontextmenu></oncontextmenu>
    <image>images/book.gif</image>
    <imageOpen>images/bookopen.gif</imageOpen>
    <dirurl></dirurl>
    <contents>
    <% 
      List twoMenuList = user.getChildMenuList(menuList,firstMenu[0]);    //�����˵���
      for(int j=0;j<twoMenuList.size();j++){
        String[] twoMenu = (String[]) twoMenuList.get(j);
        List threeMenuList = user.getChildMenuList(menuList,twoMenu[0]);
        if(threeMenuList.size() > 0){  //�ж��Ƿ��������˵�
    %>
      <entity>
      <eid><%=twoMenu[0]%></eid>
      <description><%=twoMenu[1]%></description>
      <oncontextmenu></oncontextmenu>
      <image>images/book.gif</image>
      <imageOpen>images/bookopen.gif</imageOpen>
      <dirurl></dirurl>
    <contents>  
    <%
          for(int k=0;k<threeMenuList.size();k++){   //�����˵���
            String[] threeMenu = (String[]) threeMenuList.get(k);
            List fourMenuList = user.getChildMenuList(menuList,threeMenu[0]);
            if(fourMenuList.size() > 0){  //�ж��Ƿ����ļ��˵�
    %>
        <entity>
        <eid><%=threeMenu[0]%></eid>
        <description><%=threeMenu[1]%></description>
        <oncontextmenu></oncontextmenu>
        <image>images/book.gif</image>
        <imageOpen>images/bookopen.gif</imageOpen>
        <dirurl></dirurl>
      <contents>  
          <%
            for(int n=0;n<fourMenuList.size();n++){                        //�ļ��˵�
              String[] fourMenu = (String[]) fourMenuList.get(n);
          %>
            <entity>
              <eid><%=fourMenu[0]%></eid>
              <description><%=fourMenu[1]%></description>
              <image>images/paper.gif</image>
              <imageOpen>images/paper.gif</imageOpen>
                <onClick>displayCustomer(12345)</onClick>
              <dirurl>..<%=fourMenu[2]%></dirurl>
            </entity> 
          <%  }%>
          </contents>
            </entity>
         <%
          }else{
        %>
          <entity>
            <eid><%=threeMenu[0]%></eid>
            <description><%=threeMenu[1]%></description>
            <image>images/paper.gif</image>
            <imageOpen>images/paper.gif</imageOpen>
              <onClick>displayCustomer(12345)</onClick>
            <dirurl>..<%=threeMenu[2]%></dirurl>
          </entity>
    <%
            }
            }
    %>
        </contents>
        </entity>
    <%
        }else{  //û�����˵�
    %>
          <entity>
            <eid><%=twoMenu[0]%></eid>
            <description><%= twoMenu[1]%></description>
            <image>images/paper.gif</image>
            <imageOpen>images/paper.gif</imageOpen>
              <onClick>displayCustomer(12345)</onClick>
            <dirurl>..<%=twoMenu[2]%></dirurl>
          </entity>
   <%
        }
    
      }
    %>
</contents>
    </entity>
<%} %>
      <entity>
        <eid>entity6_5</eid>
        <description>ϵͳ�˳�</description>
        <image>images/book.gif</image>
        <imageOpen>images/book.gif</imageOpen>
          <onClick>displayCustomer(12345)</onClick>
        <dirurl>../rights/logout.jsp</dirurl>
      </entity>
</tree>
