package com.yihaomen.test;


import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.FontUIResource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.yihaomen.mybatis.model.User;

public class Login extends JFrame {
	private JPanel panel1;
	private JLabel label1, label2;
	static JPasswordField text1;
	//static JTextField jtf1, jtf2,jtf3;
	static JTextField jtf1,jtf3;
	private MyJButton but1;
	//static JComboBox comboBox1, comboBox2,comboBox3;
	static JComboBox comboBox1,comboBox3;

	final Object[] WorkshopNo = getWorkshopNo();
	final Object[] LineLeader = getLineLeader();

	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	static {
		try {
		    //读取内部配置文件
			reader = Resources.getResourceAsReader("Configuration.xml");
			
			/*// 读取系统外配置文件 (即Jar包外文件) --- 外部工程引用该Jar包时需要在工程下创建config目录存放配置文件
		    String filePath = System.getProperty("user.dir") + "/Configuration.xml";
		    FileReader reader=new FileReader(filePath);*/
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getSession() {
		return sqlSessionFactory;
	}

	public Login() {
		super("管理人員登陸");
		setBounds(212, 159, 600, 450);
		setResizable(false);
		Container c = getContentPane();
		panel1 = new JPanel();
		panel1.setLayout(null);
		label1 = new JLabel("實時工時系統", JLabel.CENTER);
		label2 = new JLabel("管理人員：");
		text1 = new JPasswordField("");
//		text1 = new JPasswordField();


		but1 = new MyJButton("確認 ", 2);

		comboBox1 = new JComboBox(getWorkshopNo());// getWorkNo 0313199858
		comboBox1.setEditable(true);
		comboBox1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		jtf1 = (JTextField) comboBox1.getEditor().getEditorComponent();

	/*	comboBox2 = new JComboBox(getLineNoByWorkshopNo());// getLineNoByWorkNo
		comboBox2.setEditable(false);// 可編輯
		comboBox2.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		jtf2 = (JTextField) comboBox2.getEditor().getEditorComponent();*/
		
		comboBox3 = new JComboBox();// getLineNoByWorkNo
		comboBox3.addItem("");
		comboBox3.addItem("日班");
		comboBox3.addItem("夜班");
		comboBox3.setEditable(false);// 可編輯
		comboBox3.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		jtf3 = (JTextField) comboBox3.getEditor().getEditorComponent();

		label1.setBounds(150, 50, 300, 40);
		label1.setFont(new Font("微软雅黑", Font.BOLD, 40));
		but1.setFont(new Font("微软雅黑", Font.BOLD, 18));
		label2.setBounds(170, 200, 100, 30);
		text1.setBounds(270, 200, 160, 40);
		but1.setBounds(250, 300, 120, 40);
		/*comboBox1.setBounds(160, 120, 100, 30);
		comboBox2.setBounds(260, 120, 100, 30);
		comboBox3.setBounds(360, 120, 100, 30);*/
		
		comboBox1.setBounds(180, 120, 120, 40);
		comboBox3.setBounds(300, 120, 120, 40);

		panel1.add(label1);
		panel1.add(label2);
		panel1.add(text1);
		panel1.add(but1);
		panel1.add(comboBox1);
	//	panel1.add(comboBox2);
		panel1.add(comboBox3);
		c.add(panel1);
		but1.addActionListener(new TextFrame_jButton1_actionAdapter1(this));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/**
		 * @author 
		 */
		comboBox1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub

				if (e.getStateChange() == ItemEvent.SELECTED) {
					// 要执行的代码
					String key = jtf1.getText();
					System.out.println("key: " + key);
					/*comboBox2.removeAllItems();
					for (Object item : getLineNoByWorkshopNo()) {
						comboBox2.addItem(item);
					}*/
				}
			}
		});

