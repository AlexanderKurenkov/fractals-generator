package generator.fractals;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import generator.FractalType;

// класс, реализующий фрактал Мандельброта
public class MandelbrotSetFractal extends FractalBase
{
	// максимальные и минимальные значения для действительной и мнимой частей
	private double minRe = -2.0;
	private double minIm = -1.0;
	private double maxRe = 1.0;;
	private double maxIm;
	// множители для действительной и мнимой частей
	private double re_factor;
	private double im_factor;

	public MandelbrotSetFractal()
	{
		super(FractalType.MANDELBROT_SET); // вызов конструктора базового класса с типов фрактала в качестве аргумента
		this.setNumIterations(16); // число итераций по умолчанию
	}

	public MandelbrotSetFractal(int numIterations)
	{

		this(); // вызов конструктора без аргументов
		this.setNumIterations(numIterations); // установка числа итераций
	}

	// переопределение метода базового класса для отрисовки фрактала
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); // вызов метода базового класса

		int height = this.getHeight();
		int width = 3 * (height / 2);
		BufferedImage image = this.getFractalImage();

		// значение для сохранения соотношения сторон (ширина пустого пространства слева и справа)
		int someNumber = (this.getWidth() - width) / 2;

		this.maxIm = (this.minIm + (this.maxRe - this.minRe) * height / width);
		this.re_factor = (this.maxRe - this.minRe) / (width - 1);
		this.im_factor = (this.maxIm - this.minIm) / (height - 1);

		for (int y = 0; y < height; y++)
		{
			double c_im = this.minIm + (y) * this.im_factor;
			for (int x = 0; x < width; x++)
			{
				double c_re = this.minRe + x * this.re_factor;

				// определение принадлежности значения к множеству Мандельброта
				int b;
				if ((b = this.belongsToSet(c_re, c_im)) < 0)
				{
					// отрисовка черного пикселя
					image.setRGB(x, y, Color.BLACK.getRGB());
				} else
				{
					// отрисовка цветного пикселя
					image.setRGB(x, y, this.getShadingColor(b).getRGB());
				}
			}
		}

		// отрисовка фрактала, используя текущий графический контекст
		g.drawImage(image, someNumber, 0, width, height, null);
	}

	// метод для определения принадлежности точки множеству Мандельброта
	private int belongsToSet(double c_re, double c_im)
	{
		double z_re = c_re;
		double z_im = c_im;

		for (int n = 0; n < this.getNumIterations(); n++)
		{
			if (this.imaginaryAbs(z_re, z_im) > 4)
				return n;

			double z_im_mid = z_im * z_im;
			z_im = 2 * z_im * z_re + c_im;
			z_re = z_re * z_re - z_im_mid + c_re;
		}

		return -1;
	}

	// метод для вычисления абсолютного значения мнимой части
	private double imaginaryAbs(double c_re, double c_im)
	{
		return c_re * c_re + c_im * c_im;
	}

	// метод для получения цвета (оттенка, насыщенности и яркости)
	// вычисляется на основе числа итераций
	private Color getShadingColor(int b)
	{
		return Color.getHSBColor(this.getColor().getRed() / (float) 100, this.getColor().getGreen() / (float) 100, (float) b / (float) this.getNumIterations());
	}
}
