package generator.fractals;

import java.awt.Color;
import java.awt.Graphics;

import generator.FractalType;

// класс, реализующий фрактал кривой Коха
public class KochSnowflakeFractal extends FractalBase
{
	public KochSnowflakeFractal()
	{
		// вызов конструктора базового класса с типов фрактала в качестве аргумента
		super(FractalType.KOCH_SNOWFLAKE);
		//поколение кривой Коха (число итераций) по умолчанию
		this.setNumIterations(3);
	}

	public KochSnowflakeFractal(int numIterations, Color color)
	{
		// вызов конструктора без аргументов
		this();
		//поколение кривой Коха (число итераций)
		this.setNumIterations(numIterations);
		// установка значения цвета для рисования
		this.setColor(color);
	}

	// переопределение метода базового класса для отрисовки фрактала
	@Override
	public void paintComponent(Graphics g)
	{
		// вызов метода базового класса
		super.paintComponent(g);

		// обновление параметров фрактала при изменении размера экрана
		if (this.isScreenSizeChanged())
		{
			this.setFractalHeight(this.getHeight() - this.getHeight() / 5);
			this.setFractalWidth(this.getWidth());
			int xStart = this.getFractalWidth() / 3;
			this.drawSnowflake(this.getGraphics(), this.getNumIterations(), this.getColor(), xStart + 20, this.getFractalHeight() - 20, xStart + this.getFractalHeight() - 20, this.getFractalHeight() - 20);
			this.drawSnowflake(this.getGraphics(), this.getNumIterations(), this.getColor(), xStart + this.getFractalHeight() - 20, this.getFractalHeight() - 20, xStart + this.getFractalHeight() / 2, 20);
			this.drawSnowflake(this.getGraphics(), this.getNumIterations(), this.getColor(), xStart + this.getFractalHeight() / 2, 20, xStart + 20, this.getFractalHeight() - 20);
		}

		// отрисовка фрактала, используя текущий графический контекст
		g.drawImage(this.getFractalImage(), 0, 0, this.getInitialFractalWidth(), this.getInitialFractalHeight(), null);
	}

	// метод для отрисовки фрактальной кривой
	private void drawSnowflake(Graphics g, int numIterations, Color color, int x1, int y1, int x5, int y5)
	{
		int deltaX, deltaY, x2, y2, x3, y3, x4, y4;

		if (numIterations == 0)
		{
			g.setColor(color);
			g.drawLine(x1, y1, x5, y5);
		} else
		{
			deltaX = x5 - x1;
			deltaY = y5 - y1;

			x2 = x1 + deltaX / 3;
			y2 = y1 + deltaY / 3;

			x3 = (int) (0.5 * (x1 + x5) + Math.sqrt(3) * (y1 - y5) / 6);
			y3 = (int) (0.5 * (y1 + y5) + Math.sqrt(3) * (x5 - x1) / 6);

			x4 = x1 + 2 * deltaX / 3;
			y4 = y1 + 2 * deltaY / 3;

			this.drawSnowflake(g, numIterations - 1, this.getColor(), x1, y1, x2, y2);
			this.drawSnowflake(g, numIterations - 1, this.getColor(), x2, y2, x3, y3);
			this.drawSnowflake(g, numIterations - 1, this.getColor(), x3, y3, x4, y4);
			this.drawSnowflake(g, numIterations - 1, this.getColor(), x4, y4, x5, y5);
		}
	}
}
