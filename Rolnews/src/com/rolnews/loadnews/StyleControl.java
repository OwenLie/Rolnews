package com.rolnews.loadnews;


/**
 * 控制显示的新闻描述数据分行
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
    	String logoLoc = "滚动新闻 -- 新闻概要";
    	
    	if(url.contains("baidu"))
    		logoLoc = "百度新闻 -- 全球最大的中文新闻平台";
    	else if(url.contains("cnr"))
    		logoLoc = "央广网 -- 中央人民广播电台";
    	else if(url.contains("sohu"))
    		logoLoc = "搜狐新闻 -- 搜狐";
    	else if(url.contains("163.com"))
    		logoLoc = "网易新闻 -- 有态度的新闻门户";
    	else if(url.contains("sina"))
    		logoLoc = "新浪新闻中心";
    	else if(url.contains("ifeng"))
    		logoLoc = "凤凰网 -- 中国领先的综合门户网站";
    	else if(url.contains("huanqiu"))
    		logoLoc = "环球网 -- 全球生活新门户";
    	
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
//        		"<title>详情描述</title>" +
//        		"</head>" +
//        		"<body>" +
//        		"<span>" + logoLoc + "</span><br/>" +
//        		"<span style=\"font-family:宋体\">" + strDest + strHtmlEnd;
//        
//        return this.strDest;
//    }
}
