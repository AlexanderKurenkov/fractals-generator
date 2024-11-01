package generator.fractals;

import java.awt.Graphics;

import generator.FractalType;

// класс, реализующий фрактал кривой Дракона
public class DragonCurveFractal extends FractalBase
{
	private String seq = "FX";
	private double scale = 3;

	public DragonCurveFractal()
	{
		// вызов конструктора базового класса с типов фрактала в качестве аргумента
		super(FractalType.DRAGON_CURVE);

		this.setNumIterations(14); // число итераций по умолчанию
	}

	public DragonCurveFractal(int numIterations)
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

			this.scale = ((double) this.getHeight() / 350.0);
			this.buildString(this.getNumIterations());
			this.drawString(this.getGraphics(), this.getHeight(), this.getWidth());
		}

		// отрисовка фрактала, используя текущий графический контекст
		g.drawImage(this.getFractalImage(), 0, 0, this.getInitialFractalWidth(), this.getInitialFractalHeight(), null);
	}

	// метод для отрисовки кривой
	public void drawString(Graphics g, int height, int width)
	{
		int angle = 90;
		int x = width / 3, y = 4 * height / 6;
		int newx = 0, newy = 0;

		for (int i = 0; i < this.seq.length(); i++)
		{
			if (this.seq.charAt(i) == 'F')
			{
				newx = x + (int) Math.round(this.scale * Math.sin(((2 * Math.PI) / 360) * angle));
				newy = y - (int) Math.round(this.scale * Math.cos(((2 * Math.PI) / 360) * angle));

				g.setColor(this.getColor());
				g.drawLine(x, y, newx, newy);

			} else if (this.seq.charAt(i) == '+')
			{
				angle += 90;
				if (angle > 180)
					angle = -(360 - angle);

			} else if (this.seq.charAt(i) == '-')
			{
				angle -= 90;
				if (angle <= -180)
					angle = (360 + angle);
			}

			x = newx;
			y = newy;
		}
	}

	// метод для замены символов в строке
	public void rewrite()
	{
		String newSeq = new String();

		// обработка каждого из символов
		for (int i = 0; i < this.seq.length(); i++)
		{
			if (this.seq.charAt(i) == 'X')
			{
				newSeq = newSeq.concat("X+YF+");
			} else if (this.seq.charAt(i) == 'Y')
			{
				newSeq = newSeq.concat("-FX-Y");
			} else
			{
				newSeq = newSeq.concat(Character.toString(this.seq.charAt(i)));
			}
		}

		this.seq = newSeq;
	}

	// метод для создания строки символов
	public void buildString(int numIteraitons)
	{
		for (int i = 0; i < numIteraitons; i++)
			this.rewrite();
	}
}
