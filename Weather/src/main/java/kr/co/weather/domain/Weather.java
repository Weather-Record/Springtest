package kr.co.weather.domain;

import java.util.Date;

public class Weather {
	private int seq;
	private String baseDate;//기준일
	private String baseTime;//기준 시간
	private String t1h; //기온 ℃
	private String reh; //습도 %
	private String rn1; //1시간 강수량 - 범주 (1 mm)
	private String sky; //하늘상태 - 코드값
	private String uuu; //동서바람성분 m/s
	private String vvv; //납북바람성분 m/s
	private String pty; //강수형태 - 코드값
	private String lgt; //낙뢰 - 코드값
	private String vec; //풍향 deg
	private String wsd; //풍속 m/s
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getBaseDate() {
		return baseDate;
	}
	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}
	public String getBaseTime() {
		return baseTime;
	}
	public void setBaseTime(String baseTime) {
		this.baseTime = baseTime;
	}
	public String getT1h() {
		return t1h;
	}
	public void setT1h(String t1h) {
		this.t1h = t1h;
	}
	public String getReh() {
		return reh;
	}
	public void setReh(String reh) {
		this.reh = reh;
	}
	public String getRn1() {
		return rn1;
	}
	public void setRn1(String rn1) {
		this.rn1 = rn1;
	}
	public String getSky() {
		return sky;
	}
	public void setSky(String sky) {
		this.sky = sky;
	}
	public String getUuu() {
		return uuu;
	}
	public void setUuu(String uuu) {
		this.uuu = uuu;
	}
	public String getVvv() {
		return vvv;
	}
	public void setVvv(String vvv) {
		this.vvv = vvv;
	}
	public String getPty() {
		return pty;
	}
	public void setPty(String pty) {
		this.pty = pty;
	}
	public String getLgt() {
		return lgt;
	}
	public void setLgt(String lgt) {
		this.lgt = lgt;
	}
	public String getVec() {
		return vec;
	}
	public void setVec(String vec) {
		this.vec = vec;
	}
	public String getWsd() {
		return wsd;
	}
	public void setWsd(String wsd) {
		this.wsd = wsd;
	}
	@Override
	public String toString() {
		return "Weather [seq=" + seq + ", baseDate=" + baseDate + ", baseTime=" + baseTime + ", t1h=" + t1h + ", reh="
				+ reh + ", rn1=" + rn1 + ", sky=" + sky + ", uuu=" + uuu + ", vvv=" + vvv + ", pty=" + pty + ", lgt="
				+ lgt + ", vec=" + vec + ", wsd=" + wsd + "]";
	}
}
