package generator.fractals;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import generator.FractalType;

// класс, реализующий фрактал треугольника Серпинского
public class SierpinskiTriangleFractal extends FractalBase
{
	private Color color1, color2, color3;

	public SierpinskiTriangleFractal()
	{
		// вызов конструктора базового класса с типов фрактала в качестве аргумента
		super(FractalType.SIERPINSKI_TRIANGLE);

		this.setNumIterations(100000); // число итераций по умолчанию

		this.color1 = this.getColor();

		// получение произвольного цвета для рисования
		Random random = new Random();
		this.color2 = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
		this.color3 = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}

	public SierpinskiTriangleFractal(int numIterations)
	{
		this(); // вызов конструктора без аргументов
		this.setNumIterations(numIterations); // установка числа итераций
	}

	// переопределение метода базового класса для отрисовки фрактала
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); // вызов метода базового класса

		// обновление параметров фрактала при изменении размера экрана
		if (this.isScreenSizeChanged())
		{
			this.setFractalHeight(this.getHeight() * 2 - this.getHeight() / 4);
			this.setFractalWidth(this.getWidth());

			int i = 0;

			double x = -1 + (Math.random() * 2);
			double y = -1 + (Math.random() * 2);

			// получение значения цвета в зависимости от числа итераций
			while (i < this.getNumIterations())
			{
				int colorIndex = (int) (Math.random() * (3));
				x = this.x(x, colorIndex);
				y = this.y(y, colorIndex);

				if (i > 19)
					this.plot(x, y, this.getFractalHeight(), this.getFractalWidth(), 0, this.getGraphics(), colorIndex);

				i++;
			}
		}

		// отрисовка фрактала, используя текущий графический контекст
		g.drawImage(this.getFractalImage(), 0, 0, this.getInitialFractalWidth(), this.getInitialFractalHeight(), null);
	}

	// вычисление нового значения координаты x
	private double x(double x, int colorIndex)
	{
		switch (colorIndex)
		{
			case 0:
				return x / 2.0;
			case 1:
				return (x + 1) / 2.0;
			case 2:
				return x / 2.0;
		}

		return -2;
	}

	// вычисление нового значения координаты y
	private double y(double y, int colorIndex)
	{
		switch (colorIndex)
		{
			case 0:
				return y / 2.0;
			case 1:
				return y / 2.0;
			case 2:
				return (y + 1) / 2.0;
		}

		return -2;
	}

	// Метод для отрисовки точки в зависимости от текущего цвета
	private void plot(double x, double y, int height, int width, int blankSpace, Graphics g, int colorIndex)
	{
		switch (colorIndex)
		{
			case 0:
				g.setColor(this.color1);
				break;
			case 1:
				g.setColor(this.color2);
				break;
			case 2:
				g.setColor(this.color3);
				break;
		}

		x = x + 1;
		y = y + 1;

		x = x * (height / 2) - height / 8;
		y = width - 1 - y * (width / 2) + height / 20;

		g.drawOval((int) (x + blankSpace), (int) (y + blankSpace), 1, 1);
	}
}
