package com.rolnews.loadnews;


/**
 * ������ʾ�������������ݷ���
 * @author Owen Lie
 */
public class StyleControl {

//	private String strDest;
	private String logoLoc;
    
//    private final String strHtmlEnd = 
//    		"</span><br/>" +
//    		"</body>" +
//    		"</html>";
    
    public String getLogo(String url)
    {
    	String logoLoc = "�������� -- ���Ÿ�Ҫ";
    	
    	if(url.contains("baidu"))
    		logoLoc = "�ٶ����� -- ȫ��������������ƽ̨";
    	else if(url.contains("cnr"))
    		logoLoc = "����� -- ��������㲥��̨";
    	else if(url.contains("sohu"))
    		logoLoc = "�Ѻ����� -- �Ѻ�";
    	else if(url.contains("163.com"))
    		logoLoc = "�������� -- ��̬�ȵ������Ż�";
    	else if(url.contains("sina"))
    		logoLoc = "������������";
    	else if(url.contains("ifeng"))
    		logoLoc = "����� -- �й����ȵ��ۺ��Ż���վ";
    	else if(url.contains("huanqiu"))
    		logoLoc = "������ -- ȫ���������Ż�";
    	
    	this.logoLoc = logoLoc;
    	
    	return this.logoLoc;
    }
    
//    public String CovertDestionString(String strDest)
//    {
//        if(strDest.trim().equals("<br>"))
//        	return null;
//        
//        this.strDest = "<html>" +
//        		"<head>" +
//        		"<title>��������</title>" +
//        		"</head>" +
//        		"<body>" +
//        		"<span>" + logoLoc + "</span><br/>" +
//        		"<span style=\"font-family:����\">" + strDest + strHtmlEnd;
//        
//        return this.strDest;
//    }
}
