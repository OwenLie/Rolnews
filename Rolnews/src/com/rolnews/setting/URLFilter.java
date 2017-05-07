package com.rolnews.setting;

/**
 * ����Arguments.WEBSURL�е���ַ��Ϣ����Ϲ��캯�����������ֵɸѡ��ַ
 * 
 * @author Owen Lie
 */
public class URLFilter {

	/**
	 * ɸѡ��ַ
	 */
	public String getCategoryURL(String url, String category)
	{
		String result = null;
		switch(url)
		{
		case "http://news.baidu.com/":
			result = selectURL(category, "baidu");
			break;
		case "http://news.sohu.com/":
			result = selectURL(category, "souhu");
			break;
		case "http://www.163.com/":
			result = selectURL(category, "wangyi");
			break;
		case "http://www.sina.com.cn/":
			result = selectURL(category, "sina");
			break;
		case "http://www.ifeng.com/":
			result = selectURL(category, "ifeng");
			break;
		case "http://www.huanqiu.com/":
			result = selectURL(category, "huanqiu");
			break;
		default:
			break;
		}
		return result;
	}
	
	/**
	 * ���ݴ���Ĳ�����ȡɸѡ����Ӧ����ַ
	 */
	private String selectURL(String category, String webs)
	{
		String result = "";
		switch(category)
		{
			//��������
			case "yule":
				switch(webs)
				{
					case "baidu":
						result = "http://yule.baidu.com/";
						break;
					case "souhu":
						result = "http://yule.sohu.com/";
						break;
					case "wangyi":
						result = "http://ent.163.com/";
						break;
					case "sina":
						result = "http://ent.sina.com.cn/";
						break;
					case "ifeng":
						result = "http://ent.ifeng.com/";
						break;
					case "huanqiu":
						result = "http://ent.huanqiu.com/";
						break;
					default:
						result = null;
						break;
				}break;
			//��������
			case "junshi":
				switch(webs)
				{
				case "baidu":
					result = "http://mil.news.baidu.com/";
					break;
				case "souhu":
					result = "http://mil.sohu.com/";
					System.out.println("������" + result);
					break;
				case "wangyi":
					result = "http://war.163.com/";
					break;
				case "sina":
					result = null;
					break;
				case "ifeng":
					result = "http://news.ifeng.com/mil/";
					break;
				case "huanqiu":
					result = "http://mil.huanqiu.com/";
					break;
				default:
					result = null;
					break;
				}break;
			//�ƾ�����
			case "caijing":
				switch(webs)
				{
				case "baidu":
					result = "http://finance.baidu.com/";
					break;
				case "souhu":
					result = "http://business.sohu.com/";
					break;
				case "wangyi":
					result = "http://money.163.com/";
					break;
				case "sina":
					result = "http://finance.sina.com.cn/";
					break;
				case "ifeng":
					result = "http://finance.ifeng.com/";
					break;
				case "huanqiu":
					result = "http://finance.huanqiu.com/";
					break;
				default:
					result = null;
					break;
				}break;
			//��������
			case "tiyu":
				switch(webs)
				{
				case "baidu":
					result = "http://sports.baidu.com/";
					break;
				case "souhu":
					result = "http://sports.sohu.com/";
					break;
				case "wangyi":
					result = "http://sports.163.com/";
					break;
				case "sina":
					result = "http://sports.sina.com.cn/";
					break;
				case "ifeng":
					result = "http://sports.ifeng.com/";
					break;
				case "huanqiu":
					result = null;
					break;
				default:
					result = null;
					break;
				}break;
			//��������
			case "shehui":
				switch(webs)
				{
				case "baidu":
					result = "http://shehui.news.baidu.com/";
					break;
				case "souhu":
					result = null;
					break;
				case "wangyi":
					result = null;
					break;
				case "sina":
					result = null;
					break;
				case "ifeng":
					result = null;
					break;
				case "huanqiu":
					result = "http://society.huanqiu.com/";
					break;
				default:
					result = null;
					break;
				}break;
			//�Ƽ�����
			case "keji":
				switch(webs)
				{
				case "baidu":
					result = "http://tech.baidu.com/";
					break;
				case "souhu":
					result = "http://it.sohu.com/";
					break;
				case "wangyi":
					result = "http://tech.163.com/";
					break;
				case "sina":
					result = "http://tech.sina.com.cn/";
					break;
				case "ifeng":
					result = "http://tech.ifeng.com/";
					break;
				case "huanqiu":
					result = "http://tech.huanqiu.com/";
					break;
				default:
					result = null;
					break;
				}break;
			//��Ϸ����
			case "youxi":
				switch(webs)
				{
				case "baidu":
					result = "http://youxi.news.baidu.com/";
					break;
				case "souhu":
					result = null;
					break;
				case "wangyi":
					result = "http://play.163.com/";
					break;
				case "sina":
					result = null;
					break;
				case "ifeng":
					result = "http://games.ifeng.com/";
					break;
				case "huanqiu":
					result = "http://game.huanqiu.com/";
					break;
				default:
					result = null;
					break;
				}break;
			//��ʷ����
			case "lishi":
				switch(webs)
				{
				case "baidu":
					result = null;
					break;
				case "souhu":
					result = "http://history.sohu.com/";
					break;
				case "wangyi":
					result = null;
					break;
				case "sina":
					result = "http://history.sina.com.cn/";
					break;
				case "ifeng":
					result = null;
					break;
				case "huanqiu":
					result = "http://history.huanqiu.com/";
					break;
				default:
					result = null;
					break;
				}break;
			default:
				result = null;
				break;
		}
		return result;
	}
}
