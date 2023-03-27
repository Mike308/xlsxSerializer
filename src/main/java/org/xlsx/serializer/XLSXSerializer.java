package org.xlsx.serializer;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xlsx.serializer.annotation.OmitField;
import org.xlsx.serializer.annotation.XLSXProperty;
import org.xlsx.serializer.model.ValueField;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XLSXSerializer {
    private static <T> Stream<String> getHeaderStream(Class<T> c) {
        return Arrays.stream(c.getDeclaredFields()).filter(field -> !field.isAnnotationPresent(OmitField.class)).map(field -> getHeader(field));
    }

    private static String getHeader(Field field) {
        if (field.isAnnotationPresent(XLSXProperty.class))
            return field.getAnnotation(XLSXProperty.class).name() == null ? field.getName() : field.getAnnotation(XLSXProperty.class).name();
        else return field.getName();
    }

    private static boolean fieldFilter(Field field, List<String> fieldNames) {
        return fieldNames.stream().anyMatch(s -> s.equals(getHeader(field)));
    }

    private static <T> List<String> getHeader(Class<T> c) {
        return getHeaderStream(c).collect(Collectors.toList());
    }

    private static <T> List<String> getHeader(Class<T> c, List<String> fieldNames) {
        return getHeaderStream(c).filter(fieldNames::contains).sorted(Comparator.comparingInt(fieldNames::indexOf)).collect(Collectors.toList());
    }

    private static <T> List<ValueField> getValues(T object, List<String> fieldNames) {
        AtomicInteger i = new AtomicInteger(0);
        return Arrays.stream(object.getClass().getDeclaredFields()).filter(field -> fieldFilter(field, fieldNames))
                .sorted(Comparator.comparingInt(o -> fieldNames.indexOf(getHeader(o))))
                .filter(field -> !field.isAnnotationPresent(OmitField.class)).map(field -> {
                    try {
                        field.setAccessible(true);
                        return getValueField(object, i, field);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }

    private static <T> ValueField getValueField(T object, AtomicInteger i, Field field) throws IllegalAccessException {
        if (field.getType().isArray() && field.get(object) instanceof String[])
            return new ValueField(i.getAndIncrement(), field.get(object) == null ? "" : String.join(",", (String[]) field.get(object)));
        return new ValueField(i.getAndIncrement(), field.get(object) == null ? "" : field.get(object).toString());
    }

    private static <T> List<ValueField> getValues(T object) {
        AtomicInteger i = new AtomicInteger(0);
        return Arrays.stream(object.getClass().getDeclaredFields()).filter(field -> !field.isAnnotationPresent(OmitField.class)).map(field -> {
            try {
                field.setAccessible(true);
                return getValueField(object, i, field);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }


    @SneakyThrows
    public static <T> Workbook serialize(List<T> objects, Class<T> c) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        List<String> header = getHeader(c);
        AtomicInteger columnIndex = new AtomicInteger(0);
        Row headerRow = sheet.createRow(0);
        header.forEach(h -> {
            Cell headerCell = headerRow.createCell(columnIndex.get());
            headerCell.setCellValue(h);
            columnIndex.getAndIncrement();
        });
        AtomicInteger rowIndex = new AtomicInteger(1);
        objects.forEach(t -> {
            List<ValueField> values = getValues(t);
            Row valueRow = sheet.createRow(rowIndex.get());
            values.forEach(excelFileValue -> {
                Cell cellValue = valueRow.createCell(excelFileValue.getColumnIndex());
                cellValue.setCellValue(excelFileValue.getValue());
            });
            rowIndex.getAndIncrement();
        });
        return workbook;
    }

    @SneakyThrows
    public static <T> Workbook serialize(List<T> objects, Class<T> c, List<String> fieldNames) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        List<String> header = getHeader(c, fieldNames);
        AtomicInteger columnIndex = new AtomicInteger(0);
        Row headerRow = sheet.createRow(0);
        header.forEach(h -> {
            Cell headerCell = headerRow.createCell(columnIndex.get());
            headerCell.setCellValue(h);
            columnIndex.getAndIncrement();
        });
        AtomicInteger rowIndex = new AtomicInteger(1);
        objects.forEach(t -> {
            List<ValueField> values = getValues(t, fieldNames);
            Row valueRow = sheet.createRow(rowIndex.get());
            values.forEach(excelFileValue -> {
                Cell cellValue = valueRow.createCell(excelFileValue.getColumnIndex());
                cellValue.setCellValue(excelFileValue.getValue());
            });
            rowIndex.getAndIncrement();
        });
        return workbook;
    }

}
