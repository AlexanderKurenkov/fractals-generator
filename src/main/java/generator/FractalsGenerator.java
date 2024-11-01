package generator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import generator.fractals.DragonCurveFractal;
import generator.fractals.FractalBase;
import generator.fractals.KochSnowflakeFractal;
import generator.fractals.MandelbrotSetFractal;
import generator.fractals.SierpinskiTriangleFractal;

// класс для создания пользовательского интерфейса
public class FractalsGenerator extends JFrame
{
	private JFrame frame; // окно приложения
	private Font font; // шрифт текста
	private FractalBase fractal; // ссылка на базовый класс для фракталов
	private JFileChooser jFileChooser; // окно для сохранения изображения

	// метод для запуска программы
	public void run()
	{
		// указание стиля для окна
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
		{
		}

		this.font = UIManager.getDefaults().getFont("Label.font"); // получение шрифта по умолчанию

		// создание главного окна
		this.frame = new JFrame("Генератор Фракталов");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// создание приветственного сообщения в центре окна
		JPanel panel = new JPanel(new GridBagLayout());
		JLabel centeredLabel = new JLabel("Для создания фрактала перейдите в меню Файл > Создать...");
		centeredLabel.setFont(new Font(this.font.getName(), Font.PLAIN, 15));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(centeredLabel, gbc);

		this.frame.getContentPane().add(panel);

		// получение размеров экрана
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int screenWidth = (int) toolkit.getScreenSize().getWidth();
		int screenHeight = (int) toolkit.getScreenSize().getHeight();

		// установка размеров окна равной размеру экрана
		this.frame.setSize(screenWidth, screenHeight);

		// создание окна для сохранения изображения
		this.jFileChooser = new JFileChooser();

		// создание меню главного окна приложения
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Файл");
		JMenu helpMenu = new JMenu("О программе"); // меню «О программе», содержащее вкладки «Справка», «О разработчике».

		JMenu newItem = new JMenu("Создать");
		JMenuItem saveItem = new JMenuItem("Сохранить");
		JMenuItem exitItem = new JMenuItem("Выйти");

		JMenuItem infoItem = new JMenuItem("Справка");
		JMenuItem aboutItem = new JMenuItem("О разработчике");

		JMenuItem kochSnowflakeItem = new JMenuItem("Снежинка Коха");
		JMenuItem mandelbrotSetItem = new JMenuItem("Множество Мандельброта");
		JMenuItem sierpinskiTriangleItem = new JMenuItem("Треугольник Серпинского");
		JMenuItem dragonCurveItem = new JMenuItem("Кривая дракона");

		// добавление вкладок в меню "Создать"
		newItem.add(kochSnowflakeItem);
		newItem.add(mandelbrotSetItem);
		newItem.add(sierpinskiTriangleItem);
		newItem.add(dragonCurveItem);

		// добавление вкладок в меню "Файл"
		fileMenu.add(newItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);

		// добавление вкладок в меню "О Программе"
		helpMenu.add(infoItem);
		helpMenu.add(aboutItem);

		// добавление меню "Файл" и "О Программе" на панель меню
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		// добавление панели меню к окну программы
		this.frame.setJMenuBar(menuBar);

		// обработчики событый для каждого из пунктов меню
		saveItem.addActionListener(evt -> FractalsGenerator.this.saveImageAction(evt));

		exitItem.addActionListener(e ->
			{
				int result = JOptionPane.showConfirmDialog(FractalsGenerator.this.frame, "Вы действительно хотите выйти?", "Выход", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION)
				{
					System.exit(0);
				}
			});

		// полиморфное создание экземпляров класса для каждого из фракталов
		kochSnowflakeItem.addActionListener(evt ->
			{
				FractalsGenerator.this.fractal = new KochSnowflakeFractal();
				FractalsGenerator.this.setPanel(FractalsGenerator.this.fractal);
			});

		mandelbrotSetItem.addActionListener(evt ->
			{
				FractalsGenerator.this.fractal = new MandelbrotSetFractal();
				FractalsGenerator.this.setPanel(FractalsGenerator.this.fractal);
			});
		sierpinskiTriangleItem.addActionListener(evt ->
			{
				FractalsGenerator.this.fractal = new SierpinskiTriangleFractal();
				FractalsGenerator.this.setPanel(FractalsGenerator.this.fractal);
			});
		dragonCurveItem.addActionListener(evt ->
			{
				FractalsGenerator.this.fractal = new DragonCurveFractal();
				FractalsGenerator.this.setPanel(FractalsGenerator.this.fractal);
			});

		infoItem.addActionListener(evt -> FractalsGenerator.this.showInfoFrame());
		aboutItem.addActionListener(evt -> FractalsGenerator.this.showAboutFrame());

		// выравнивание окна по центру экрана
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
	}

	// создание окна для программы
	private JFrame createFrame(String title)
	{
		JFrame frame = new JFrame(title);

		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);

