package cn.dream.test;

import cn.dream.handler.module.ReadExcel;
import cn.dream.handler.module.WriteExcel;
import cn.dream.test.entity.StudentInfoEntity;
import cn.dream.util.DateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaohuichao
 * @createdDate 2021/9/21 12:12
 */
@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class WriteAndReadTest {


    private static List<StudentInfoEntity> studentTestEntityList = new ArrayList<>();

    private static ClassPathResource classPathResource;

    private static File writeOutputFile;

    private static WriteExcel writeExcel;


    @BeforeAll
    public static void init() throws IOException {
        classPathResource = new ClassPathResource("template");

        File file = classPathResource.getFile();

        file = new File(file, WriteAndReadTest.class.getSimpleName());
        boolean mkdirDire = !file.exists() && file.mkdirs();

        writeOutputFile = new File(file, "writeExcel结果.xlsx");

        writeExcel = WriteExcel.newInstance(new XSSFWorkbook());

        initData();
    }

    /**
     * 初始化数据
     */
    public static void initData(){
        StudentInfoEntity studentTestEntity = new StudentInfoEntity();
        studentTestEntity.setUid("001");
        studentTestEntity.setUserName("张三01");
        studentTestEntity.setSex('男');
        studentTestEntity.setAge(27);
        studentTestEntity.setBirthday(DateUtils.parseDate("2015-02-03 15:55:00","yyyy-MM-dd HH:mm:ss"));
        studentTestEntity.setAuditStatus(0);
        studentTestEntity.setIsPublic(1);
        studentTestEntity.setRecordDate("2017-02-03 15:55:00");
        studentTestEntity.setCreateBy(2);
        studentTestEntity.setCreateName("admin管理员");
        studentTestEntityList.add(studentTestEntity);


        studentTestEntity = new StudentInfoEntity();
        studentTestEntity.setUid("002");
        studentTestEntity.setUserName("张三02");
        studentTestEntity.setSex('女');
        studentTestEntity.setAge(25);
        studentTestEntity.setBirthday(DateUtils.parseDate("2011-02-03 15:55:00","yyyy-MM-dd HH:mm:ss"));
        studentTestEntity.setAuditStatus(2);
        studentTestEntity.setIsPublic(2);
        studentTestEntity.setRecordDate("2018-02-03 15:55:00");
        studentTestEntity.setCreateBy(2);
        studentTestEntity.setCreateName("admin管理员");
        studentTestEntityList.add(studentTestEntity);


        studentTestEntity = new StudentInfoEntity();
        studentTestEntity.setUid("001");
        studentTestEntity.setUserName("张三01");
        studentTestEntity.setSex('男');
        studentTestEntity.setAge(22);
        studentTestEntity.setBirthday(DateUtils.parseDate("2014-02-03 15:55:00","yyyy-MM-dd HH:mm:ss"));
        studentTestEntity.setAuditStatus(0);
        studentTestEntity.setIsPublic(1);
        studentTestEntity.setRecordDate("2019-02-03 15:55:00");
        studentTestEntity.setCreateBy(2);
        studentTestEntity.setCreateName("admin管理员");
        studentTestEntityList.add(studentTestEntity);

    }



    @Test
    @Order(0)
    public void write(){

        writeExcel.createSheet("我是一个测试write的Sheet");

        writeExcel.setSheetData(StudentInfoEntity.class,studentTestEntityList);

        writeExcel.generateHeader();

        writeExcel.generateBody();
    }


    @Test
    @Order(1)
    public void writeComplete() throws IOException {
        writeOutputFile = writeExcel.write(writeOutputFile);
        System.out.println("准备读取----------------------------------------------");
    }

    @Test
    @Order(2)
    public void read() throws IOException, IllegalAccessException, InvalidFormatException {

        ReadExcel readExcel = ReadExcel.newInstance(WorkbookFactory.create(writeOutputFile));

        readExcel.setSheetDataCls(StudentInfoEntity.class);

        readExcel.toggleSheet(0);
        readExcel.readData();

        List<StudentInfoEntity> result = readExcel.getResult();
        result.forEach(System.err::println);

    }


    /**
     * 最终写入到指定文件
     * @throws IOException
     */
    @AfterAll
    public static void after() throws IOException {

    }


}
