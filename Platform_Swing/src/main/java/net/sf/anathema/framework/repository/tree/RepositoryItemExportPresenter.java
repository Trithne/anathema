package net.sf.anathema.framework.repository.tree;

import net.sf.anathema.framework.environment.Resources;
import net.sf.anathema.framework.fx.ExceptionIndicator;
import net.sf.anathema.framework.view.PrintNameFile;
import net.sf.anathema.initialization.FxApplicationFrame;
import net.sf.anathema.lib.control.ChangeListener;
import net.sf.anathema.lib.gui.action.SmartAction;
import net.sf.anathema.lib.gui.file.FileChoosingUtilities;
import net.sf.anathema.lib.message.Message;

import java.awt.Component;
import java.io.IOException;
import java.nio.file.Path;

public class RepositoryItemExportPresenter {

  private final Resources resources;
  private final IRepositoryTreeModel model;
  private final RepositoryTreeView view;
  private final AmountMessaging messaging;
  private final FileExporter fileExporter;

  public RepositoryItemExportPresenter(
          Resources resources,
          RepositoryTreeModel repositoryTreeModel,
          RepositoryTreeView treeView,
          AmountMessaging fileCountMessaging) {
    this.resources = resources;
    this.model = repositoryTreeModel;
    this.view = treeView;
    this.messaging = fileCountMessaging;
    fileExporter = new FileExporter(new RepositoryZipPathCreator(model.getRepositoryPath()), model, resources);
  }

  public void initPresentation() {
    final SmartAction action = new SmartAction(resources.getString("AnathemaCore.Tools.RepositoryView.ExportName"),
            new FileUi().getExportFileIcon()) {

      @Override
      protected void execute(Component parentComponent) {
        try {
          Path saveFile = FileChoosingUtilities.selectSaveFile(parentComponent, "Export.zip");
          if (saveFile == null) {
            return;
          }
          PrintNameFile[] printNameFiles = fileExporter.exportToZip(saveFile);
          messaging.addMessage("AnathemaCore.Tools.RepositoryView.ExportDoneMessage", printNameFiles.length);
        } catch (IOException e) {
          Message message = new Message(resources.getString("AnathemaCore.Tools.RepositoryView.FileError"), e);
          ExceptionIndicator.indicate(resources, FxApplicationFrame.getOwner(), message);
        }
      }
    };
    action.setToolTipText(resources.getString("AnathemaCore.Tools.RepositoryView.ExportToolTip"));
    view.addActionButton(action);
    model.addTreeSelectionChangeListener(new ChangeListener() {
      @Override
      public void changeOccurred() {
        action.setEnabled(model.canSelectionBeDeleted());
      }
    });
    action.setEnabled(false);
  }
}