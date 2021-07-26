package guiapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class YearMonthDate {
	private String eto = "";
	private String iconimg = "";
	private Calendar birthday;
	private int biryear;
	private int birmonth;
	private int birdate;

	public String getEto() {
		return eto;
	}

	public String getIconimg() {
		return iconimg;
	}

	public Calendar getBirthday() {
		return birthday;
	}

	public int getBiryear() {
		return biryear;
	}

	// 誕生年月日を取得
	public void getbirth(Date date) {
		birthday = Calendar.getInstance();
		birthday.setTime(date);
		biryear = birthday.get(Calendar.YEAR);
		birmonth = birthday.get(Calendar.MONTH);
		birdate = birthday.get(Calendar.DATE);
	}

	// 現在の年齢を計算
	public int calcAge() {
		Calendar todayCal = Calendar.getInstance();
		int tyear = todayCal.get(Calendar.YEAR);
		int tmonth = todayCal.get(Calendar.MONTH);
		int tdate = todayCal.get(Calendar.DATE);

		int age = tyear - biryear;
		if (tmonth < birmonth || (tmonth == birmonth && tdate < birdate)) {
			age--;
		}
		return age;
	}

	// 和暦を表示
	public String warekiYear(Date wdate) {
		Locale locale = new Locale("ja", "JP", "JP");
		Calendar warekical = Calendar.getInstance(locale);
		warekical.setTime(wdate);

		DateFormat japaneseFormat = new SimpleDateFormat("GGGGy", locale);
		String wayear = japaneseFormat.format(warekical.getTime());
		return wayear;
	}

	// うるう日生まれの人はうるう日がない年には3月1日生まれにする。
	public String showFakeBirthday(int fyear) {
		String fakebirthday = "";
		boolean isuruu = false;
		if (fyear % 4 == 0) {
			if ((fyear % 100) == 0) {
				if ((fyear % 400) == 0) {
					isuruu = true;
				}
			} else {
				isuruu = true;
			}
		}
		if (!isuruu && birmonth == 1 && birdate == 29) {
			fakebirthday = "誕生日は3月1日です。\r\n";
		} else {
			fakebirthday = "誕生日は" + (birmonth + 1) + "月" + birdate + "日です。\r\n";
		}
		return fakebirthday;
	}

	// 干支を表示
	public void calcEto(int year) {
		String[] etoMod = { "申(さる)", "酉(とり)", "戌(いぬ)", "亥(い)", "子(ね)", "丑(うし)", "寅(とら)", "卯(う)", "辰(たつ)", "巳(み)",
				"午(うま)", "未(ひつじ)" };
		eto = etoMod[year % 12];
		iconimg = "/eto" + (year % 12) + ".jpg";
	}

	// 主要な出来事の時に何歳だったか
	public String incidentAge(int year) {
		String msg = "";
		StringBuilder sb = new StringBuilder(msg);

		for (int i = 0; i < 6; i++) {
			String[] incident = { "阪神・淡路大震災", "アメリカ同時多発テロ", "JR福知山線脱線事故", "東日本大震災", "韓国セウォル号沈没事故", "令和への改元" };
			int[] iyear = { 1995, 2001, 2005, 2011, 2014, 2019 };
			int[] imonth = { 0, 8, 3, 2, 3, 4 };
			int[] idate = { 17, 11, 25, 11, 16, 1 };

			int iage = 0;
			iage = iyear[i] - year;
			if (birmonth > imonth[i] || (birmonth == imonth[i] && birdate > idate[i])) {
				iage--;
			}

			String imessage = incident[i] + "の時" + iage + "歳でした。\r\n";
			if (iage < 0) {
				if (year == iyear[i]) {
					imessage = incident[i] + "の年に生まれましたが、\r\nその日にはまだ生まれていません。\r\n";
				} else {
					imessage = incident[i] + "の時はまだ生まれていません。\r\n";
				}
			}
			if (iage == 0) {
				if (birmonth == imonth[i] && birdate == idate[i]) {
					imessage = incident[i] + "の日に生まれました。\r\n";
				}
			}
			sb.append(imessage);
		}
		msg = sb.toString();
		return msg;
	}
}
