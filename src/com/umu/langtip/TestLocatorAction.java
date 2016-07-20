package com.umu.langtip;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.PsiFile;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.project.Project;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TestLocatorAction extends AnAction {
  @Override
  public void update(AnActionEvent e) {

    e.getPresentation().setEnabled(canEnableAction(e));
  }

  boolean canEnableAction(AnActionEvent e) {
    Editor editor = e.getData(PlatformDataKeys.EDITOR);
    PsiFile file = e.getData(LangDataKeys.PSI_FILE);

    // Need the following objects.
    return editor != null &&
        file != null
        && e.getProject() != null &&
        editor.getSelectionModel().hasSelection();
  }

  public void actionPerformed(AnActionEvent actionEvent) {
    // No selection? Bail out.

    Project project = actionEvent.getData(PlatformDataKeys.PROJECT);
    final Editor editor = actionEvent.getData(PlatformDataKeys.EDITOR);
    if (editor == null || !editor.getSelectionModel().hasSelection()) {
      return;
    }

/*
    final AsyncLocatorTester tester = new AsyncLocatorTester(
        new ElementorReader()
    );

    tester.addResultsReadyListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent changeEvent) {
        String hint = "";
        if (changeEvent.getSource() instanceof Pair) {
          Pair pair = (Pair) changeEvent.getSource();
          hint = String.format("%s", pair.second);
        }

        HintManager.getInstance().showInformationHint(editor, hint);
      }
    });
*/

    String selectedText = editor.getSelectionModel().getSelectedText();

    String basePath = project.getBasePath();
    LangFile lang = new LangFile();
    basePath = lang.getWorkPath(basePath);
    String str = lang.read(basePath);
    String result = lang.find(selectedText);


    if (selectedText != null) {
      HintManager
          .getInstance()
          .showErrorHint(editor, result);
      return;
    }

    /*Messages.showMessageDialog("ddd",
            String.format("Hello, %s!\n Welcome to PubEditor.", "dddd"), "Information",
            Messages.getInformationIcon());*/

    // tester.testLocator(selectedText);
  }
}