		return frame;
	}

	// создание окна с информацией о программе
	private void showInfoFrame()
	{
		JFrame infoFrame = this.createFrame("Справка");

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.PAGE_AXIS));

		labelPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel titleLabel = new JLabel("Программа: \"Генератор фракталов\"");
		JLabel versionLabel = new JLabel("Версия: 1.0");
		JLabel requirementsLabel = new JLabel("Системные требования: Java 11+");

		titleLabel.setFont(new Font(this.font.getName(), Font.PLAIN, 14));
		versionLabel.setFont(new Font(this.font.getName(), Font.PLAIN, 14));
		requirementsLabel.setFont(new Font(this.font.getName(), Font.PLAIN, 14));

		labelPanel.add(titleLabel, gbc);
		labelPanel.add(versionLabel, gbc);
		labelPanel.add(requirementsLabel, gbc);

		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		requirementsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		Container contentPanel = infoFrame.getContentPane();
		contentPanel.add(labelPanel, BorderLayout.CENTER);

		infoFrame.setVisible(true);
	}

	// создание окна с информацией о разработчике
	private void showAboutFrame()
	{
		JFrame aboutFrame = this.createFrame("О Разработчике");

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.PAGE_AXIS));

		labelPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel developerLabel = new JLabel("Разработчик: студент гр. з-422П5-5 Куренков А.Ю.");
		JLabel dateLabel = new JLabel("Дата создания: 30 марта 2024г.");

		developerLabel.setFont(new Font(this.font.getName(), Font.PLAIN, 14));
		dateLabel.setFont(new Font(this.font.getName(), Font.PLAIN, 14));

		labelPanel.add(developerLabel, gbc);
		labelPanel.add(dateLabel, gbc);

		developerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		Container contentPanel = aboutFrame.getContentPane();
		contentPanel.add(labelPanel, BorderLayout.CENTER);

		aboutFrame.setVisible(true);
	}

	// метод для сохранения изображения фрактала
	public void saveImageAction(ActionEvent evt)
	{
		// отображение диалогового окна для указания сохраняемого файла
		int returnVal = this.jFileChooser.showOpenDialog(null);
		String image_name = new String();
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			image_name = this.jFileChooser.getSelectedFile().getPath();
		}

		// создание файла в формате png для сохраняемого изображения
		try
		{
			File outputfile = new File(image_name + ".png");
			ImageIO.write(this.fractal.getFractalImage(), "png", outputfile);
		} catch (IOException e)
		{
			System.out.println(e);
		}
	}

	// создание диалогового окна для указания параметров фрактала
	private void setPanel(JPanel panel)
	{
		JDialog dialog = new JDialog(this.frame, "Параметры фрактала", true);
		dialog.setSize(400, 200);
		dialog.setLocationRelativeTo(this.frame);

		Container dialogContainer = dialog.getContentPane();
		dialogContainer.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.20;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 10, 10, 10);

		JLabel label1 = new JLabel("Число итераций:");
		label1.setFont(new Font(this.font.getName(), Font.PLAIN, 14));
		dialogContainer.add(label1, c);

		c.weightx = 0.80;
		c.gridx = 1;
		JTextField inputField = new JTextField();
		inputField.setText(String.valueOf(this.fractal.getNumIterations()));
		inputField.setColumns(10);
		dialogContainer.add(inputField, c);

		c.weightx = 0.20;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10, 10, 10, 10);

		JLabel label2 = new JLabel("Цвет фрактала:");
		label2.setFont(new Font(this.font.getName(), Font.PLAIN, 14));
		dialogContainer.add(label2, c);

		c.insets = new Insets(10, 10, 10, 10);
		c.weightx = 0.60;
		c.gridx = 1;
		JLabel colorLabel = new JLabel();
		colorLabel.setOpaque(true);
		colorLabel.setBackground(this.fractal.getColor());
		dialogContainer.add(colorLabel, c);

		c.insets = new Insets(10, 10, 10, 10);
		c.weightx = 0.20;
		c.gridx = 2;

		JButton chooseColorButton = new JButton("Выбрать");

		chooseColorButton.addActionListener(arg0 ->
			{
				Color selectedColor = JColorChooser.showDialog(null, "Выбор цвета", Color.BLACK);
				if (selectedColor != null)
				{
					FractalsGenerator.this.fractal.setColor(selectedColor);
					colorLabel.setBackground(selectedColor);
				}
			});
		dialogContainer.add(chooseColorButton, c);

		JButton button = new JButton("OK");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.PAGE_END;
		c.insets = new Insets(10, 10, 10, 10);
		c.gridwidth = 1;
		c.gridy = 2;
		dialogContainer.add(button, c);

		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (this.validateInput(inputField.getText()))
				{
					int result = Integer.parseInt(inputField.getText());
					FractalsGenerator.this.fractal.setNumIterations(result);

					dialog.dispose();

					FractalsGenerator.this.frame.getContentPane().removeAll();
					FractalsGenerator.this.frame.add(panel, BorderLayout.CENTER);
					FractalsGenerator.this.frame.validate();
					FractalsGenerator.this.frame.repaint();
				} else
				{
					JOptionPane.showMessageDialog(FractalsGenerator.this.frame, "Число итераций должно быть положительным целым числом. Повторите ввод");
				}
			}

			private boolean validateInput(String inputValue)
			{
				try
				{
					int value = Integer.parseInt(inputValue);
					return value > 0;
				} catch (NumberFormatException e)
				{
					return false;
				}
			}
		});

		dialog.setVisible(true);
	}

}
