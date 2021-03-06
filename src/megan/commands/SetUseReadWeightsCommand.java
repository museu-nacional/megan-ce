/*
 *  Copyright (C) 2015 Daniel H. Huson
 *
 *  (Some files contain contributions from other authors, who are then mentioned separately.)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package megan.commands;

import jloda.gui.commands.ICheckBoxCommand;
import jloda.util.ResourceManager;
import jloda.util.parse.NexusStreamParser;
import megan.core.Document;
import megan.viewer.MainViewer;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SetUseReadWeightsCommand extends megan.importblast.commands.CommandBase implements ICheckBoxCommand {
    public boolean isSelected() {
        return getDir().getDocument().isUseWeightedReadCounts();
    }

    public String getSyntax() {
        return "set useReadWeights={true|false};";
    }

    public void apply(NexusStreamParser np) throws Exception {
        np.matchIgnoreCase("set useReadWeights=");
        boolean use = np.getBoolean();
        np.matchIgnoreCase(";");
        final Document doc = getDoc();
        doc.setUseWeightedReadCounts(use);
        doc.setDirty(true);
        doc.reloadFromConnector(doc.getParameterString());
        // need to write this back (it gets cleaned away during reload...)
        if (doc.getDataTable().getParameters() == null || doc.getDataTable().getParameters().isEmpty())
            doc.getDataTable().setParameters(doc.getParameterString());


        getDir().getMainViewer().updateData();
        getDir().getMainViewer().updateTree();
        getDir().getMainViewer().setDoReInduce(true);
        //getDir().getMainViewer().setDoReset(true);
    }

    public void actionPerformed(ActionEvent event) {
        execute("set useReadWeights=" + !getDir().getDocument().isUseWeightedReadCounts() + ";");
    }

    public boolean isApplicable() {
        return getViewer() instanceof MainViewer && getDoc().getNumberOfReads() > 0 && getDoc().getMeganFile().hasDataConnector();
    }

    public static final String NAME = "Use Read Weights for Assignments";

    public String getName() {
        return NAME;
    }

    public String getDescription() {
        return "Request that assignments displayed in main taxonomy viewer reflect read weights rather than read counts.\n" +
                "Only makes sense when reads have magnitudes or when using long read mode.";
    }

    public ImageIcon getIcon() {
        return ResourceManager.getIcon("sun/toolbarButtonGraphics/general/Preferences16.gif");
    }

    public boolean isCritical() {
        return true;
    }

    public KeyStroke getAcceleratorKey() {
        return null;
    }
}