		/*comboBox2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					jtf2.setText((String) e.getItem());
				}
			}
		});*/

	}

	public Object[] getWorkshopNo() {// TODO
		List<User> user;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			user = session.selectList("selectWorkshopNo");
			int con = user.size();
			Object[] a = new Object[con];
			for (int i = 0; i < con; i++) {
				a[i] = user.get(i).getWorkshopNo();
			}
			return a;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public Object[] getLineNoByWorkshopNo() {//TODO 線號
		List<User> user;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			String wno = jtf1.getText();
			user = session.selectList("selectLineNoByWorkshopNo", wno);
			System.out.println(user.size());
			int con = user.size() + 1;
			Object[] a = new Object[con];
			a[0] = "";
			for (int i = 0; i < con - 1; i++) {
				a[i + 1] = user.get(i).getLineNo();
			}
			return a;
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	@SuppressWarnings("rawtypes")
	public Object[] getLineLeader() {
		// TODO
		List<User> user;

		SqlSession session = sqlSessionFactory.openSession();
		user = session.selectList("selectUserByPermission");
		System.out.println("Admin.size(): " + user.size());
		Object[] a = new Object[user.size()];
		for (int i = 0; i < user.size(); i++) {
			a[i] = user.get(i).getCardID();
			// System.out.println("eif: "+ user.get(i).getCardID());
	//		System.out.println("Admin: " + a[i]);
		}
		return a;
	}

	public static void main(String args[]) {
		InitGlobalFont(new Font("微软雅黑", Font.BOLD, 18));
		Login d = new Login();
	}

	private static void InitGlobalFont(Font font) {
		FontUIResource fontRes = new FontUIResource(font);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys
				.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}
}

class TextFrame_jButton1_actionAdapter1 implements ActionListener {
	private Login adaptee;

	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	static {
		try {
			reader = Resources.getResourceAsReader("Configuration.xml");
			/* String filePath = System.getProperty("user.dir") + "/Configuration.xml";
			 FileReader reader=new FileReader(filePath);*/
			 sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getSession() {
		return sqlSessionFactory;
	}

	public TextFrame_jButton1_actionAdapter1(Login adaptee) {
		this.adaptee = adaptee;
	}

	public static boolean isHave(Object[] a, String s) {
		/*
		 * 此方法有两个参数，第一个是要查找的字符串数组，第二个是要查找的字符或字符串
		 */
		for (int i = 0; i < a.length; i++) {
			// if (obj[i].indexOf(s) != -1) {// 循环查找字符串数组中的每个字符串中是否包含所有查找的内容
			if (a[i].equals(s)) {
				return true;// 查找到了就返回真，不在继续查询
			}
		}
		return false;// 没找到返回false
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		char[] pass = adaptee.text1.getPassword();
		String CardID = new String(pass);
		System.out.println("CardID: " + CardID);
		String pattern = "^[0-9]\\d{9}$";
		Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
		Matcher m = r.matcher(CardID);
	//	String LineNo = Login.jtf2.getText();
		String Shift = "";

		Object ShiftName = Login.comboBox3.getSelectedItem();
		System.out.println("test"+ShiftName);
		
		/*if (Login.jtf2.getText().equals("")) {
			JOptionPane.showMessageDialog(adaptee, "未選擇線別，請選擇");
			return;
		}*/
		
		if (ShiftName.equals("")) { 
			JOptionPane.showMessageDialog(adaptee, "未選擇班別，請選擇");
			return;
		}
		if (m.matches() == true) {
			Object[] a = adaptee.LineLeader;
			if(ShiftName.equals("日班")){
				Shift="D";
			}else if(ShiftName.equals("夜班")){
				Shift="N";
			}
			if (isHave(a, CardID)) {// 调用自己定义的函数isHave，如果包含则返回true,否则返回false
				System.out.println("成功登陸！");
				adaptee.dispose();
				String WorkshopNo = Login.jtf1.getText();
				if(Shift.equals("D")){
					//JLabelA d = new JLabelA(WorkshopNo, LineNo);
					JLabelA d = new JLabelA(WorkshopNo);
				}else if(Shift.equals("N")){
					//JLabelN d = new JLabelN(WorkshopNo, LineNo);
					JLabelN d = new JLabelN(WorkshopNo);
				}
				
			} else {
				JOptionPane.showMessageDialog(adaptee, "輸入有誤，請重新刷卡");
				System.out.println("不包含");// 打印结果
			}
		} else {
			JOptionPane.showMessageDialog(adaptee, "輸入有誤，請重新刷卡");
			System.out.println("不合法卡號，含有非數字字符或卡號長度不正確");
		}
	}
}
