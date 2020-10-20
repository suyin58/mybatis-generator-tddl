package com.toolplat.generator.gui.controller;

import com.toolplat.generator.gui.view.AlertUtil;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseFXController implements Initializable {
    private static final Logger _LOG = LoggerFactory.getLogger(BaseFXController.class);
    private static final Map<FXMLPage, SoftReference<? extends BaseFXController>> cacheNodeMap = new HashMap<>();
    private Stage primaryStage;
    private Stage dialogStage;

    public BaseFXController loadFXMLPage(String title, FXMLPage fxmlPage, boolean cache) {
        SoftReference<? extends BaseFXController> parentNodeRef = cacheNodeMap.get(fxmlPage);
        if (cache && parentNodeRef != null) {
            return parentNodeRef.get();
        }
        URL skeletonResource = Thread.currentThread().getContextClassLoader().getResource(fxmlPage.getFxml());
        FXMLLoader loader = new FXMLLoader(skeletonResource);
        Parent loginNode;
        try {
            loginNode = loader.load();
            BaseFXController controller = loader.getController();
            dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(getPrimaryStage());
            dialogStage.setScene(new Scene(loginNode));
            dialogStage.setMaximized(false);
            dialogStage.setResizable(false);
            dialogStage.show();
            controller.setDialogStage(dialogStage);
            // put into cache map
            SoftReference<BaseFXController> softReference = new SoftReference<>(controller);
            cacheNodeMap.put(fxmlPage, softReference);

            return controller;
        } catch (IOException e) {
            AlertUtil.showErrorAlert(e.getMessage(), e);
        }
        return null;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void showDialogStage() {
        if (dialogStage != null) {
            dialogStage.show();
        }
    }

    public void closeDialogStage() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

}
