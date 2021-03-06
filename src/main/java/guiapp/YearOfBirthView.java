package guiapp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class YearOfBirthView extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField biryearField, birmonthField, birdateField, fakeageField;
	JLabel biryearlabel, birmonthlabel, birdatelabel, fakeagelabel, reallabel, differlabel, iconlabel;
	JTextPane labelarea;

	JButton b1, b2, b3;
	ImageIcon etoicon = null;
	JPanel panel1;

	YearMonthDate ymd = new YearMonthDate();
	String faStr = "";
	String bdStr = "";
	int realage = 0;
	String rmessage = "";
	String dmessage = "";

	YearOfBirthView() {
		super("生まれ年説明書");
		setSize(450, 730);

		List<JTextField> fields = new ArrayList<JTextField>();
		for (int i = 0; i < 4; i++) {
			fields.add(new JTextField(""));
		}
		for (JTextField f : fields) {
			f.setAlignmentX(0.5f);
			f.addActionListener(this);
		}

		this.biryearField = fields.get(0);
		this.birmonthField = fields.get(1);
		this.birdateField = fields.get(2);
		this.fakeageField = fields.get(3);
		biryearField.setDocument(new TextLimit(4));
		birmonthField.setDocument(new TextLimit(2));
		birdateField.setDocument(new TextLimit(2));
		fakeageField.setDocument(new TextLimit(2));

		biryearlabel = new JLabel("あなたの実際の誕生年を半角数字で入力してください。 (例)1995");
		birmonthlabel = new JLabel("あなたの誕生月を半角数字で入力してください。 (例)4");
		birdatelabel = new JLabel("あなたの誕生日の日付を半角数字で入力してください。 (例)20");
		fakeagelabel = new JLabel("何歳と自称したいですか？半角数字で入力してください。 (例)21");
		reallabel = new JLabel(rmessage);
		differlabel = new JLabel(dmessage);
		labelarea = new JTextPane();
		labelarea.setText("");
		iconlabel = new JLabel(etoicon);

		JLabel labels[] = { biryearlabel, birmonthlabel, birdatelabel, fakeagelabel, reallabel, differlabel,
				iconlabel };
		for (JLabel l : labels) {
			l.setAlignmentX(0.5f);
		}

		b1 = new JButton("生年月日確定");
		b1.setFont(new Font("Monospaced", Font.PLAIN, 12));
		b1.setAlignmentX(0.5f);
		b2 = new JButton("自称年齢確定");
		b2.setFont(new Font("Monospaced", Font.PLAIN, 12));
		b2.setAlignmentX(0.5f);
		b3 = new JButton("リセット");
		b3.setFont(new Font("Monospaced", Font.PLAIN, 12));
		b3.setAlignmentX(0.5f);

		panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

		panel1.add(biryearlabel);
		panel1.add(biryearField);
		panel1.add(birmonthlabel);
		panel1.add(birmonthField);
		panel1.add(birdatelabel);
		panel1.add(birdateField);
		panel1.add(b1);
		panel1.add(fakeagelabel);
		panel1.add(fakeageField);
		panel1.add(b2);
		panel1.add(b3);
		panel1.add(reallabel);
		panel1.add(differlabel);

		labelarea.setOpaque(false);
		labelarea.setEditable(false);
		labelarea.setFocusable(false);
		SimpleAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
		labelarea.setParagraphAttributes(attribs, true);

		panel1.add(labelarea);
		panel1.add(iconlabel);

		Container contentPane = getContentPane();
		contentPane.add(panel1, BorderLayout.PAGE_START);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b2.setEnabled(false);
		b3.addActionListener(this);
		fakeageField.setEnabled(false);
		// 閉じた時の処理
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent ev) {
				System.exit(0);
			}
		});
	}

	// 確定ボタンが押された時の反応
	public void actionPerformed(ActionEvent ev) {
		Object obj = ev.getSource();

		if (obj == b1) {
			String sbyear = biryearField.getText();
			String sbmonth = birmonthField.getText();
			String sbdate = birdateField.getText();
			biryearField.setText("");
			birmonthField.setText("");
			birdateField.setText("");
			biryearField.requestFocus();
			makebdStr(sbyear, sbmonth, sbdate);
			reallabel.setText(rmessage);
		}
		if (obj == b2) {
			faStr = fakeageField.getText();
			fakeageField.setText("");
			fakeageField.requestFocus();
			try {
				infoyear();
				etoicon = new ImageIcon(getClass().getResource(ymd.getIconimg()));
				iconlabel.setIcon(etoicon);
			} catch (Exception e) {
				faStr = "";
				dmessage = "自称年齢を半角で入力して下さい。";
			}
			differlabel.setText(dmessage);
		}
		if (obj == b3) {
			bdStr = "";
			faStr = "";
			realage = 0;
			rmessage = "";
			dmessage = "";
			reallabel.setText(rmessage);
			differlabel.setText(dmessage);
			biryearField.setEnabled(true);
			birmonthField.setEnabled(true);
			birdateField.setEnabled(true);
			b1.setEnabled(true);
			fakeageField.setEnabled(false);
			b2.setEnabled(false);
			labelarea.setText("");
			etoicon = null;
			iconlabel.setIcon(null);
			biryearField.setText("");
			birmonthField.setText("");
			birdateField.setText("");
			fakeageField.setText("");
			biryearField.requestFocus();
		}
	}

	// 入力した生年月日をまとめる。
	public void makebdStr(String sbyear, String sbmonth, String sbdate) {
		bdStr = sbyear + "/" + sbmonth + "/" + sbdate;
		biryearField.setText("");
		birmonthField.setText("");
		birdateField.setText("");
		try {
			infoage();
		} catch (Exception e) {
			resetbirthday();
		}
	}

	// 入力した誕生年とそこから計算した実年齢を表示
	public void infoage() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		dateFormat.setLenient(false);
		Date date;
		date = dateFormat.parse(bdStr);
		ymd.getbirth(date);
		realage = ymd.calcAge();
		if (realage > 0) {
			fakeageField.setEnabled(true);
			fakeageField.requestFocus();
			b2.setEnabled(true);
			biryearField.setEnabled(false);
			birmonthField.setEnabled(false);
			birdateField.setEnabled(false);
			b1.setEnabled(false);
			rmessage = "あなたは" + ymd.getBiryear() + "年生まれ" + realage + "歳ですね？";
		} else {
			resetbirthday();
		}
	}

	public void resetbirthday() {
		rmessage = "生年月日の数値が不正です。";
		bdStr = "";
		realage = 0;
	}

	// 自称年齢を確定後に表示されるメッセージ
	// 何歳サバを読むか
	public void infoyear() {
		int fakeage = Integer.parseInt(faStr);
		int differ = realage - fakeage;
		int fakeyear = ymd.getBiryear() + differ;
		if (differ < 0) {
			fakeageField.setEnabled(false);
			b2.setEnabled(false);
			dmessage = -(differ) + "歳年上にサバを読みます。";
		} else if (differ >= 0) {
			fakeageField.setEnabled(false);
			b2.setEnabled(false);
			dmessage = differ + "歳サバを読みます。";
		} else {
			faStr = "";
			dmessage = "自称年齢を半角で入力して下さい。";
		}
		// 自称年齢の誕生日の説明
		Calendar fakebd = ymd.getBirthday();
		fakebd.set(Calendar.YEAR, fakeyear);
		Date fakedate = fakebd.getTime();
		String fakewareki = ymd.warekiYear(fakedate);
		ymd.calcEto(fakeyear);
		labelarea.setText("私は" + fakeyear + "年(" + fakewareki + "年)生まれ：" + fakeage + "歳です。\r\n"
				+ ymd.showFakeBirthday(fakeyear) + "干支は" + ymd.getEto() + "年です。\r\n" + ymd.incidentAge(fakeyear));
	}
}
