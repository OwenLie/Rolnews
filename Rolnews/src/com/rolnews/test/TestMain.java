package com.rolnews.test;

import com.rolnews.windows.AboutFrame;

public class TestMain {

	public static void main(String args[])
	{
//		DataProcessor processor = new DataProcessor("HotWords.xml", "HotWords");
//		
//		HotWord hotWord = new HotWord("ϰ��ƽ", "http://news.baidu.com", "2015-11-25");
//		HotWord hotWord1 = new HotWord("��ǿ����", "http://news.baidu.com", "2015-11-25");
//		HotWord hotWord2 = new HotWord("IS����ը��ͻ�ը��", "http://news.baidu.com", "2015-11-25");
//		List<HotWord> words = new ArrayList<HotWord>();
//		words.add(hotWord);
//		words.add(hotWord1);
//		words.add(hotWord2);
//		processor.AddHotWord(words);
//		
//		words = processor.QueryHotWords("Word", "ϰ��ƽ");
//		Iterator<HotWord> it = words.iterator();
//		while(it.hasNext())
//		{
//			HotWord word = it.next();
//			System.out.println(word.getWord());
//			System.out.println(word.getHref());
//		}
		
//		JFrame frame = new JFrame("Build Frame");
//		frame.setLayout(null);
//		frame.setBounds(500, 500, 500, 300);
//		frame.setVisible(true);
//		
//		ImageIcon icon = new ImageIcon("C:\\Users\\profe\\OneDrive\\Work Space\\Java Program\\Rolnews\\bin\\com\\rolnews\\windows\\news.png");
//		icon.setImage(icon.getImage().getScaledInstance(200, 60, Image.SCALE_DEFAULT));
//		JLabel logo = new JLabel(icon);
//		logo.setBounds(80, 40, 200, 60);
//		
//		JLabel label = new JLabel("Rolnews Test");
//		label.setForeground(Color.black);
//		label.setFont(new Font("Dialog", 2, 25));
//		label.setBounds(110, 80, 200, 60);
//		
//		frame.add(logo);
//		frame.add(label);
		
		
//		final JTextField hello = new JTextField("ϰ��ƽ���»�");
//		hello.setFont(new Font("������κ", 1, 15));
//		hello.setForeground(new Color(0, 0, 0, 255));
//		hello.setBounds(20, 20, 400, 30);
//		
//		JButton button = new JButton("click");
//		button.setBounds(100, 100, 100, 30);
//		button.addMouseListener(new MouseAdapter()
//		{
//			//��갴���¼�
//			public void mousePressed(MouseEvent e)
//			{
//				FadeAndShow flash = new FadeAndShow(hello, "�������ţ����Ե�������Ч��");
//				Timer timer = new Timer(40, flash);
//				flash.SetTimer(timer);
//				
//				Thread thread = new Thread(flash);
//				thread.start();
//			}
//		});
//		
//		frame.add(hello);
//		frame.add(button);
	
		AboutFrame about = new AboutFrame();
		about.showAbout();
	}
}
