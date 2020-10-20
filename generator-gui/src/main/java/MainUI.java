import com.dingtalk.generator.gui.controller.MainUIController;
import com.dingtalk.generator.gui.util.ConfigHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 这是本软件的主入口,要运行本软件请直接运行本类就可以了,不用传入任何参数
 * 初始代码源于：https://github.com/zouzg/mybatis-generator-gui
 * 本软件要求jkd版本大于1.8.0.40
 */
public class MainUI extends Application {

	private static final Logger _LOG = LoggerFactory.getLogger(MainUI.class);

	@Override
	public void start(Stage primaryStage) throws Exception {
		ConfigHelper.createEmptyFiles();
		URL url = Thread.currentThread().getContextClassLoader().getResource("fxml/MainUI.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(url);
		Parent root = fxmlLoader.load();
		primaryStage.setResizable(true);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

		MainUIController controller = fxmlLoader.getController();
		controller.setPrimaryStage(primaryStage);
	}

	public static void main(String[] args) {
		 String content = "[\"root\",{},[\"p\",{},[\"span\",{\"data-type\":\"text\"},[\"span\",{\"data-type\":\"leaf\"},\"\"]],[\"img\",{\"id\":\"cawicb\",\"name\":\"\",\"src\":\"https://static.dingtalk.com/media/lALPD1lNN9bytevNIobNCYc_2439_8838.png_620x10000q90.jpg\"},[\"span\",{\"data-type\":\"text\"},[\"span\",{\"data-type\":\"leaf\"},\"\"]]],[\"span\",{\"data-type\":\"text\"},[\"span\",{\"data-type\":\"leaf\"},\" \"]]]]";
        String regex;
        regex = "\"src\":\"(.*?)\"";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(content);
        while (ma.find())
        {
           System.out.println(ma.group());
        } 
		launch(args);
	}

}
