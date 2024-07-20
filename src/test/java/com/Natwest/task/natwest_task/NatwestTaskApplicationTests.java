package com.Natwest.task.natwest_task;

import com.Natwest.task.service.ReportGenerationService;
import com.Natwest.task.vo.FileType;
import com.Natwest.task.vo.ReportGenerationVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ComponentScan(basePackages = "com.Natwest.*")
class NatwestTaskApplicationTests {

	@Autowired
	private ReportGenerationService reportGenerationService;

	@Test
	void contextLoads1() {

		ReportGenerationVO reportGenerationVO = new ReportGenerationVO();
		reportGenerationVO.setId(1);
		reportGenerationVO.setInputFilePath("D:\\Natwest task\\Natwest-task\\src\\test\\junittestfile\\input_junit_test1.csv"); // One row
		reportGenerationVO.setInputExtension(FileType.CSV);
		//ref
		reportGenerationVO.setReferenceFilePath("D:\\Natwest task\\Natwest-task\\src\\test\\junittestfile\\reference_junit_test1.csv");
		reportGenerationVO.setReferenceExtension(FileType.CSV);
		//output
		reportGenerationVO.setOutputFilePath("D:\\Natwest task\\Natwest-task\\src\\test\\junittestfile\\output_junit_test1.csv");
		reportGenerationVO.setOutputExtension(FileType.CSV);
//		String expected = "fileName";
//		FileType expectedFileType = FileType.CSV;

		reportGenerationVO.setNatwestConfiguration(reportGenerationService.getConfiguration());
		reportGenerationService.generateReport(reportGenerationVO);

		// Verify the generated output file
		String expectedFilePath = "D:\\Natwest task\\Natwest-task\\src\\test\\junittestfile\\expected_junit_test1.csv";
        FileType expectedFileExtension = FileType.CSV;

		try {
            assertFileContentEquals(expectedFilePath, reportGenerationVO.getOutputFilePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


	@Test
	void contextLoads2() {

		ReportGenerationVO reportGenerationVO = new ReportGenerationVO();
		reportGenerationVO.setId(1);
		reportGenerationVO.setInputFilePath("D:\\Natwest task\\Natwest-task\\src\\test\\junittestfile\\input_junit_test2.csv"); // One row
		reportGenerationVO.setInputExtension(FileType.CSV);
		//ref
		reportGenerationVO.setReferenceFilePath("D:\\Natwest task\\Natwest-task\\src\\test\\junittestfile\\reference_junit_test2.csv");
		reportGenerationVO.setReferenceExtension(FileType.CSV);
		//output
		reportGenerationVO.setOutputFilePath("D:\\Natwest task\\Natwest-task\\src\\test\\junittestfile\\output_junit_test2.csv");
		reportGenerationVO.setOutputExtension(FileType.CSV);
//		String expected = "fileName";
//		FileType expectedFileType = FileType.CSV;

		reportGenerationVO.setNatwestConfiguration(reportGenerationService.getConfiguration());
		reportGenerationService.generateReport(reportGenerationVO);

		// Verify the generated output file
		String expectedFilePath = "D:\\Natwest task\\Natwest-task\\src\\test\\junittestfile\\expected_junit_test2.csv";
		FileType expectedFileExtension = FileType.CSV;

		try {
			assertFileContentEquals(expectedFilePath, reportGenerationVO.getOutputFilePath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private void assertFileContentEquals(String expectedFilePath, String actualFilePath) throws IOException {
		List<String> expectedLines = readLinesFromFile(expectedFilePath);
		List<String> actualLines = readLinesFromFile(actualFilePath);

		// Compare line by line
		assertEquals(expectedLines.size(), actualLines.size(), "Number of lines in files do not match");

		for (int i = 0; i < expectedLines.size(); i++) {
			assertEquals(expectedLines.get(i), actualLines.get(i), "Line " + (i + 1) + " does not match");
		}
	}

	private List<String> readLinesFromFile(String filePath) throws IOException {
		List<String> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		}
		return lines;
	}

}
