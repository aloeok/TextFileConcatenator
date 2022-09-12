package task.concatenator.utils.wrap;

import java.nio.file.Path;

/**
 * Класс-"обёртка" для объекта класса Path, имплементирующий интерфейс Comparable для сравнения
 * объектов класса Path по именам файлов, на которые они указывают
 */
public class PathCompFilenameWrap implements Comparable<PathCompFilenameWrap> {
	/**
	 * "Обёрнутый" path
	 */
	private final Path path;
	
	/**
	 * Имя файла, на который указывает путь path
	 */
	private final String fileName;
	
	/**
	 * Конструктор
	 */
	public PathCompFilenameWrap(Path path) {
		this.path = path;
		this.fileName = path.getFileName().toString();
	}
	
	/**
	 * Функция возвращает "обёрнутый" Path
	 */
	public Path getPath () {
		return path;
	}
	
	/**
	 * Функция получает на вход другой объект-обёртку
	 * Функция возвращает значение вызова функции fileName.compareTo(o.fileName), иными словами сравнивает
	 * имена файлов, на которые соответственно указывают сравниваемые обёрнутые Path'ы
	 */
	public int compareTo (PathCompFilenameWrap o) {
		return fileName.compareTo(o.fileName);
	}
}
