package com.orangehrm.utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class ScreenshotUtil {

    /**
     * Captures screenshot and returns the path where it is saved.
     *
     * @param driver   WebDriver instance
     * @param testName Name of the test method
     * @return String Path of the screenshot
     * @throws IOException if file save fails
     */
    public static String captureScreenshot(WebDriver driver, String testName) throws IOException {
        // Get timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Create screenshot folder if it doesn't exist
        String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
        File dir = new File(screenshotDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Create full path with filename
        String screenshotPath = screenshotDir + testName + "_" + timestamp + ".png";

        // Capture screenshot
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(screenshotPath);
        FileUtils.copyFile(srcFile, destFile);

        return screenshotPath;
    }
}
