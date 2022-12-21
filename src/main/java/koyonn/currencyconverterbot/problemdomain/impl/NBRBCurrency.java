package koyonn.currencyconverterbot.problemdomain.impl;

import java.util.Objects;

import koyonn.currencyconverterbot.problemdomain.NBRBCurrencyContract;

public class NBRBCurrency implements NBRBCurrencyContract {
	// id валюты
	private final long id;
	// Аббревиатура валюты
	private final String abbreviation;
	// Базовое количество иностранной валюты, к которой
	// рассчитывается обменный курс в белорусских рублях
	private final long scale;
	// Название валюты
	private final String curName;
	// Официальный курс валюты
	private final double officialRate;

	public NBRBCurrency(long id, String abbreviation, long scale, String curName, double officialRate) {
		this.id = id;
		this.abbreviation = abbreviation;
		this.scale = scale;
		this.curName = curName;
		this.officialRate = officialRate;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public String getAbbreviation() {
		return abbreviation;
	}

	@Override
	public long getScale() {
		return scale;
	}

	@Override
	public String getCurName() {
		return curName;
	}

	@Override
	public double getOfficialRate() {
		return officialRate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, abbreviation, scale, curName, officialRate);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return true;
		}
		NBRBCurrency another = (NBRBCurrency) o;
		return id == another.id && abbreviation.equals(another.abbreviation) && scale == another.scale
		        && curName.equals(another.curName) && officialRate == another.officialRate;
	}

	@Override
	public String toString() {
		return "Currency { " + "id = "
		        + id
		        + ", abbreviation = "
		        + abbreviation
		        + ", scale = "
		        + scale
		        + ", curName = "
		        + curName
		        + ", officialRate = "
		        + officialRate
		        + "}\n";
	}
}
