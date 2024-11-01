package generator;

import javax.swing.SwingUtilities;

// главный класс приложения
public class App
{
	// точка входа в программу
	public static void main(String[] args)
	{
		// Создание объекта типа FractalsGenerator в потоке диспетчеризации событий Swing (event dispatch thread).
		// Это гарантирует, что доступ к UI-компонентам Swing, которые не являютcя потоково-безопасными,
		// осуществляется только из одного потока.
		SwingUtilities.invokeLater(() ->
			{
				try
				{
					new FractalsGenerator().run();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			});
	}
}
