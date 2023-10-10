package koyonn.currencyconverterbot.problemdomain;

public interface NBRBCurrencyContract {

	public long getId();

	public String getAbbreviation();

	/**
	 * Геттер базового количества иностранной валюты
	 *
	 * @return базовое количество иностранной валюты, к которой рассчитывается обменный курс в белорусских рублях
	 */
	public long getScale();

	/**
	 * Геттер названия валюты
	 *
	 * @return название валюты
	 */
	public String getCurName();

	/**
	 * Геттер официального курса валюты к белорусскому рублю
	 *
	 * @return курс валюты
	 */
	public double getOfficialRate();
}
