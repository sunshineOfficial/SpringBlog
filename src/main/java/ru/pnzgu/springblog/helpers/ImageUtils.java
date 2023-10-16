package ru.pnzgu.springblog.helpers;

/**
 * Интерфейс для утилит изображений.
 */
public interface ImageUtils {
    /**
     * Сжимает изображение.
     * 
     * @param imageData данные изображения
     * @return сжатое изображение
     */
    byte[] compress(byte[] imageData);

    /**
     * Распаковывает изображение.
     *
     * @param imageData данные изображения
     * @return распакованное изображение
     */
    byte[] decompress(byte[] imageData);
}
