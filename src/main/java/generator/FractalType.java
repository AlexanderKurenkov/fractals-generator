package generator;

// объявление перечисления FractalType
public enum FractalType
{
	// константы перечисления
	KOCH_SNOWFLAKE("Снежинка Коха"), MANDELBROT_SET("Множество Мандельброта"), DRAGON_CURVE("Кривая дракона"), SIERPINSKI_TRIANGLE("Треугольник Серпинского");

	// закрытое поле для строкового значения фрактала
	private String stringValue;

	// приватный конструктов класса для установки строкового значения
	private FractalType(String stringValue)
	{
		this.stringValue = stringValue;
	}

	// метод-доступа для получения строкового значения фрактал
	public String getStringValue()
	{
		return this.stringValue;
	}
}