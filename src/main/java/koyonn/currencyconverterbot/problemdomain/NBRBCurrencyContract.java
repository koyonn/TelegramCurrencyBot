package koyonn.currencyconverterbot.problemdomain;

public interface NBRBCurrencyContract {
    
    /**
     * Геттер id валюты
     *
     * @return id валюты
     */
    public long getId();

    /**
     * Геттер аббревиатуры валюты
     *
     * @return аббревиатура валюты
     */
    public String getAbbreviation();

    /**
     * Геттер базового количества иностранной валюты
     *
     * @return базовое количество иностранной валюты, к которой
     *         расчитывается обменный курс в белорусских рублях
     */
    public long getScale();

    /**
     * Геттер нахвания валюты
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
