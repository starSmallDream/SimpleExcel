package cn.dream.test2;

import cn.dream.handler.module.ReadExcel;
import cn.dream.handler.module.WriteExcel;
import cn.dream.test2.entity.StudentEntity;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class WriteExcelTest {


    private static WriteExcel writeExcel;

    private static File targetFile;


    @BeforeAll
    public static void beforeAll() throws IOException, InterruptedException {

        writeExcel = WriteExcel.newInstance(new XSSFWorkbook());

        ClassPathResource template_result = new ClassPathResource("template");
        File file = template_result.getFile();

        file = new File(file, "template_result");
        if(!file.exists() && file.mkdirs()){
        }

        targetFile = new File(file, "写入Excel001.xlsx");

        prepareData();
    }

    private static List<StudentEntity> studentList = new ArrayList<>();

    /**
     * 准备数据
     */
    public static void prepareData() throws InterruptedException {

        studentList.add(
                StudentEntity.builder()
                        .uid("001").name("张三").age(25).birthdayDate(new Date())
                        .createMillisecond(System.currentTimeMillis()).isPublic(0)
                        .provinceName("北京市").cityName("朝阳区").distinctName(null)
                        .build()
        );

        Thread.sleep(1500);

        studentList.add(
                StudentEntity.builder()
                        .uid("002").name("赵四").age(22).birthdayDate(new Date())
                        .createMillisecond(System.currentTimeMillis()).isPublic(1)
                        .provinceName("北京市").cityName("东城区").distinctName(null)
                        .build()
        );

        studentList.add(
                StudentEntity.builder()
                        .uid("005").name("王五").age(25).birthdayDate(new Date())
                        .createMillisecond(System.currentTimeMillis()).isPublic(0)
                        .provinceName("北京市").cityName("朝阳区").distinctName(null)
                        .build()
        );
        studentList.add(
                StudentEntity.builder()
                        .uid("009").name("小白").age(23).birthdayDate(new Date())
                        .createMillisecond(System.currentTimeMillis()).isPublic(1)
                        .provinceName("北京市").cityName("朝阳区").distinctName(null)
                        .build()
        );
        studentList.add(
                StudentEntity.builder()
                        .uid("006").name("李四").age(21).birthdayDate(new Date())
                        .createMillisecond(System.currentTimeMillis()).isPublic(1)
                        .provinceName("郑州市").cityName("朝阳区").distinctName(null)
                        .build()
        );



    }


    @Test
    @Order(0)
    public void test1() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {


        writeExcel.createSheet("学生列表01");

        writeExcel.setSheetData(StudentEntity.class,studentList);

        writeExcel.generateHeader();

        writeExcel.generateBody();

        writeExcel.flushData();


//        FieldNameFunction<StudentTestEntity> studentTestEntityFieldNameFunction = FieldNameFunction.newInstance(StudentTestEntity.class);
//        studentTestEntityFieldNameFunction.addFieldGetMethod(StudentTestEntity::getName);
//
//        FieldNameFunction<StudentTestEntity> objectFieldNameFunction = FieldNameFunction.newInstance();
//        objectFieldNameFunction.addFieldGetMethod(StudentTestEntity::getName);


    }



    @Test
    @Order(1)
    public void writeComplete() throws IOException {
        targetFile = writeExcel.write(targetFile);
        System.out.println("准备读取----------------------------------------------");
    }

    @Test
    @Order(2)
    public void read() throws IOException, IllegalAccessException, InvalidFormatException {

        ReadExcel readExcel = ReadExcel.newInstance(WorkbookFactory.create(targetFile));

        readExcel.setSheetDataCls(StudentEntity.class);

        readExcel.toggleSheet(0);
        readExcel.readData();

        List<StudentEntity> result = readExcel.getResult();
        result.forEach(System.err::println);

    }


    @AfterAll
    public static void afterAll() throws IOException {

//        writeExcel.write(targetFile);

    }



}
