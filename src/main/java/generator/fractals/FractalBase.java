package generator.fractals;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

import generator.FractalType;

// базовый класс для фракталов
public class FractalBase extends JPanel
{
	// логическое значение для отслеживания изменения размеров экрана
	private boolean screenSizeChanged;
	// начальная высота и ширина фрактала
	private int initialFractalHeight, initialFractalWidth;
	// текущая высота и ширина фрактала
	private int fractalWidth, fractalHeight;
	// число итераций при создании фрактала
	private int numIterations;

	private FractalType fractalType; // тип фрактала

	private Color color; //цвет фрактала

	// изображение для отрисовки фрактала
	private BufferedImage fractalImage;
	// графический контекст для работы с 2D графикой
	private Graphics2D graphics;

	/*
	 * методы доступа для закрытых полей класса
	 */
	public boolean isScreenSizeChanged()
	{
		return this.screenSizeChanged;
	}

	public int getInitialFractalWidth()
	{
		return this.initialFractalWidth;
	}

	public int getInitialFractalHeight()
	{
		return this.initialFractalHeight;
	}

	public int getFractalWidth()
	{
		return this.fractalWidth;
	}

	public void setFractalWidth(int width)
	{
		this.fractalWidth = width;
	}

	public int getFractalHeight()
	{
		return this.fractalHeight;
	}

	public void setFractalHeight(int height)
	{
		this.fractalHeight = height;
	}

	public BufferedImage getFractalImage()
	{
		return this.fractalImage;
	}

	@Override
	public Graphics2D getGraphics()
	{
		return this.graphics;
	}

	public Color getColor()
	{
		return this.color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public int getNumIterations()
	{
		return this.numIterations;
	}

	public void setNumIterations(int numIterations)
	{
		this.numIterations = numIterations;
	}

	public FractalType getFractalType()
	{
		return this.fractalType;
	}

	public void setFractalType(FractalType fractalType)
	{
		this.fractalType = fractalType;
	}

	public FractalBase(FractalType fractalType)
	{
		this.fractalType = fractalType;
		this.color = Color.WHITE; // цвет фрактала по умолчанию

		// метка с названием фрактала
		JLabel label = new JLabel();
		label.setFont(new Font("Tahoma", 0, 18));
		label.setForeground(new Color(255, 255, 255));
		label.setText(fractalType.getStringValue());

		// выравнивание метки по центру экрана
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.insets = new Insets(10, 10, 10, 10);

		// добавление метки к окну
		this.add(label, constraints);
	}

	// метод для отрисовки фрактала
	@Override
	public void paintComponent(Graphics g)
	{
		// вызов метод базового класса
		super.paintComponent(g);

		// инициализация области экрана для отрисовки фрактала
		if (this.fractalImage == null || this.initialFractalWidth != this.getWidth() || this.initialFractalHeight != this.getHeight())
		{
			this.initialFractalHeight = this.getHeight();
			this.initialFractalWidth = this.getWidth();
			this.fractalImage = new BufferedImage(this.initialFractalWidth, this.initialFractalHeight, BufferedImage.TYPE_3BYTE_BGR);
			this.graphics = this.fractalImage.createGraphics();
			this.screenSizeChanged = true;
		}
	}
}